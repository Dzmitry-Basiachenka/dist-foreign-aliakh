package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.validateFieldAndVefiryErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyButtonsVisibility;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
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
        verifyWindow(window, "Usage Age Weights", 525, 300, Unit.PIXELS);
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
        window = new AaclUsageAgeWeightWindow(false);
        verifyWindow(window, "Usage Age Weights", 525, 300, Unit.PIXELS);
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
        validateFieldAndVefiryErrorMessage(weightField, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        validateFieldAndVefiryErrorMessage(weightField, "   ", binder, emptyFieldValidationMessage, false);
        validateFieldAndVefiryErrorMessage(weightField, " -1 ", binder, positiveNumberValidationMessage, false);
        validateFieldAndVefiryErrorMessage(weightField, ".05", binder, positiveNumberValidationMessage, false);
        validateFieldAndVefiryErrorMessage(weightField, "99999999999", binder, positiveNumberValidationMessage, false);
        validateFieldAndVefiryErrorMessage(weightField, "value", binder, positiveNumberValidationMessage, false);
        validateFieldAndVefiryErrorMessage(weightField, "0", binder, null, true);
        validateFieldAndVefiryErrorMessage(weightField, " 0.00 ", binder, null, true);
        validateFieldAndVefiryErrorMessage(weightField, "125", binder, null, true);
        validateFieldAndVefiryErrorMessage(weightField, "125.123456789", binder, null, true);
        validateFieldAndVefiryErrorMessage(weightField, "9999999999.99", binder, null, true);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid, boolean isEditorEnabled) {
        UiCommonHelper.verifyGrid((Grid) grid, Arrays.asList(
            Triple.of("Usage Period", -1.0, -1),
            Triple.of("Default Weight", -1.0, -1),
            Triple.of("Scenario Weight", -1.0, -1)));
        List<Column> columns = grid.getColumns();
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
}
