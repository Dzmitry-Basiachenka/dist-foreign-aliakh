package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link CommonUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/09/19
 *
 * @author Uladzislau Shalamitski
 */
public class CommonUsageControllerTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String USAGE_BATCH_ID = "bf6d5d00-0d61-453f-8a76-8a7b133efecc";
    private static final String SCENARIO_NAME = "Test Scenario Name";

    private TestUsageController controller;
    private IUsageService usageService;
    private IUsagesFilterController filterController;
    private IUsagesFilterWidget filterWidgetMock;
    private IUsageBatchService usageBatchService;
    private IScenarioService scenarioService;
    private IFundPoolService fundPoolService;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new TestUsageController();
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IUsagesFilterController.class);
        scenarioService = createMock(IScenarioService.class);
        filterWidgetMock = createMock(IUsagesFilterWidget.class);
        fundPoolService = createMock(IFundPoolService.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, fundPoolService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void getBeansCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        prepareGetAppliedFilterExpectations(filter);
        expect(usageService.getUsagesCount(filter)).andReturn(1).once();
        replay(filterWidgetMock, usageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidgetMock, usageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setFiscalYear(2017);
        prepareGetAppliedFilterExpectations(usageFilter);
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidgetMock, usageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(filterWidgetMock, usageService, filterController);
    }

    @Test
    public void testInitUsagesFilterWidget() {
        UsagesFilterWidget filterWidget = new UsagesFilterWidget();
        Whitebox.setInternalState(controller, "filterController", filterController);
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(filterController);
        assertSame(filterWidget, controller.initUsagesFilterWidget());
        verify(filterController);
    }

    @Test
    public void testGetScenariosNamesAssociatedWithUsageBatch() {
        List<String> names = Collections.singletonList(SCENARIO_NAME);
        expect(scenarioService.getScenariosNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(names).once();
        replay(scenarioService);
        assertSame(names, controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID));
        verify(scenarioService);
    }

    @Test
    public void testGetUsageBatches() {
        expect(usageBatchService.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(Collections.emptyList()).once();
        replay(usageBatchService);
        controller.getUsageBatches(FAS_PRODUCT_FAMILY);
        verify(usageBatchService);
    }

    @Test
    public void testUsageBatchExists() {
        expect(usageBatchService.usageBatchExists("Usage Batch Name")).andReturn(false).once();
        replay(usageBatchService);
        assertFalse(controller.usageBatchExists("Usage Batch Name"));
        verify(usageBatchService);
    }

    @Test
    public void testGetAdditionalFundNamesByUsageBatchId() {
        String batchId = RupPersistUtils.generateUuid();
        List<String> names = Arrays.asList("Test 1", "Test 2");
        expect(fundPoolService.getPreServiceFeeFundNamesByUsageBatchId(batchId)).andReturn(names).once();
        replay(fundPoolService);
        assertEquals(names, controller.getPreServiceFeeFundNamesByUsageBatchId(batchId));
        verify(fundPoolService);
    }

    @Test
    public void testDeleteUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatchService.deleteUsageBatch(usageBatch);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        controller.deleteUsageBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testIsValidUsagesState() {
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(usageService.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND)).andReturn(true).once();
        replay(filterController, filterWidgetMock, usageService);
        assertTrue(controller.isValidUsagesState(UsageStatusEnum.WORK_NOT_FOUND));
        verify(filterController, filterWidgetMock, usageService);
    }

    @Test
    public void testScenarioExists() {
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(true).once();
        replay(scenarioService);
        assertTrue(controller.scenarioExists(SCENARIO_NAME));
        verify(scenarioService);
    }

    private void prepareGetAppliedFilterExpectations(UsageFilter expectedUsageFilter) {
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(expectedUsageFilter).once();
    }

    private interface ITestUsageWidget extends ICommonUsageWidget {
    }

    private interface ITestUsageController extends ICommonUsageController {
    }

    private static class TestUsageController extends CommonUsageController implements ITestUsageController {

        @Override
        public void onFilterChanged(FilterChangedEvent event) {
        }

        @Override
        public void onScenarioCreated(ScenarioCreateEvent event) {
        }

        @Override
        public IStreamSource getExportUsagesStreamSource() {
            return null;
        }

        @Override
        protected ITestUsageWidget instantiateWidget() {
            return null;
        }
    }
}
