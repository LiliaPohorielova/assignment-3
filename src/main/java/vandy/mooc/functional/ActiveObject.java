package vandy.mooc.functional;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class demonstrates how to apply a variant of the <A
 * HREF="https://en.wikipedia.org/wiki/Active_object">Active Object
 * pattern</A> using modern Java features that call a generic {@link
 * Function} with a generic param to perform a computation
 * asynchronously.  It implements the {@link Future} interface to
 * enable clients to check if the computation is complete, wait for
 * its completion, and retrieve the result of the computation.
 */

@SuppressWarnings("SameParameterValue")
public class ActiveObject<T, R>
    implements Future<R> {
    /**
     * The closure below updates this private field to store the
     * results of the {@link Function} applied to the param {@code n}.
     */
    R mResult;

    /**
     * This field stores the {@link FutureTask} that wraps the virtual
     * {@link Thread} used to perform the {@link Function}.
     */
    RunnableFuture<R> mRunnableFuture;

    /**
     * Store the virtual {@link Thread} object used to run a
     * {@link Function} in the background.
     */
    Thread mThread;

    /**
     * Store the params passed to the constructor.
     */
    T mParams;

    /**
     * The constructor creates a virtual {@link Thread} closure that
     * applies the {@link Function} to the parameter {@code params}.
     *
     * @param function The function to apply to the param {@code
     *                 params}
     * @param params   The params to apply the function to
     */
    public ActiveObject(Function<T, R> function,
                        T params) {
        // Create a virtual Thread closure and assign it to
        // mRunnableFuture.
        mRunnableFuture = makeThreadClosure(function, params);
    }

    /**
     * This factory method creates a closure that will run in a
     * background {@link Thread} and apply the {@link Function}.
     *
     * @param function The {@link Function} to apply to the param
     *                 {@code params}
     * @param params   The params to apply the {@link Function} to
     * @return A {@link Thread} that runs in the background and
     * applies the {@link Function} to the param {@code params}
     */
    protected RunnableFuture<R> makeThreadClosure(Function<T, R> function,
                                                  T params) {
        mParams = params;

        // Create a FutureTask containing closure that applies the
        // 'function' to the 'params'.
        RunnableFuture<R> runnableFuture = new FutureTask<>(() -> function.apply(params));

        // Create a new unstarted virtual Thread that will run
        // mRunnableFuture in the background after it's started.
        mThread = Thread.ofVirtual().unstarted(runnableFuture);

        // Return runnableFuture.
        return runnableFuture;
    }

    /**
     * Start the virtual {@link Thread} that runs the {@link Function}.
     */
    public void start() {
        // Start the virtual Thread.
        mThread.start();
    }

    /**
     * This helper class provides a wrapper for an {@link Array} of
     * {@link ActiveObject} objects.
     */
    public static class ActiveObjectArray<K, V, R>
        extends Array<ActiveObject<Map.Entry<K, V>, R>> {
    }

    /**
     * This factory method creates and returns an {@link Array} of
     * unstarted Java virtual {@link ActiveObject} objects that run the
     * {@code task} associated with each {@link Map.Entry} in the
     * {@code inputMap}.
     *
     * @param inputMap A {@link Map} of input data
     * @param task     {@link Function} to run in each {@link ActiveObject}
     * @return An {@link Array} of unstarted Java virtual {@link ActiveObject}
     * objects that run the {@code task} associated with each
     * {@link Map.Entry} in the {@code inputMap}
     */
    public static <K, V, R> ActiveObjectArray<K, V, R> makeActiveObjects
    (Function<Map.Entry<K, V>, R> task,
     Map<K, V> inputMap) {
        ActiveObjectArray<K, V, R> activeObjects = new ActiveObjectArray<>();
        inputMap.forEach((key, value) -> activeObjects.add(new ActiveObject<>(task, Map.entry(key, value))));
        return activeObjects;
    }

    /*
     * The following method implementations are provided for you.
     */

    /**
     * @return The params passed to the constructor
     */
    public T getParams() {
        return mParams;
    }

    /**
     * This method cancels the {@link Thread} that is running the
     * {@link Function}.
     *
     * @param mayInterruptIfRunning {@code true} if the thread
     *                              executing this task should be
     *                              interrupted (if the thread is
     *                              known to the implementation);
     *                              otherwise, in-progress tasks are
     *                              allowed to complete
     * @return {@code true} if the task was successfully canceled,
     * else false
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return mRunnableFuture.cancel(mayInterruptIfRunning);
    }

    /**
     * This method returns true if the {@link Function} has been
     * canceled, else false.
     *
     * @return true if the {@link Function}, else false
     */
    @Override
    public boolean isCancelled() {
        return mRunnableFuture.isCancelled();
    }

    /**
     * This method returns true if the {@link Function} has completed.
     *
     * @return true if the {@link Function} has completed, else false
     */
    @Override
    public boolean isDone() {
        return mRunnableFuture.isDone();
    }

    /**
     * This method returns the result of the {@link Function} or null.
     *
     * @return The result of the {@link Function} or null.
     * @throws InterruptedException If the Thread is interrupted
     * @throws ExecutionException   If the {@link Function} failed
     */
    public R get()
        throws InterruptedException, ExecutionException {
        return mRunnableFuture.get();
    }

    /**
     * This method returns the result of the {@link Function} or null
     * if the {@link Function} does not complete before the timeout.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return The result of the {@link Function} or null if the
     * timeout expires before the result is available
     * @throws InterruptedException If the Thread is interrupted
     * @throws ExecutionException   If the {@link Function} failed
     * @throws TimeoutException     If the {@link Function} does not
     *                              complete
     */
    @Override
    public R get(long timeout, @NotNull TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        return mRunnableFuture.get(timeout, unit);
    }

    /**
     * @return the underlying {@link Thread}
     */
    public Thread getThread() {
        return mThread;
    }
}
