package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Div;
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
import java.io.IOException;
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

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private final List<RightsholderTotalsHolder> rightsholderTotalsHolders =
        loadExpectedRightsholderTotalsHolder("rightsholder_total_holder_1000010022.json");

    private FasScenarioWidget scenarioWidget;
    private IFasScenarioController controller;
    private FasScenarioMediator mediator;
    private Scenario scenario;

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
        mediator = createMock(FasScenarioMediator.class);
        expect(controller.getExportScenarioUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getExportScenarioRightsholderTotalsStreamSource()).andReturn(streamSource).once();
        buildScenario();
        expect(controller.getScenario()).andReturn(scenario).once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        scenarioWidget = new FasScenarioWidget(controller);
        Whitebox.setInternalState(scenarioWidget, mediator);
        scenarioWidget.setController(controller);
        replay(controller, streamSource, ui, UI.class, mediator);
        scenarioWidget.init();
        verify(controller, streamSource, ui, UI.class, mediator);
        reset(controller, mediator);
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
        expect(controller.isScenarioEmpty()).andReturn(false).once();
        expect(controller.getScenario()).andReturn(scenario).once();
        mediator.onScenarioUpdated(false, scenario);
        expectLastCall().once();
        replay(controller, mediator);
        scenarioWidget.refresh();
        verify(controller, mediator);
    }

    @Test
    public void testGridValues() {
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(rightsholderTotalsHolders).once();
        expect(controller.getSize()).andReturn(1).once();
        replay(controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) getDialogContent(scenarioWidget)).getComponentAt(1);
        Object[][] expectedCells = {
            {"2000197554", "Andrew Goodwin", "2000017003", "ProLitteris", "20,000.00", "6,400.00", "13,600.00", "16.0"}
        };
        verifyGridItems(grid, rightsholderTotalsHolders, expectedCells);
        verify(controller);
        Object[][] expectedFooterColumns = {
            {"grossTotal", "20,000.00", STYLE_ALIGN_RIGHT},
            {"serviceFeeTotal", "6,400.00", STYLE_ALIGN_RIGHT},
            {"netTotal", "13,600.00", STYLE_ALIGN_RIGHT}
        };
        verifyFooterItems(grid, 1, "Totals", expectedFooterColumns);
    }

    private void verifyContent(Component content) {
        assertEquals(VerticalLayout.class, content.getClass());
        var verticalLayout = (VerticalLayout) content;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyToolbar(verticalLayout.getComponentAt(0));
        verifyGrid(verticalLayout.getComponentAt(1));
        verifyEmptyScenarioLabel(((VerticalLayout) verticalLayout.getComponentAt(2)).getComponentAt(0));
        verifyButtonsLayout(getFooterComponent(scenarioWidget, 1));
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(JustifyContentMode.BETWEEN, horizontalLayout.getJustifyContentMode());
        assertEquals("100%", horizontalLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnit().orElseThrow());
        assertEquals("view-scenario-toolbar", horizontalLayout.getId().orElseThrow());
        assertEquals("view-scenario-toolbar", horizontalLayout.getClassName());
        var div = (Div) horizontalLayout.getComponentAt(0);
        assertEquals("<div></div>", div.getElement().getOuterHTML());
        var searchWidget = (SearchWidget) horizontalLayout.getComponentAt(1);
        assertEquals("Enter Rightsholder Name/Account # or Payee Name/Account #",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
        assertEquals("60%", searchWidget.getWidth());
        var menuComponent = horizontalLayout.getComponentAt(2);
        assertThat(menuComponent, instanceOf(Button.class));
        var menuButton = (Button) menuComponent;
        assertNotNull(menuComponent);
        assertEquals("Hide/Unhide", menuButton.getTooltip().getText());
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("RH Account #", "RH Name", "Payee Account #", "Payee Name", "Gross Amt in USD",
                "Service Fee Amount", "Net Amt in USD", "Service Fee %"),
            columns.stream().map(column -> column.getHeaderText()).collect(Collectors.toList()));
    }

    private void verifyEmptyScenarioLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        assertEquals("label-scenario-empty-message", ((Label) component).getClassName());
        assertTrue(StringUtils.contains(component.getElement().getOuterHTML(),
            "Scenario is empty due to all usages were excluded"));
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertTrue(horizontalLayout.isSpacing());
        assertEquals("scenario-buttons-layout", horizontalLayout.getId().orElseThrow());
        assertEquals(4, horizontalLayout.getComponentCount());
        var excludeByRroButton = horizontalLayout.getComponentAt(0);
        assertThat(excludeByRroButton, instanceOf(Button.class));
        assertEquals("Exclude By RRO", ((Button) excludeByRroButton).getText());
        var fileDownloader = horizontalLayout.getComponentAt(1);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export Details", ((Button) fileDownloader.getChildren().findFirst().orElseThrow()).getText());
        fileDownloader = horizontalLayout.getComponentAt(2);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().orElseThrow()).getText());
        var closeButton = horizontalLayout.getComponentAt(3);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId("65d4cdde-eeff-4b31-885f-78170d9e7790");
        scenario.setName("Scenario name");
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setServiceFeeTotal(new BigDecimal("6400.00"));
        scenario.setNetTotal(new BigDecimal("13600.00"));
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
    }

    private List<RightsholderTotalsHolder> loadExpectedRightsholderTotalsHolder(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<List<RightsholderTotalsHolder>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
