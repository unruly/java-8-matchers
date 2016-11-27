package co.unruly.matchers.function;

interface SingleArgumentDescribableFunctionalInterface extends SerializedLambdaResolvable {

    default String getArgumentDescription() {
        String actualSignature = asSerializedLambda().getInstantiatedMethodType();
        String argumentType = actualSignature.substring(actualSignature.indexOf('(') + 1, actualSignature.indexOf(')') - 1);
        argumentType = argumentType.substring(argumentType.lastIndexOf('/') + 1);
        return argumentType;
    }

}
