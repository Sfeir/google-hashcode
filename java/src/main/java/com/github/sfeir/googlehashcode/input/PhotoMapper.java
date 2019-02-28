package com.github.sfeir.googlehashcode.input;

import com.github.sfeir.googlehashcode.utils.Mapper;

public class PhotoMapper implements Mapper<Photo> {
	@Override
	public Photo map(int i, String line) {
		String[] values = line.split(" ");
		Photo result = new Photo();
		if (values.length < 3) {
			return result;
		}
		result.id = i;
		result.orientation = Orientation.fromString(values[0]);
		for (int j=2; j < values.length; j++) {
			result.tags.add(values[j]);
		}
		return result;
	}

}
