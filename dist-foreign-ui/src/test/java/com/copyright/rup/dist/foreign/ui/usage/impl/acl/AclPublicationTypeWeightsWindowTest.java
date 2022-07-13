package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
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
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.vaadin.data.provider.ListDataProvider;
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

    private final List<AclPublicationType> defaultParams = ImmutableList.of(
        buildAclPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00", 201506),
        buildAclPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "1.50", 201506),
        buildAclPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", "1.00", 201506),
        buildAclPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00", 201506),
        buildAclPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "1.10", 201506));
    private final List<AclPublicationType> appliedParams = ImmutableList.of(
        buildAclPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00", 201506),
        buildAclPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "2.00", 201506),
        buildAclPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", "3.00", 201506),
        buildAclPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00", 201506),
        buildAclPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "5.00", 201506));

    private AclPublicationTypeWeightsWindow window;

    @Before
    public void setUp() {
        window = new AclPublicationTypeWeightsWindow(true);
    }

    @Test
    public void testConstructorInEditMode() {
        verifyWindow(window, "Pub Type Weights", 525, 250, Unit.PIXELS);
        assertFalse(window.isResizable());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        assertEquals(2, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponent(0), "Save", true);
        verifyButton(buttonsLayout.getComponent(1), "Close", true);
    }

    @Test
    public void testConstructorInViewMode() {
        window = new AclPublicationTypeWeightsWindow(false);
        verifyWindow(window, "Pub Type Weights", 525, 250, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        verifyButtonsLayout(content.getComponent(1), "Save", "Close");
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsVisibility(ImmutableMap.of(
            buttonsLayout.getComponent(0), false,
            buttonsLayout.getComponent(1), true));
    }

    @Test
    public void testSaveButtonClickListener() {
        window.setAppliedParameters(appliedParams);
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        IParametersSaveListener<List<AclPublicationType>> listener = createMock(IParametersSaveListener.class);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        Capture<ParametersSaveEvent<List<AclPublicationType>>> event = new Capture<>();
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
        assertGridItems(appliedParams);
    }

    @Test
    public void testFireParametersSaveEvent() {
        IParametersSaveListener<List<AclPublicationType>> listener = createMock(IParametersSaveListener.class);
        ParametersSaveEvent<List<AclPublicationType>> event = new ParametersSaveEvent<>(window, defaultParams);
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
        assertFalse(grid.getDataProvider().isInMemory());
        assertFalse(grid.getEditor().isEnabled());
    }

    @SuppressWarnings("unchecked")
    private void assertGridItems(List<AclPublicationType> params) {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<AclPublicationType> grid = (Grid<AclPublicationType>) content.getComponent(0);
        assertTrue(grid.getDataProvider() instanceof ListDataProvider);
        assertEquals(params, ((ListDataProvider<AclPublicationType>) grid.getDataProvider()).getItems());
    }

    private AclPublicationType buildAclPublicationType(String id, String name, String weight, Integer period) {
        AclPublicationType pubType = new AclPublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        pubType.setPeriod(period);
        return pubType;
    }
}
