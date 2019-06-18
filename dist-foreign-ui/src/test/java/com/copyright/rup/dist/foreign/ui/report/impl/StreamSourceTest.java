package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.util.CommonDateUtils;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

/**
 * Verifies {@link StreamSource}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/04/2019
 *
 * @author Aliaksanr Liakh
 */
public class StreamSourceTest {

    @Test
    public void testConstructor() throws IOException {
        String fileName = "file name ";
        String fileContent = "file content";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(fileContent, StandardCharsets.UTF_8);
        StreamSource streamSource = new StreamSource(fileNameSupplier, "csv", isSupplier);
        assertEquals("file_name_" + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            streamSource.getFileName());
        try (InputStream is = streamSource.getStream()) {
            String text = IOUtils.toString(is, StandardCharsets.UTF_8.name());
            assertEquals(fileContent, text);
        }
    }
}
