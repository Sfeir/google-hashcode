package com.github.sfeir.googlehashcode.utils;

import com.github.sfeir.googlehashcode.utils.io.writer.ObjectListToFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

class TestWriter {

	@Test
	void write() {

		ObjectListToFile<TestWriteObject> objectListToFile = new ObjectListToFile<>();

		ArrayList<TestWriteObject> lines = new ArrayList<>();
		lines.add(new TestWriteObject("hash","code",5,123456789L));
		lines.add(new TestWriteObject("sfeir","lille",5,987654321L));

		try {
			File tempFile = File.createTempFile("test_write", ".csv");
			System.out.println("Temp file On Default Location: " + tempFile.getAbsolutePath());
			objectListToFile.write(tempFile.getAbsolutePath(), lines);
		} catch (Exception e) {
			System.out.println("Exception occurred");
			e.printStackTrace();
		}

	}

	class TestWriteObject {
		private String firstName;
		private String lastName;
		private Integer age;
		private Long date;

		public TestWriteObject(String firstName, String lastName, Integer age, Long date) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.date = date;
		}

		@Override
		public String toString() {
			return "\"" + firstName + "\"," + "\"" + lastName+ "\"," + age + "," + date;
		}
	}

}
