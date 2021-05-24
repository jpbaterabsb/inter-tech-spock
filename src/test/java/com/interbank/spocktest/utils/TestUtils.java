package com.interbank.spocktest.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
    public static String extractJsonToPath(String path) throws IOException {
        String url = TestUtils.class
                .getClassLoader().getResource(path).getFile();
        return Files.readString(Paths.get(url));
    }
}
