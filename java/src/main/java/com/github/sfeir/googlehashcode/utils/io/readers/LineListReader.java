package com.github.sfeir.googlehashcode.utils.io.readers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A String reader which will only return the content of the file as a list of string (line by line).
 * 
 * @author Sfeir
 */
public class LineListReader implements Reader<List<String>> {
	private Charset charset = null;
	
	/**
	 * Create a new LineListReader setted.
	 * 
	 * This method will assume that the files are using UTF-8 charset.
	 */
	public LineListReader() {
		this(Charset.forName("UTF-8"));
	}
	
	/**
	 * Create a new LineListReader setted to read the given charset.
	 * 
	 * @param charset the charset to use while reading the file.
	 */
	public LineListReader(Charset cs) {
		this.charset = cs;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<String> read(String file) throws IOException {
		Path filepath = Paths.get(file);
		return Files.readAllLines(filepath, this.charset);
	}

}
