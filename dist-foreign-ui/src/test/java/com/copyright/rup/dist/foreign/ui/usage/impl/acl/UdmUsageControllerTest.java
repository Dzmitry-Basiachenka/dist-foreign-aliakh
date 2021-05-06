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
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
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

    private static final String UDM_BATCH_UID = "5acc58a4-49c0-4c20-b96e-39e637a0657f";
    private static final String UDM_USAGE_UID_1 = "35c42bac-c6f6-4559-a788-206c376dc969";
    private static final String UDM_USAGE_UID_2 = "1854c871-3a1b-40e9-84e6-d854dbc77a76";
    private static final String UDM_USAGE_ORIGIN_UID_1 = "2e8f2ad2-62cb-4f75-9c7b-45c8892aa9ed";
    private static final String UDM_USAGE_ORIGIN_UID_2 = "d9cc5299-94c7-4e03-bab2-e4ad42efe385";
    private static final int EXPECTED_COUNT = 2;

    private final UdmUsageController controller = new UdmUsageController();
    private final UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
    private IUdmUsageService udmUsageService;
    private IUdmBatchService udmBatchService;
    private CsvProcessorFactory csvProcessorFactory;
    private IUdmUsageFilterController udmUsageFilterController;
    private IUdmUsageFilterWidget udmUsageFilterWidget;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        udmBatchService = createMock(IUdmBatchService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        udmUsageFilterController = createMock(IUdmUsageFilterController.class);
        udmUsageFilterWidget = createMock(IUdmUsageFilterWidget.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, udmUsageFilterController);
        Whitebox.setInternalState(controller, udmUsageFilterWidget);
        Whitebox.setInternalState(controller, udmBatchService);
    }

    @Test
    public void testLoadBeans() {
        List<UdmUsageDto> udmUsages = Collections.singletonList(new UdmUsageDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(udmUsageFilter).once();
        expect(udmUsageService.getUsageDtos(eq(udmUsageFilter), capture(pageableCapture), isNull()))
            .andReturn(udmUsages).once();
        replay(udmUsageFilterController, udmUsageFilterWidget, udmUsageService);
        assertSame(udmUsages, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(udmUsageFilterController, udmUsageFilterWidget, udmUsageService);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(udmUsageFilter).once();
        expect(udmUsageService.getUsagesCount(udmUsageFilter)).andReturn(10).once();
        replay(udmUsageFilterController, udmUsageFilterWidget, udmUsageService);
        assertEquals(10, controller.getBeansCount());
        verify(udmUsageFilterController, udmUsageFilterWidget, udmUsageService);
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

    @Test
    public void testLoadUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        List<UdmUsage> udmUsages = Arrays.asList(
            buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1),
            buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2));
        expect(udmBatchService.insertUdmBatch(udmBatch, udmUsages)).andReturn(EXPECTED_COUNT).once();
        replay(udmBatchService);
        int count = controller.loadUdmBatch(udmBatch, udmUsages);
        verify(udmBatchService);
        assertEquals(EXPECTED_COUNT, count);
    }

    private UdmUsage buildUdmUsage(String usageId, String originId) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageId);
        udmUsage.setOriginalDetailId(originId);
        udmUsage.setBatchId(UDM_BATCH_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
        udmUsage.setPeriodEndDate(LocalDate.of(2021, 12, 31));
        udmUsage.setUsageDate(LocalDate.of(2021, 12, 12));
        udmUsage.setWrWrkInst(122825347L);
        udmUsage.setReportedStandardNumber("0927-7765");
        udmUsage.setReportedTitle("Colloids and surfaces. B, Biointerfaces");
        udmUsage.setReportedPubType("Journal");
        udmUsage.setPubFormat("format");
        udmUsage.setArticle("Green chemistry");
        udmUsage.setLanguage("English");
        udmUsage.setCompanyId(45489489L);
        udmUsage.setSurveyRespondent("fa0276c3-55d6-42cd-8ffe-e9124acae02f");
        udmUsage.setIpAddress("ip24.12.119.203");
        udmUsage.setSurveyCountry("United States");
        udmUsage.setSurveyStartDate(LocalDate.of(2021, 12, 12));
        udmUsage.setSurveyEndDate(LocalDate.of(2021, 12, 12));
        udmUsage.setReportedTypeOfUse("COPY_FOR_MYSELF");
        udmUsage.setQuantity(7);
        return udmUsage;
    }
}
