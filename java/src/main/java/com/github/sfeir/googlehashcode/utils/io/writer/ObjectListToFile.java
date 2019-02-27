package com.github.sfeir.googlehashcode.utils.io.writer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A String writer which will write a list of mapped object to a file.
 * 
 * This class can be very useful for CSV files.
 * 
 * @author Sfeir
 */
public class ObjectListToFile<T> implements Writer<List<T>> {

	private final Charset charset;

	/**
	 * Create a new ObjectListToFile.
	 *
	 * This method will assume that the files are using UTF-8 charset.
	 */
	public ObjectListToFile() {
		this(Charset.forName("UTF-8"));
	}

	/**
	 * Create a new ObjectListToFile set to use the given mapper charset.
	 *
	 * @param cs the Charset to use while reading files.
	 */
	public ObjectListToFile(Charset cs) {
		this.charset = cs;
	}
	
	/** {@inheritDoc} */
	@Override
	public void write(String file, List<T> objects) throws IOException {
		Path filepath = new File(file).toPath();
		List<String> collect = objects.stream().map(Object::toString).collect(Collectors.toList());
		Files.write(filepath, collect, this.charset, StandardOpenOption.CREATE);
	}
}
