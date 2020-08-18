package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.ImmutableMap;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
import java.util.Iterator;
import java.util.Objects;

/**
 * Verifies {@link FasExcludePayeeFilterWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/15/19
 *
 * @author Uladzislau Shalamitski
 */
public class FasExcludePayeeFilterWidgetTest {

    private IExcludePayeeFilterController controller;
    private FasExcludePayeeFilterWidget widget;

    @Before
    public void setUp() {
        controller = createMock(IExcludePayeeFilterController.class);
        widget = new FasExcludePayeeFilterWidget();
        widget.setController(controller);
    }

    @Test
    public void testStructure() {
        expect(controller.getParticipatingStatuses())
            .andReturn(ImmutableMap.of("Participating", Boolean.TRUE, "Not Participating", Boolean.FALSE))
            .once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(5, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLabel(widget.getComponent(0));
        verifyScenariosItemsFilterLayout(widget.getComponent(1));
        verifyParticipatingStatusCombobox(widget.getComponent(2));
        verifyMinimumThresholdComponent(widget.getComponent(3));
        verifyButtonsLayout(widget.getComponent(4));
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
        ExcludePayeeFilter filter = Whitebox.getInternalState(widget, "filter");
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
                                                             ExcludePayeeFilter filter, String valueToSet,
                                                             BigDecimal filterValue, boolean applyEnabled) {
        minimumThreshold.setValue(valueToSet);
        if (Objects.nonNull(minimumThreshold.getErrorMessage())) {
            assertEquals("Field&#32;value&#32;should&#32;be&#32;positive&#32;number&#32;and&#32;should&#32;not&#32;" +
                "exceed&#32;10&#32;digits", minimumThreshold.getErrorMessage().getFormattedHtmlMessage());
        }
        assertEquals(applyEnabled, applyButton.isEnabled());
        assertEquals(filterValue, filter.getNetAmountMinThreshold());
    }

    private void verifyScenariosItemsFilterLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals("Scenarios", button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
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