package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsWidget;
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

import java.util.List;

/**
 * Verifies {@link AclScenarioDetailsByRightsholderController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/17/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclScenarioDetailsByRightsholderController.class, Windows.class})
public class AclScenarioDetailsByRightsholderControllerTest {

    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final String SCENARIO_UID = "e1412e69-5a7a-42c1-9caa-ac17dcdf1ddb";
    private static final String SEARCH_VALUE = "search";
    private static final int OFFSET = 5;
    private static final int COUNT = 10;
    private static final String SORT_PROPERTY = "detailId";

    private AclScenarioDetailsByRightsholderController controller;
    private IAclScenarioUsageService aclScenarioUsageService;

    @Before
    public void setUp() {
        AclScenario scenario = buildAclScenario();
        controller = createPartialMock(AclScenarioDetailsByRightsholderController.class, "initWidget", "getWidget");
        aclScenarioUsageService = createMock(IAclScenarioUsageService.class);
        Whitebox.setInternalState(controller, aclScenarioUsageService);
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, RH_ACCOUNT_NUMBER);
    }

    @Test
    public void testLoadBeans() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(new AclScenarioDetailDto());
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(aclScenarioUsageService.getByScenarioIdAndRhAccountNumber(eq(RH_ACCOUNT_NUMBER), eq(SCENARIO_UID),
            eq(SEARCH_VALUE), capture(pageableCapture), capture(sortCapture))).andReturn(scenarioDetailDtos).once();
        replay(controller, aclScenarioUsageService);
        List<AclScenarioDetailDto> actualScenarioDetailDtos = controller.loadBeans(OFFSET, COUNT,
            List.of(new QuerySortOrder(SORT_PROPERTY, SortDirection.DESCENDING)));
        assertEquals(OFFSET, pageableCapture.getValue().getOffset());
        assertEquals(COUNT, pageableCapture.getValue().getLimit());
        assertEquals(SORT_PROPERTY, sortCapture.getValue().getProperty());
        assertEquals(Direction.DESC.getValue(), sortCapture.getValue().getDirection());
        assertEquals(scenarioDetailDtos, actualScenarioDetailDtos);
        verify(controller, aclScenarioUsageService);
    }

    @Test
    public void testGetSize() {
        expect(controller.getWidget()).andReturn(new WidgetMock()).once();
        expect(aclScenarioUsageService.getCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_UID,
            SEARCH_VALUE)).andReturn(10).once();
        replay(controller, aclScenarioUsageService);
        assertEquals(10, controller.getSize());
        verify(controller, aclScenarioUsageService);
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

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclScenarioDetailsByRightsholderWidget.class));
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("Scenario name");
        return aclScenario;
    }

    private static class WidgetMock extends Window implements IAclCommonScenarioDetailsWidget {

        @Override
        public String getSearchValue() {
            return SEARCH_VALUE;
        }

        @Override
        public IAclCommonScenarioDetailsWidget init() {
            return this;
        }

        @Override
        public void setController(IAclCommonScenarioDetailsController controller) {
            // do nothing
        }
    }
}
