package co.unruly.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class TimeMatchers {

    /**
     * Matches a time strictly after that given
     *
     * @param time Time for comparison
     * @param <T> The type of time, e.g. Instant or LocalDateTime
     */
    public static <T extends Comparable<T> & Temporal> Matcher<T> after(T time) {
        return new TypeSafeMatcher<T>() {
            @Override
            protected boolean matchesSafely(T actual) {
                return actual.compareTo(time) > 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("after ").appendValue(time);
            }
        };
    }

    /**
     * Matches a time strictly before that given
     *
     * @param time Time for comparison
     * @param <T> The type of time, e.g. Instant or LocalDateTime
     */
    public static <T extends Comparable<T> & Temporal> Matcher<T> before(T time) {
        return new TypeSafeMatcher<T>() {
            @Override
            protected boolean matchesSafely(T actual) {
                return actual.compareTo(time) < 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("before ").appendValue(time);
            }
        };
    }

    /**
     * Matches a Comparable value greater than or equal to the first given value and less than or
     * equal to the second given value
     *
     * @param <T> The type to compare, e.g. Temporal or TemporalAmount
     */
    public static <T extends Comparable<T>> Matcher<T> between(T earlierOrEqual, T laterOrEqual) {
        return new TypeSafeMatcher<T>() {
            @Override
            protected boolean matchesSafely(T actual) {
                return actual.compareTo(earlierOrEqual) >= 0 && actual.compareTo(laterOrEqual) <= 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("between ").appendValue(earlierOrEqual)
                .appendText(" and ")
                .appendValue(laterOrEqual)
                .appendText(" inclusive");
            }
        };
    }

    /**
     * Matches an amount of time strictly longer than that specified
     * @param amount amount of time for comparison
     * @param <T> The type to compare, typically Duration
     */
    public static <T extends Comparable<T> & TemporalAmount> Matcher<T> longerThan(T amount) {
        return new TypeSafeMatcher<T>() {

            @Override
            protected boolean matchesSafely(T actual) {
                return actual.compareTo(amount) > 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("longer than ").appendValue(amount);
            }
        };
    }

    /**
     * Matches an amount of time strictly shorter than that specified
     * @param amount amount of time for comparison
     * @param <T> The type to compare, typically Duration
     */
    public static <T extends Comparable<T> & TemporalAmount> Matcher<T> shorterThan(T amount) {
        return new TypeSafeMatcher<T>() {

            @Override
            protected boolean matchesSafely(T actual) {
                return actual.compareTo(amount) < 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("shorter than ").appendValue(amount);
            }
        };
    }

    /**
     * Matches any Period for which the years, months and days match the provided matchers
     */
    public static Matcher<Period> matches(Matcher<Integer> yearsMatcher, Matcher<Integer> monthsMatcher, Matcher<Integer> daysMatcher) {
        return new TypeSafeMatcher<Period>() {
            @Override
            protected boolean matchesSafely(Period item) {
                return yearsMatcher.matches(item.getYears()) && monthsMatcher.matches(item.getMonths()) && daysMatcher.matches(item.getDays());
            }

            @Override
            public void describeTo(Description description) {
                description
                        .appendText("a Period with years matching ")
                        .appendValue(yearsMatcher)
                        .appendText(" months matching ")
                        .appendValue(yearsMatcher)
                        .appendText(" and days matching ")
                        .appendValue(daysMatcher);
            }
        };
    }
}
