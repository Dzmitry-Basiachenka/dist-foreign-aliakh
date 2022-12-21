package com.copyright.rup.dist.foreign.ui.usage.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsVisibility;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridEditableFieldErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AclUsageAgeWeightWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/15/2022
 *
 * @author Anton Azarenka
 */
public class AclUsageAgeWeightWindowTest {

    private static final String WEIGHT_1 = "1.00";
    private static final String WEIGHT_2 = "0.90";
    private static final String WEIGHT_3 = "0.80";

    private final List<UsageAge> defaultParams = Arrays.asList(
        buildUsageAge(0, new BigDecimal(WEIGHT_1)),
        buildUsageAge(1, new BigDecimal(WEIGHT_2)));
    private final List<UsageAge> appliedParams = Arrays.asList(
        buildUsageAge(0, new BigDecimal(WEIGHT_1)),
        buildUsageAge(1, new BigDecimal(WEIGHT_3)));
    private AclUsageAgeWeightWindow window;

    @Before
    public void setUp() {
        window = new AclUsageAgeWeightWindow(true);
        window.setDefault(defaultParams);
    }

    @Test
    public void testConstructorInEditMode() {
        verifyWindow(window, "Usage Age Weights", 600, 430, Unit.PIXELS);
        assertFalse(window.isResizable());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, true);
        assertEquals(1, content.getExpandRatio(component), 0);
        verifyButtonsLayout(content.getComponent(1), "Save", "Close", null, "Default");
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsVisibility(ImmutableMap.of(
            buttonsLayout.getComponent(0), true,
            buttonsLayout.getComponent(1), true,
            buttonsLayout.getComponent(2), true,
            buttonsLayout.getComponent(3), true));
    }

    @Test
    public void testConstructorInViewMode() {
        window = new AclUsageAgeWeightWindow(false);
        verifyWindow(window, "Usage Age Weights", 600, 430, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, false);
        assertEquals(1, content.getExpandRatio(component), 0);
        verifyButtonsLayout(content.getComponent(1), "Save", "Close", null, "Default");
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsVisibility(ImmutableMap.of(
            buttonsLayout.getComponent(0), false,
            buttonsLayout.getComponent(1), true,
            buttonsLayout.getComponent(2), false,
            buttonsLayout.getComponent(3), false));
    }

    @Test
    public void testSaveButtonClickListener() {
        window.setAppliedParameters(appliedParams);
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        IParametersSaveListener<List<UsageAge>> listener = createMock(IParametersSaveListener.class);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        Capture<ParametersSaveEvent<List<UsageAge>>> event = newCapture();
        listener.onSave(capture(event));
        expectLastCall().once();
        replay(listener);
        saveButton.click();
        assertEquals(appliedParams, event.getValue().getSavedParameters());
        verify(listener);
    }

    @Test
    public void testDefaultButtonClickListener() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button defaultButton = (Button) buttonsLayout.getComponent(3);
        defaultButton.click();
        Object[][] expectedCells = {
            {0, WEIGHT_1, WEIGHT_1},
            {1, WEIGHT_2, WEIGHT_2},
        };
        verifyGridItems((Grid) ((VerticalLayout) window.getContent()).getComponent(0), defaultParams, expectedCells);
    }

    @Test
    public void testSetDefaultParameters() {
        window.setDefault(appliedParams);
        assertSame(appliedParams, Whitebox.getInternalState(window, "defaultValues"));
    }

    @Test
    public void testSetAppliedParameters() {
        window.setAppliedParameters(appliedParams);
        List<UsageAge> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        Object[][] expectedCells = {
            {0, WEIGHT_1, WEIGHT_1},
            {1, WEIGHT_2, WEIGHT_3},
        };
        verifyGridItems((Grid) ((VerticalLayout) window.getContent()).getComponent(0), appliedParams, expectedCells);
    }

    @Test
    public void testFireParametersSaveEvent() {
        IParametersSaveListener<List<UsageAge>> listener = createMock(IParametersSaveListener.class);
        ParametersSaveEvent<List<UsageAge>> event = new ParametersSaveEvent<>(window, defaultParams);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        listener.onSave(event);
        expectLastCall().once();
        replay(listener);
        window.fireParametersSaveEvent(event);
        verify(listener);
    }

    @Test
    public void testWeightFieldValidation() {
        Grid grid = Whitebox.getInternalState(window, "grid");
        Binder binder = grid.getEditor().getBinder();
        List<TextField> fields = (List<TextField>) binder.getFields().collect(Collectors.toList());
        TextField weightField = fields.get(0);
        String emptyFieldValidationMessage = "Field value should be specified";
        String rangeNumberValidationMessage = "Field value should be in range from 0 to 1";
        String scaleNumberValidationMessage = "Field value should not exceed 2 digits after the decimal point";
        verifyGridEditableFieldErrorMessage(weightField, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "   ", binder, emptyFieldValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, " -1 ", binder, rangeNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, ".05", binder, scaleNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "99999999999", binder, rangeNumberValidationMessage,
            false);
        verifyGridEditableFieldErrorMessage(weightField, "value", binder, rangeNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "0", binder, null, true);
        verifyGridEditableFieldErrorMessage(weightField, "-1", binder, rangeNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, " 0.00 ", binder, null, true);
        verifyGridEditableFieldErrorMessage(weightField, "1.1", binder, rangeNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "0.1", binder, null, true);
        verifyGridEditableFieldErrorMessage(weightField, "0.12", binder, null, true);
        verifyGridEditableFieldErrorMessage(weightField, "0.123", binder, scaleNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "0.12345", binder, scaleNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "0.123456", binder, scaleNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "0.1234567", binder, scaleNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(weightField, "9999999999.99", binder, rangeNumberValidationMessage, false);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid, boolean isEditorEnabled) {
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Period Prior", -1.0, -1),
            Triple.of("Default Weight", -1.0, -1),
            Triple.of("Scenario Weight", -1.0, -1)));
        List<Column> columns = grid.getColumns();
        columns.forEach(column -> assertFalse(column.isSortable()));
        assertFalse(grid.getDataProvider().isInMemory());
        assertEquals(isEditorEnabled, grid.getEditor().isEnabled());
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }
}
