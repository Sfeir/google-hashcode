package com.github.sfeir.googlehashcode.compute;

import com.github.sfeir.googlehashcode.output.Scorer;
import com.github.sfeir.googlehashcode.output.Slide;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Job implements Runnable {

    private List<Slide> slides;

    private Integer score;

    public Job(List<Slide> slides) {
        this.slides = slides;
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
            return score - o.score;
        }
    }

    public void run() {
        Slide selectedSlide = slides.get(0);
        score = 0;

        while (slides.size() > 0) {
            slides.remove(selectedSlide);
            final Slide current = selectedSlide;
            ScoredImage scoredImage = slides.stream()
                    .map(slide -> new ScoredImage(Scorer.score(slide, current), slide))
                    .sorted()
                    .findFirst().get();
            score += scoredImage.score;
            selectedSlide = scoredImage.slide;
        }
    }

}
