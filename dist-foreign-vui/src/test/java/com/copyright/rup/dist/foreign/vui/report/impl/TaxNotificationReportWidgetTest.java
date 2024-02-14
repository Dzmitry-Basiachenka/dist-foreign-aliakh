package com.copyright.rup.dist.foreign.vui.report.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

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
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link TaxNotificationReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/25/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class TaxNotificationReportWidgetTest {

    private static final String SCENARIO_ID_1 = "32894a7f-b218-4347-b8f9-8236f75c9b2a";
    private static final String SCENARIO_ID_2 = "69a22caf-57da-45fa-afbb-35cf96ffd0ba";
    private static final Scenario SCENARIO_1 = buildScenario(SCENARIO_ID_1, "Scenario One");
    private static final Scenario SCENARIO_2 = buildScenario(SCENARIO_ID_2, "Scenario Two");
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "Field value should be specified";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number " + "or zero and should not exceed 99999";

    private TaxNotificationReportWidget widget;
    private ITaxNotificationReportController controller;

    private static Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }

    @Before
    public void setUp() {
        controller = createMock(ITaxNotificationReportController.class);
        widget = new TaxNotificationReportWidget();
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        expect(controller.getScenarios()).andReturn(List.of(SCENARIO_1, SCENARIO_2)).once();
        replay(controller);
        widget.init();
        verifyWindow(widget, StringUtils.EMPTY, "600px", "400px", Unit.PIXELS, true);
        assertEquals("tax-notification-report-window", widget.getClassName());
        assertEquals("tax-notification-report-window", widget.getId().orElseThrow());
        assertThat(UiTestHelper.getDialogContent(widget), instanceOf(VerticalLayout.class));
        var content = (VerticalLayout) UiTestHelper.getDialogContent(widget);
        assertEquals(3, content.getComponentCount());
        var searchWidget = content.getComponentAt(0);
        assertThat(searchWidget, instanceOf(SearchWidget.class));
        var numberOfDaysField = content.getComponentAt(2);
        assertThat(numberOfDaysField, instanceOf(IntegerField.class));
        var grid = content.getComponentAt(1);
        assertThat(grid, instanceOf(Grid.class));
        verifyGrid((Grid) content.getComponentAt(1), List.of(Pair.of("Scenario Name", null)));
        verifyNumberOfDaysField((IntegerField) numberOfDaysField);
        verifyButtonsLayout(UiTestHelper.getFooterComponent(widget, 1));
        verify(controller);
    }

    @Test
    public void testExportButtonState() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getUIId()).andReturn(1).anyTimes();
        expect(ui.getLocale()).andReturn(createMock(Locale.class)).anyTimes();
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(source).times(3);
        expect(controller.getCsvStreamSource()).andReturn(streamSource).times(3);
        expect(controller.getScenarios()).andReturn(List.of(SCENARIO_1, SCENARIO_2)).once();
        replay(controller, ui, streamSource, UI.class);
        widget.init();
        var content = (VerticalLayout) UiTestHelper.getDialogContent(widget);
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponentAt(1);
        var numberOfDaysField = (IntegerField) content.getComponentAt(2);
        var exportButton = (Button) Whitebox.getInternalState(widget, "exportButton");
        grid.select(SCENARIO_1);
        numberOfDaysField.setValue(-1);
        assertFalse(exportButton.isEnabled());
        numberOfDaysField.setValue(10);
        assertTrue(exportButton.isEnabled());
        grid.select(SCENARIO_2);
        assertTrue(exportButton.isEnabled());
        grid.deselectAll();
        assertFalse(exportButton.isEnabled());
        verify(controller, ui, streamSource, UI.class);
    }

    @Test
    public void testGetSelectedScenarioIds() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).anyTimes();
        expect(ui.getUIId()).andReturn(1).anyTimes();
        expect(ui.getLocale()).andReturn(createMock(Locale.class)).anyTimes();
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(source).times(2);
        expect(controller.getCsvStreamSource()).andReturn(streamSource).times(2);
        expect(controller.getScenarios()).andReturn(List.of(SCENARIO_1, SCENARIO_2)).once();
        replay(controller, ui, streamSource, UI.class);
        widget = new TaxNotificationReportWidget();
        widget.setController(controller);
        widget.init();
        var content = (VerticalLayout) UiTestHelper.getDialogContent(widget);
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponentAt(1);
        grid.select(SCENARIO_1);
        assertEquals(Set.of(SCENARIO_ID_1), widget.getSelectedScenarioIds());
        grid.select(SCENARIO_2);
        assertEquals(Set.of(SCENARIO_ID_1, SCENARIO_ID_2), widget.getSelectedScenarioIds());
        grid.deselectAll();
        assertEquals(Set.of(), widget.getSelectedScenarioIds());
        verify(controller, ui, streamSource, UI.class);
    }

    @Test
    public void testNumberOfDaysFieldValidation() {
        expect(controller.getScenarios()).andReturn(List.of(SCENARIO_1, SCENARIO_2)).once();
        replay(controller);
        widget = new TaxNotificationReportWidget();
        widget.setController(controller);
        widget.init();
        verify(controller);
        Binder binder = Whitebox.getInternalState(widget, "binder");
        IntegerField numberOfDays = Whitebox.getInternalState(widget, "numberOfDaysField");
        validateFieldAndVerifyErrorMessage(numberOfDays, null, binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(numberOfDays, -1, binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE,
            false);
        validateFieldAndVerifyErrorMessage(numberOfDays, 999999, binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE,
            false);
        validateFieldAndVerifyErrorMessage(numberOfDays, 1, binder, "", true);
    }

    private void verifyNumberOfDaysField(IntegerField field) {
        assertEquals("Number of days since last notification", field.getLabel());
        assertEquals(Integer.valueOf(15), field.getValue());
    }

    public void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        var fileDownloader = layout.getComponentAt(0);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().get()).getText());
        var closeButton = layout.getComponentAt(1);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }
}
