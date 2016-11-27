package co.unruly.matchers.function;

import java.lang.invoke.SerializedLambda;
import java.util.function.Function;

import static co.unruly.matchers.function.DescriptionUtils.withPrefixedArticle;
import static co.unruly.matchers.function.LambdaUtils.actualMethodOf;

@FunctionalInterface
public interface DescribableFunction<T, R> extends Function<T, R>, SerializedLambdaResolvable, SingleArgumentDescribableFunctionalInterface {

    default String getResultDescription() {
        SerializedLambda lambda = asSerializedLambda();
        String resultType = actualMethodOf(lambda).getReturnType().getSimpleName();
        if (! lambda.getImplMethodName().startsWith("lambda$")) {
            return lambda.getImplMethodName() + " (" + withPrefixedArticle(resultType) + ")";
        }
        return resultType;
    }

}
