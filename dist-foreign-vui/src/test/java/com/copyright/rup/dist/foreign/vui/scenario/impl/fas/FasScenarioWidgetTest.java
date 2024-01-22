package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.FooterRow.FooterCell;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link FasScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasScenarioWidgetTest {

    private FasScenarioWidget scenarioWidget;
    private IFasScenarioController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> fileSource =
            new SimpleImmutableEntry<>(() -> "file.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(fileSource).times(2);
        controller = createMock(IFasScenarioController.class);
        expect(controller.getExportScenarioUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getExportScenarioRightsholderTotalsStreamSource()).andReturn(streamSource).once();
        Scenario scenario = buildScenario();
        expect(controller.getScenario()).andReturn(scenario).once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(controller, streamSource, ui, UI.class);
        scenarioWidget = new FasScenarioWidget(controller);
        scenarioWidget.setController(controller);
        scenarioWidget.init();
        verify(controller, streamSource, ui, UI.class);
    }

    @Test
    public void testComponentStructure() {
        assertFalse(scenarioWidget.isResizable());
        assertFalse(scenarioWidget.isDraggable());
        assertFalse(scenarioWidget.isResizable());
        verifyContent(getDialogContent(scenarioWidget));
    }

    @Test
    public void testGetSearchValue() {
        var searchWidget = new SearchWidget(controller);
        searchWidget.setSearchValue("search");
        Whitebox.setInternalState(scenarioWidget, searchWidget);
        assertEquals("search", scenarioWidget.getSearchValue());
    }

    @Test
    public void testRefresh() {
        //TODO: {dbasiachenka} implement
    }

    private void verifyContent(Component content) {
        assertEquals(VerticalLayout.class, content.getClass());
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(3, layout.getComponentCount());
        verifySearchWidget(layout.getComponentAt(0));
        verifyGrid(layout.getComponentAt(1));
        verifyEmptyScenarioLabel(((VerticalLayout) layout.getComponentAt(2)).getComponentAt(0));
        verifyButtonsLayout(getFooterComponent(scenarioWidget, 1));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(JustifyContentMode.CENTER, horizontalLayout.getJustifyContentMode());
        assertEquals("100%", horizontalLayout.getWidth());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponentAt(0);
        assertEquals("Enter Rightsholder Name/Account # or Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals("60%", searchWidget.getWidth());
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnit().orElseThrow());
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("RH Account #", "RH Name", "Payee Account #", "Payee Name", "Gross Amt in USD",
                "Service Fee Amount", "Net Amt in USD", "Service Fee %"),
            columns.stream().map(column -> column.getHeaderText()).collect(Collectors.toList()));
        FooterRow footerRow = (FooterRow) grid.getFooterRows().get(1);
        verifyTotalCellValue(footerRow.getCell(grid.getColumnByKey("grossTotal")), "20,000.00");
        verifyTotalCellValue(footerRow.getCell(grid.getColumnByKey("serviceFeeTotal")), "6,400.00");
        verifyTotalCellValue(footerRow.getCell(grid.getColumnByKey("netTotal")), "13,600.00");
    }

    private void verifyTotalCellValue(FooterCell cell, String expectedValue) {
        assertTrue(StringUtils.contains(cell.getComponent().getElement().getOuterHTML(), expectedValue));
    }

    private void verifyEmptyScenarioLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        assertTrue(StringUtils.contains(component.getElement().getOuterHTML(),
            "Scenario is empty due to all usages were excluded"));
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        assertEquals("scenario-buttons-layout", layout.getId().orElseThrow());
        assertEquals(4, layout.getComponentCount());
        Component excludeByRroButton = layout.getComponentAt(0);
        assertThat(excludeByRroButton, instanceOf(Button.class));
        assertEquals("Exclude By RRO", ((Button) excludeByRroButton).getText());
        Component fileDownloader = layout.getComponentAt(1);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export Details", ((Button) fileDownloader.getChildren().findFirst().orElseThrow()).getText());
        fileDownloader = layout.getComponentAt(2);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().orElseThrow()).getText());
        Component closeButton = layout.getComponentAt(3);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }

    private Scenario buildScenario() {
        var scenario = new Scenario();
        scenario.setId("65d4cdde-eeff-4b31-885f-78170d9e7790");
        scenario.setName("Scenario name");
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotal(new BigDecimal("6400.00"));
        scenario.setNetTotal(new BigDecimal("13600.00"));
        return scenario;
    }
}
