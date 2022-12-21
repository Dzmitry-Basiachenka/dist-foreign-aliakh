package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsDrillDownByRightsholderWidget;
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
 * Verifies {@link NtsDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NtsDrillDownByRightsholderController.class, Windows.class})
public class NtsDrillDownByRightsholderControllerTest {

    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final String SEARCH_VALUE = "123";
    private static final int OFFSET = 5;
    private static final int COUNT = 10;
    private static final String SORT_PROPERTY = "detailId";

    private IUsageService usageServiceMock;
    private NtsDrillDownByRightsholderController controller;

    @Before
    public void setUp() {
        usageServiceMock = createMock(IUsageService.class);
        controller = createPartialMock(NtsDrillDownByRightsholderController.class, "initWidget", "getWidget");
        Whitebox.setInternalState(controller, usageServiceMock);
    }

    @Test
    public void testLoadBeans() {
        List<UsageDto> usageDtos = Collections.singletonList(new UsageDto());
        Scenario scenario = new Scenario();
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(usageServiceMock.getByScenarioAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(scenario), eq(SEARCH_VALUE),
            capture(pageableCapture), capture(sortCapture)))
            .andReturn(usageDtos).once();
        replayAll();
        List<UsageDto> actualUsageDtos = controller.loadBeans(OFFSET, COUNT,
            Collections.singletonList(new QuerySortOrder(SORT_PROPERTY, SortDirection.DESCENDING)));
        verifyAll();
        assertEquals(OFFSET, pageableCapture.getValue().getOffset());
        assertEquals(COUNT, pageableCapture.getValue().getLimit());
        assertEquals(SORT_PROPERTY, sortCapture.getValue().getProperty());
        assertEquals(Direction.DESC.getValue(), sortCapture.getValue().getDirection());
        assertEquals(usageDtos, actualUsageDtos);
    }

    @Test
    public void testGetSize() {
        Scenario scenario = new Scenario();
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(usageServiceMock.getCountByScenarioAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(scenario),
            eq(SEARCH_VALUE))).andReturn(42).once();
        replayAll();
        assertEquals(42, controller.getSize());
        verifyAll();
    }

    @Test
    public void testShowWidget() {
        WidgetMock widget = new WidgetMock();
        Scenario scenario = new Scenario();
        mockStatic(Windows.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replayAll();
        controller.showWidget(RH_ACCOUNT_NUMBER, RH_NAME, scenario);
        verifyAll();
        assertEquals("Rothchild Consultants (Account #: 100000001)", widget.getCaption());
    }

    private static class WidgetMock extends Window implements INtsDrillDownByRightsholderWidget {

        @Override
        public String getSearchValue() {
            return SEARCH_VALUE;
        }

        @Override
        public INtsDrillDownByRightsholderWidget init() {
            return this;
        }

        @Override
        public void setController(ICommonDrillDownByRightsholderController controller) {
            // do nothing
        }
    }
}
