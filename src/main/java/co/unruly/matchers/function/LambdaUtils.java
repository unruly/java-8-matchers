package co.unruly.matchers.function;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

final class LambdaUtils {

    static Method actualMethodOf(SerializedLambda lambda) {
        return Stream.of(containingClassOf(lambda).getDeclaredMethods())
                .filter(method -> Objects.equals(method.getName(), lambda.getImplMethodName()))
                .findFirst()
                .orElseThrow(() -> new UnableToGuessMethodException(lambda));
    }

    static Class<?> containingClassOf(SerializedLambda lambda) {
        try {
            String className = lambda.getImplClass().replaceAll("/", ".");
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getClass().getSimpleName() + ": '" + e.getMessage() + "'", e);
        }
    }

    private LambdaUtils() { }
}
