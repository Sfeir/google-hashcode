package com.sfeir.hashcode;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Video;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws URISyntaxException, IOException {
        // READ input
        String inputName = args[0];
        Path inputPath = Paths.get(App.class.getResource("/" + inputName).toURI());
        List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        System.out.println(lines);
        Init init = new Init(lines.remove(0));
        VideoFactory videoFactory = new VideoFactory(lines.remove(0));
        List<Video> videos = videoFactory.getVideos();
        List<Cache> caches = new ArrayList<>();
        for (int i = 0; i < init.numberOfCaches(); i++) {
            caches.add(new Cache(i, init.cacheSize()));
        }

        // Write output
        Files.write(new File(args[1]).toPath(), lines);
    }
}
