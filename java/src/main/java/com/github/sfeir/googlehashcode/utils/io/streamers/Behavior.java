package com.github.sfeir.googlehashcode.utils.io.streamers;

/**
 * Base interface for applying behaviors on a line. Used by streamers.
 * 
 * @author Sfeir
 *
 * @param <T> the type of object expected by the implemented behavior.
 */
public interface Behavior<T> {
	/**
	 * Apply the implemented behavior to the given line object.
	 * 
	 * @param line the line which will receive a behavior.
	 */
	void apply(T line);
}
