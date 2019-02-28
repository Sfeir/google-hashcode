package com.github.sfeir.googlehashcode.photosslides;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.sfeir.googlehashcode.input.Orientation;
import com.github.sfeir.googlehashcode.input.Photo;
import com.github.sfeir.googlehashcode.output.Slide;

public class PhotosToSlides implements PhotoToSlides {

    

	@Override
	public List<Slide> transform(List<Photo> photos) {
		Slide[] horizontalSlide = photos.stream().filter(photo ->   photo.orientation == Orientation.HORIZONTAL)
		.map(photo -> new Slide(photo)).toArray(Slide[]::new);
		
		Photo[] verticalPhotos = photos.stream()
				.filter(photo -> photo.orientation == Orientation.VERTICAL).toArray(Photo[]::new);
		Slide slide = null;
		List<Slide> resultList = new ArrayList<>();
		for(Photo current : verticalPhotos) {
			if(null == slide) {
				slide = new Slide(current);
			} else {
				slide.photo2=current;
				resultList.add(slide);
				slide = null;
			}
		}
		resultList.addAll(Arrays.asList(horizontalSlide));		
		return resultList ;
	}
}