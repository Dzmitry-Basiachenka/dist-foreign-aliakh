package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageWidget;

import com.vaadin.ui.HorizontalLayout;

import org.apache.commons.io.IOUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ByteArrayStreamSource.class, OffsetDateTime.class})
public class AaclUsageControllerTest {

    private AaclUsageController controller;
    private IAaclUsageWidget usagesWidget;
    private IAaclUsageFilterController filterController;
    private IAaclUsageFilterWidget filterWidgetMock;
    private IUsageBatchService usageBatchService;
    private IResearchService researchService;
    private IAaclUsageService aaclUsageService;
    private CsvProcessorFactory csvProcessorFactory;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new AaclUsageController();
        usagesWidget = createMock(IAaclUsageWidget.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IAaclUsageFilterController.class);
        filterWidgetMock = createMock(IAaclUsageFilterWidget.class);
        researchService = createMock(IResearchService.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, researchService);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, aaclUsageService);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        usageFilter = new UsageFilter();
    }

    @Test
    public void getBeansCount() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidgetMock, aaclUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidgetMock, aaclUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(aaclUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidgetMock, aaclUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(filterWidgetMock, aaclUsageService, filterController);
    }

    @Test
    public void testIsValidFilteredUsageStatus() {
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND))
            .andReturn(true).once();
        replay(filterController, filterWidgetMock, aaclUsageService);
        assertTrue(controller.isValidFilteredUsageStatus(UsageStatusEnum.RH_FOUND));
        verify(filterController, filterWidgetMock, aaclUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testOnFilterChanged() {
        FilterChangedEvent filterChangedEvent = new FilterChangedEvent(new HorizontalLayout());
        usagesWidget.refresh();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock, researchService);
        controller.onFilterChanged(filterChangedEvent);
        verify(usageBatchService, filterController, filterWidgetMock, researchService);
    }

    @Test
    public void testLoadUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertAaclBatch(usageBatch, usages)).andReturn(1).once();
        usageBatchService.sendForMatching(usages);
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock, researchService);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidgetMock, researchService);
    }

    @Test
    public void testGetSendForClassificationUsagesStreamSource() throws IOException {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("FAS");
        Capture<OutputStream> outputStreamCapture = new Capture<>();
        OffsetDateTime date = OffsetDateTime.parse("2020-01-21T02:10:37-05:00");
        mockStatic(OffsetDateTime.class);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(filter).once();
        researchService.sendForClassification(same(filter), capture(outputStreamCapture));
        expectLastCall().andAnswer(() -> {
            OutputStream out = outputStreamCapture.getValue();
            IOUtils.write("report content", out);
            IOUtils.closeQuietly(out);
            return null;
        }).once();
        expect(OffsetDateTime.now()).andReturn(date).once();
        replay(OffsetDateTime.class, usageBatchService, filterController, filterWidgetMock, researchService);
        IStreamSource streamSource = controller.getSendForClassificationUsagesStreamSource();
        assertEquals("send_for_classification_01_21_2020_02_10.csv", streamSource.getSource().getKey().get());
        assertEquals("report content", IOUtils.toString(streamSource.getSource().getValue().get()));
        verify(OffsetDateTime.class, usageBatchService, filterController, filterWidgetMock, researchService);
    }

    @Test
    public void testGetClassifiedUsageCsvProcessor() {
        ClassifiedUsageCsvProcessor processorMock = createMock(ClassifiedUsageCsvProcessor.class);
        expect(csvProcessorFactory.getClassifiedUsageCsvProcessor()).andReturn(processorMock).once();
        replay(csvProcessorFactory);
        assertSame(processorMock, controller.getClassifiedUsageCsvProcessor());
        replay(csvProcessorFactory);
    }

    @Test
    public void testLoadClassifiedUsages() {
        // TODO {srudak} implement test once service logic is ready
    }
}
