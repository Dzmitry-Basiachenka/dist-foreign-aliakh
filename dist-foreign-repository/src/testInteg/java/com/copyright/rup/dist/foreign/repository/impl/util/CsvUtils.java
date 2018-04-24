package com.copyright.rup.dist.foreign.repository.impl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that provides functionality for testing CSV files.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/23/2018
 *
 * @author Aliaksandr Liakh
 */
public final class CsvUtils {

    private CsvUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Read lines from an instance of {@link InputStream}.
     *
     * @param is the instance of {@link InputStream} of a CSV
     * @return the CSV lines
     */
    public static List<String> readLines(InputStream is) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Read lines from a file.
     *
     * @param path     the path
     * @param fileName the file name of a CSV
     * @return the CSV lines
     */
    public static List<String> readLines(String path, String fileName) throws IOException {
        try (InputStream is = Files.newInputStream(Paths.get(path, fileName))) {
            return readLines(is);
        }
    }
}
