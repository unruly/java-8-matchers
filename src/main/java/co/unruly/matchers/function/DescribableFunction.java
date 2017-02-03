package co.unruly.matchers.function;

import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.util.function.Function;

import static co.unruly.matchers.internal.DescriptionUtils.withPrefixedArticle;

@FunctionalInterface
public interface DescribableFunction<T, R> extends Function<T, R>, SerializedLambdaResolvable, SingleArgumentDescribableFunctionalInterface {

    default String getResultDescription() {
        SerializedLambda lambda = asSerializedLambda();
        MethodType lambdaMethodType = MethodType.fromMethodDescriptorString(lambda.getImplMethodSignature(), getClass().getClassLoader());
        String resultType = lambdaMethodType.returnType().getSimpleName();
        if (! lambda.getImplMethodName().startsWith("lambda$")) {
            return lambda.getImplMethodName() + " (" + withPrefixedArticle(resultType) + ")";
        }
        return resultType;
    }

}
