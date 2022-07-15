package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderWidget;
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
 * Verifies {@link AclDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclDrillDownByRightsholderController.class, Windows.class})
public class AclDrillDownByRightsholderControllerTest {

    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final String SCENARIO_UID = "e1412e69-5a7a-42c1-9caa-ac17dcdf1ddb";
    private static final String SEARCH_VALUE = "search";
    private static final int OFFSET = 5;
    private static final int COUNT = 10;
    private static final String SORT_PROPERTY = "detailId";

    private AclDrillDownByRightsholderController controller;
    private IAclScenarioService aclScenarioService;

    @Before
    public void setUp() {
        AclScenario scenario = new AclScenario();
        scenario.setId(SCENARIO_UID);
        controller = createPartialMock(AclDrillDownByRightsholderController.class, "initWidget", "getWidget");
        aclScenarioService = createMock(IAclScenarioService.class);
        Whitebox.setInternalState(controller, aclScenarioService);
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
    }

    @Test
    public void testLoadBeans() {
        List<AclScenarioDetailDto> scenarioDetailDtos = Collections.singletonList(new AclScenarioDetailDto());
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(aclScenarioService.getByScenarioIdAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(SCENARIO_UID),
            eq(SEARCH_VALUE), capture(pageableCapture), capture(sortCapture))).andReturn(scenarioDetailDtos).once();
        replay(controller, aclScenarioService);
        List<AclScenarioDetailDto> actualScenarioDetailDtos = controller.loadBeans(OFFSET, COUNT,
            Collections.singletonList(new QuerySortOrder(SORT_PROPERTY, SortDirection.DESCENDING)));
        assertEquals(OFFSET, pageableCapture.getValue().getOffset());
        assertEquals(COUNT, pageableCapture.getValue().getLimit());
        assertEquals(SORT_PROPERTY, sortCapture.getValue().getProperty());
        assertEquals(Direction.DESC.getValue(), sortCapture.getValue().getDirection());
        assertEquals(scenarioDetailDtos, actualScenarioDetailDtos);
        verify(controller, aclScenarioService);
    }

    @Test
    public void testGetSize() {
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(aclScenarioService.getCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_UID,
            SEARCH_VALUE)).andReturn(10).once();
        replay(controller, aclScenarioService);
        assertEquals(10, controller.getSize());
        verify(controller, aclScenarioService);
    }

    @Test
    public void testShowWidget() {
        mockStatic(Windows.class);
        WidgetMock widget = new WidgetMock();
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        replay(controller, Windows.class);
        controller.showWidget(RH_ACCOUNT_NUMBER, RH_NAME, new AclScenario());
        assertEquals("Rothchild Consultants (Account #: 100000001)", widget.getCaption());
        verify(controller, Windows.class);
    }

    private static class WidgetMock extends Window implements IAclDrillDownByRightsholderWidget {

        @Override
        public String getSearchValue() {
            return SEARCH_VALUE;
        }

        @Override
        public IAclDrillDownByRightsholderWidget init() {
            return this;
        }

        @Override
        public void setController(IAclDrillDownByRightsholderController controller) {
            // do nothing
        }
    }
}
