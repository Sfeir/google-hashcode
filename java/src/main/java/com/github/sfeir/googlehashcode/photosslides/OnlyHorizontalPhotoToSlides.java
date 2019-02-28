package com.github.sfeir.googlehashcode.photosslides;

import java.util.ArrayList;
import java.util.List;

import com.github.sfeir.googlehashcode.input.Orientation;
import com.github.sfeir.googlehashcode.input.Photo;
import com.github.sfeir.googlehashcode.output.Slide;

public class OnlyHorizontalPhotoToSlides implements PhotoToSlides {

	@Override
	public List<Slide> transform(List<Photo> photos) {
		final List<Slide> result = new ArrayList<>();
		for (Photo p : photos) {
			if (p.orientation == Orientation.HORIZONTAL) {
				result.add(new Slide(p));
			}
		}
		return result;
	}

}
