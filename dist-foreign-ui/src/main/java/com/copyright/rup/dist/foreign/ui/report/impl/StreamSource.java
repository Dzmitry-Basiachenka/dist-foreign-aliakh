package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.vaadin.ui.component.downloader.FileDownloadException;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.UI;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

/**
 * Implementation of {@link IStreamSource} for file sources.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/04/2019
 *
 * @author Aliaksanr Liakh
 */
public class StreamSource implements IStreamSource {

    private final Supplier<String> fileNameSupplier;
    private final String extension;
    private final Supplier<InputStream> isSupplier;

    /**
     * Constructor.
     *
     * @param fileNameSupplier {@link Supplier} to get file name
     * @param extension        file name extension
     * @param isSupplier       {@link Supplier} to get file content
     */
    public StreamSource(Supplier<String> fileNameSupplier, String extension, Supplier<InputStream> isSupplier) {
        this.fileNameSupplier = fileNameSupplier;
        this.extension = extension;
        this.isSupplier = isSupplier;
    }

    @Override
    public String getFileName() {
        return VaadinUtils.encodeAndBuildFileName(
            fileNameSupplier.get() + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm"),
            extension);
    }

    @Override
    public InputStream getStream() {
        InputStream is = null;
        try {
            is = isSupplier.get();
        } catch (FileDownloadException e) {
            UI.getCurrent().getErrorHandler().error(new ErrorEvent(e));
        }
        return is;
    }
}
