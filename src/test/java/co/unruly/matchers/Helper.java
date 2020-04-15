package co.unruly.matchers;

import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.fail;

class Helper {
    static <S> void testFailingMatcher(S testData, Matcher<S> matcher, String expectedDescription, String actualDescription) {
        try {
            assertThat(testData, matcher);
            fail("Supposed to not match " + matcher + ", but " + testData + " matched");
        } catch (AssertionError e) {
            assertThat(e.toString(), stringContainsInOrder(asList(expectedDescription, actualDescription)));
        }
    }
}
