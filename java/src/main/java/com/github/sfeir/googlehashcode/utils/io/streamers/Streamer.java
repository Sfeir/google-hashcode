package com.github.sfeir.googlehashcode.utils.io.streamers;

import java.io.IOException;

/**
 * Base class for streamers used in Hashcode.
 * 
 * @author Sfeir
 * @param <T> the Object type which will be manipulated by this Streamer.
 */
public abstract class Streamer<T> {
	/** The behavior to apply on each line of the input file */
	protected Behavior<T> behavior;
	
	/**
	 * Set the given behavior as the one to apply on each line streamed.
	 * 
	 * @param bhvr the behavior to use on each line.
	 */
	public void use(Behavior<T> bhvr) {
		this.behavior = bhvr;
	}
	
	/**
	 * Stream each line from the given path and apply it a behavior.
	 * 
	 * This method will not keep any trace of the file in memory after completion.
	 * 
	 * @param path the path of the wanted file (must be on the local filesystem).
	 * @throws IOException if the file can't be reached and can't be read.
	 */
	public abstract void stream(String path) throws IOException;
}
