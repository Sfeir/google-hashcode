package com.github.sfeir.googlehashcode.compute;

import com.github.sfeir.googlehashcode.output.Scorer;
import com.github.sfeir.googlehashcode.output.Slide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Job implements Runnable {

    private List<Slide> slides;
    private Integer score;
    private int idx;

    public Job(List<Slide> slides, int i) {
        this.slides = slides;
        this.idx = i;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public Integer getScore() {
        return score;
    }

    private class ScoredImage implements Comparable<ScoredImage> {
        public int score;
        public Slide slide;

        public ScoredImage(int score, Slide slide) {
            this.score = score;
            this.slide = slide;
        }

        @Override
        public int compareTo(ScoredImage o) {
            return o.score - score;
        }
    }

    public void run() {
        Slide selectedSlide = slides.get(this.idx);
        score = 0;

      //  while (slides.size() > 1) {
            final Slide current = selectedSlide;
            final List<ScoredImage> scoreds = new ArrayList<>();
            for (int i=this.idx+1; i < slides.size(); i++) {
            	Slide slide = slides.get(i);
            	scoreds.add(new ScoredImage(Scorer.score(slide, current), slide));
            }
            for (int i=0; i < this.idx; i++) {
            	Slide slide = slides.get(i);
            	scoreds.add(new ScoredImage(Scorer.score(slide, current), slide));
            }
            Collections.sort(scoreds);
            score += scoreds.get(0).score;
            selectedSlide = scoreds.get(0).slide;
     //       slides.remove(selectedSlide);
     //   }
    }
}
