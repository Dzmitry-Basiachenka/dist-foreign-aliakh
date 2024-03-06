package com.copyright.rup.dist.foreign.vui.usage.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.BigDecimalField;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link PublicationTypeWeightsWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Aliaksandr Liakh
 */
public class PublicationTypeWeightsWindowTest {

    private static final String NAME_1 = "Book";
    private static final String NAME_2 = "Business or Trade Journal";
    private static final String WEIGHT_1 = "1.00";
    private static final String WEIGHT_2 = "1.50";
    private static final String WEIGHT_3 = "2.00";

    private final List<PublicationType> defaultParams = List.of(
        buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", NAME_1, WEIGHT_1),
        buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", NAME_2, WEIGHT_2));
    private final List<PublicationType> appliedParams = List.of(
        buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", NAME_1, WEIGHT_1),
        buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", NAME_2, WEIGHT_3));
    private PublicationTypeWeightsWindow window;

    @Before
    public void setUp() {
        window = new PublicationTypeWeightsWindow(true);
        window.setDefault(defaultParams);
    }

    @Test
    public void testConstructorInEditMode() {
        verifyContent((VerticalLayout) getDialogContent(window));
        var buttonsLayout = getFooterLayout(window);
        verifyButton(buttonsLayout.getComponentAt(0), "Save", true, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true, true);
        verifyLabel(buttonsLayout.getComponentAt(2), StringUtils.EMPTY);
        verifyButton(buttonsLayout.getComponentAt(3), "Default", true, true);
    }

    @Test
    public void testConstructorInViewMode() {
        window = new PublicationTypeWeightsWindow(false);
        verifyContent((VerticalLayout) getDialogContent(window));
        var buttonsLayout = getFooterLayout(window);
        verifyButton(buttonsLayout.getComponentAt(0), "Save", false, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true, true);
        verifyLabel(buttonsLayout.getComponentAt(2), StringUtils.EMPTY);
        verifyButton(buttonsLayout.getComponentAt(3), "Default", false, true);
    }

    @Test
    public void testSaveButtonClickListener() {
        var mappingWindow = new TestPublicationTypeWeightsWindow(true);
        mappingWindow.setAppliedParameters(appliedParams);
        var saveButton = (Button) getFooterLayout(mappingWindow).getComponentAt(0);
        saveButton.click();
        assertTrue(mappingWindow.isClosed());
        var event = mappingWindow.getComponentEvent();
        assertThat(event, instanceOf(ParametersSaveEvent.class));
        var parametersSaveEvent = (ParametersSaveEvent) event;
        assertEquals(appliedParams, parametersSaveEvent.getSavedParameters());
        assertEquals(mappingWindow, parametersSaveEvent.getSource());
    }

    @Test
    public void testDefaultButtonClickListener() {
        var defaultButton = (Button) getFooterLayout(window).getComponentAt(3);
        defaultButton.click();
        Object[][] expectedCells = {
            {NAME_1, WEIGHT_1, WEIGHT_1},
            {NAME_2, WEIGHT_2, WEIGHT_2},
        };
        var grid = (Grid) ((VerticalLayout) getDialogContent(window)).getComponentAt(0);
        verifyGridItems(grid, defaultParams, expectedCells);
    }

    @Test
    public void testSetDefaultParameters() {
        window.setDefault(appliedParams);
        assertSame(appliedParams, Whitebox.getInternalState(window, "defaultValues"));
    }

    @Test
    public void testSetAppliedParameters() {
        window.setAppliedParameters(appliedParams);
        List<PublicationType> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        Object[][] expectedCells = {
            {NAME_1, WEIGHT_1, WEIGHT_1},
            {NAME_2, WEIGHT_2, WEIGHT_3},
        };
        var grid = (Grid) ((VerticalLayout) getDialogContent(window)).getComponentAt(0);
        verifyGridItems(grid, appliedParams, expectedCells);
    }

    @Test
    public void testPublicationTypeWeightFieldValidation() {
        Grid grid = Whitebox.getInternalState(window, "grid");
        var binder = grid.getEditor().getBinder();
        var fields = (List<BigDecimalField>) binder.getFields().collect(Collectors.toList());
        var weightField = fields.get(0);
        var positiveNumberValidationMessage = "Field value should be positive number and should not exceed 10 digits";
        assertFieldValidationMessage(weightField, new BigDecimal("-1"), binder, positiveNumberValidationMessage, false);
        assertFieldValidationMessage(weightField, new BigDecimal(".05"), binder, null, true);
        assertFieldValidationMessage(weightField, new BigDecimal("99999999999"), binder,
            positiveNumberValidationMessage, false);
        assertFieldValidationMessage(weightField, BigDecimal.ZERO, binder, null, true);
        assertFieldValidationMessage(weightField, new BigDecimal("0.00"), binder, null, true);
        assertFieldValidationMessage(weightField, new BigDecimal("125"), binder, null, true);
        assertFieldValidationMessage(weightField, new BigDecimal("125.123456789"), binder, null, true);
        assertFieldValidationMessage(weightField, new BigDecimal("999999999.99"), binder, null, true);
    }

    private void verifyContent(VerticalLayout content) {
        verifyWindow(window, "Pub Type Weights", "500px", "300px", Unit.PIXELS, false);
        assertEquals(1, content.getComponentCount());
        verifyGrid((Grid<PublicationType>) content.getComponentAt(0));
    }

    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Pub Type", "188px"),
            Pair.of("Default Weight", "150px"),
            Pair.of("Scenario Weight", "160px")));
    }

    private PublicationType buildPublicationType(String id, String name, String weight) {
        var pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }

    private static class TestPublicationTypeWeightsWindow extends PublicationTypeWeightsWindow {

        private ComponentEvent<?> componentEvent;
        private boolean closed;

        TestPublicationTypeWeightsWindow(boolean isEditable) {
            super(isEditable);
        }

        EventObject getComponentEvent() {
            return componentEvent;
        }

        boolean isClosed() {
            return closed;
        }

        @Override
        protected void fireEvent(ComponentEvent<?> event) {
            this.componentEvent = event;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
