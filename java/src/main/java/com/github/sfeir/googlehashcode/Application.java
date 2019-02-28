package com.github.sfeir.googlehashcode;

import java.io.IOException;
import java.util.List;

import com.github.sfeir.googlehashcode.input.Photo;
import com.github.sfeir.googlehashcode.input.PhotoMapper;
import com.github.sfeir.googlehashcode.utils.io.readers.LineToObjectReader;
import com.github.sfeir.googlehashcode.utils.io.readers.Reader;

public class Application {
	public static void main(String[] args) throws IOException {
		Reader<List<Photo>> photoReader = new LineToObjectReader<Photo>(new PhotoMapper());
		if (args.length < 1) {
			System.out.println("usage : java --jar app.jar fileName_not_the_path");
		}
		
		String fileName = Application.class.getClassLoader().getResource(args[0]).getFile();
		List<Photo> photos = photoReader.read(fileName);
		

		// TODO Use photos !!
	}
}
