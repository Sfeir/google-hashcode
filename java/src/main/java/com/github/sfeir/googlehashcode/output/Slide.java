package com.github.sfeir.googlehashcode.output;

import com.github.sfeir.googlehashcode.input.Photo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Slide {

    private Photo photo1;
    private Photo photo2;

    public Slide(Photo photo1) {
        this.photo1 = photo1;
    }

    public Slide(Photo photo1, Photo photo2) {
        this.photo1 = photo1;
        this.photo2 = photo2;
    }

    public Set<String> getTags() {
        HashSet<String> tags = new HashSet<>(photo1.tags);
        if (photo2 != null) {
            tags.addAll(photo2.tags);
        }
        return tags;
    }

    public String toString() {
        String output = String.valueOf(photo1.id);
        if (photo2 != null) {
            output += " " + photo2.id;
        }
        return output;
    }

    @Override
    public boolean equals(Object o) {
        return photo1.id == ((Slide)o).photo1.id;
    }

    @Override
    public int hashCode() {
        return photo1.id;
    }
}
