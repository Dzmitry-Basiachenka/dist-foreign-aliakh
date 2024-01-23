package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link FasDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/19
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FasDrillDownByRightsholderController.class, Windows.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasDrillDownByRightsholderControllerTest {

    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final String SEARCH_VALUE = "123";
    private static final int OFFSET = 5;
    private static final int COUNT = 10;
    private static final String SORT_PROPERTY = "detailId";

    private Scenario scenario;
    private IUsageService usageService;
    private FasDrillDownByRightsholderController controller;

    @Before
    public void setUp() {
        scenario = new Scenario();
        usageService = createMock(IUsageService.class);
        controller = createPartialMock(FasDrillDownByRightsholderController.class, "initWidget", "getWidget");
        Whitebox.setInternalState(controller, usageService);
    }

    @Test
    public void testLoadBeans() {
        List<UsageDto> usageDtos = List.of(new UsageDto());
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(usageService.getByScenarioAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(scenario), eq(SEARCH_VALUE),
            capture(pageableCapture), capture(sortCapture))).andReturn(usageDtos).once();
        replay(controller, usageService);
        List<UsageDto> actualUsageDtos = controller.loadBeans(OFFSET, COUNT,
            List.of(new QuerySortOrder(SORT_PROPERTY, SortDirection.DESCENDING)));
        assertEquals(OFFSET, pageableCapture.getValue().getOffset());
        assertEquals(COUNT, pageableCapture.getValue().getLimit());
        assertEquals(SORT_PROPERTY, sortCapture.getValue().getProperty());
        assertEquals(Direction.DESC.getValue(), sortCapture.getValue().getDirection());
        assertEquals(usageDtos, actualUsageDtos);
        verify(controller, usageService);
    }

    @Test
    public void testGetSize() {
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(usageService.getCountByScenarioAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(scenario),
            eq(SEARCH_VALUE))).andReturn(42).once();
        replay(controller, usageService);
        assertEquals(42, controller.getSize());
        verify(controller, usageService);
    }

    @Test
    public void testShowWidget() {
        var widget = new WidgetMock();
        mockStatic(Windows.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replay(controller);
        controller.showWidget(RH_ACCOUNT_NUMBER, RH_NAME, scenario);
        assertEquals("Rothchild Consultants (Account #: 100000001)", widget.getHeaderTitle());
        verify(controller);
    }

    private static class WidgetMock extends Dialog implements IFasDrillDownByRightsholderWidget {

        @Override
        public String getSearchValue() {
            return SEARCH_VALUE;
        }

        @Override
        public IFasDrillDownByRightsholderWidget init() {
            return this;
        }

        @Override
        public void setController(ICommonDrillDownByRightsholderController controller) {
            // do nothing
        }
    }
}
