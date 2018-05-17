package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.util.VaadinUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

/**
 * An implementation of {@link com.vaadin.server.StreamResource.StreamSource}.
 * Can be used when report should be generated in current thread.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 3/20/18
 *
 * @author Uladzislau Shalamitski
 */
public class ByteArrayStreamSource implements IStreamSource {

    private final IByteArrayReportWriter reportWriter;
    private final String fileNamePrefix;

    /**
     * Constructor.
     *
     * @param fileNamePrefix the leftmost part of the export file name
     * @param reportWriter   an instance of {@link IByteArrayReportWriter}
     */
    public ByteArrayStreamSource(String fileNamePrefix, IByteArrayReportWriter reportWriter) {
        this.fileNamePrefix = fileNamePrefix;
        this.reportWriter = reportWriter;
    }

    @Override
    public InputStream getStream() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        reportWriter.writeReport(byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Override
    public String getFileName() {
        return VaadinUtils.encodeAndBuildFileName(fileNamePrefix +
            CommonDateUtils.format(LocalDate.now(), "MM_dd_YYYY"), "csv");
    }

    /**
     * Implement this interface to write report data to the given stream.
     */
    @FunctionalInterface
    public interface IByteArrayReportWriter {

        /**
         * Writes report data to the given {@link OutputStream}.
         *
         * @param outputStream output stream to write data to
         */
        void writeReport(OutputStream outputStream);
    }
}
