package com.github.sfeir.googlehashcode.output;

import java.util.HashSet;
import java.util.Set;

public class Scorer {

    public static int score(final Slide slide1, final Slide slide2) {

        Set<String> tags1 = slide1.getTags();
        Set<String> tags2 = slide2.getTags();

        Set<String> intersection = new HashSet<>(tags1); // use the copy constructor
        intersection.retainAll(tags2);

        return Math.min(
                tags1.size() - intersection.size(),
                Math.min(
                    tags2.size() - intersection.size(),
                    intersection.size()
                )
        );
    }
}
