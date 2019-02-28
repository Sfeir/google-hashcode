package com.github.sfeir.googlehashcode.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.sfeir.googlehashcode.utils.io.readers.LineToObjectReader;
import com.github.sfeir.googlehashcode.utils.io.readers.Reader;
import com.github.sfeir.googlehashcode.utils.io.streamers.Behavior;
import com.github.sfeir.googlehashcode.utils.io.streamers.LineToObjectStreamer;
import com.github.sfeir.googlehashcode.utils.io.streamers.Streamer;

class BenchmarkStreamerVSReader {
	private static final DateFormat DF = new SimpleDateFormat("mm:ss.SSS");
	private static final String EXAMPLE_A = BenchmarkStreamerVSReader.class.getClassLoader().getResource("example-folder/a_example.in").getFile();
	private static final String EXAMPLE_B = BenchmarkStreamerVSReader.class.getClassLoader().getResource("example-folder/b_small.in").getFile();
	private static final String EXAMPLE_C = BenchmarkStreamerVSReader.class.getClassLoader().getResource("example-folder/c_medium.in").getFile();
	private static final String EXAMPLE_D = BenchmarkStreamerVSReader.class.getClassLoader().getResource("example-folder/d_big.in").getFile();
	private static final String EXAMPLE_E = BenchmarkStreamerVSReader.class.getClassLoader().getResource("example-folder/e_large.in").getFile();
	
	
	@Test
	void bench() throws IOException {
		System.out.println("Starting Bench...");
		Date clockR = null;
		Date clockS = null;
		Date start = null;
		Date readerD = null;
		Date streamerD = null;
		
		// a_example.in
		System.out.println("Using file a_example.in (26b) ");
		start = new Date();
		useReader(EXAMPLE_A);
		clockR = new Date();
		useStreamer(EXAMPLE_A);
		clockS = new Date();
		streamerD = new Date(clockS.getTime() - clockR.getTime());
		readerD = new Date(clockR.getTime() - start.getTime());
		System.out.println("Reader : " + DF.format(readerD) + " -- Streamer : " + DF.format(streamerD));

		// b_small.in
		System.out.println("Using file b_small.in (56b) ");
		start = new Date();
		useReader(EXAMPLE_B);
		clockR = new Date();
		useStreamer(EXAMPLE_B);
		clockS = new Date();
		streamerD = new Date(clockS.getTime() - clockR.getTime());
		readerD = new Date(clockR.getTime() - start.getTime());
		System.out.println("Reader : " + DF.format(readerD) + " -- Streamer : " + DF.format(streamerD));
		

		// c_medium.in
		System.out.println("Using file c_medium.in (49Kb) ");
		start = new Date();
		useReader(EXAMPLE_C);
		clockR = new Date();
		useStreamer(EXAMPLE_C);
		clockS = new Date();
		streamerD = new Date(clockS.getTime() - clockR.getTime());
		readerD = new Date(clockR.getTime() - start.getTime());
		System.out.println("Reader : " + DF.format(readerD) + " -- Streamer : " + DF.format(streamerD));

		// d_big.in
		System.out.println("Using file d_big.in (265Ko) ");
		start = new Date();
		useReader(EXAMPLE_D);
		clockR = new Date();
		useStreamer(EXAMPLE_D);
		clockS = new Date();
		streamerD = new Date(clockS.getTime() - clockR.getTime());
		readerD = new Date(clockR.getTime() - start.getTime());
		System.out.println("Reader : " + DF.format(readerD) + " -- Streamer : " + DF.format(streamerD));

		// e_large.in
		System.out.println("Using file e_large.in (2.4Mo) ");
		start = new Date();
		useReader(EXAMPLE_E);
		clockR = new Date();
		useStreamer(EXAMPLE_E);
		clockS = new Date();
		streamerD = new Date(clockS.getTime() - clockR.getTime());
		readerD = new Date(clockR.getTime() - start.getTime());
		System.out.println("Reader : " + DF.format(readerD) + " -- Streamer : " + DF.format(streamerD));
	}
	
	private static void useReader(String file) throws IOException {
		System.out.println("Reading " + new File(file).getName() + " using Reader");
		Reader<List<MappedObject>> reader = new LineToObjectReader<MappedObject>(new MapperOfObject());
		List<MappedObject> resultList = reader.read(file);
		for (MappedObject counter: resultList) {
			counter.hashCode(); // Useless operation just to "manipulate it".
		}
	}
	
	private static void useStreamer(String file) throws IOException {
		System.out.println("Reading " + new File(file).getName() + " using Streamer");
		Streamer<MappedObject> streamer = new LineToObjectStreamer<MappedObject>(new MapperOfObject());
		streamer.use(new Behavior<BenchmarkStreamerVSReader.MappedObject>() {
			@Override
			public void apply(MappedObject line) {
				line.hashCode(); // Useless operation just to "manipulate it".
			}
		});
		streamer.stream(file);
	}
	
	private static class MappedObject {
		private int tNumber;
		private int mNumber;
		private int lineNumber;
		
		public MappedObject(int l, int t, int m) {
			this.lineNumber = l;
			this.tNumber = t;
			this.mNumber = m;
		}
		
		@Override
		public String toString() {
			return "Line " + this.lineNumber + " => T: " + this.tNumber + " M: " + this.mNumber;
		}
	}
	
	private static class MapperOfObject implements Mapper<MappedObject> {
		private int lineSeen = 0;
		@Override
		public MappedObject map(int lineNumber, String t) {
			int tCounter = 0;
			int mCounter = 0;
			this.lineSeen++;
			for (int i=0; i < t.length(); i++) {
				if (t.charAt(i) == 'T') { 
					tCounter++;
				} else if (t.charAt(i) == 'M') {
					mCounter++;
				}
			}
			return new MappedObject(this.lineSeen, tCounter, mCounter);
		}
	}

}
