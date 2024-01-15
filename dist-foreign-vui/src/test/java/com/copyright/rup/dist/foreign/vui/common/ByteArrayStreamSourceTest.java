package com.copyright.rup.dist.foreign.vui.common;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource.IByteArrayReportWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Verifies {@link ByteArrayStreamSource}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/11/2024
 *
 * @author Aliaksandr Liakh
 */
public class ByteArrayStreamSourceTest {

    @Test
    public void testGetSource() throws IOException {
        String fileNamePrefix = "report";
        IByteArrayReportWriter reportWriter = createMock(IByteArrayReportWriter.class);
        reportWriter.writeReport(anyObject(OutputStream.class));
        expectLastCall().once();
        ByteArrayStreamSource byteArrayStreamSource = new ByteArrayStreamSource(fileNamePrefix, reportWriter);
        replay(reportWriter);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source = byteArrayStreamSource.getSource();
        String fileName = source.getKey().get();
        assertTrue(fileName.startsWith(fileNamePrefix));
        assertTrue(fileName.endsWith(".csv"));
        try (InputStream is = source.getValue().get()) {
            IOUtils.toString(is, StandardCharsets.UTF_8);
        }
        verify(reportWriter);
    }
}
