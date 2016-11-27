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

    public static <I, O> Matcher<I> whereNot(DescribablePredicate<? super I> booleanProperty) {
        return whereNot(booleanProperty.getResultDescription(), booleanProperty);
    }

    public static <I, O> Matcher<I> whereNot(String propertyDescription, DescribablePredicate<? super I> booleanProperty) {
        String entityDescription = booleanProperty.getArgumentDescription();
        return where(entityDescription, propertyDescription, booleanProperty::test, is(false));
    }

    public static <I, O> Matcher<I> where(DescribablePredicate<? super I> booleanProperty) {
        return where(booleanProperty.getResultDescription(), booleanProperty);
    }

    public static <I, O> Matcher<I> where(String propertyDescription, DescribablePredicate<? super I> booleanProperty) {
        String entityDescription = booleanProperty.getArgumentDescription();
        return where(entityDescription, propertyDescription, booleanProperty::test, is(true));
    }

    public static <I, O> Matcher<I> where(DescribableFunction<? super I, O> property, Matcher<? super O> matcher) {
        String propertyDescription = property.getResultDescription();
        return where(propertyDescription, property, matcher);
    }

    public static <I, O> Matcher<I> where(String propertyDescription, DescribableFunction<? super I, O> property, Matcher<? super O> matcher) {
        String entityDescription = property.getArgumentDescription();
        return where(entityDescription, propertyDescription, property, matcher);
    }

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
