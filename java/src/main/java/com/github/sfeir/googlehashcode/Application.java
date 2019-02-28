package com.github.sfeir.googlehashcode;

import com.github.sfeir.googlehashcode.compute.Job;
import com.github.sfeir.googlehashcode.input.Photo;
import com.github.sfeir.googlehashcode.input.PhotoMapper;
import com.github.sfeir.googlehashcode.jobs.JobsBuilder;
import com.github.sfeir.googlehashcode.output.Slide;
import com.github.sfeir.googlehashcode.photosslides.PhotosToSlideFactory;
import com.github.sfeir.googlehashcode.utils.io.readers.LineToObjectReader;
import com.github.sfeir.googlehashcode.utils.io.readers.Reader;
import com.github.sfeir.googlehashcode.utils.io.writer.ObjectListToFile;
import com.github.sfeir.googlehashcode.utils.io.writer.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
	public static void main(String[] args) throws IOException {
        Reader<List<Photo>> photoReader = new LineToObjectReader<Photo>(new PhotoMapper());
        if (args.length < 1) {
            System.out.println("usage : java --jar app.jar fileName_not_the_path");
        }

        String fileName = Application.class.getClassLoader().getResource(args[0]).getFile();
        List<Photo> photos = photoReader.read(fileName);

        List<Slide> slides = PhotosToSlideFactory.getPhotoToSlides().transform(photos);
        // TODO Use Slides !!

        Queue<Job> inputJobs = JobsBuilder.build(slides);
        List<Job> outputJobs = new ArrayList();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        while (!inputJobs.isEmpty()) {
            Job job = inputJobs.poll();
            executor.execute(job);
            outputJobs.add(job);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("all threads finished");

        // iterate on the jobs
        // get the better score
        Job best = null;
        Integer score = 0;
        for (Job job : outputJobs) {
            if (job.getScore() > score) {
                best = job;
                score = job.getScore();
            }
        }
        // write the best solution

        Writer<List<Slide>> slideWriter = new ObjectListToFile<>();
        slideWriter.write("output-" + new File(fileName).getName(), best.getSlides());

    }
}
