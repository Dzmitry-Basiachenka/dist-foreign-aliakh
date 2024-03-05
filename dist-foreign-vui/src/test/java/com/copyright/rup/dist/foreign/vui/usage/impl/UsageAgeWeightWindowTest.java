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

import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.EventObject;
import java.util.List;

/**
 * Verifies {@link UsageAgeWeightWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/13/2020
 *
 * @author Uladzislau Shalamitski
 */
public class UsageAgeWeightWindowTest {

    private static final String WEIGHT_1 = "1.00";
    private static final String WEIGHT_2 = "0.90";
    private static final String WEIGHT_3 = "0.80";

    private final List<UsageAge> defaultParams = List.of(
        buildUsageAge(2020, new BigDecimal(WEIGHT_1)),
        buildUsageAge(2019, new BigDecimal(WEIGHT_2)));
    private final List<UsageAge> appliedParams = List.of(
        buildUsageAge(2020, new BigDecimal(WEIGHT_1)),
        buildUsageAge(2019, new BigDecimal(WEIGHT_3)));
    private UsageAgeWeightWindow window;

    @Before
    public void setUp() {
        window = new UsageAgeWeightWindow(true);
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
    public void testConstructorInViewMode() {
        window = new UsageAgeWeightWindow(false);
        verifyContent((VerticalLayout) getDialogContent(window));
        var buttonsLayout = getFooterLayout(window);
        verifyButton(buttonsLayout.getComponentAt(0), "Save", false, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true, true);
        verifyLabel(buttonsLayout.getComponentAt(2), StringUtils.EMPTY);
        verifyButton(buttonsLayout.getComponentAt(3), "Default", false, true);
    }

    @Test
    public void testSaveButtonClickListener() {
        var mappingWindow = new TestUsageAgeWeightWindow(true);
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
        Object[][] expectedCells = {
            {"2020", WEIGHT_1, WEIGHT_1},
            {"2019", WEIGHT_2, WEIGHT_2},
        };
        Grid grid = (Grid) ((VerticalLayout) getDialogContent(window)).getComponentAt(0);
        verifyGridItems(grid, defaultParams, expectedCells);
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
            {"2020", WEIGHT_1, WEIGHT_1},
            {"2019", WEIGHT_2, WEIGHT_3},
        };
        Grid grid = (Grid) ((VerticalLayout) getDialogContent(window)).getComponentAt(0);
        verifyGridItems(grid, appliedParams, expectedCells);
    }

    @Test
    public void testWeightFieldValidation() {
        //TODO {aliakh} implement when new grid editing is implemented
    }

    private void verifyContent(VerticalLayout content) {
        verifyWindow(window, "Usage Age Weights", "525px", "300px", Unit.PIXELS, false);
        assertEquals(1, content.getComponentCount());
        verifyGrid((Grid<UsageAge>) content.getComponentAt(0));
    }

    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Usage Period", null),
            Pair.of("Default Weight", null),
            Pair.of("Scenario Weight", null)));
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        var usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private static class TestUsageAgeWeightWindow extends UsageAgeWeightWindow {

        private ComponentEvent<?> componentEvent;
        private boolean closed;

        TestUsageAgeWeightWindow(boolean isEditable) {
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
