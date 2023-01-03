package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsVisibility;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AclPublicationTypeWeightsWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclPublicationTypeWeightsWindowTest {

    private final List<AclPublicationType> appliedParams = ImmutableList.of(
        buildAclPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "BK", "Book", "1.00", 201506),
        buildAclPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "OT", "Other",  "2.00", 201512));

    private IAclScenariosController controller;
    private AclPublicationTypeWeightsWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
        window = new AclPublicationTypeWeightsWindow(controller, true);
    }

    @Test
    public void testConstructorInEditMode() {
        verifyWindow(window, "Pub Type Weights", 525, 405, Unit.PIXELS);
        assertFalse(window.isResizable());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertThat(component, instanceOf(Grid.class));
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsLayout(content.getComponent(1), "Add", "Save", "Close");
        verifyButtonsVisibility(ImmutableMap.of(
            buttonsLayout.getComponent(0), true,
            buttonsLayout.getComponent(1), true,
            buttonsLayout.getComponent(2), true));
    }

    @Test
    public void testConstructorInViewMode() {
        window = new AclPublicationTypeWeightsWindow(controller, false);
        verifyWindow(window, "Pub Type Weights", 525, 405, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertThat(component, instanceOf(Grid.class));
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsLayout(buttonsLayout, "Add", "Save", "Close");
        verifyButtonsVisibility(ImmutableMap.of(
            buttonsLayout.getComponent(0), false,
            buttonsLayout.getComponent(1), false,
            buttonsLayout.getComponent(2), true));
    }

    @Test
    public void testSaveButtonClickListener() {
        window.setAppliedParameters(appliedParams);
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(1);
        IParametersSaveListener<List<AclPublicationType>> listener = createMock(IParametersSaveListener.class);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        Capture<ParametersSaveEvent<List<AclPublicationType>>> event = newCapture();
        listener.onSave(capture(event));
        expectLastCall().once();
        replay(listener);
        saveButton.click();
        assertEquals(appliedParams, event.getValue().getSavedParameters());
        verify(listener);
    }

    @Test
    public void testSetAppliedParameters() {
        window.setAppliedParameters(appliedParams);
        List<AclPublicationType> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        Object[][] expectedCells = {
            {"BK - Book", 201506, "1.00"},
            {"OT - Other", 201512, "2.00"},
        };
        verifyGridItems((Grid) ((VerticalLayout) window.getContent()).getComponent(0), appliedParams, expectedCells);
    }

    @Test
    public void testFireParametersSaveEvent() {
        IParametersSaveListener<List<AclPublicationType>> listener = createMock(IParametersSaveListener.class);
        ParametersSaveEvent<List<AclPublicationType>> event = new ParametersSaveEvent<>(window, appliedParams);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        listener.onSave(event);
        expectLastCall().once();
        replay(listener);
        window.fireParametersSaveEvent(event);
        verify(listener);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Pub Type", -1.0, -1),
            Triple.of("Period", -1.0, -1),
            Triple.of("Weight", -1.0, -1)));
        List<Column> columns = grid.getColumns();
        columns.forEach(column -> assertFalse(column.isSortable()));
        assertTrue(grid.getDataProvider().isInMemory());
        assertFalse(grid.getEditor().isEnabled());
    }

    private AclPublicationType buildAclPublicationType(String id, String name, String description, String weight,
                                                       Integer period) {
        AclPublicationType pubType = new AclPublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setDescription(description);
        pubType.setWeight(new BigDecimal(weight));
        pubType.setPeriod(period);
        return pubType;
    }
}
