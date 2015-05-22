package co.unruly.matchers;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Optional;
import java.util.OptionalInt;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class OptionalMatchersTest {

    @Test
    public void empty_success() throws Exception {
        assertThat(Optional.empty(), OptionalMatchers.empty());
    }

    @Test
    public void empty_failure() throws Exception {
        assertThat(Optional.of(1),not(OptionalMatchers.empty()));
    }

    @Test
    public void empty_failureMessage() throws Exception {
        Helper.testFailingMatcher(Optional.of(1), OptionalMatchers.empty(), "An empty Optional","<Optional[1]>");
    }

    @Test
    public void contains_success() throws Exception {
        assertThat(Optional.of("Hi!"), OptionalMatchers.contains("Hi!"));
    }

    @Test
    public void contains_failureNonEmpty() throws Exception {
        assertThat(Optional.of("Hi!"), not(OptionalMatchers.contains("Yay")));
    }

    @Test
    public void contains_failureEmpty() throws Exception {
        assertThat(Optional.empty(),not(OptionalMatchers.contains("Woot")));
    }


    @Test
    public void contains_failureMessages() throws Exception {
        Helper.testFailingMatcher(Optional.of(1), OptionalMatchers.contains(2), "Optional[2]","<Optional[1]>");
    }

    @Test
    public void containsMatcher_success() throws Exception {
        assertThat(Optional.of(4),OptionalMatchers.contains(Matchers.greaterThan(3)));
    }

    @Test
    public void containsMatcher_failureDiffering() throws Exception {
        assertThat(Optional.of(100),not(OptionalMatchers.contains(Matchers.lessThanOrEqualTo(19))));
    }

    @Test
    public void containsMatcher_failureEmpty() throws Exception {
        assertThat(Optional.empty(),not(OptionalMatchers.contains(Matchers.lessThanOrEqualTo(19))));
    }

    @Test
    public void containsMatcher_failureMessage() throws Exception {
        Helper.testFailingMatcher(Optional.of(2), OptionalMatchers.contains(Matchers.equalTo(4)), "Optional with an item that matches<4>","<Optional[2]>");
    }

    @Test
    public void emptyInt_success() throws Exception {
        assertThat(OptionalInt.empty(),OptionalMatchers.emptyInt());
    }

    @Test
    public void emptyInt_failure() throws Exception {
        assertThat(OptionalInt.of(0),not(OptionalMatchers.emptyInt()));
    }

    @Test
    public void containsInt_success() throws Exception {
        assertThat(OptionalInt.of(0),OptionalMatchers.containsInt(0));
    }

    @Test
    public void containsInt_failureDiffering() throws Exception {
        assertThat(OptionalInt.of(0),not(OptionalMatchers.containsInt(1)));
    }

    @Test
    public void containsInt_failureEmpty() throws Exception {
        assertThat(OptionalInt.empty(),not(OptionalMatchers.containsInt(1)));
    }

    @Test
    public void containsIntMatcher_success() throws Exception {
        assertThat(OptionalInt.of(0),OptionalMatchers.containsInt(Matchers.equalTo(0)));
    }

    @Test
    public void containsIntMatcher_failureEmpty() throws Exception {
        assertThat(OptionalInt.empty(),not(OptionalMatchers.containsInt(Matchers.equalTo(1))));
    }

    @Test
    public void containsIntMatcher_failureDiffering() throws Exception {
        assertThat(OptionalInt.of(0),not(OptionalMatchers.containsInt(Matchers.equalTo(1))));
    }
}
