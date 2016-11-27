package co.unruly.matchers.function;

import static java.util.Arrays.binarySearch;

public final class DescriptionUtils {

    private static final char[] CHARS_FOR_AN = {'A', 'E', 'I', 'O', 'a', 'e', 'i', 'o'};

    public static String withPrefixedArticle(String noun) {
        return (binarySearch(CHARS_FOR_AN, noun.charAt(0)) < 0 ? "a " : "an ") + noun;
    }

    private DescriptionUtils() {}
}
