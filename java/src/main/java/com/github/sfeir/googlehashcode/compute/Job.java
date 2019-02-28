package com.github.sfeir.googlehashcode.compute;

import com.github.sfeir.googlehashcode.output.Slide;

import java.util.List;
import java.util.concurrent.Callable;

public class Job implements Callable<Integer> {

    private List<Slide> slides;


    public Job(List<Slide> slides) {
        this.slides = slides;
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }

}
