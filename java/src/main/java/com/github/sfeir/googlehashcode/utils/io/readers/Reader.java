package com.github.sfeir.googlehashcode.utils.io.readers;

import java.io.IOException;

/**
 * Base interface for readers used in Hashcode.
 * 
 * @author Sfeir
 */
public interface Reader<T> {
	/**
	 * Read the given file and return it as a <pre>T</pre> object.
	 * 
	 * Note: File should be on the local filesystem.
	 * 
	 * @return the Java object representing the file's content.
	 * @throws IOException if an error occurs while reading the file.
	 */
	T read(String file) throws IOException;
}
