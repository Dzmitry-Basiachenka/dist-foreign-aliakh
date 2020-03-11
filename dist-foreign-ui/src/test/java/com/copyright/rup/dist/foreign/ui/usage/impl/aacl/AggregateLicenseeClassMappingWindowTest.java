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

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AggregateLicenseeClassMappingWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Ihar Suvorau
 */
public class AggregateLicenseeClassMappingWindowTest {

    private AggregateLicenseeClassMappingWindow window;
    private final List<DetailLicenseeClass> defaultParams =
        Arrays.asList(buildDetailLicenseeClass(1), buildDetailLicenseeClass(2));
    private final List<DetailLicenseeClass> appliedParams =
        Arrays.asList(buildDetailLicenseeClass(2), buildDetailLicenseeClass(1));

    @Before
    public void setUp() {
        window = new AggregateLicenseeClassMappingWindow();
        window.setDefaultParameters(defaultParams);
    }

    @Test
    public void testConstructor() {
        assertEquals("Licensee Class Mapping", window.getCaption());
        verifySize(window);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        assertEquals(3, buttonsLayout.getComponentCount());
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Button defaultButton = (Button) buttonsLayout.getComponent(1);
        Button closeButton = (Button) buttonsLayout.getComponent(2);
        assertEquals("Save", saveButton.getCaption());
        assertEquals("Default", defaultButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    @Test
    public void testSaveButtonClickListener() {
        window.setAppliedParameters(appliedParams);
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        IParametersSaveListener<List<DetailLicenseeClass>> listener = createMock(IParametersSaveListener.class);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        Capture<ParametersSaveEvent<List<DetailLicenseeClass>>> event = new Capture<>();
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
        Button defaultButton = (Button) buttonsLayout.getComponent(1);
        defaultButton.click();
        assertGridItems(defaultParams);
    }

    @Test
    public void testSetDefaultParameters() {
        window.setDefaultParameters(appliedParams);
        assertSame(appliedParams, Whitebox.getInternalState(window, "defaultValues"));
    }

    @Test
    public void testSetAppliedParameters() {
        window.setAppliedParameters(appliedParams);
        List<DetailLicenseeClass> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        assertGridItems(appliedParams);
    }

    @Test
    public void testFireParametersSaveEvent() {
        IParametersSaveListener<List<DetailLicenseeClass>> listener = createMock(IParametersSaveListener.class);
        ParametersSaveEvent<List<DetailLicenseeClass>> event = new ParametersSaveEvent<>(window, defaultParams);
        window.addListener(ParametersSaveEvent.class, listener, IParametersSaveListener.SAVE_HANDLER);
        listener.onSave(event);
        expectLastCall().once();
        replay(listener);
        window.fireParametersSaveEvent(event);
        verify(listener);
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail Licensee Class ID", "Enrollment Profile", "Discipline",
            "Aggregate Licensee Class ID", "Aggregate Licensee Class Name"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(160.0, 140.0, 210.0, 190.0, -1.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1, -1, -1, -1, 1),
            columns.stream().map(Grid.Column::getExpandRatio).collect(Collectors.toList()));
        columns.forEach(column -> assertTrue(column.isSortable()));
        assertFalse(grid.getDataProvider().isInMemory());
    }

    @SuppressWarnings("unchecked")
    private void assertGridItems(List<DetailLicenseeClass> params) {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<DetailLicenseeClass> grid = (Grid<DetailLicenseeClass>) content.getComponent(0);
        assertTrue(grid.getDataProvider().isInMemory());
        assertEquals(params, ((ListDataProvider<DetailLicenseeClass>) grid.getDataProvider()).getItems());
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer id) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        return detailLicenseeClass;
    }
}
