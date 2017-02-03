package co.unruly.matchers.function;

import java.lang.invoke.SerializedLambda;

public class UnableToGuessMethodException extends RuntimeException {
    UnableToGuessMethodException(SerializedLambda lambda) {
        super("Could not determine the single abstract method of " + lambda);
    }
}
