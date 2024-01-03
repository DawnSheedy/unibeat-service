package com.dawnsheedy.util;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static String getMimetype(File file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file);
    }
}
