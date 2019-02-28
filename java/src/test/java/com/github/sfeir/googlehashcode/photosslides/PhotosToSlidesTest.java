package com.github.sfeir.googlehashcode.photosslides;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.sfeir.googlehashcode.input.Orientation;
import com.github.sfeir.googlehashcode.input.Photo;
import com.github.sfeir.googlehashcode.output.Slide;

class PhotosToSlidesTest {

	private PhotoToSlides transformor;
	@BeforeEach
	public void setUp(){
		transformor = new PhotosToSlides();
	}
	
	@Test
	void test() {
		Photo ph1 = new Photo();
		ph1.orientation = Orientation.HORIZONTAL;
		Photo ph2 = new Photo();
		Photo pv3 = new Photo();
		pv3.orientation = Orientation.VERTICAL;
		ph2.orientation = Orientation.VERTICAL;
		Photo[] testPhotos = {ph1,ph2,pv3};
		
		List<Slide> resultSize = transformor.transform(Arrays.asList(testPhotos));
		
		assertEquals(2, resultSize.size());
		
		
	}

}
