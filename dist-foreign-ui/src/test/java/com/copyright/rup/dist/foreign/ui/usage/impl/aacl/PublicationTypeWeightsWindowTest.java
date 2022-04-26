package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsVisibility;
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
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
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

    private final List<PublicationType> defaultParams = ImmutableList.of(
        buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00"),
        buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "1.50"),
        buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", "1.00"),
        buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00"),
        buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "1.10"));
    private final List<PublicationType> appliedParams = ImmutableList.of(
        buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00"),
        buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "2.00"),
        buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", "3.00"),
        buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00"),
        buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "5.00"));

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
        List<PublicationType> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        assertGridItems(appliedParams);
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
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, StringUtils.EMPTY, binder,
            emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "   ", binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, " -1 ", binder, positiveNumberValidationMessage,
            false);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, ".05", binder, positiveNumberValidationMessage,
            false);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "99999999999", binder,
            positiveNumberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "value", binder, positiveNumberValidationMessage,
            false);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "0", binder, null, true);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, " 0.00 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "125", binder, null, true);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "125.123456789", binder, null, true);
        validateFieldAndVerifyErrorMessage(publicationTypeWeight, "999999999.99", binder, null, true);
    }

    private static void validateFieldAndVerifyErrorMessage(AbstractField field, String value, Binder binder,
                                                           String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages = errors
            .stream()
            .map(ValidationResult::getErrorMessage)
            .collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
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

    @SuppressWarnings("unchecked")
    private void assertGridItems(List<PublicationType> params) {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<PublicationType> grid = (Grid<PublicationType>) content.getComponent(0);
        assertTrue(grid.getDataProvider() instanceof ListDataProvider);
        assertEquals(params, ((ListDataProvider<PublicationType>) grid.getDataProvider()).getItems());
    }

    private PublicationType buildPublicationType(String id, String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
