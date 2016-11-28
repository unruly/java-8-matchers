package co.unruly.matchers.function;

import java.lang.invoke.SerializedLambda;
import java.util.function.Predicate;

@FunctionalInterface
public interface DescribablePredicate<T> extends Predicate<T>, SerializedLambdaResolvable, SingleArgumentDescribableFunctionalInterface {

    default String getResultDescription() {
        SerializedLambda lambda = asSerializedLambda();
        if (! lambda.getImplMethodName().startsWith("lambda$")) {
            return lambda.getImplMethodName();
        }
        return "boolean";
    }

}
