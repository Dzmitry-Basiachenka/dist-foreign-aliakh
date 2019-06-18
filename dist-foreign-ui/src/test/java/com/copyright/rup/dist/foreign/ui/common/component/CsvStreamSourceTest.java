package com.copyright.rup.dist.foreign.ui.common.component;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.ui.report.api.ICsvReportProvider;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * Verifies {@link CsvStreamSource}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class CsvStreamSourceTest {

    private CsvStreamSource csvStreamSource;
    private ICsvReportProvider csvReportProvider;

    @Before
    public void setUp() {
        csvReportProvider = createMock(ICsvReportProvider.class);
        csvStreamSource = new CsvStreamSource(csvReportProvider);
    }

    @Test
    public void testGetFileName() {
        String fileName = "file name.csv";
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getFileName()).andReturn(fileName).once();
        expect(csvReportProvider.getCsvStreamSource()).andReturn(streamSource).once();
        replay(csvReportProvider, streamSource);
        assertEquals(fileName, csvStreamSource.getFileName());
        verify(csvReportProvider, streamSource);
    }

    @Test
    public void doGetStream() {
        InputStream is = createMock(InputStream.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getStream()).andReturn(is).once();
        expect(csvReportProvider.getCsvStreamSource()).andReturn(streamSource).once();
        replay(csvReportProvider, streamSource);
        assertEquals(is, csvStreamSource.getStream());
        verify(csvReportProvider, streamSource);
    }
}
