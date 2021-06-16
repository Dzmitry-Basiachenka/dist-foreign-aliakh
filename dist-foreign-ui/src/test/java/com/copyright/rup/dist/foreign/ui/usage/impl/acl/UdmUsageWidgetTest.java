package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmUsageWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, SecurityUtils.class})
public class UdmUsageWidgetTest {

    private static final List<String> VISIBLE_COLUMNS_FOR_RESEARCHER =
        Arrays.asList("Detail ID", "Period", "Usage Detail ID", "Detail Status", "Assignee", "RH Account #",
            "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Comment", "Det LC ID",
            "Det LC Name", "Channel", "Usage Date", "Survey Start Date", "Survey End Date", "Reported TOU",
            "Ineligible Reason", "Load Date", "Updated By", "Updated Date");
    private static final List<String> VISIBLE_COLUMNS_FOR_MANAGER =
        Arrays.asList("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Detail Status", "Assignee",
            "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Comment", "Det LC ID",
            "Det LC Name", "Company ID", "Company Name", "Survey Respondent", "IP Address", "Survey Country",
            "Channel", "Usage Date", "Survey Start Date", "Survey End Date", "Annual Multiplier",
            "Statistical Multiplier", "Reported TOU", "Quantity", "Annualized Copies", "Ineligible Reason",
            "Load Date", "Updated By", "Updated Date");
    private static final List<String> VISIBLE_COLUMNS_FOR_SPECIALIST_AND_VIEW_ONLY =
        Arrays.asList("Detail ID", "Period", "Usage Origin", "Usage Detail ID", "Detail Status", "Assignee",
            "RH Account #", "RH Name", "Wr Wrk Inst", "Reported Title", "System Title", "Reported Standard Number",
            "Standard Number", "Reported Pub Type", "Publication Format", "Article", "Language", "Comment", "Det LC ID",
            "Det LC Name", "Company ID", "Company Name", "Survey Respondent", "Survey Country", "Channel", "Usage Date",
            "Survey Start Date", "Survey End Date", "Annual Multiplier", "Statistical Multiplier", "Reported TOU",
            "Quantity", "Annualized Copies", "Ineligible Reason", "Load Date", "Updated By", "Updated Date");
    private UdmUsageWidget usagesWidget;
    private IUdmUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        UdmUsageFilterWidget filterWidget = new UdmUsageFilterWidget(createMock(IUdmUsageFilterController.class));
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        mockStatic(ForeignSecurityUtils.class);
    }

    @Test
    public void testWidgetStructure() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(true).once();
        replay(controller, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        verify(controller, ForeignSecurityUtils.class);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayout(layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testWidgetStructureForResearcher() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).once();
        replay(controller, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        verify(controller, ForeignSecurityUtils.class);
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget, 100, 100, Unit.PERCENTAGE);
        assertTrue(usagesWidget.getFirstComponent() instanceof UdmUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyToolbarLayoutForResearcher(layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testColumnsIsVisibleForResearcher() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).once();
        replay(controller, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        verify(controller, ForeignSecurityUtils.class);
        Component secondComponent = usagesWidget.getSecondComponent();
        VerticalLayout layout = (VerticalLayout) secondComponent;
        Grid grid = (Grid) layout.getComponent(1);
        List<Column<String, ?>> columns = grid.getColumns();
        assertEquals(VISIBLE_COLUMNS_FOR_RESEARCHER,
            columns.stream().filter(column -> !column.isHidden()).map(Column::getCaption).collect(Collectors.toList()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testColumnsIsVisibleForSpecialistAndViewOnly() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).once();
        replay(controller, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        verify(controller, ForeignSecurityUtils.class);
        Component secondComponent = usagesWidget.getSecondComponent();
        VerticalLayout layout = (VerticalLayout) secondComponent;
        Grid grid = (Grid) layout.getComponent(1);
        List<Column<String, ?>> columns = grid.getColumns();
        assertEquals(VISIBLE_COLUMNS_FOR_SPECIALIST_AND_VIEW_ONLY,
            columns.stream().filter(column -> !column.isHidden()).map(Column::getCaption).collect(Collectors.toList()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testColumnsIsVisibleForManager() {
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(true).once();
        replay(controller, ForeignSecurityUtils.class);
        usagesWidget = new UdmUsageWidget();
        usagesWidget.setController(controller);
        usagesWidget.init();
        verify(controller, ForeignSecurityUtils.class);
        Component secondComponent = usagesWidget.getSecondComponent();
        VerticalLayout layout = (VerticalLayout) secondComponent;
        Grid grid = (Grid) layout.getComponent(1);
        List<Column<String, ?>> columns = grid.getColumns();
        assertEquals(VISIBLE_COLUMNS_FOR_MANAGER,
            columns.stream().filter(column -> !column.isHidden()).map(Column::getCaption).collect(Collectors.toList()));
    }

    private void verifyToolbarLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        verifySize(layout, 100, -1, Unit.PIXELS);
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyLoadButton(layout.getComponent(0));
        verifySearchWidget(layout.getComponent(1));
    }

    private void verifyToolbarLayoutForResearcher(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        verifySize(layout, 100, -1, Unit.PIXELS);
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyLoadButton(layout.getComponent(0));
        verifySearchWidgetForResearcher(layout.getComponent(1));
    }

    private void verifyLoadButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("Load", button.getCaption());
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof SearchWidget);
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, 65, -1, Unit.PIXELS);
        assertEquals("Enter Reported/System Title or Usage Detail ID or Standard Number or Article or " +
                "Survey Respondent or Comment",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifySearchWidgetForResearcher(Component component) {
        assertTrue(component instanceof SearchWidget);
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, 65, -1, Unit.PIXELS);
        assertEquals("Enter Reported/System Title or Usage Detail ID or Standard Number or Article or Comment",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyGrid(Grid grid) {
        List<Column> columns = grid.getColumns();
        assertEquals(VISIBLE_COLUMNS_FOR_MANAGER, columns.stream().map(Column::getCaption)
            .collect(Collectors.toList()));
        verifySize(grid, 100, 100, Unit.PERCENTAGE);
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Usages Count: 0", footerRow.getCell("detailId").getText());
    }

    private void verifySize(Component component, float width, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
