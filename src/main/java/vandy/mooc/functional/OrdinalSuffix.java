package vandy.mooc.functional;

import java.util.HashMap;
import java.util.Map;

/**
 * A Java utility class that handles ordinal suffixes for numeric
 * values.  For example, it will convert 1 to 1st, 2 to 2nd, and so
 * on.
 */
public class OrdinalSuffix {
    /**
     * A Java utility class should contain a private constructor.
     */
    private OrdinalSuffix() {}

    // Mapping of integers to their corresponding ordinal indicators.
    private static final Map<Integer, String> sOrdinalIndicators =
        new HashMap<>() {{
            // 1, 2, 3 are special cases.
            put(1, "st");
            put(2, "nd");
            put(3, "rd");
            // 11, 12, 13 are exceptions to the rule.
            put(11, "th");
            put(12, "th");
            put(13, "th");
        }};

    /**
     * Returns the input value with its ordinal suffix.
     *
     * @param value The double value for which to get the ordinal suffix

     * @return A string representation of the input value with its
     *         ordinal suffix
     */
    public static String getOrdinalSuffix(double value) {
        // Round the double value and cast it to an int.
        int intValue = (int) Math.round(value);

        // Get the last digit of the intValue.
        int lastDigit = intValue % 10;

        // Get the ordinal indicator for the last digit, if not found
        // in the map, default to "th".
        String ordinalIndicator = sOrdinalIndicators
            .getOrDefault(lastDigit, "th");

        // Return the intValue with its ordinal indicator.
        return intValue + ordinalIndicator;
    }
}
