package com.github.sfeir.googlehashcode.input;

import java.util.HashSet;
import java.util.Set;

public class Photo {
	public int id;
	public Orientation orientation;
	public Set<String> tags;
	
	public Photo() {
		this.orientation = Orientation.HORIZONTAL;
		this.tags = new HashSet<>();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Photo " + String.valueOf(this.id) + " : (Orientation " + this.orientation.name() + " tags : <");
		for (String t : tags) {
			result.append(t).append(" ");
		}
		result.append(">");
		return result.toString();
	}
}
