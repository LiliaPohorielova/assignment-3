package vandy.mooc.functional;

import io.magnum.autograder.junit.Rubric;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class FleschKincaidGradeLevelCalculatorTests {
    @Rule
    public Timeout timeout = new Timeout(3, TimeUnit.SECONDS);

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#calculate(String)}
     */
    @Test
    @Rubric(
        value = "testCalculate",
        goal = "To ensure that the Flesch-Kincaid grade level is calculated correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCalculate() {
        // Arrange
        String text = "Text";

        // Act
        double actualCalculateResult =
            FleschKincaidGradeLevelCalculator.calculate(text);

        // Assert
        assertEquals(-3.40, actualCalculateResult, 0.01);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#calculate(String)}
     */
    @Test
    @Rubric(
        value = "testCalculate2",
        goal = "To ensure that the Flesch-Kincaid grade level is calculated correctly for a " +
            "string with only vowels.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCalculate2() {
        // Arrange
        String text = "aeiouy";

        // Act
        double actualCalculateResult =
            FleschKincaidGradeLevelCalculator.calculate(text);

        // Assert
        assertEquals(-3.40, actualCalculateResult, 0.01);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#calculate(String)}
     */
    @Test
    @Rubric(
        value = "testCalculate3",
        goal = "To ensure that the Flesch-Kincaid grade level is calculated correctly for a " +
            "string with a single character.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCalculate3() {
        // Arrange
        String text = "e";

        // Act
        double actualCalculateResult =
            FleschKincaidGradeLevelCalculator.calculate(text);

        // Assert
        assertEquals(-3.40, actualCalculateResult, 0.01);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#calculate(String)}
     */
    @Test
    @Rubric(
        value = "testCalculate4",
        goal = "To ensure that the Flesch-Kincaid grade level is calculated correctly for a null " +
            "string.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCalculate4() {
        // Arrange
        String text = null;

        // Act
        double actualCalculateResult =
            FleschKincaidGradeLevelCalculator.calculate(text);

        // Assert
        assertEquals(0.0, actualCalculateResult, 0.0);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#calculate(String)}
     */
    @Test
    @Rubric(
        value = "testCalculate5",
        goal = "To ensure that the Flesch-Kincaid grade level is calculated correctly for an " +
            "empty string.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCalculate5() {
        // Arrange
        String text = "";

        // Act
        double actualCalculateResult =
            FleschKincaidGradeLevelCalculator.calculate(text);

        // Assert
        assertEquals(0.0, actualCalculateResult, 0.0);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#computeFleschKincaidGradeLevelScore(int, int, int)}
     */
    @Test
    @Rubric(
        value = "testComputeFleschKincaidGradeLevelScore",
        goal = "To ensure that the Flesch-Kincaid grade level score is computed correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testComputeFleschKincaidGradeLevelScore() {
        // Arrange
        int sentenceCount = 3;
        int wordCount = 3;
        int syllableCount = 3;

        // Act
        double actualComputeFleschKincaidGradeLevelScoreResult =
            FleschKincaidGradeLevelCalculator
                .computeFleschKincaidGradeLevelScore(
                    sentenceCount,
                    wordCount,
                    syllableCount);

        // Assert
        assertEquals(-3.40, actualComputeFleschKincaidGradeLevelScoreResult, 0.01);
    }

    /**
     * Method under test:
     * {@link
     * FleschKincaidGradeLevelCalculator#computeFleschKincaidGradeLevelScore(int,
     * int, int)}
     */
    @Test
    @Rubric(
        value = "testComputeFleschKincaidGradeLevelScore2",
        goal = "To ensure that the Flesch-Kincaid grade level score is computed correctly for a " +
            "simple input.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testComputeFleschKincaidGradeLevelScore2() {
        // Arrange
        int sentenceCount = 1;
        int wordCount = 1;
        int syllableCount = 1;

        // Act
        double actualComputeFleschKincaidGradeLevelScoreResult =
            FleschKincaidGradeLevelCalculator
                .computeFleschKincaidGradeLevelScore(
                    sentenceCount,
                    wordCount,
                    syllableCount);

        // Assert
        assertEquals(-3.40, actualComputeFleschKincaidGradeLevelScoreResult, 0.01);
    }

    /**
     * Method under test:
     * {@link
     * FleschKincaidGradeLevelCalculator#computeFleschKincaidGradeLevelScore(int,
     * int, int)}
     */
    @Test
    @Rubric(
        value = "testComputeFleschKincaidGradeLevelScore3",
        goal = "To ensure that the Flesch-Kincaid grade level score is computed correctly for a " +
            "more complex input.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testComputeFleschKincaidGradeLevelScore3() {
        // Arrange
        int sentenceCount = 1;
        int wordCount = 3;
        int syllableCount = 3;

        // Act
        double actualComputeFleschKincaidGradeLevelScoreResult =
            FleschKincaidGradeLevelCalculator
                .computeFleschKincaidGradeLevelScore(
                    sentenceCount,
                    wordCount,
                    syllableCount);

        // Assert
        assertEquals(-2.62, actualComputeFleschKincaidGradeLevelScoreResult, 0.01);
    }

    /**
     * Method under test:
     * {@link
     * FleschKincaidGradeLevelCalculator#computeFleschKincaidGradeLevelScore(int,
     * int, int)}
     */
    @Test
    @Rubric(
        value = "testComputeFleschKincaidGradeLevelScore4",
        goal = "To ensure that the Flesch-Kincaid grade level score is computed correctly when " +
            "the sentence count is 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testComputeFleschKincaidGradeLevelScore4() {
        // Arrange
        int sentenceCount = 0;
        int wordCount = 3;
        int syllableCount = 3;

        // Act
        double actualComputeFleschKincaidGradeLevelScoreResult =
            FleschKincaidGradeLevelCalculator
                .computeFleschKincaidGradeLevelScore(
                    sentenceCount,
                    wordCount,
                    syllableCount);

        // Assert
        assertEquals(
            Double.POSITIVE_INFINITY,
            actualComputeFleschKincaidGradeLevelScoreResult,
            0.0);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSentences(String)}
     */
    @Test
    @Rubric(
        value = "testCountSentences",
        goal = "To ensure that the number of sentences in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSentences() {
        // Arrange
        String text = "Text";

        // Act
        int actualCountSentencesResult =
            FleschKincaidGradeLevelCalculator.countSentences(text);

        // Assert
        assertEquals(1, actualCountSentencesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countWords(String)}
     */
    @Test
    @Rubric(
        value = "testCountWords",
        goal = "To ensure that the number of words in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountWords() {
        // Arrange
        String text = "Text";

        // Act
        int actualCountWordsResult =
            FleschKincaidGradeLevelCalculator.countWords(text);

        // Assert
        assertEquals(1, actualCountWordsResult);
    }

// ...

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSyllables(String)}
     */
    @Test
    @Rubric(
        value = "testCountSyllables2",
        goal = "To ensure that the number of syllables in the text is counted correctly for a " +
            "simple word.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables2() {
        // Arrange
        String text = "foo";

        // Act
        int actualCountSyllablesResult =
            FleschKincaidGradeLevelCalculator.countSyllables(text);

        // Assert
        assertEquals(1, actualCountSyllablesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSyllables(String)}
     */
    @Test
    @Rubric(
        value = "testCountSyllables3",
        goal = "To ensure that the number of syllables in the text is counted correctly for a " +
            "single character word.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables3() {
        // Arrange
        String text = "e";

        // Act
        int actualCountSyllablesResult =
            FleschKincaidGradeLevelCalculator.countSyllables(text);

        // Assert
        assertEquals(1, actualCountSyllablesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSentences(String)}
     */
    @Test
    @Rubric(
        value = "testCountSentences2",
        goal = "To ensure that the number of sentences in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSentences2() {
        // Arrange
        String text = "a.b is a file name. And so is b.c.";

        // Act
        assertEquals(2, FleschKincaidGradeLevelCalculator.countSentences(text));
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSentences(String)}
     */
    @Test
    @Rubric(
        value = "testCountSentences3",
        goal = "To ensure that the number of sentences in an empty string is counted as 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSentences3() {
        // Arrange
        String text = null;

        // Act
        int actualCountSentencesResult =
            FleschKincaidGradeLevelCalculator.countSentences(text);

        // Assert
        assertEquals(0, actualCountSentencesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSentences(String)}
     */
    @Test
    @Rubric(
        value = "testCountSentences4",
        goal = "To ensure that the number of sentences in an empty string is counted as 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSentences4() {
        // Arrange
        String text = "";

        // Act
        int actualCountSentencesResult =
            FleschKincaidGradeLevelCalculator.countSentences(text);

        // Assert
        assertEquals(0, actualCountSentencesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countWords(String)}
     */
    @Test
    @Rubric(
        value = "testCountWords2",
        goal = "To ensure that the number of words in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountWords2() {
        // Arrange
        String text = "foo bar times 2 is foo $2.00 @ bar foo bar @bar";

        // Act
        assertEquals(10, FleschKincaidGradeLevelCalculator.countWords(text));
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countWords(String)}
     */
    @Test
    @Rubric(
        value = "testCountWords3",
        goal = "To ensure that the number of words in an empty string is counted as 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountWords3() {
        // Arrange
        String text = null;

        // Act
        int actualCountWordsResult =
            FleschKincaidGradeLevelCalculator.countWords(text);

        // Assert
        assertEquals(0, actualCountWordsResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countWords(String)}
     */
    @Test
    @Rubric(
        value = "testCountWords4",
        goal = "To ensure that the number of words in an empty string is counted as 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountWords4() {
        // Arrange
        String text = "";

        // Act
        int actualCountWordsResult =
            FleschKincaidGradeLevelCalculator.countWords(text);

        // Assert
        assertEquals(0, actualCountWordsResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSyllables(String)}
     */
    @Test
    @Rubric(
        value = "testCountSyllables",
        goal = "To ensure that the number of syllables in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables() {
        // Arrange
        String text = "Text";

        // Act
        int actualCountSyllablesResult =
            FleschKincaidGradeLevelCalculator.countSyllables(text);

        // Assert
        assertEquals(1, actualCountSyllablesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSyllables(String)}
     */
    @Test
    @Rubric(
        value = "testCountSyllables4",
        goal = "To ensure that the number of syllables in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables4() {
        // Arrange
        String text = "The cat in the hat.";

        // Act
        assertEquals(5, FleschKincaidGradeLevelCalculator.countSyllables(text));
    }

    @Test
    @Rubric(
        value = "testCountSyllables7",
        goal = "To ensure that the number of syllables in the text is counted correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables7() {
        // Arrange
        String text = "The cat ate the canary.";

        // Act
        assertEquals(8, FleschKincaidGradeLevelCalculator.countSyllables(text));
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSyllables(String)}
     */
    @Test
    @Rubric(
        value = "testCountSyllables5",
        goal = "To ensure that the number of syllables in a null string is counted as 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables5() {
        // Arrange
        String text = null;

        // Act
        int actualCountSyllablesResult =
            FleschKincaidGradeLevelCalculator.countSyllables(text);

        // Assert
        assertEquals(0, actualCountSyllablesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countSyllables(String)}
     */
    @Test
    @Rubric(
        value = "testCountSyllables6",
        goal = "To ensure that the number of syllables in an empty string is counted as 0.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountSyllables6() {
        // Arrange
        String text = "";

        // Act
        int actualCountSyllablesResult =
            FleschKincaidGradeLevelCalculator.countSyllables(text);

        // Assert
        assertEquals(0, actualCountSyllablesResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countItems(String,
     * BreakIterator, Predicate, Function)}
     */
    @Test
    @Rubric(
        value = "testCountItems",
        goal = "To ensure that the countItems method counts the items in the text correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountItems() {
        // Arrange
        String text = "Here is a sentence. And here is another sentence.";
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        Predicate<String> predicate = s -> true;
        Function<String, Integer> mapper = s -> 1;

        // Act
        assertEquals(
            2,
            FleschKincaidGradeLevelCalculator.countItems(
                text,
                iterator,
                predicate,
                mapper));
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countItems(String,
     * BreakIterator, Predicate, Function)}
     */
    @Test
    @Rubric(
        value = "testCountItems2",
        goal = "To ensure that the countItems method counts the items in a null string correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountItems2() {
        // Arrange
        String text = null;
        BreakIterator iterator = null;
        Predicate<String> predicate = null;
        Function<String, Integer> mapper = null;

        // Act
        int actualCountItemsResult =
            FleschKincaidGradeLevelCalculator.countItems(
                text,
                iterator,
                predicate,
                mapper);

        // Assert
        assertEquals(0, actualCountItemsResult);
    }

    /**
     * Method under test:
     * {@link FleschKincaidGradeLevelCalculator#countItems(String,
     * BreakIterator, Predicate, Function)}
     */
    @Test
    @Rubric(
        value = "testCountItems3",
        goal = "To ensure that the countItems method counts the items in an empty string " +
            "correctly.",
        points = 1.0,
        reference = "FleschKincaidGradeLevelCalculatorTests.java"
    )
    public void testCountItems3() {
        // Arrange
        String text = "";
        BreakIterator iterator = null;
        Predicate<String> predicate = null;
        Function<String, Integer> mapper = null;

        // Act
        int actualCountItemsResult =
            FleschKincaidGradeLevelCalculator.countItems(
                text,
                iterator,
                predicate,
                mapper);

        // Assert
        assertEquals(0, actualCountItemsResult);
    }

    @Test
    @Rubric(
        value = "testBardAnalyzerRunAndReturnSortedResults",
        goal = "To verify that BardPlayAnalyzer's runAndReturnResults() method returns expected results.",
        points = 10,
        reference = "BardPlayAnalyzer.java"
    )
    public void testBardAnalyzerRunAndReturnResults() {
        var results = new BardPlayAnalyzer().runAndReturnResults().toArray();
        for (int i = 0; i < results.length; i++) {
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
