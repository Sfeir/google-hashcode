package com.github.sfeir.googlehashcode.compute;

import com.github.sfeir.googlehashcode.output.Slide;

import java.util.List;

public class Job implements Runnable {

    private List<Slide> slides;
    private Integer score = 0;


    public Job(List<Slide> slides) {
        this.slides = slides;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public void run() {

    }
}
