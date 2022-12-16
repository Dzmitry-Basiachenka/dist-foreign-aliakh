package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.aclci.AclciUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclciUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageControllerTest {

    private static final String USAGE_BATCH_NAME = "ACLCI Usage Batch";
    private static final Long LICENSEE_ACCOUNT_NUMBER = 1000008985L;
    private static final String LICENSEE_NAME = "FarmField Inc.";

    private AclciUsageController controller;
    private IAclciUsageFilterController filterController;
    private IAclciUsageFilterWidget filterWidget;
    private IUsageBatchService usageBatchService;
    private CsvProcessorFactory csvProcessorFactory;
    private AclciUsageService aclciUsageService;
    private ITelesalesService telesalesService;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new AclciUsageController();
        filterController = createMock(IAclciUsageFilterController.class);
        filterWidget = createMock(IAclciUsageFilterWidget.class);
        usageBatchService = createMock(IUsageBatchService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        aclciUsageService = createMock(AclciUsageService.class);
        telesalesService = createMock(ITelesalesService.class);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, aclciUsageService);
        Whitebox.setInternalState(controller, telesalesService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclciUsageWidget.class));
    }

    @Test
    public void testOnFilterChanged() {
        //TODO: implement
    }

    @Test
    public void testLoadUsageData() {
        //TODO: implement
    }

    @Test
    public void testGetBeansCount() {
        usageFilter.setLicenseTypes(Collections.singleton(AclciLicenseTypeEnum.CURR_REPUB_K12));
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aclciUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidget, aclciUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidget, aclciUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setLicenseTypes(Collections.singleton(AclciLicenseTypeEnum.CURR_REPUB_K12));
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(aclciUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.singletonList(new UsageDto())).once();
        replay(filterWidget, aclciUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(filterWidget, aclciUsageService, filterController);
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        //TODO: implement
    }

    @Test
    public void testIsValidFilteredUsageStatus() {
        //TODO: implement
    }

    @Test
    public void testIsBatchProcessingCompleted() {
        //TODO: implement
    }

    @Test
    public void testDeleteUsageBatch() {
        //TODO: implement
    }

    @Test
    public void testGetAclciUsageCsvProcessor() {
        AclciUsageCsvProcessor processor = createMock(AclciUsageCsvProcessor.class);
        expect(csvProcessorFactory.getAclciUsageCsvProcessor()).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getAclciUsageCsvProcessor());
        verify(csvProcessorFactory);
    }

    @Test
    public void testLoadAclciUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        List<Usage> usages = Collections.singletonList(new Usage());
        List<String> insertedUsageIds = Collections.singletonList("407be4e7-4f2e-4d5f-8610-09070c37880f");
        expect(usageBatchService.insertAclciBatch(usageBatch, usages)).andReturn(insertedUsageIds).once();
        aclciUsageService.sendForMatching(insertedUsageIds, USAGE_BATCH_NAME);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget);
        controller.loadAclciUsageBatch(usageBatch, usages);
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testGetErrorResultStreamSource() {
        //TODO{aliakh} implement after implementing AclciUsageBatchUploadWindow
    }

    @Test
    public void testGetLicenseeName() {
        expect(telesalesService.getLicenseeName(LICENSEE_ACCOUNT_NUMBER)).andReturn(LICENSEE_NAME).once();
        replay(telesalesService);
        assertSame(LICENSEE_NAME, controller.getLicenseeName(LICENSEE_ACCOUNT_NUMBER));
        verify(telesalesService);
    }
}
