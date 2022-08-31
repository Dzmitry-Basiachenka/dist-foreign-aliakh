package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link AclScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioHistoryWidgetTest {

    private static final String SCENARIO_ID = "f97f7e80-89b2-43a7-9d6f-2d500b8f2fab";

    private AclScenarioHistoryWidget widget;
    private IAclScenarioHistoryController controller;

    @Before
    public void setUp() {
        controller = createMock(AclScenarioHistoryController.class);
        widget = new AclScenarioHistoryWidget();
        widget.setController(controller);
        widget.init();
    }

    @Test
    public void testStructure() {
        assertEquals("scenario-history-widget", widget.getId());
        verifyWindow(widget, StringUtils.EMPTY, 700, 350, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(true, true, true, true), content.getMargin());
        assertEquals(2, content.getComponentCount());
        Grid grid = (Grid) content.getComponent(0);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Type", -1.0, -1),
            Triple.of("User", -1.0, -1),
            Triple.of("Date", -1.0, -1),
            Triple.of("Reason", -1.0, -1)
        ));
        verifyButton(content.getComponent(1), "Close", true);
    }

    @Test
    public void testGridValues() {
        List<ScenarioAuditItem> auditItems = Collections.singletonList(buildScenarioAuditItem());
        expect(controller.getActions(SCENARIO_ID)).andReturn(auditItems).once();
        replay(controller);
        widget.populateHistory(buildAclScenario());
        Grid grid = (Grid) ((VerticalLayout) widget.getContent()).getComponent(0);
        Object[][] expectedCells = {
            {ScenarioActionTypeEnum.ADDED_USAGES, "user@copyright.com", "08/31/2022 12:00 AM", "some reason"}
        };
        verifyGridItems(grid, auditItems, expectedCells);
        verify(controller);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_ID);
        return aclScenario;
    }

    private ScenarioAuditItem buildScenarioAuditItem() {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setActionType(ScenarioActionTypeEnum.ADDED_USAGES);
        auditItem.setCreateUser("user@copyright.com");
        auditItem.setCreateDate(Date.from(LocalDate.of(2022, 8, 31).atStartOfDay(
            ZoneId.systemDefault()).toInstant()));
        auditItem.setActionReason("some reason");
        return auditItem;
    }
}
