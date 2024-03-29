package com.copyright.rup.dist.foreign.vui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;

import com.vaadin.flow.data.provider.QuerySortOrder;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link CommonUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/09/2019
 *
 * @author Uladzislau Shalamitski
 */
public class CommonUsageControllerTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String USAGE_BATCH_ID = "bf6d5d00-0d61-453f-8a76-8a7b133efecc";
    private static final String USAGE_BATCH_NAME = "Usage Batch";
    private static final String SCENARIO_NAME = "Scenario Name";

    private TestUsageController controller;
    private IUsageService usageService;
    private IFasNtsUsageFilterController filterController;
    private IFasNtsUsageFilterWidget filterWidgetMock;
    private IUsageBatchService usageBatchService;
    private IScenarioService scenarioService;
    private IFundPoolService fundPoolService;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IFasNtsUsageFilterController.class);
        scenarioService = createMock(IScenarioService.class);
        filterWidgetMock = createMock(IFasNtsUsageFilterWidget.class);
        fundPoolService = createMock(IFundPoolService.class);
        controller = new TestUsageController(filterController);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, fundPoolService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testGetScenariosNamesAssociatedWithUsageBatch() {
        List<String> scenarioNames = List.of(SCENARIO_NAME);
        expect(scenarioService.getScenariosNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(scenarioNames).once();
        replay(scenarioService);
        assertSame(scenarioNames, controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID));
        verify(scenarioService);
    }

    @Test
    public void testGetUsageBatches() {
        List<UsageBatch> usageBatches = List.of();
        expect(usageBatchService.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(usageBatches).once();
        replay(usageBatchService);
        assertSame(usageBatches, controller.getUsageBatches(FAS_PRODUCT_FAMILY));
        verify(usageBatchService);
    }

    @Test
    public void testUsageBatchExists() {
        expect(usageBatchService.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        replay(usageBatchService);
        assertFalse(controller.usageBatchExists(USAGE_BATCH_NAME));
        verify(usageBatchService);
    }

    @Test
    public void testGetAdditionalFundNamesByUsageBatchId() {
        List<String> fundNames = List.of();
        expect(fundPoolService.getNtsFundPoolNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(fundNames).once();
        replay(fundPoolService);
        assertSame(fundNames, controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID));
        verify(fundPoolService);
    }

    @Test
    public void testScenarioExists() {
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(true).once();
        replay(scenarioService);
        assertTrue(controller.scenarioExists(SCENARIO_NAME));
        verify(scenarioService);
    }

    @Test
    public void testGetInvalidRightsholders() {
        List<Long> invalidRhs = List.of(1000017527L);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(usageService.getInvalidRightsholdersByFilter(usageFilter)).andReturn(invalidRhs).once();
        replay(filterController, filterWidgetMock, usageService);
        assertSame(invalidRhs, controller.getInvalidRightsholders());
        verify(filterController, filterWidgetMock, usageService);
    }

    private interface ITestUsageWidget extends ICommonUsageWidget {
    }

    private interface ITestUsageController extends ICommonUsageController {
    }

    private static class TestUsageController extends CommonUsageController implements ITestUsageController {

        private final ICommonUsageFilterController usageFilterController;

        TestUsageController(
            ICommonUsageFilterController usageFilterController) {
            this.usageFilterController = usageFilterController;
        }

        @Override
        public ICommonUsageFilterController getUsageFilterController() {
            return usageFilterController;
        }

        @Override
        public IStreamSource getExportUsagesStreamSource() {
            return null;
        }

        @Override
        public void onScenarioCreated(ScenarioCreateEvent event) {
        }

        @Override
        public void deleteUsageBatch(UsageBatch usageBatch) {
        }

        @Override
        public boolean isBatchProcessingCompleted(String batchId) {
            return false;
        }

        @Override
        protected ITestUsageWidget instantiateWidget() {
            return null;
        }

        @Override
        public int getBeansCount() {
            return 0;
        }

        @Override
        public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
            return true;
        }

        @Override
        public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
            return null;
        }
    }
}
