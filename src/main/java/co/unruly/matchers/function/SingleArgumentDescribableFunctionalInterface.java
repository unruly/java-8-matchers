package co.unruly.matchers.function;

import java.lang.invoke.MethodType;

interface SingleArgumentDescribableFunctionalInterface extends SerializedLambdaResolvable {

    default String getArgumentDescription() {
        MethodType methodType = MethodType.fromMethodDescriptorString(asSerializedLambda().getInstantiatedMethodType(), getClass().getClassLoader());
        return methodType.parameterType(0).getSimpleName();
    }

}
