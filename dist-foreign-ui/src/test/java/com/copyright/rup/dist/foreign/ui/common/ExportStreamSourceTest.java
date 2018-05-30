package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource.IReportWriter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;

/**
 * Verifies {@link ExportStreamSource}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/20/18
 *
 * @author Aliaksandr Radkevich
 */
public class ExportStreamSourceTest {

    private ExportStreamSource streamSource;
    private IReportWriter reportWriter;

    @Before
    public void setUp() {
        reportWriter = createMock(IReportWriter.class);
        streamSource = new ExportStreamSource("file_name_", reportWriter);
    }

    @Test
    public void testGetStream() {
        ExecutorService executorService = EasyMock.createMock(ExecutorService.class);
        Whitebox.setInternalState(streamSource, executorService);
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(reportWriter, executorService);
        assertNotNull(streamSource.getStream());
        verify(reportWriter, executorService);
    }

    @Test
    public void testGetFileName() {
        assertEquals("file_name_" + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            streamSource.getFileName());
    }
}
