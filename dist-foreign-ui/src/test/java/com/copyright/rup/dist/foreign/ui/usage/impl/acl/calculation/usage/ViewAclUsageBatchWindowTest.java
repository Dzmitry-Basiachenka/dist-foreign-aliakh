package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewAclUsageBatchWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/26/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewAclUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = "598f47d0-0ecf-4c69-85ac-25bb481313dd";
    private static final List<String> SCENARIO_NAMES = Arrays.asList("ACL Scenario 2021", "ACL Scenario 2022");

    private ViewAclUsageBatchWindow window;
    private Grid<AclUsageBatch> aclUsageBatchGrid;
    private IAclUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAclUsageController.class);
        aclUsageBatchGrid = createMock(Grid.class);
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch()));
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewAclUsageBatchWindow(controller);
        Whitebox.setInternalState(window, "grid", aclUsageBatchGrid);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    public void testGridValues() {
        List<AclUsageBatch> usageBatches = List.of(buildAclUsageBatch());
        Grid grid = (Grid) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"ACL Usage Batch", 202212, "202112, 202106", "Y"}
        };
        verifyGridItems(grid, usageBatches, expectedCells);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "View ACL Usage Batch", 1100, 550, Sizeable.Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertThat(content.getComponent(0), instanceOf(SearchWidget.class));
        Component component = content.getComponent(1);
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifyGrid(grid, Arrays.asList(
            Triple.of("Usage Batch Name", -1.0, 1),
            Triple.of("Distribution Period", 150.0, -1),
            Triple.of("Periods", 580.0, -1),
            Triple.of("Editable", 80.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Delete", "Close");
        assertEquals("view-acl-usage-batch-window", window.getStyleName());
        assertEquals("view-acl-usage-batch-window", window.getId());
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(aclUsageBatchGrid.getSelectedItems()).andReturn(Set.of(buildAclUsageBatch())).once();
        expect(controller.getScenarioNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'ACL Usage Batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(Windows.class, confirmWindowCapture, controller, aclUsageBatchGrid);
        listener.buttonClick(null);
        verify(Windows.class, confirmWindowCapture, controller, aclUsageBatchGrid);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(aclUsageBatchGrid.getSelectedItems()).andReturn(Set.of(buildAclUsageBatch())).once();
        expect(controller.getScenarioNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(SCENARIO_NAMES).once();
        Windows.showNotificationWindow(
            eq("Usage batch cannot be deleted because it is associated with the following scenario(s):" +
                "<ul><li>ACL Scenario 2021</li><li>ACL Scenario 2022</li></ul>"));
        expectLastCall().once();
        replay(Windows.class, confirmWindowCapture, controller, aclUsageBatchGrid);
        listener.buttonClick(null);
        verify(Windows.class, confirmWindowCapture, controller, aclUsageBatchGrid);
    }

    @Test
    public void testDeleteButtonEnabled() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<AclUsageBatch> grid = (Grid<AclUsageBatch>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        assertFalse(deleteButton.isEnabled());
        grid.select(buildAclUsageBatch());
        assertTrue(deleteButton.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(aclUsageBatchGrid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        aclUsageBatchGrid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, aclUsageBatchGrid);
        window.performSearch();
        verify(searchWidget, aclUsageBatchGrid);
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(2, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("ACL Usage Batch");
        usageBatch.setDistributionPeriod(202212);
        usageBatch.setPeriods(new HashSet<>(Arrays.asList(202106, 202112)));
        usageBatch.setEditable(true);
        return usageBatch;
    }
}
