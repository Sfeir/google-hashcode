package com.github.sfeir.googlehashcode.photosslides;

import java.util.List;

import com.github.sfeir.googlehashcode.input.Photo;
import com.github.sfeir.googlehashcode.output.Slide;

public interface PhotoToSlides {
	List<Slide> transform(List<Photo> photos);
}
