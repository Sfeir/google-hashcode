package com.github.sfeir.googlehashcode.utils.io.readers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.sfeir.googlehashcode.utils.Mapper;

/**
 * A String reader which will only return the content of the file as a list of mapped object.
 * 
 * This class can be very useful for CSV files.
 * 
 * @author Sfeir
 */
public class LineToObjectReader<T> implements Reader<List<T>> {
	private final Mapper<T> mapper;
	private final Charset charset;
	
	/**
	 * Create a new LineToObjectReader set to use the given mapper.
	 * 
	 * This method will assume that the files are using UTF-8 charset.
	 * 
	 * @param mpr the Mapper to use to transform each line into an object.
	 */
	public LineToObjectReader(Mapper<T> mpr) {
		this(mpr, Charset.forName("UTF-8"));
	}
	
	/**
	 * Create a new LineToObjectReader set to use the given mapper and read the given charset.
	 * 
	 * @param mpr the Mapper to use to transform each line into an object.
	 * @param cs the Charset to use while reading files.
	 */
	public LineToObjectReader(Mapper<T> mpr, Charset cs) {
		this.mapper = mpr;
		this.charset = cs;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<T> read(String file) throws IOException {
		Path filepath = new File(file).toPath();
		List<String> lines =  Files.readAllLines(filepath, this.charset);
		List<T> result = new ArrayList<>();
		for (int i = 1; i < lines.size(); i++) {
			result.add(this.mapper.map(i, lines.get(i)));
		}
		return result;
	}
}
