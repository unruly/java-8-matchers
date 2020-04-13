package co.unruly.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

class OptionalMatchersTest {

    @Test
    void empty_success() {
        assertThat(Optional.empty(), OptionalMatchers.empty());
    }

    @Test
    void empty_failure() {
        assertThat(Optional.of(1), not(OptionalMatchers.empty()));
    }

    @Test
    void empty_failureMessage() {
        Helper.testFailingMatcher(Optional.of(1), OptionalMatchers.empty(), "An empty Optional","<Optional[1]>");
    }

    @Test
    void contains_success() {
        assertThat(Optional.of("Hi!"), OptionalMatchers.contains("Hi!"));
    }

    @Test
    void contains_failureNonEmpty() {
        assertThat(Optional.of("Hi!"), not(OptionalMatchers.contains("Yay")));
    }

    @Test
    void contains_failureEmpty() {
        assertThat(Optional.empty(), not(OptionalMatchers.contains("Woot")));
    }


    @Test
    void contains_failureMessages() {
        Helper.testFailingMatcher(Optional.of(1), OptionalMatchers.contains(2), "Optional[2]","<Optional[1]>");
    }

    @Test
    void containsMatcher_success() {
        assertThat(Optional.of(4), OptionalMatchers.contains(Matchers.greaterThan(3)));
    }

    @Test
    void containsMatcher_success_typechecksWhenOptionalsArgIsStrictSubtype() {
        Optional<List<String>> optionalToMatch = Optional.of(Arrays.asList("a"));
        Matcher<Iterable<? super String>> matcherOfStrictSuperType = hasItem("a");
        assertThat(optionalToMatch, OptionalMatchers.contains(matcherOfStrictSuperType));
    }

    @Test
    void containsMatcher_failureDiffering() {
        assertThat(Optional.of(100), not(OptionalMatchers.contains(Matchers.lessThanOrEqualTo(19))));
    }

    @Test
    void containsMatcher_failureEmpty() {
        assertThat(Optional.empty(), not(OptionalMatchers.contains(Matchers.lessThanOrEqualTo(19))));
    }

    @Test
    void containsMatcher_failureMessage() {
        Helper.testFailingMatcher(Optional.of(2), OptionalMatchers.contains(Matchers.equalTo(4)), "Optional with an item that matches<4>","<Optional[2]>");
    }

    @Test
    void emptyInt_success() {
        assertThat(OptionalInt.empty(), OptionalMatchers.emptyInt());
    }

    @Test
    void emptyInt_failure() {
        assertThat(OptionalInt.of(0), not(OptionalMatchers.emptyInt()));
    }

    @Test
    void containsInt_success() {
        assertThat(OptionalInt.of(0), OptionalMatchers.containsInt(0));
    }

    @Test
    void containsInt_failureDiffering() {
        assertThat(OptionalInt.of(0), not(OptionalMatchers.containsInt(1)));
    }

    @Test
    void containsInt_failureEmpty() {
        assertThat(OptionalInt.empty(), not(OptionalMatchers.containsInt(1)));
    }

    @Test
    void containsIntMatcher_success() {
        assertThat(OptionalInt.of(0), OptionalMatchers.containsInt(Matchers.equalTo(0)));
    }

    @Test
    void containsIntMatcher_failureEmpty() {
        assertThat(OptionalInt.empty(), not(OptionalMatchers.containsInt(Matchers.equalTo(1))));
    }

    @Test
    void containsIntMatcher_failureDiffering() {
        assertThat(OptionalInt.of(0), not(OptionalMatchers.containsInt(Matchers.equalTo(1))));
    }
}
