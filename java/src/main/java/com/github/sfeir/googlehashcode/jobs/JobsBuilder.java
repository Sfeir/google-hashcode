package com.github.sfeir.googlehashcode.jobs;

import com.github.sfeir.googlehashcode.compute.Job;
import com.github.sfeir.googlehashcode.output.Slide;

import java.util.*;

public class JobsBuilder {

    public static Queue<Job> build(List<Slide> slides) {

        Queue<Job> queue = new ArrayDeque<>();

        for (int i = 0; i < slides.size(); i++) {
            queue.add(new Job(slides, i));
        }

        return queue;
    }

}
