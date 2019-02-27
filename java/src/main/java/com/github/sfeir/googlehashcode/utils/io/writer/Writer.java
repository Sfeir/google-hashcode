package com.github.sfeir.googlehashcode.utils.io.writer;

import java.io.IOException;

/**
 * Base interface for writers used in Hashcode.
 *
 * @author Sfeir
 */
public interface Writer<T> {
    /**
     * Write a list of <pre>T</pre> objects and write them to path.
     *
     * Note: File should be on the local filesystem.
     *
     * @param file the path of the file to be written
     * @param objects the list of objects to be written
     * @throws IOException if an error occurs while writing the file.
     */
    void write(String file, T objects) throws IOException;
}
