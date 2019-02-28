package com.github.sfeir.googlehashcode.jobs;

import com.github.sfeir.googlehashcode.compute.Job;
import com.github.sfeir.googlehashcode.output.Slide;

import java.util.*;

public class JobsBuilder {

    public static Queue<Job> build(List<Slide> slides) {

        Queue<Job> queue = new ArrayDeque<>();

        queue.add(new Job(new ArrayList<>(slides)));

        for (int i = 1; i < slides.size(); i++) {
            List<Slide> list = slides.subList(i, slides.size());
            list.addAll(slides.subList(0, i-1));
            queue.add(new Job(list));
        }

        return queue;
    }

}
