package com.github.sfeir.googlehashcode.utils;

import java.util.function.Function;

/**
 * Base interface to transform a String line into an Object.
 * 
 * @author Sfeir
 * @param <T> The targeted Object.
 */
public interface Mapper<T> extends Function<String, T> {
}
