package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;
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
 * Verifies {@link AaclDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/01/20
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AaclDrillDownByRightsholderController.class, Windows.class})
public class AaclDrillDownByRightsholderControllerTest {

    private static final String SCENARIO_UID = "10bd15c1-b907-457e-94c0-9d6bb66e706f";
    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String SORT_PROPERTY = "detailId";
    private static final String SEARCH = "search";
    private static final int OFFSET = 5;
    private static final int COUNT = 10;

    private AaclDrillDownByRightsholderController controller;
    private IAaclUsageService usageService;

    @Before
    public void setUp() {
        controller = createPartialMock(AaclDrillDownByRightsholderController.class, "initWidget", "getWidget");
        usageService = createMock(IAaclUsageService.class);
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_UID);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
    }

    @Test
    public void testLoadBeans() {
        List<UsageDto> usageDtos = Collections.singletonList(new UsageDto());
        Capture<Pageable> pageableCapture = new Capture<>();
        Capture<Sort> sortCapture = new Capture<>();
        expect(controller.getWidget()).andReturn(new AaclDrillDownByRightsholderControllerTest.WidgetMock()).once();
        expect(usageService.getByScenarioAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(SCENARIO_UID), eq(SEARCH),
            capture(pageableCapture), capture(sortCapture)))
            .andReturn(usageDtos).once();
        replay(usageService, controller);
        List<UsageDto> actualUsageDtos = controller.loadBeans(OFFSET, COUNT,
            Collections.singletonList(new QuerySortOrder(SORT_PROPERTY, SortDirection.DESCENDING)));
        verify(usageService, controller);
        assertEquals(OFFSET, pageableCapture.getValue().getOffset());
        assertEquals(COUNT, pageableCapture.getValue().getLimit());
        assertEquals(SORT_PROPERTY, sortCapture.getValue().getProperty());
        assertEquals(Sort.Direction.DESC.getValue(), sortCapture.getValue().getDirection());
        assertEquals(usageDtos, actualUsageDtos);
    }

    @Test
    public void testGetSize() {
        expect(controller.getWidget()).andReturn(new AaclDrillDownByRightsholderControllerTest.WidgetMock()).once();
        expect(usageService.getCountByScenarioAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(SCENARIO_UID), eq(SEARCH)))
            .andReturn(42).once();
        replay(usageService, controller);
        assertEquals(42, controller.getSize());
        verify(usageService, controller);
    }

    @Test
    public void testShowWidget() {
        mockStatic(Windows.class);
        WidgetMock widget = new WidgetMock();
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        controller.showWidget(100000001L, "Rothchild Consultants", new Scenario());
        verifyAll();
        assertEquals("Rothchild Consultants (Account #: 100000001)", widget.getCaption());
    }

    private static class WidgetMock extends Window implements IAaclDrillDownByRightsholderWidget {

        @Override
        public String getSearchValue() {
            return SEARCH;
        }

        @Override
        public IAaclDrillDownByRightsholderWidget init() {
            return this;
        }

        @Override
        public void setController(ICommonDrillDownByRightsholderController controller) {
            // do nothing
        }
    }
}
