package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

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

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewAclciUsageBatchWindow}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/12/23
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewAclciUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = "964c4046-813d-40c3-9774-0365f9cc3267";
    private static final String UNCHECKED = "unchecked";
    private static final String GRID = "grid";

    private final List<UsageBatch> usageBatches = loadExpectedUsageBatch("usage_batch_dto_043fh67j.json");
    private ViewAclciUsageBatchWindow window;
    private IAclciUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAclciUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn("ACLCI").once();
        expect(controller.getUsageBatches("ACLCI")).andReturn(usageBatches).once();
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewAclciUsageBatchWindow(controller);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testStructure() {
        assertEquals("View Usage Batch", window.getCaption());
        verifySize(window);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        verifyGrid(component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(2, buttonsLayout.getComponentCount());
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Button closeButton = (Button) buttonsLayout.getComponent(1);
        assertEquals("Delete", deleteButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(window, searchWidget);
        Whitebox.setInternalState(window, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        window.performSearch();
        verify(searchWidget, grid);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteButtonEnabled() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<UsageBatch> grid = (Grid<UsageBatch>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        assertFalse(deleteButton.isEnabled());
        grid.select(usageBatches.get(0));
        assertTrue(deleteButton.isEnabled());
    }

    @Test
    public void testGridValues() {
        Grid grid = (Grid) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"ACLCI Batch", 5588L, "RGS Energy Group, Inc.", "06/30/2023", "user@copyright.com", "04/12/2023 2:55 PM"}
        };
        verifyGridItems(grid, usageBatches, expectedCells);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageBatchClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(usageBatches.get(0))).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'ACLCI Batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteWithInProgressUsageBatch() {
        mockStatic(Windows.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(usageBatches.get(0))).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'ACLCI Batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteUsageBatchClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, GRID, grid);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(usageBatches.get(0))).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(List.of("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid<?> grid = (Grid<?>) component;
        UiTestHelper.verifyGrid(grid, List.of(
            Triple.of("Usage Batch Name", -1.0, 1),
            Triple.of("Licensee Account #", 180.0, -1),
            Triple.of("Licensee Name", 180.0, -1),
            Triple.of("Period End Date", 120.0, -1),
            Triple.of("Created By", 170.0, -1),
            Triple.of("Created Date", 170.0, -1)
        ));
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteUsageBatchButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteUsageBatchButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    private List<UsageBatch> loadExpectedUsageBatch(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
