package com.github.sfeir.googlehashcode.input;

import com.github.sfeir.googlehashcode.utils.Mapper;

public class PhotoMapper implements Mapper<Photo> {
	@Override
	public Photo apply(String t) {
		String[] values = t.split(" ");
		Photo result = new Photo();
		if (values.length < 3) {
			return result;
		}
		
		result.orientation = Orientation.fromString(values[0]);
		for (int i=2; i < values.length; i++) {
			result.tags.add(values[i]);
		}
		return result;
	}

}
