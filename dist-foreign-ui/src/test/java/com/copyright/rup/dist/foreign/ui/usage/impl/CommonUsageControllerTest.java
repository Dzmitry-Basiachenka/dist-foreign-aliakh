package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
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
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;

import com.vaadin.data.provider.QuerySortOrder;

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
    public void testInitUsagesFilterWidget() {
        IFasNtsUsageFilterWidget filterWidget = new FasNtsUsageFilterWidget(filterController);
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
        expect(fundPoolService.getNtsFundPoolNamesByUsageBatchId(batchId)).andReturn(names).once();
        replay(fundPoolService);
        assertEquals(names, controller.getAdditionalFundNamesByUsageBatchId(batchId));
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
        List<Long> invalidRhs = Collections.singletonList(1000017527L);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(usageService.getInvalidRightsholdersByFilter(usageFilter)).andReturn(invalidRhs).once();
        replay(filterController, filterWidgetMock, usageService);
        assertEquals(invalidRhs, controller.getInvalidRightsholders());
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
        public void onFilterChanged(FilterChangedEvent event) {
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
