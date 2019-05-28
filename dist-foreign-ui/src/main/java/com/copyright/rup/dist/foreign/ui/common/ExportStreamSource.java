package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.util.VaadinUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An implementation of {@link com.vaadin.server.StreamResource.StreamSource}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/19/18
 *
 * @author Aliaksandr Radkevich
 */
public class ExportStreamSource implements IStreamSource {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final IReportWriter reportWriter;
    private final String fileNamePrefix;

    /**
     * Constructor.
     *
     * @param fileNamePrefix the leftmost part of the export file name
     * @param reportWriter   an instance of {@link IReportWriter}
     */
    public ExportStreamSource(String fileNamePrefix, IReportWriter reportWriter) {
        this.fileNamePrefix = fileNamePrefix;
        this.reportWriter = reportWriter;
    }

    @Override
    public InputStream getStream() {
        try {
            PipedOutputStream outputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
            executorService.execute(() -> reportWriter.writeReport(outputStream));
            return pipedInputStream;
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public String getFileName() {
        return VaadinUtils.encodeAndBuildFileName(getFileNamePrefix() +
            CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm"), "csv");
    }

    /**
     * Gets file name prefix.
     *
     * @return file name prefix
     */
    protected String getFileNamePrefix() {
        return fileNamePrefix;
    }

    /**
     * Implement this interface to write report data to the given stream.
     */
    @FunctionalInterface
    public interface IReportWriter {

        /**
         * Writes report data to the given {@link PipedOutputStream}.
         *
         * @param pipedOutputStream output stream to write data to
         */
        void writeReport(PipedOutputStream pipedOutputStream);
    }
}
