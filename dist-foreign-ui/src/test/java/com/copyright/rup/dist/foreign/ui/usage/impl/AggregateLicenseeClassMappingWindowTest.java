package com.copyright.rup.dist.foreign.ui.usage.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsVisibility;
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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

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

import java.util.Arrays;
import java.util.List;

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

    private final List<DetailLicenseeClass> defaultParams = Arrays.asList(
        buildDetailLicenseeClass(1, "EXGP", "Life Sciences", 51, "HGP", "Social & Behavioral Sciences"),
        buildDetailLicenseeClass(2, "MU", "Business Management", 52, "EXU4", "Medical & Health"));
    private final List<DetailLicenseeClass> appliedParams = Arrays.asList(
        buildDetailLicenseeClass(1, "EXGP", "Life Sciences", 51, "HGP", "Social & Behavioral Sciences"),
        buildDetailLicenseeClass(2, "MU", "Business Management", 53, "EXU2", "Education"));
    private AggregateLicenseeClassMappingWindow window;

    @Before
    public void setUp() {
        window = new AggregateLicenseeClassMappingWindow(true);
        window.setDefault(defaultParams);
    }

    @Test
    public void testConstructorInEditMode() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        verifyCommonWindowComponents(content);
        verifyButtonsLayout(content.getComponent(1), "Save", "Close", null, "Default");
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsVisibility(ImmutableMap.of(
            buttonsLayout.getComponent(0), true,
            buttonsLayout.getComponent(1), true,
            buttonsLayout.getComponent(2), true,
            buttonsLayout.getComponent(3), true));
    }

    @Test
    public void testConstructorInReadOnlyMode() {
        window = new AggregateLicenseeClassMappingWindow(false);
        VerticalLayout content = (VerticalLayout) window.getContent();
        verifyCommonWindowComponents(content);
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
        AggregateLicenseeClassMappingWindow mappingWindow = new AggregateLicenseeClassMappingWindow(false);
        mappingWindow.setAppliedParameters(appliedParams);
        List<DetailLicenseeClass> currentValues = Whitebox.getInternalState(mappingWindow, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        Object[][] expectedCells = {
            {1, "EXGP", "Life Sciences", 51, "HGP", "Social & Behavioral Sciences"},
            {2, "MU", "Business Management", 53, "EXU2", "Education"}
        };
        Grid grid = (Grid) ((VerticalLayout) mappingWindow.getContent()).getComponent(0);
        verifyGridItems(grid, appliedParams, expectedCells);
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

    private void verifyCommonWindowComponents(VerticalLayout content) {
        verifyWindow(window, "Licensee Class Mapping", 950, 550, Unit.PIXELS);
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Det LC ID", -1.0, 1),
            Triple.of("Det LC Enrollment", -1.0, 2),
            Triple.of("Det LC Discipline", -1.0, 3),
            Triple.of("Agg LC ID", -1.0, 1),
            Triple.of("Agg LC Enrollment", -1.0, 2),
            Triple.of("Agg LC Discipline", -1.0, 3)));
        List<Column> columns = grid.getColumns();
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

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detLicClassId, String detLicClassEnrollment,
                                                         String detLicClassDiscipline, Integer aggLicClassId,
                                                         String aggLicClassEnrollment, String aggLicClassDiscipline) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(detLicClassId);
        detailLicenseeClass.setEnrollmentProfile(detLicClassEnrollment);
        detailLicenseeClass.setDiscipline(detLicClassDiscipline);
        detailLicenseeClass.setAggregateLicenseeClass(
            buildAggregateLicenseeClass(aggLicClassId, aggLicClassEnrollment, aggLicClassDiscipline));
        return detailLicenseeClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer aggLicClassId, String aggLicClassEnrollment,
                                                               String aggLicClassDiscipline) {
        AggregateLicenseeClass aggLicClass = new AggregateLicenseeClass();
        aggLicClass.setId(aggLicClassId);
        aggLicClass.setEnrollmentProfile(aggLicClassEnrollment);
        aggLicClass.setDiscipline(aggLicClassDiscipline);
        return aggLicClass;
    }
}
