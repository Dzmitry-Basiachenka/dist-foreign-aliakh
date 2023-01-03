package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.google.common.collect.Lists;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link SalAuditWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaScript.class)
public class SalAuditWidgetTest {

    private SalAuditWidget widget;
    private ISalAuditController controller;
    private ICommonAuditFilterWidget filterWidget;
    private ICommonAuditFilterController filterController;

    @Before
    public void setUp() {
        controller = createMock(ISalAuditController.class);
        widget = new SalAuditWidget(controller);
        widget.setController(controller);
        filterController = createMock(ICommonAuditFilterController.class);
        ISalAuditFilterController auditFilterController = createMock(ISalAuditFilterController.class);
        filterWidget = new SalAuditFilterWidget(auditFilterController);
        filterWidget.setController(filterController);
    }

    @Test
    public void testInit() {
        initWidget();
        assertEquals(200, widget.getSplitPosition(), 0);
        assertTrue(widget.isLocked());
        assertEquals("audit-widget", widget.getStyleName());
        Component component = widget.getFirstComponent();
        assertThat(component, instanceOf(ICommonAuditFilterWidget.class));
        component = widget.getSecondComponent();
        assertThat(component, instanceOf(VerticalLayout.class));
        verifyGridLayout((VerticalLayout) component);
    }

    @Test
    public void testRefresh() {
        initWidget();
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        List<UsageDto> liabilities = Lists.newArrayList(new UsageDto());
        Capture<List<QuerySortOrder>> orderCapture = newCapture();
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.loadBeans(eq(0), eq(1), capture(orderCapture))).andReturn(liabilities).once();
        replay(controller, JavaScript.class);
        widget.refresh();
        Grid<?> grid = widget.getAuditGrid();
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(1, dataProvider.size(new Query<>()));
        Stream<?> stream = dataProvider.fetch(new Query<>(0, 1, Collections.emptyList(), null, null));
        assertEquals(liabilities, stream.collect(Collectors.toList()));
        assertFalse(grid.getStyleName().contains("empty-audit-grid"));
        assertEquals(0, orderCapture.getValue().size());
        verify(controller, JavaScript.class);
    }

    @Test
    public void testRefreshEmptyGrid() {
        initWidget();
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).once();
        expect(controller.getSize()).andReturn(0).once();
        replay(controller, JavaScript.class);
        widget.refresh();
        Grid<?> grid = widget.getAuditGrid();
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(0, dataProvider.size(new Query<>()));
        assertTrue(grid.getStyleName().contains("empty-audit-grid"));
        verify(controller, JavaScript.class);
    }

    private void verifyGridLayout(VerticalLayout layout) {
        verifySize(layout, 100, Unit.PERCENTAGE, 100);
        assertTrue(layout.isSpacing());
        assertEquals(2, layout.getComponentCount());
        verifyToolbar(layout.getComponent(0));
        verifyGrid(layout.getComponent(1));
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        verifySize(layout, 100, Unit.PIXELS, -1);
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyExportButton(layout.getComponent(0));
        verifySearchWidget(layout.getComponent(1));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, 75, Unit.PIXELS, -1);
        assertEquals("Enter Detail ID or Wr Wrk Inst or System Title",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyExportButton(Component component) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals("Export", button.getCaption());
        assertEquals(1, button.getExtensions().size());
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifySize(grid, 100, Unit.PERCENTAGE, 100);
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Type", "Detail Status", "Product Family", "Usage Batch Name",
            "Period End Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Wr Wrk Inst",
            "System Title", "Standard Number", "Standard Number Type", "Gross Amt in USD", "Service Fee Amount",
            "Net Amt in USD", "Scenario Name", "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date",
            "Reported Work Portion ID", "Reported Standard Number or Image ID Number", "Reported Title",
            "Reported Media Type", "Media Type Weight", "Reported Article or Chapter Title", "Reported Author",
            "Reported Publisher", "Reported Publication Date", "Reported Page Range", "Reported Vol/Number/Series",
            "Date of Scored Assessment", "Assessment Name", "Coverage Year", "Grade", "Grade Group",
            "Assessment Type", "Question Identifier", "States", "Number of Views", "Comment"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
    }

    private void verifySize(Component component, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void initWidget() {
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getAuditFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController, streamSource);
        widget.init();
        verify(controller, filterController, streamSource);
        reset(controller, filterController, streamSource);
    }
}
