package co.unruly.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class OptionalMatchers {
    /**
     * Matches an empty Optional.
     */
    public static <T> Matcher<Optional<T>> empty() {
        return new TypeSafeMatcher<Optional<T>>() {
            @Override
            protected boolean matchesSafely(Optional<T> item) {
                return !item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An empty Optional");
            }
        };
    }

    /**
     * Matches a non empty Optional with the given content
     *
     * @param content Expected contents of the Optional
     * @param <T> The type of the Optional's content
     */
    public static <T> Matcher<Optional<T>> contains(T content) {
        return new TypeSafeMatcher<Optional<T>>() {
            @Override
            protected boolean matchesSafely(Optional<T> item) {
                return item.map(content::equals).orElse(false);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(Optional.of(content).toString());
            }
        };
    }

    /**
     * Matches a non empty Optional with content matching the given matcher
     *
     * @param matcher To match against the Optional's content
     * @param <T> The type of the Optional's content
     */
    public static <T> Matcher<Optional<T>> contains(Matcher<T> matcher) {
        return new TypeSafeMatcher<Optional<T>>() {
            @Override
            protected boolean matchesSafely(Optional<T> item) {
                return item.map(matcher::matches).orElse(false);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Optional with an item that matches" + matcher);
            }
        };
    }

    /**
     * Matches an empty OptionalInt.
     */
    public static Matcher<OptionalInt> emptyInt() {
        return new TypeSafeMatcher<OptionalInt>() {
            @Override
            protected boolean matchesSafely(OptionalInt item) {
                return !item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An empty OptionalInt");
            }
        };
    }

    /**
     * Matches a non empty OptionalInt with the given content
     *
     * @param content Expected contents of the Optional
     */
    public static Matcher<OptionalInt> containsInt(int content) {
        return new TypeSafeMatcher<OptionalInt>() {
            @Override
            protected boolean matchesSafely(OptionalInt item) {
                return item.isPresent() && item.getAsInt() == content;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(Optional.of(content).toString());
            }
        };
    }

    /**
     * Matches a non empty OptionalInt with content matching the given matcher
     *
     * @param matcher To match against the OptionalInt's content
     */
    public static Matcher<OptionalInt> containsInt(Matcher<Integer> matcher) {
        return new TypeSafeMatcher<OptionalInt>() {
            @Override
            protected boolean matchesSafely(OptionalInt item) {
                return item.isPresent() && matcher.matches(item.getAsInt());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("OptionalInt with an item that matches" + matcher);
            }
        };
    }

    /**
     * Matches an empty OptionalLong.
     */
    public static Matcher<OptionalLong> emptyLong() {
        return new TypeSafeMatcher<OptionalLong>() {
            @Override
            protected boolean matchesSafely(OptionalLong item) {
                return !item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An empty OptionalLong");
            }
        };
    }

    /**
     * Matches a non empty OptionalLong with the given content
     *
     * @param content Expected contents of the Optional
     */
    public static Matcher<OptionalLong> containsLong(long content) {
        return new TypeSafeMatcher<OptionalLong>() {
            @Override
            protected boolean matchesSafely(OptionalLong item) {
                return item.isPresent() && item.getAsLong() == content;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(Optional.of(content).toString());
            }
        };
    }

    /**
     * Matches a non empty OptionalLong with content matching the given matcher
     *
     * @param matcher To match against the OptionalLong's content
     */
    public static Matcher<OptionalLong> containsLong(Matcher<Long> matcher) {
        return new TypeSafeMatcher<OptionalLong>() {
            @Override
            protected boolean matchesSafely(OptionalLong item) {
                return item.isPresent() && matcher.matches(item.getAsLong());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("OptionalLong with an item that matches" + matcher);
            }
        };
    }

    /**
     * Matches an empty OptionalDouble.
     */
    public static Matcher<OptionalDouble> emptyDouble() {
        return new TypeSafeMatcher<OptionalDouble>() {
            @Override
            protected boolean matchesSafely(OptionalDouble item) {
                return !item.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An empty OptionalDouble");
            }
        };
    }

    /**
     * Matches a non empty OptionalDouble with the given content
     *
     * @param content Expected contents of the Optional
     */
    public static Matcher<OptionalDouble> containsDouble(double content) {
        return new TypeSafeMatcher<OptionalDouble>() {
            @Override
            protected boolean matchesSafely(OptionalDouble item) {
                return item.isPresent() && item.getAsDouble() == content;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(Optional.of(content).toString());
            }
        };
    }

    /**
     * Matches a non empty OptionalDouble with content matching the given matcher
     *
     * @param matcher To match against the OptionalDouble's content
     */
    public static Matcher<OptionalDouble> containsDouble(Matcher<Double> matcher) {
        return new TypeSafeMatcher<OptionalDouble>() {
            @Override
            protected boolean matchesSafely(OptionalDouble item) {
                return item.isPresent() && matcher.matches(item.getAsDouble());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("OptionalDouble with an item that matches" + matcher);
            }
        };
    }
}
