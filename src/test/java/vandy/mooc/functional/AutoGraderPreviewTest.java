package vandy.mooc.functional;

import io.magnum.autograder.junit.ConsoleFormatter;
import io.magnum.autograder.junit.JUnitEvaluation;
import io.magnum.autograder.junit.JUnitEvaluator;
import org.apache.commons.text.WordUtils;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AutoGraderPreviewTest {
    /**
     * The test in this class provides the same feedback that is provided when
     * you submit your assignment to the Coursera grader on the assignment
     * hand-in page.
     */
    @Test
    public void graderPreview() {
        try {
            run(new File("./build/results.txt"),
                List.of(
                    ArrayTests.class,
                    FleschKincaidGradeLevelCalculatorTests.class,
                    BardPlayAnalyzerTests.class
                ));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(File resultsFile, List<Class<?>> classes) throws Exception {
        JUnitEvaluator eval = new JUnitEvaluator(classes);
        JUnitEvaluation estimatedScore = eval.evaluate(new ConsoleFormatter());

        System.out.println(WordUtils.wrap("\n\n" +
                                              "Your assignment HAS NOT BEEN SUBMITTED. The " +
                                              "following test results " +
                                              "only estimate your grade.\n\n", 80));

        System.out.println(WordUtils.wrap("Your estimated score is: " +
                                              estimatedScore.getScore() + "/" +
                                              estimatedScore.getTotalPoints(), 80));

        System.out.println(WordUtils.wrap(
            "(This is not your actual grade for this " +
                "assignment, just an estimate. Your official grade will be " +
                "calculated after you submit your submission.zip file to Coursera.\n\n",
            80));

        System.out.println(estimatedScore.getFeedback());

        PrintWriter writer = new PrintWriter(resultsFile, StandardCharsets.UTF_8);
        writer.print(estimatedScore.getFeedback());
        writer.close();
    }
}