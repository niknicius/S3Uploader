package io.nanuvem.s3upload.utils;

import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {

    public static List<Path> walk(Path locationPath) throws IOException {
        List<Path> files = new LinkedList<>();
        Files.walk(locationPath).filter(Files::isRegularFile).forEach(files::add);
        return files;
    }

}
