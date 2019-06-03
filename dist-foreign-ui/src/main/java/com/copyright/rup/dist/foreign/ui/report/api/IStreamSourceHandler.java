package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import java.io.PipedOutputStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Interface to get implementations of {@link IStreamSource} for reports of different types.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/04/2019
 *
 * @author Aliaksanr Liakh
 */
public interface IStreamSourceHandler {

    /**
     * Gets {@link IStreamSource} for CSV file.
     *
     * @param fileNameSupplier {@link Supplier} to get file name
     * @param posConsumer      {@link Consumer} to get file content
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getCsvStreamSource(Supplier<String> fileNameSupplier, Consumer<PipedOutputStream> posConsumer);
}
