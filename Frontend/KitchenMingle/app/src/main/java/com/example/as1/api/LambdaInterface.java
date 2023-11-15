package com.example.as1.api;

/**
 * The LambdaInterface is a functional interface that defines a single abstract method, {@code doSomething},
 * which takes a generic parameter and performs an action with it.
 * This interface is designed to be used as a functional interface for lambda expressions or method references
 * to represent actions or operations that take a parameter of type T and return no result.
 *
 * @param <T> The type of the parameter that the {@code doSomething} method accepts.
 * @since 1.0
 */
public interface LambdaInterface<T> {
    /**
     * Performs an action or operation with the given parameter.
     *
     * @param result The parameter of type T on which the action or operation is performed.
     */
    public void doSomething(T result);
}
