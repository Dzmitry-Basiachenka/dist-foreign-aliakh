package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM_dd_YYYY");

    private final ISendForResearchReportWriter reportWriter;
    private final String fileNamePrefix;

    /**
     * Constructor.
     *
     * @param fileNamePrefix the leftmost part of the export file name
     * @param reportWriter   an instance of {@link ISendForResearchReportWriter}
     */
    public ByteArrayStreamSource(String fileNamePrefix, ISendForResearchReportWriter reportWriter) {
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
        return VaadinUtils.encodeAndBuildFileName(fileNamePrefix + LocalDate.now().format(FORMATTER), "csv");
    }

    /**
     * Implement this interface to write report data to the given stream.
     */
    @FunctionalInterface
    public interface ISendForResearchReportWriter {

        /**
         * Writes report data to the given {@link OutputStream}.
         *
         * @param outputStream output stream to write data to
         */
        void writeReport(OutputStream outputStream);
    }
}
