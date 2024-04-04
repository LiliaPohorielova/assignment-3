package vandy.mooc.functional;

import io.magnum.autograder.junit.Rubric;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class BardPlayAnalyzerTests {
    @Rule
    public Timeout timeout = new Timeout(3, TimeUnit.SECONDS);

    @Test
    @Rubric(
        value = "testActiveObjectConstructor",
        goal = "To ensure that the ActiveObject constructor initializes the object correctly.",
        points = 1.0,
        reference = "ActiveObject.java"
    )
    public void testActiveObjectConstructor() throws Exception {
        var task = new ActiveObject<>(x -> x + 1, 1);
        assertTrue(task.getThread().isVirtual());
        assertSame(task.getThread().getState(), Thread.State.NEW);
        assertFalse(task.isDone() || task.isCancelled());
        assertNotNull(task);
        task.start();
        var result = task.get();
        assertEquals(2, result.intValue());
    }

    @Test
    @Rubric(
        value = "testMakeActiveObjectThreadClosure",
        goal = "To ensure that the makeThreadClosure method creates a runnable closure correctly " +
            "and performs the task as expected.",
        points = 1.0,
        reference = "ActiveObject.java"
    )
    public void testActiveObjectMakeThreadClosure() throws Exception {
        var task = new ActiveObject<>(x -> x + 1, 1);
        assertTrue(task.getThread().isVirtual());
        assertSame(task.getThread().getState(), Thread.State.NEW);
        assertFalse(task.isDone() || task.isCancelled());
        var runnable = task.makeThreadClosure(x -> x + 1, 1);
        assertNotNull(runnable);
        assertSame(task.getThread().getState(), Thread.State.NEW);
        assertFalse(runnable.isDone() || task.isCancelled());
        runnable.run();
        var result = runnable.get();
        assertEquals(2, result.intValue());
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerStart",
        goal = "To ensure that the start method properly starts the ActiveObject's thread.",
        points = 1.0,
        reference = "ActiveObject.java"
    )
    public void testBardAnalyzerStart() {
        var task = new ActiveObject<>(x -> x + 1, 1);
        task.mThread = Thread
            .ofVirtual()
            .unstarted(new FutureTask<>(() -> 9));
        assertSame(task.getThread().getState(), Thread.State.NEW);
        task.start();
        assertSame(task.getThread().getState(), Thread.State.RUNNABLE);
    }


    @Test
    @Rubric(
        value = "testBardAnalyzerMakeActiveObjects",
        goal = "To ensure that the makeActiveObjects method correctly creates ActiveObjects based" +
            " on the input map and task function.",
        points = 1.0,
        reference = "ActiveObject.java"
    )
    public void testMakeActiveObjects() throws Exception {
        var map = new HashMap<String, Integer>();
        map.put("k1", 1);
        map.put("k2", 2);
        var list = ActiveObject.makeActiveObjects(x -> x.getValue() + 1, map);
        assertEquals(2, list.size());
        var result = 1;
        for (int i = 0; i < list.size(); i++) {
            var x = list.get(i);
            assertSame(x.getThread().getState(), Thread.State.NEW);
            x.start();
            assertEquals(i + 2, x.get().intValue());
            assertTrue(x.isDone());
            assertFalse(x.isCancelled());
        }
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerMakeActiveObjects",
        goal = "To ensure that BardPlayAnalyzer's makeActiveObjects() method creates the expected" +
            " number of ActiveObjects.",
        points = 1.0,
        reference = "BardPlayAnalyzerTests.java"
    )
    public void testBardAnalyzerMakeActiveObjects() {
        var o = new BardPlayAnalyzer().makeActiveObjects();
        assertEquals(36, o.size());
        o.forEach(x -> assertSame(x.getThread().getState(), Thread.State.NEW));
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerStartActiveObjects",
        goal = "To verify that BardPlayAnalyzer's startActiveObjects() method correctly starts " +
            "the ActiveObjects.",
        points = 1.0,
        reference = "BardPlayAnalyzerTests.java"
    )
    public void testBardAnalyzerStartActiveObjects() {
        var b = new BardPlayAnalyzer();
        var o = b.makeActiveObjects();
        o.forEach(x -> assertSame(x.getThread().getState(), Thread.State.NEW));
        o.forEach(x -> assertNotSame(x.getThread().getState(), Thread.State.RUNNABLE));
        b.startActiveObjects(o);
        o.forEach(x -> {
            Thread.State state = x.getThread().getState();
            assertThat(state).isIn(List.of(Thread.State.RUNNABLE, Thread.State.TERMINATED));
        });
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerProcessActiveObjects",
        goal = "To ensure that BardPlayAnalyzer's processActiveObjects() method processes the " +
            "ActiveObjects and returns the expected results.",
        points = 1.0,
        reference = "BardPlayAnalyzerTests.java"
    )
    public void testBardAnalyzerProcessActiveObjects() {
        var o1 = new ActiveObject<Map.Entry<String, String>, Double>(
            x -> Double.valueOf(x.getValue()),
            Map.entry("1", "1"));
        var o2 = new ActiveObject<Map.Entry<String, String>, Double>(
            x -> Double.valueOf(x.getValue()),
            Map.entry("2", "2"));
        o1.start();
        o2.start();
        var b = new BardPlayAnalyzer();
        var results = b.processActiveObjects(new ActiveObject.ActiveObjectArray<>() {{
            add(o1);
            add(o2);
        }});
        String[] expected = {
            "1.00 (1st grade) is the score for 1",
            "2.00 (2nd grade) is the score for 2"
        };
        assertEquals(2, results.size());
        for (int i = 0; i < results.size(); i++) {
            assertEquals(expected[i], results.get(i));
        }
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerProcessInput",
        goal = "To verify that BardPlayAnalyzer's processInput() method returns the correct " +
            "result based on the input.",
        points = 1.0,
        reference = "BardPlayAnalyzerTests.java"
    )
    public void testBardAnalyzerProcessInput() {
        var result = new BardPlayAnalyzer().processInput(Map.entry("1", "two. sentences."));
        var expected = 8.79;
        assertEquals(expected, result, 0.01);
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerRunAndReturnSortedResults",
        goal = "To verify that BardPlayAnalyzer's runAndReturnResults() method returns expected " +
            "results.",
        points = 1.0,
        reference = "BardPlayAnalyzerTests.java"
    )
    public void testBardAnalyzerRunAndReturnSortedResults() {
        var results = new BardPlayAnalyzer().runAndReturnResults().toArray();
        for (int i = 0; i < results.length; i++) {
//            System.out.println(results[i]);
            assertEquals(expected[i], results[i]);
        }
    }

    private static final String[] expected = {
        "6.43 (6th grade) is the score for A Midsummer Nights Dream",
        "5.90 (6th grade) is the score for Alls Well That Ends Well",
        "6.11 (6th grade) is the score for As You Like It",
        "5.80 (6th grade) is the score for Cymbeline",
        "6.86 (7th grade) is the score for King Henry VIII",
        "6.93 (7th grade) is the score for King John",
        "7.21 (7th grade) is the score for King Richard II",
        "5.89 (6th grade) is the score for King Richard III",
        "5.64 (6th grade) is the score for Loves Labours Lost",
        "6.31 (6th grade) is the score for Measure for Measure",
        "4.95 (5th grade) is the score for Much Ado About Nothing",
        "5.93 (6th grade) is the score for Second Part of King Henry IV",
        "5.88 (6th grade) is the score for The Comedy of Errors",
        "7.30 (7th grade) is the score for The First Part of Henry IV",
        "5.38 (5th grade) is the score for The First Part of King Henry IV",
        "5.64 (6th grade) is the score for The History of Troilus and Cressida",
        "7.41 (7th grade) is the score for The Life of King Henry V",
        "5.16 (5th grade) is the score for The Life of Timon of Athens",
        "6.94 (7th grade) is the score for The Merchant of Venice",
        "4.93 (5th grade) is the score for The Merry Wives of Windsor",
        "6.78 (7th grade) is the score for The Second Part of King Henry VI",
        "5.82 (6th grade) is the score for The Taming of the Shrew",
        "6.04 (6th grade) is the score for The Tempest",
        "5.85 (6th grade) is the score for The Third Part of King Henry VI",
        "5.33 (5th grade) is the score for The Tragedy of Anthony and Cleopatra",
        "5.88 (6th grade) is the score for The Tragedy of Coriolanus",
        "5.23 (5th grade) is the score for The Tragedy of Hamlet",
        "5.16 (5th grade) is the score for The Tragedy of Julius Caesar",
        "4.54 (5th grade) is the score for The Tragedy of King Lear",
        "4.97 (5th grade) is the score for The Tragedy of MacBeth",
        "4.96 (5th grade) is the score for The Tragedy of Othello",
        "4.61 (5th grade) is the score for The Tragedy of Romeo and Juliet",
        "7.25 (7th grade) is the score for The Tragedy of Titus Andronicus",
        "5.74 (6th grade) is the score for The Two Gentlemen of Verona",
        "6.48 (6th grade) is the score for The Winters Tale",
        "5.17 (5th grade) is the score for Twelfth Night"
    };
}