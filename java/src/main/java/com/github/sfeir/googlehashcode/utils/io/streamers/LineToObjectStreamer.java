package com.github.sfeir.googlehashcode.utils.io.streamers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

import com.github.sfeir.googlehashcode.utils.Mapper;

/**
 * This class reads a string file line by line, cast the line into an object and apply a behavior on it.
 * 
 * @author Sfeir
 */
public class LineToObjectStreamer<T> extends Streamer<T> {
	private final Mapper<T> mapper;
	private final Charset charset;
	
	/**
	 * Create a new Streamer which will use the given mapper to convert a line into an object.
	 * 
	 * This constructor will use UTF-8 as the default Charset.
	 * 
	 * @param mpr the mapper to use.
	 */
	public LineToObjectStreamer(Mapper<T> mpr) {
		this(mpr, Charset.forName("UTF-8"));
	}
	
	/**
	 * Create a new Streamer which will use the given mapper to convert a line into an object.
	 * 
	 * @param mpr the mapper to use.
	 * @param cs the charset to use while reading files.
	 */
	public LineToObjectStreamer(Mapper<T> mpr, Charset cs) {
		this.mapper = mpr;
		this.charset = cs;
	}
	
	/** {@inheritDoc} */
	@Override
	public void stream(String path) throws IOException {
		InputStream is = null;
		Scanner sc = null;
		try {
			is = new FileInputStream(path);
			sc = new Scanner(is, this.charset);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				T objLine = this.mapper.apply(line);
				this.behavior.apply(objLine);
			}
		} finally {
			if (null != sc) {
				sc.close();
			}
			if (null != is) { 
				is.close();
			}
		}
	}
}
