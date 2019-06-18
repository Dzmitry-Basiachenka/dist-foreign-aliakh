package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.ui.report.api.IStreamSourceHandler;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Implementation of {@link IStreamSourceHandler}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/04/2019
 *
 * @author Aliaksanr Liakh
 */
@Service
public class StreamSourceHandler implements IStreamSourceHandler {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public IStreamSource getCsvStreamSource(Supplier<String> fileNameSupplier,
                                            Consumer<PipedOutputStream> posConsumer) {
        return new StreamSource(fileNameSupplier, "csv", () -> getInputStream(posConsumer));
    }

    private InputStream getInputStream(Consumer<PipedOutputStream> posConsumer) {
        try {
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);
            executorService.execute(() -> posConsumer.accept(pos));
            return pis;
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }
}
