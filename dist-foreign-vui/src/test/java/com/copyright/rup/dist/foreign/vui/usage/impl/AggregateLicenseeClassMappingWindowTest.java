package com.copyright.rup.dist.foreign.vui.usage.impl;

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

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.EventObject;
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

    private final List<DetailLicenseeClass> defaultParams = List.of(
        buildDetailLicenseeClass(1, "EXGP", "Life Sciences", 51, "HGP", "Social & Behavioral Sciences"),
        buildDetailLicenseeClass(2, "MU", "Business Management", 52, "EXU4", "Medical & Health"));
    private final List<DetailLicenseeClass> appliedParams = List.of(
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
        verifyContent((VerticalLayout) getDialogContent(window));
        var buttonsLayout = getFooterLayout(window);
        verifyButton(buttonsLayout.getComponentAt(0), "Save", true, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true, true);
        verifyLabel(buttonsLayout.getComponentAt(2), StringUtils.EMPTY);
        verifyButton(buttonsLayout.getComponentAt(3), "Default", true, true);
    }

    @Test
    public void testConstructorInReadOnlyMode() {
        window = new AggregateLicenseeClassMappingWindow(false);
        verifyContent((VerticalLayout) getDialogContent(window));
        var buttonsLayout = getFooterLayout(window);
        verifyButton(buttonsLayout.getComponentAt(0), "Save", false, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true, true);
        verifyLabel(buttonsLayout.getComponentAt(2), StringUtils.EMPTY);
        verifyButton(buttonsLayout.getComponentAt(3), "Default", false, true);
    }

    @Test
    public void testSaveButtonClickListener() {
        var mappingWindow = new TestAggregateLicenseeClassMappingWindow(true);
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
        assertGridItems(defaultParams);
    }

    @Test
    public void testSetDefaultParameters() {
        window.setDefault(appliedParams);
        assertSame(appliedParams, Whitebox.getInternalState(window, "defaultValues"));
    }

    @Test
    public void testSetAppliedParameters() {
        window = new AggregateLicenseeClassMappingWindow(false);
        window.setAppliedParameters(appliedParams);
        List<DetailLicenseeClass> currentValues = Whitebox.getInternalState(window, "currentValues");
        assertNotSame(appliedParams, currentValues);
        assertEquals(appliedParams, currentValues);
        currentValues.forEach(
            currentValue -> assertNotSame(appliedParams.get(currentValues.indexOf(currentValue)), currentValue));
        Object[][] expectedCells = {
            {"1", "EXGP", "Life Sciences", "51", "HGP", "Social & Behavioral Sciences"},
            {"2", "MU", "Business Management", "53", "EXU2", "Education"}
        };
        Grid grid = (Grid) ((VerticalLayout) getDialogContent(window)).getComponentAt(0);
        verifyGridItems(grid, appliedParams, expectedCells);
    }

    private void verifyContent(VerticalLayout content) {
        verifyWindow(window, "Licensee Class Mapping", "950px", "550px", Unit.PIXELS, false);
        assertEquals(1, content.getComponentCount());
        verifyGrid((Grid<DetailLicenseeClass>) content.getComponentAt(0));
    }

    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Det LC ID", "105px"),
            Pair.of("Det LC Enrollment", "180px"),
            Pair.of("Det LC Discipline", "155px"),
            Pair.of("Agg LC ID", "110px"),
            Pair.of("Agg LC Enrollment", "190px"),
            Pair.of("Agg LC Discipline", "230px")));
    }

    private void assertGridItems(List<DetailLicenseeClass> params) {
        var content = (VerticalLayout) getDialogContent(window);
        var grid = (Grid<DetailLicenseeClass>) content.getComponentAt(0);
        assertEquals(params, ((ListDataProvider<DetailLicenseeClass>) grid.getDataProvider()).getItems());
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detLicClassId, String detLicClassEnrollment,
                                                         String detLicClassDiscipline, Integer aggLicClassId,
                                                         String aggLicClassEnrollment, String aggLicClassDiscipline) {
        var detLicClass = new DetailLicenseeClass();
        detLicClass.setId(detLicClassId);
        detLicClass.setEnrollmentProfile(detLicClassEnrollment);
        detLicClass.setDiscipline(detLicClassDiscipline);
        detLicClass.setAggregateLicenseeClass(
            buildAggregateLicenseeClass(aggLicClassId, aggLicClassEnrollment, aggLicClassDiscipline));
        return detLicClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer aggLicClassId, String aggLicClassEnrollment,
                                                               String aggLicClassDiscipline) {
        var aggLicClass = new AggregateLicenseeClass();
        aggLicClass.setId(aggLicClassId);
        aggLicClass.setEnrollmentProfile(aggLicClassEnrollment);
        aggLicClass.setDiscipline(aggLicClassDiscipline);
        return aggLicClass;
    }

    private static class TestAggregateLicenseeClassMappingWindow extends AggregateLicenseeClassMappingWindow {

        private ComponentEvent<?> componentEvent;
        private boolean closed;

        TestAggregateLicenseeClassMappingWindow(boolean isEditable) {
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
