package com.copyright.rup.dist.foreign.ui.usage.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsVisibility;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridEditableFieldErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

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

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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

    private final List<PublicationType> defaultParams = ImmutableList.of(
        buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", NAME_1, WEIGHT_1),
        buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", NAME_2, WEIGHT_2));
    private final List<PublicationType> appliedParams = ImmutableList.of(
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
        verifyWindow(window, "Pub Type Weights", 525, 250, Unit.PIXELS);
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
        window = new PublicationTypeWeightsWindow(false);
        verifyWindow(window, "Pub Type Weights", 525, 250, Unit.PIXELS);
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
        IParametersSaveListener<List<PublicationType>> listener = createMock(IParametersSaveListener.class);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        Capture<ParametersSaveEvent<List<PublicationType>>> event = new Capture<>();
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
            {NAME_1, WEIGHT_1, WEIGHT_1},
            {NAME_2, WEIGHT_2, WEIGHT_2},
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
        List<PublicationType> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        Object[][] expectedCells = {
            {NAME_1, WEIGHT_1, WEIGHT_1},
            {NAME_2, WEIGHT_2, WEIGHT_3},
        };
        verifyGridItems((Grid) ((VerticalLayout) window.getContent()).getComponent(0), appliedParams, expectedCells);
    }

    @Test
    public void testFireParametersSaveEvent() {
        IParametersSaveListener<List<PublicationType>> listener = createMock(IParametersSaveListener.class);
        ParametersSaveEvent<List<PublicationType>> event = new ParametersSaveEvent<>(window, defaultParams);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        listener.onSave(event);
        expectLastCall().once();
        replay(listener);
        window.fireParametersSaveEvent(event);
        verify(listener);
    }

    @Test
    public void testPublicationTypeWeightFieldValidation() {
        Grid grid = Whitebox.getInternalState(window, "grid");
        Binder binder = grid.getEditor().getBinder();
        List<TextField> fields = (List<TextField>) binder.getFields().collect(Collectors.toList());
        TextField publicationTypeWeight = fields.get(0);
        String emptyFieldValidationMessage = "Field value should be specified";
        String positiveNumberValidationMessage = "Field value should be positive number or zero";
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, StringUtils.EMPTY, binder,
            emptyFieldValidationMessage, false);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "   ", binder, emptyFieldValidationMessage, false);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, " -1 ", binder, positiveNumberValidationMessage,
            false);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, ".05", binder, positiveNumberValidationMessage,
            false);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "99999999999", binder,
            positiveNumberValidationMessage, false);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "value", binder, positiveNumberValidationMessage,
            false);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "0", binder, null, true);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, " 0.00 ", binder, null, true);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "125", binder, null, true);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "125.123456789", binder, null, true);
        verifyGridEditableFieldErrorMessage(publicationTypeWeight, "999999999.99", binder, null, true);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid, boolean isEditorEnabled) {
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Pub Type", -1.0, -1),
            Triple.of("Default Weight", -1.0, -1),
            Triple.of("Scenario Weight", -1.0, -1)));
        List<Column> columns = grid.getColumns();
        columns.forEach(column -> assertFalse(column.isSortable()));
        assertFalse(grid.getDataProvider().isInMemory());
        assertEquals(isEditorEnabled, grid.getEditor().isEnabled());
    }

    private PublicationType buildPublicationType(String id, String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
