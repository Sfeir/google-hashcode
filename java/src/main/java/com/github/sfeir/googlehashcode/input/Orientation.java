package com.github.sfeir.googlehashcode.input;

public enum Orientation {
	HORIZONTAL,
	VERTICAL;
	
	public static Orientation fromString(String value) {
		if (value.equals("V") || value.equals("v")) {
			return Orientation.VERTICAL;
		}
		return Orientation.HORIZONTAL;
	}
}
