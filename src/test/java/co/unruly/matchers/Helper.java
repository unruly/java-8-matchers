package co.unruly.matchers;

import org.hamcrest.Matcher;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class Helper {
    static <S> void testFailingMatcher(S testData, Matcher<S> matcher, String expectedDescription, String actualDescription) {
        try {
            assertThat(testData,matcher);
            fail("Supposed to not match " + matcher + ", but " + testData + " matched");
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
            assertThat(e.toString(), stringContainsInOrder(asList(expectedDescription, actualDescription)));
        }
    }
}
