package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;
import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
public class TaxNotificationReportWidgetTest {

    private static final String SCENARIO_ID_1 = "32894a7f-b218-4347-b8f9-8236f75c9b2a";
    private static final String SCENARIO_ID_2 = "69a22caf-57da-45fa-afbb-35cf96ffd0ba";
    private static final Scenario SCENARIO_1 = buildScenario(SCENARIO_ID_1, "Scenario One");
    private static final Scenario SCENARIO_2 = buildScenario(SCENARIO_ID_2, "Scenario Two");
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "Field value should be specified";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number " + "or zero and should not exceed 99999";

    private TaxNotificationReportWidget widget;

    private static Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }

    @Before
    public void setUp() {
        ITaxNotificationReportController controller = createMock(ITaxNotificationReportController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(
            new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getScenarios()).andReturn(Arrays.asList(SCENARIO_1, SCENARIO_2)).once();
        replay(controller, streamSource);
        widget = new TaxNotificationReportWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, streamSource);
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 600, 400, Unit.PIXELS);
        assertEquals("tax-notification-report-window", widget.getStyleName());
        assertEquals("tax-notification-report-window", widget.getId());
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(4, content.getComponentCount());
        Component searchWidget = content.getComponent(0);
        assertThat(searchWidget, instanceOf(SearchWidget.class));
        Component numberOfDaysField = content.getComponent(2);
        assertThat(numberOfDaysField, instanceOf(TextField.class));
        Component grid = content.getComponent(1);
        assertThat(grid, instanceOf(Grid.class));
        verifyGrid((Grid) content.getComponent(1), List.of(Triple.of("Scenario Name", -1.0, -1)));
        verifyNumberOfDaysField((TextField) numberOfDaysField);
        verifyButtonsLayout(content.getComponent(3), "Export", "Close");
    }

    @Test
    public void testExportButtonState() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        TextField numberOfDaysField = (TextField) content.getComponent(2);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(3);
        Button exportButton = (Button) buttonsLayout.getComponent(0);
        grid.select(SCENARIO_1);
        numberOfDaysField.setValue("asdf");
        assertFalse(exportButton.isEnabled());
        numberOfDaysField.setValue("-1");
        assertFalse(exportButton.isEnabled());
        numberOfDaysField.setValue("10");
        assertTrue(exportButton.isEnabled());
        grid.select(SCENARIO_2);
        assertTrue(exportButton.isEnabled());
        grid.deselectAll();
        assertFalse(exportButton.isEnabled());
    }

    @Test
    public void testGetSelectedScenarioIds() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        grid.select(SCENARIO_1);
        assertEquals(Collections.singleton(SCENARIO_ID_1), widget.getSelectedScenarioIds());
        grid.select(SCENARIO_2);
        assertEquals(Sets.newHashSet(SCENARIO_ID_1, SCENARIO_ID_2), widget.getSelectedScenarioIds());
        grid.deselectAll();
        assertEquals(Collections.emptySet(), widget.getSelectedScenarioIds());
    }

    @Test
    public void testNumberOfDaysFieldValidation() {
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField numberOfDays = Whitebox.getInternalState(widget, "numberOfDaysField");
        validateFieldAndVerifyErrorMessage(numberOfDays, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(numberOfDays, "   ", binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(numberOfDays, "a", binder, "Field value should contain numeric values only",
            false);
        validateFieldAndVerifyErrorMessage(numberOfDays, "-1", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE,
            false);
        validateFieldAndVerifyErrorMessage(numberOfDays, "999999", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE,
            false);
        validateFieldAndVerifyErrorMessage(numberOfDays, "0", binder, null, true);
        validateFieldAndVerifyErrorMessage(numberOfDays, "99999", binder, null, true);
    }

    private void verifyNumberOfDaysField(TextField field) {
        assertEquals("Number of days since last notification", field.getCaption());
        assertEquals("15", field.getValue());
    }
}
