package com.yutsuki.serverApi.utils;

@FunctionalInterface
public interface StateMapping<T extends Comparable<T>> {
    T getMapping();

    default boolean is(T t) {
        return getMapping().compareTo(t) == 0;
    }

    default boolean not(T t) {
        return !is(t);
    }
}
