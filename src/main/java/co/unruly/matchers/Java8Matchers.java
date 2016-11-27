package co.unruly.matchers;

import co.unruly.matchers.function.DescribableFunction;
import co.unruly.matchers.function.DescribablePredicate;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.function.Function;

import static co.unruly.matchers.function.DescriptionUtils.withPrefixedArticle;
import static org.hamcrest.CoreMatchers.is;

public final class Java8Matchers {

    /**
     * Match a {@code boolean} property of an object which should be {@code false}.
     *
     * @param booleanProperty the predicate reolving the {@code boolean} property from the object.
     */
    public static <I, O> Matcher<I> whereNot(DescribablePredicate<? super I> booleanProperty) {
        return whereNot(booleanProperty.getResultDescription(), booleanProperty);
    }

    /**
     * Match a {@code boolean} property of an object which should be {@code false}.
     *
     * @param propertyDescription a description of the property.
     * @param booleanProperty the predicate resolving the {@code boolean} property from the object.
     */
    public static <I, O> Matcher<I> whereNot(String propertyDescription, DescribablePredicate<? super I> booleanProperty) {
        String entityDescription = booleanProperty.getArgumentDescription();
        return where(entityDescription, propertyDescription, booleanProperty::test, is(false));
    }

    /**
     * Match a {@code boolean} property of an object which should be {@code true}.
     *
     * @param booleanProperty the predicate resolving the {@code boolean} property from the object.
     */
    public static <I, O> Matcher<I> where(DescribablePredicate<? super I> booleanProperty) {
        return where(booleanProperty.getResultDescription(), booleanProperty);
    }

    /**
     * Match a {@code boolean} property of an object which should be {@code true}.
     *
     * @param propertyDescription a description of the property.
     * @param booleanProperty the predicate resolving the {@code boolean} property from the object.
     */
    public static <I, O> Matcher<I> where(String propertyDescription, DescribablePredicate<? super I> booleanProperty) {
        String entityDescription = booleanProperty.getArgumentDescription();
        return where(entityDescription, propertyDescription, booleanProperty::test, is(true));
    }

    /**
     * Match a value/property derived from an object.
     *
     * @param property the function which resolves the derived value to match.
     * @param matcher the {@code Matcher} for the derived value.
     */
    public static <I, O> Matcher<I> where(DescribableFunction<? super I, O> property, Matcher<? super O> matcher) {
        String propertyDescription = property.getResultDescription();
        return where(propertyDescription, property, matcher);
    }

    /**
     * Match a value/property derived from an object.
     *
     * @param propertyDescription a description of the derived value.
     * @param property the function which resolves the derived value to match.
     * @param matcher the {@code Matcher} for the derived value.
     */
    public static <I, O> Matcher<I> where(String propertyDescription, DescribableFunction<? super I, O> property, Matcher<? super O> matcher) {
        String entityDescription = property.getArgumentDescription();
        return where(entityDescription, propertyDescription, property, matcher);
    }

    /**
     * Match a value/property derived from an object.
     *
     * @param entityDescription a description of the object the matched value is derived from.
     * @param propertyDescription a description of the derived value.
     * @param property the function which resolves the derived value to match.
     * @param matcher the {@code Matcher} for the derived value.
     */
    public static <I, O> Matcher<I> where(String entityDescription, String propertyDescription, Function<? super I, O> property, Matcher<? super O> matcher) {
        return new TypeSafeDiagnosingMatcher<I>() {

            @Override
            public void describeTo(Description description) {
                description.appendText(entityDescription).appendText(" with ").appendText(withPrefixedArticle(propertyDescription))
                    .appendText(" which ").appendDescriptionOf(matcher);
            }


            @Override
            protected boolean matchesSafely(I objectToMatch, Description mismatch) {
                O actual = property.apply(objectToMatch);
                boolean match = matcher.matches(actual);
                if (!match) {
                    mismatch.appendText("had the ").appendText(propertyDescription).appendText(" ").appendValue(actual);
                }
                return match;
            }
        };
    }


    private Java8Matchers() {}
}
