package com.github.sfeir.googlehashcode.utils.io.streamers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * This class reads a string file line by line and apply a behavior on each line.
 * 
 * @author Sfeir
 */
public class LineStreamer extends Streamer<String> {
	private final Charset charset;
	
	/**
	 * Create a new Streamer which will read the files line by line as strings.
	 * 
	 * This constructor will use UTF-8 as the default Charset.
	 */
	public LineStreamer() {
		this(Charset.forName("UTF-8"));
	}
	

	/**
	 * Create a new Streamer which will read the files line by line as strings.
	 * 
	 * @param cs the charset to use while reading files.
	 */
	public LineStreamer(Charset cs) {
		this.charset = cs;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stream(String path) throws IOException {
		InputStream is = null;
		Scanner sc = null;
		try {
			is = new FileInputStream(path);
			sc = new Scanner(is, this.charset);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				this.behavior.apply(line);
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
