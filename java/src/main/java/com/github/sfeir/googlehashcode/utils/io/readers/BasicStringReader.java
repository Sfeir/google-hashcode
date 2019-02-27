package com.github.sfeir.googlehashcode.utils.io.readers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * A String reader which will only return the content of the file in a single String.
 * 
 * @author Sfeir
 */
public class BasicStringReader implements Reader<String> {
	private final boolean preserveLS;
	private final Charset charset;
	
	/**
	 * Create a new BasicStringReader with the given configuration for the line separators.
	 * 
	 * @param preserveLineSeparators if set to true, this reader will return a string which will
	 * 	include the line separators (OS dependant !). If set to false, it will trim them while 
	 * 	reading.
	 * @param charset the charset to use while reading the file.
	 */
	public BasicStringReader(boolean preserveLineSeparators, Charset charset) {
		this.preserveLS = preserveLineSeparators;
		this.charset = charset;
	}
	
	/**
	 * Create a new BasicStringReader with the given configuration for the line separators.
	 * 
	 * This method will assume that the files are using UTF-8 charset.
	 * 
	 * @param preserveLineSeparators if set to true, this reader will return a string which will
	 * 	include the line separators (OS dependant !). If set to false, it will trim them while 
	 * 	reading.
	 */
	public BasicStringReader(boolean preserveLineSeparators) {
		this(preserveLineSeparators, Charset.forName("UTF-8"));
	}
	
	/** {@inheritDoc} */
	@Override
	public String read(String file) throws IOException {
		Path filepath = Paths.get(file);
		if (preserveLS) {
			byte[] resultBytes = Files.readAllBytes(filepath);
			return new String(resultBytes, this.charset);
		}
		return Files.readAllLines(filepath)
				.stream()
				.collect(Collectors.joining(" "));
	}

}
