package ru.mai.folderssaver.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    public Resource getFileAsResource(String path) throws IOException {
        Path file = Paths.get("/files/").resolve(path);

        return new UrlResource(file.toUri());
    }
}

