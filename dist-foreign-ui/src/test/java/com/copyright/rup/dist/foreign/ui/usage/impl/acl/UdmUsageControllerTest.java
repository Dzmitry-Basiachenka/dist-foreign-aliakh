package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamSource.class})
public class UdmUsageControllerTest {

    private final UdmUsageController controller = new UdmUsageController();
    private final UsageFilter usageFilter = new UsageFilter();
    private IUdmUsageService udmUsageService;
    private CsvProcessorFactory csvProcessorFactory;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, udmUsageService);
    }

    @Test
    public void testLoadBeans() {
        List<UdmUsageDto> udmUsages = Collections.singletonList(new UdmUsageDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(udmUsages).once();
        replay(udmUsageService);
        assertSame(udmUsages, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(udmUsageService);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmUsageService.getUsagesCount(usageFilter)).andReturn(10).once();
        replay(udmUsageService);
        assertEquals(10, controller.getBeansCount());
        verify(udmUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetUdmUsageCsvProcessor() {
        UdmCsvProcessor processorMock = createMock(UdmCsvProcessor.class);
        expect(csvProcessorFactory.getUdmCsvProcessor()).andReturn(processorMock).once();
        replay(csvProcessorFactory);
        assertSame(processorMock, controller.getCsvProcessor());
        verify(csvProcessorFactory);
    }
}
