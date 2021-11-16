package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AaclUsageAgeWeightWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/13/2020
 *
 * @author Uladzislau Shalamitski
 */
public class AaclUsageAgeWeightWindowTest {

    private AaclUsageAgeWeightWindow window;
    private final List<UsageAge> defaultParams =
        Arrays.asList(buildUsageAge(2020, new BigDecimal("1.00")), buildUsageAge(2019, new BigDecimal("0.75")));
    private final List<UsageAge> appliedParams =
        Arrays.asList(buildUsageAge(2020, new BigDecimal("0.75")), buildUsageAge(2019, new BigDecimal("1.00")));

    @Before
    public void setUp() {
        window = new AaclUsageAgeWeightWindow(true);
        window.setDefault(defaultParams);
    }

    @Test
    public void testConstructorInEditMode() {
        assertEquals("Usage Age Weights", window.getCaption());
        verifySize(window);
        assertFalse(window.isResizable());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, true);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        assertEquals(4, buttonsLayout.getComponentCount());
        Component saveButton = buttonsLayout.getComponent(0);
        Component closeButton = buttonsLayout.getComponent(1);
        Component placeholderLabel = buttonsLayout.getComponent(2);
        Component defaultButton = buttonsLayout.getComponent(3);
        assertEquals("Save", saveButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
        assertTrue(placeholderLabel instanceof Label);
        assertNull(placeholderLabel.getCaption());
        assertEquals("Default", defaultButton.getCaption());
        assertTrue(saveButton.isVisible());
        assertTrue(defaultButton.isVisible());
        assertTrue(placeholderLabel.isVisible());
        assertTrue(closeButton.isVisible());
    }

    @Test
    public void testConstructorInViewMode() {
        window = new AaclUsageAgeWeightWindow(false);
        assertEquals("Usage Age Weights", window.getCaption());
        verifySize(window);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, false);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        assertEquals(4, buttonsLayout.getComponentCount());
        Component saveButton = buttonsLayout.getComponent(0);
        Component closeButton = buttonsLayout.getComponent(1);
        Component placeholderLabel = buttonsLayout.getComponent(2);
        Component defaultButton = buttonsLayout.getComponent(3);
        assertEquals("Save", saveButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
        assertTrue(placeholderLabel instanceof Label);
        assertNull(placeholderLabel.getCaption());
        assertEquals("Default", defaultButton.getCaption());
        assertFalse(saveButton.isVisible());
        assertFalse(defaultButton.isVisible());
        assertFalse(placeholderLabel.isVisible());
        assertTrue(closeButton.isVisible());
    }

    @Test
    public void testSaveButtonClickListener() {
        window.setAppliedParameters(appliedParams);
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        IParametersSaveListener<List<UsageAge>> listener = createMock(IParametersSaveListener.class);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        Capture<ParametersSaveEvent<List<UsageAge>>> event = new Capture<>();
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
        assertGridItems(defaultParams);
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
        assertGridItems(appliedParams);
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
        String positiveNumberValidationMessage = "Field value should be positive number or zero";
        verifyField(weightField, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        verifyField(weightField, "   ", binder, emptyFieldValidationMessage, false);
        verifyField(weightField, " -1 ", binder, positiveNumberValidationMessage, false);
        verifyField(weightField, ".05", binder, positiveNumberValidationMessage, false);
        verifyField(weightField, "99999999999", binder, positiveNumberValidationMessage, false);
        verifyField(weightField, "value", binder, positiveNumberValidationMessage, false);
        verifyField(weightField, "0", binder, null, true);
        verifyField(weightField, " 0.00 ", binder, null, true);
        verifyField(weightField, "125", binder, null, true);
        verifyField(weightField, "125.123456789", binder, null, true);
        verifyField(weightField, "9999999999.99", binder, null, true);
    }

    private void verifySize(Component component) {
        assertEquals(525, component.getWidth(), 0);
        assertEquals(300, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid, boolean isEditorEnabled) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Period", "Default Weight", "Scenario Weight"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1.0, -1.0, -1.0),
            columns.stream().map(Column::getWidth).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1, -1, -1),
            columns.stream().map(Column::getExpandRatio).collect(Collectors.toList()));
        columns.forEach(column -> assertFalse(column.isSortable()));
        assertFalse(grid.getDataProvider().isInMemory());
        assertEquals(isEditorEnabled, grid.getEditor().isEnabled());
    }

    @SuppressWarnings("unchecked")
    private void assertGridItems(List<UsageAge> params) {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<UsageAge> grid = (Grid<UsageAge>) content.getComponent(0);
        assertTrue(grid.getDataProvider().isInMemory());
        assertEquals(params, ((ListDataProvider<UsageAge>) grid.getDataProvider()).getItems());
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private void verifyField(TextField field, String value, Binder binder, String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }
}
