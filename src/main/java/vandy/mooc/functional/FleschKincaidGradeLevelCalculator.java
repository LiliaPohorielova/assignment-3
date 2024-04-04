package vandy.mooc.functional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class FleschKincaidGradeLevelCalculator {

    public static double calculate(String text) {
        if (text == null || text.isBlank()) {
            return 0.0;
        }
        int sentenceCount = countSentences(text);
        int wordCount = countWords(text);
        int syllableCount = countSyllables(text);

        return computeFleschKincaidGradeLevelScore(sentenceCount,
                wordCount,
                syllableCount);
    }

    public static double computeFleschKincaidGradeLevelScore
            (int sentenceCount,
             int wordCount,
             int syllableCount) {
        // Apply the Flesch-Kincaid formula to return the grade level
        // score.
        return (0.39 * (double) wordCount / sentenceCount)
                + (11.8 * (double) syllableCount / wordCount)
                - 15.59;
    }

    static int countSentences(String text) {
        // Create a BreakIterator for sentence boundaries.
        BreakIterator iterator = BreakIterator.getSentenceInstance();

        // Call the countItems method to count sentences.
        return countItems(text, iterator, s -> true, s -> 1);
    }

    static int countWords(String text) {
        // Create a BreakIterator for word boundaries.
        BreakIterator iterator = BreakIterator.getWordInstance();

        // Call the countItems method to count words.
        return countItems(text, iterator, s -> Character.isLetterOrDigit(s.charAt(0)), s -> 1);
    }

    static int countSyllables(String text) {
        Predicate<String> wordPredicate = word -> word.matches("\\p{Alpha}.*");
        Function<String, Integer> syllableMapper = FleschKincaidGradeLevelCalculator::countSyllablesInWord;
        return countItems(text, BreakIterator.getWordInstance(), wordPredicate, syllableMapper);
    }

    static int countItems(String text,
                          @NotNull BreakIterator iterator,
                          @Nullable Predicate<String> predicate,
                          @Nullable Function<String, Integer> mapper) {

        if (text == null || iterator == null) {
            return 0;
        }
        // Set the text to iterate over.
        iterator.setText(text);

        // Initialize the count of matching items.
        int count = 0;

        // Initialize the start index of the current item.
        int start = iterator.first();

        // Iterate through the text using the iterator.
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            // Extract the substring corresponding to the current item.
            String item = text.substring(start, end);

            // Check if the item matches the predicate.
            if (predicate == null || predicate.test(item)) {
                // Increment the count of matching items.
                count += (mapper != null) ? mapper.apply(item) : 1;
            }
        }

        // Return the count of matching items.
        return count;
    }

    private static int countSyllablesInWord(String word) {
        // Return 0 if the word is empty.
        if (word == null || word.isEmpty()) {
            return 0;
        }

        // Lowercase the word.
        word = word.toLowerCase();

        // Create a Predicate that tests if a character is a vowel.
        Predicate<Character> isVowel =
                c -> "aeiouy".indexOf(Character.toLowerCase(c)) != -1;

        int syllableCount = 0;
        boolean previousCharIsVowel = false;

        // Iterate through all the characters in the word.
        for (char c : word.toCharArray()) {
            // Determine if the current character is a vowel.
            boolean currentCharIsVowel = isVowel.test(c);

            if (currentCharIsVowel && !previousCharIsVowel) {
                syllableCount++;
            }

            // Update the previous character to the current
            // character.
            previousCharIsVowel = currentCharIsVowel;
        }

        // Remove one syllable for words ending with an 'e' that is
        // not part of a vowel digraph.
        if (word.endsWith("e")
                && !previousCharIsVowel && syllableCount > 0) {
            syllableCount--;
        }

        // Return the syllable count.
        return syllableCount;
    }
}
