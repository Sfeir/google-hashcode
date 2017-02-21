package com.sfeir.hashcode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    private static String OUTPUT_FOLDER = "/tmp";

    public static void main( String[] args ) throws URISyntaxException, IOException {
        // READ input
        String inputName = "test.txt";
        Path inputPath = Paths.get(App.class.getResource("/" + inputName).toURI());
        List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        System.out.println(lines);

        // Write output
        Files.write(new File(OUTPUT_FOLDER, inputName + "-" + new Date().getTime()).toPath(), lines);
    }
}
