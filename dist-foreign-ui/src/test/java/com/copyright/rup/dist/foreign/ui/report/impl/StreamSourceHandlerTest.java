package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import org.easymock.Capture;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.PipedInputStream;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link StreamSourceHandler}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/04/2019
 *
 * @author Aliaksanr Liakh
 */
public class StreamSourceHandlerTest {

    @Test
    public void testGetCsvStreamSource() {
        StreamSourceHandler streamSourceHandler = new StreamSourceHandler();
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(streamSourceHandler, executorService);
        Capture<Runnable> runnableCapture = new Capture<>();
        executorService.execute(capture(runnableCapture));
        expectLastCall().once();
        replay(executorService);
        IStreamSource streamSource = streamSourceHandler.getCsvStreamSource(createMock(Supplier.class),
            createMock(Consumer.class));
        assertTrue(streamSource instanceof StreamSource);
        assertTrue(streamSource.getStream() instanceof PipedInputStream);
        assertNotNull(runnableCapture.getValue());
        verify(executorService);
    }
}
