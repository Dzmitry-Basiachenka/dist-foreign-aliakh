package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeesFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.ImmutableMap;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Verifies {@link ExcludePayeesFilterWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/15/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeesFilterWidgetTest {

    private IExcludePayeesFilterController controller;
    private ExcludePayeesFilterWidget widget;

    @Before
    public void setUp() {
        controller = createMock(IExcludePayeesFilterController.class);
        widget = new ExcludePayeesFilterWidget();
        widget.setController(controller);
    }

    @Test
    public void testStructure() {
        expect(controller.getParticipatingStatuses())
            .andReturn(ImmutableMap.of("Participating", Boolean.TRUE, "Not Participating", Boolean.FALSE))
            .once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLabel(widget.getComponent(0));
        verifyParticipatingStatusCombobox(widget.getComponent(1));
        verifyMinimumThresholdComponent(widget.getComponent(2));
        verifyButtonsLayout(widget.getComponent(3));
        verify(controller);
    }

    @Test
    public void testMinimumThresholdChangeValueListener() {
        expect(controller.getParticipatingStatuses())
            .andReturn(ImmutableMap.of("Participating", Boolean.TRUE, "Not Participating", Boolean.FALSE))
            .once();
        replay(controller);
        widget.init();
        BigDecimal filterValue = new BigDecimal("45.10");
        TextField threshold = Whitebox.getInternalState(widget, "minimumThreshold");
        Button applyButton = Whitebox.getInternalState(widget, "applyButton");
        ExcludePayeesFilter filter = Whitebox.getInternalState(widget, "filter");
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "45.10", filterValue, true);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "-45.10", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "45,10", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "45.10a", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, " 0 ", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "0", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, StringUtils.EMPTY, null, false);
        verify(controller);
    }

    private void setThresholdAndValidateFilterAndApplyButton(TextField minimumThreshold, Button applyButton,
                                                             ExcludePayeesFilter filter, String valueToSet,
                                                             BigDecimal filterValue, boolean applyEnabled) {
        minimumThreshold.setValue(valueToSet);
        if (Objects.nonNull(minimumThreshold.getErrorMessage())) {
            assertEquals("Field&#32;value&#32;should&#32;be&#32;positive&#32;number&#32;and&#32;should&#32;not&#32;" +
                    "exceed&#32;10&#32;digits", minimumThreshold.getErrorMessage().getFormattedHtmlMessage());
        }
        assertEquals(applyEnabled, applyButton.isEnabled());
        assertEquals(filterValue, filter.getNetAmountMinThreshold());
    }

    private void verifyParticipatingStatusCombobox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Participating Status", comboBox.getCaption());
        assertEquals(Sizeable.Unit.PERCENTAGE, comboBox.getWidthUnits());
        Collection<String> items = ((ListDataProvider<String>) comboBox.getDataProvider()).getItems();
        assertEquals(2, CollectionUtils.size(items));
        assertTrue(items.containsAll(Arrays.asList("Participating", "Not Participating")));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyMinimumThresholdComponent(Component component) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField textField = (TextField) component;
        assertEquals("Minimum Threshold", textField.getCaption());
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.isSpacing());
        verifyButton(layout.getComponent(0), "Apply");
        verifyButton(layout.getComponent(1), "Clear");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(100, button.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, button.getWidthUnits());
        verifyButtonClickListener(button);
    }

    private void verifyButtonClickListener(Button button) {
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        assertNotNull(listeners.iterator().next());
    }
}
