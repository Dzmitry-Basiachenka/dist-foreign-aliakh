package com.copyright.rup.dist.foreign.vui.common;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.common.util.FileNameUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * An implementation of {@link IStreamSource} that can be used when report should be generated in current thread.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/20/2018
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
    public Entry<Supplier<String>, Supplier<InputStream>> getSource() {
        return new SimpleImmutableEntry<>(
            () -> FileNameUtils.encodeAndBuildFileName(fileNamePrefix +
                CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_yyyy_HH_mm"), "csv"),
            () -> {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                reportWriter.writeReport(baos);
                return new ByteArrayInputStream(baos.toByteArray());
            }
        );
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
