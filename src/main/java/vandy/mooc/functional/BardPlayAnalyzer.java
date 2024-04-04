package vandy.mooc.functional;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vandy.mooc.functional.ActiveObject.ActiveObjectArray;
import static vandy.mooc.functional.ExceptionUtils.rethrowSupplier;
import static vandy.mooc.functional.OrdinalSuffix.getOrdinalSuffix;

/**
 * This program implements an "embarrassingly parallel" application
 * that computes the Flesch-Kincaid grade level score for each play
 * written by William Shakespeare.  It demonstrates the use of modern
 * Java functional programming features (such as lambda expressions,
 * method references, and functional interfaces) in conjunction with
 * an {@link Array} of {@link ActiveObject} objects, each of which
 * computes Flesch-Kincaid grade level score for a different
 * Shakespeare play concurrently.
 */
@SuppressWarnings({"JavadocBlankLines", "RegExpRepeatedSpace", "RegExpRedundantEscape"})
public class BardPlayAnalyzer
       implements Runnable {
    /**
     * A {@link Map} that associates the titles (key) of Shakespeare
     * plays with their content (value).
     */
    private static final Map<String, String> sBardMap = FileUtils
        .loadMapFromFolder("plays", ".txt");

    /**
     * Controls whether to print debugging output or not.
     */
    private static boolean sDebug;

    /*
     * The following fields are used to strip out non-essential
     * portions of Shakespeare's plays to make the Fresh-Kincaid grade
     * level score computations more accurate.
     */

    /**
     * Act and scene headers, stage directions, and line numbers
     * to remove.
     */
    private static final String sNON_ESSENTIAL_PORTIONS_REGEX =
        "(?i)(ACT [IVX]+\\.|Scene [IVX]+\\.|\\[.*?\\]|\\d+\\.|SCENE\\.)";

    /**
     * Regular expression used to split the input string into words.
     */
    private static final String sWORD_REGEX =
        "^  [A-Za-z]+\\.";

    /**
     * End of line regex.
     */
    public static final String sEOL_REGEX = "[\\r\\n]+";

    /**
     * This is the main entry point into the program.
     */
    static public void main(String[] args) {
        System.out.println("Starting Concurrent BardPlayAnalyzer");

        // Run the Flesch-Kincaid grade level analysis a first time
        // concurrently to warm up the carrier threads.
        new BardPlayAnalyzer().run();

        // Enable debugging and run the analysis a second time while
        // timing the results.
        sDebug = true;
        RunTimer
            // Record the time needed to run the Flesch-Kincaid
            // analysis on all plays by William Shakespeare.
            .timeRun(() ->
                     // Run the Flesch-Kincaid grade level score
                     // analysis concurrently.
                     new BardPlayAnalyzer().run(),
                     "Concurrent BardPlayAnalyzer");

        // Display the timing results.
        display(RunTimer.getTimingResults());

        System.out.println("Ending Concurrent BardPlayAnalyzer");
    }

    /**
     * Runnable entry point that calls helper method to run
     * the Flesch-Kincaid grade level score analysis concurrently.
     */
    @Override
    public void run() {
        // Call helper method to run the Flesch-Kincaid grade level
        // score analysis (concurrently) and obtain the results.
        var results = runAndReturnResults();

        // Sort and print the results.
        sortAndPrintResults(results);
    }

    /**
     * Run the Flesch-Kincaid grade level score analysis concurrently
     * and return an {@link Array} containing the results.
     */
    public Array<String> runAndReturnResults() {
       // Use the factory method defined below to create an Array of
        // ActiveObject objects that each run the processInput()
        // method reference. Next, call another helper method to start
        // all these ActiveObject objects in this Array. Finally, call
        // yet another helper method to process all the ActiveObject
        // objects concurrently after all the Bard Map entries are
        // processed.
        // Create and start ActiveObjects
        var activeObjects = makeActiveObjects();
        startActiveObjects(activeObjects);

        // Process all ActiveObjects and return the results
        return processActiveObjects(activeObjects);
    }

    /**
     * @return An {@link ActiveObjectArray} containing {@link ActiveObject}
     *         objects
     */
    protected ActiveObjectArray<String, String, Double> makeActiveObjects() {
        // Call the ActiveObject.makeActiveObjects() factory method to
        // create an Array of ActiveObject objects that run the
        // processInput() method reference on each entry in the
        // BardMap.
        return ActiveObject.makeActiveObjects(this::processInput, sBardMap);
    }

    /**
     * Start all the {@link ActiveObject} objects in the provided
     * {@link Array}.
     *
     * @param activeObjects The {@link Array} of {@link ActiveObject}
     *                      objects to start
     */
    protected void startActiveObjects
        (ActiveObjectArray<String, String, Double> activeObjects) {
        // Iterate through the Array of ActiveObject objects and start
        // each one via the ActiveObject.start() method reference.

        activeObjects.forEach(ActiveObject::start);
    }

    /**
     * Process all the {@link ActiveObject} objects and return the
     * results.
     *
     * @param activeObjects The {@link Array} of started {@link
     *                      ActiveObject} objects to process
     * @return An {@link Array} of {@link String} objects that contain
     *         the results of processing all the {@link ActiveObject}
     *         objects
     */
    protected Array<String> processActiveObjects
        (ActiveObjectArray<String, String, Double> activeObjects) {
        // Create a new Array to contain the results of processing all
        // the ActiveObject objects.
        var results = new Array<String>();

        // Iterate through the activeObjects parameter and (1) call
        // the get() method on each ActiveObject in the Array to
        // obtain the Flesch-Kincaid grade level score for each
        // Shakespeare play and (2) call the makeStringResult() helper
        // method to create a formatted result and add it to the end
        // of the results Array.
        activeObjects.forEach(activeObject -> {
            try {
                var gradeLevelScore = activeObject.get();
                var result = makeStringResult(activeObject, gradeLevelScore);
                results.add(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        // Return the Array
        return results;
    }

    /*
     * The remaining method implementations are provided for you and
     * are similar/identical to the same methods in Assignment 2.
     */

    /**
     * This method runs in a background {@link ActiveObject} and
     * processes the {@code entry} to compute the Flesch-Kincaid grade
     * level score.
     *
     * @param entry The Bard {@link Map.Entry} to process
     * @return The Flesch-Kincaid grade level score for the play
     */
    protected Double processInput(Map.Entry<String, String> entry) {
        // Get the play contents.
        var play = entry.getValue();

        // Strip out the non-essential portions of play.
        var strippedPlay = stripNonEssentialPortions(play);

        // Calculate the Flesch-Kincaid grade level score for a play
        // and return that value.
        return FleschKincaidGradeLevelCalculator.calculate(strippedPlay);
    }

    /**
     * Print the results of the Flesch-Kincaid grade level score
     * analysis in sorted order.
     *
     * @param results The results to print
     */
    private void sortAndPrintResults(Array<String> results) {
        // Convert the Array to a List.
        var list = results.asList();

        // Sort the results.
        list.sort(Collections.reverseOrder());

        list
            // Print the results of the calculation.
            .forEach(BardPlayAnalyzer::display);
    }

    /**
     * Make a string that contains the grade level score for the
     * provided {@code activeObject}.
     *
     * @param activeObject The {@link ActiveObject} whose grade level
     *                     score is being printed
     * @param gradeLevelScore The grade level score for the provided
     *                     {@code activeObject}
     * @return A {@link String} containing the grade level score for
     *         the provided {@code activeObject}
     */
    @NotNull
    private static String makeStringResult
        (ActiveObject<Map.Entry<String, String>, Double> activeObject,
         Double gradeLevelScore) {
        // Format the grade level score as a string with two decimal
        // places, append the ordinal suffix (e.g., "1st", "2nd",
        // "3rd", etc.) of the score to indicate the grade level,
        // append the title for which the score was calculated, and
        // then return the formatted String.
        return String.format("%.2f", gradeLevelScore)
            + " ("
            + getOrdinalSuffix(gradeLevelScore)
            + " grade) is the score for "
            + activeObject.getParams().getKey();
    }

    /**
     * Strip non-essential portions of a {@code play}.
     *
     * @param play The play to strip
     * @return A play stripped of non-essential portions
     */
    public String stripNonEssentialPortions(String play) {
        // Remove act and scene headers, stage directions, and
        // line numbers.
        String strippedPlay = play
            .replaceAll(sNON_ESSENTIAL_PORTIONS_REGEX,
                        "");

        // Remove character names, keeping only their lines.
        Pattern pattern = Pattern
            .compile(sWORD_REGEX,
                     Pattern.MULTILINE);

        // Create a Matcher for the pattern.
        Matcher matcher = pattern
            .matcher(strippedPlay);

        // Replace everything that matches with "".
        strippedPlay = matcher.replaceAll("");

        // Remove excessive line breaks.
        strippedPlay = strippedPlay
            .replaceAll(sEOL_REGEX,
                        System.lineSeparator());

        // Remove leading and trailing spaces.
        return strippedPlay.trim();
    }


    /**
     * Display the {@link String} to the output.
     *
     * @param string The {@link String} to display
     */
    private static void display(String string) {
        if (sDebug) {
            System.out.println("["
                               + Thread.currentThread().getId()
                               + "] "
                               + string);
        }
    }
}

