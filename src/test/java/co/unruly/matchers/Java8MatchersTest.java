package co.unruly.matchers;

import org.junit.Test;

import static co.unruly.matchers.Helper.testFailingMatcher;
import static co.unruly.matchers.Java8Matchers.where;
import static co.unruly.matchers.Java8Matchers.whereNot;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class Java8MatchersTest {

    final A entity = new A();

    @Test
    public void propertiesWhichIsTrue() {
        assertThat(entity, where(A::isCool));
        assertThat(entity, where(a -> a.isCool()));
    }

    @Test
    public void propertiesWhichIsFalse() {
        assertThat(entity, whereNot(A::isBoring));
        assertThat(entity, whereNot(a -> a.isBoring()));
    }

    @Test
    public void matchProperties() {
        assertThat(entity, where(A::name, is("A")));
        assertThat(entity, where(a -> a.name(), is("A")));
    }

    @Test
    public void failMatchingProperties() {
        testFailingMatcher(entity, where(A::name, is("X")), "with a name (a String) which is \"X\"", "had the name (a String) \"A\"");
        testFailingMatcher(entity, where(a -> a.name(), is("X")), "with a String which is \"X\"", "had the String \"A\"");
        testFailingMatcher(entity, where(A::age, not(is(42))), "with an age (an int) which not is <42>", "had the age (an int) <42>");
        testFailingMatcher(entity, where(a -> a.age(), is(1337)), "with an Integer which is <1337>", "had the Integer <42>");
        testFailingMatcher(entity, where("age", a -> a.age(), is(1337)), "with an age which is <1337>", "had the age <42>");
    }






    static class A {
        boolean isCool() {
            return true;
        }
        boolean isBoring() {
            return false;
        }
        String name() {
            return "A";
        }
        int age() {
            return 42;
        }
    }

}
