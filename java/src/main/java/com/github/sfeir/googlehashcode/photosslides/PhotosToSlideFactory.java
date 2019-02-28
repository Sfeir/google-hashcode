package com.github.sfeir.googlehashcode.photosslides;

public class PhotosToSlideFactory {
	public static PhotoToSlides getPhotoToSlides() {
		return new OnlyHorizontalPhotoToSlides();
	}
}
