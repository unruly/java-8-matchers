package co.unruly.matchers.function;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LambdaMethodFinderTest {

    @Test
    public void getArgumentDescription_resolvesTypeOfArgumentForPredicate() {
        assertThat(((DescribablePredicate<String>) s -> s.isEmpty()).getArgumentDescription(), is(String.class.getSimpleName()));
    }

    @Test
    public void getArgumentDescription_resolvesTypeOfArgumentForFunction() {
        assertThat(((DescribableFunction<String, Integer>) s -> s.length()).getArgumentDescription(), is("String"));
        assertThat(((DescribableFunction<String, Integer>) String::length).getArgumentDescription(), is("String"));
    }

    @Test
    public void getResultDescription_resolvesMethodNameAndTypeForMethodReferences() {
        assertThat(((DescribableFunction<String, Integer>) String::length).getResultDescription(), is("length (an int)"));
        assertThat(((DescribableFunction<String, byte[]>) String::getBytes).getResultDescription(), is("getBytes (a byte[])"));
    }

}
