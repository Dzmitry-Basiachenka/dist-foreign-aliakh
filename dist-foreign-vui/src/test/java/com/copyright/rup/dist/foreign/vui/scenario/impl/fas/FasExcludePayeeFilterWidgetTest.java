package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyItemsFilterWidget;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterController;

import com.google.common.collect.ImmutableMap;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
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

    private IFasExcludePayeeFilterController controller;
    private FasExcludePayeeFilterWidget widget;

    @Before
    public void setUp() {
        controller = createMock(IFasExcludePayeeFilterController.class);
        widget = new FasExcludePayeeFilterWidget();
        widget.setController(controller);
    }

    @Test
    public void testStructure() {
        Map<String, Boolean> participatingStatuses = ImmutableMap.of("Participating", Boolean.TRUE,
            "Not Participating", Boolean.FALSE);
        expect(controller.getParticipatingStatuses())
            .andReturn(participatingStatuses)
            .once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(5, widget.getComponentCount());
        verifyFiltersLabel(widget.getComponentAt(0));
        verifyItemsFilterWidget(widget.getComponentAt(1), "Scenarios");
        verifySelectField(widget.getComponentAt(2), "Participating Status", true, participatingStatuses.keySet());
        verifyMinimumNetThresholdComponent(widget.getComponentAt(3));
        verifyButtonsLayout(widget.getComponentAt(4));
        verify(controller);
    }

    @Test
    public void testMinimumNetThresholdChangeValueListener() {
        expect(controller.getParticipatingStatuses())
            .andReturn(ImmutableMap.of("Participating", Boolean.TRUE, "Not Participating", Boolean.FALSE))
            .once();
        replay(controller);
        widget.init();
        BigDecimal filterValue = new BigDecimal("45.10");
        TextField threshold = Whitebox.getInternalState(widget, "minimumNetThreshold");
        Button applyButton = Whitebox.getInternalState(widget, "applyButton");
        ExcludePayeeFilter filter = Whitebox.getInternalState(widget, "filter");
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "45.10", filterValue, true);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "-45.10", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "45,10", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "45.10a", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, " 0 ", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "0", filterValue, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, StringUtils.EMPTY, null, false);
        setThresholdAndValidateFilterAndApplyButton(threshold, applyButton, filter, "   ", null, false);
        verify(controller);
    }

    private void setThresholdAndValidateFilterAndApplyButton(TextField minimumNetThreshold, Button applyButton,
                                                             ExcludePayeeFilter filter, String valueToSet,
                                                             BigDecimal filterValue, boolean applyEnabled) {
        minimumNetThreshold.setValue(valueToSet);
        if (Objects.nonNull(minimumNetThreshold.getErrorMessage())) {
            assertEquals("Field value should be positive number and should not exceed 10 digits",
                minimumNetThreshold.getErrorMessage());
        }
        assertEquals(applyEnabled, applyButton.isEnabled());
        assertEquals(filterValue, filter.getNetAmountMinThreshold());
    }

    private void verifyMinimumNetThresholdComponent(Component component) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField textField = (TextField) component;
        assertEquals("Minimum Net Threshold", textField.getLabel());
        assertEquals("calc(99.9% - 0rem)", textField.getWidth());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.isSpacing());
        verifyButton(layout.getComponentAt(0), "Apply");
        verifyButton(layout.getComponentAt(1), "Clear");
    }

    private void verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(caption, button.getText());
        assertEquals("calc(99.9% - 0rem)", button.getWidth());
    }

    private <T> Select<T> verifySelectField(Component component, String caption, boolean emptySelectionAllowed,
                                                 Collection<T> expectedItems) {
        assertThat(component, instanceOf(Select.class));
        Select<T> comboBox = (Select<T>) component;
        assertFalse(comboBox.isReadOnly());
        assertEquals(emptySelectionAllowed, !comboBox.isEmptySelectionAllowed());
        assertEquals(caption, comboBox.getLabel());
        assertEquals("calc(99.9% - 0rem)", comboBox.getWidth());
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<T> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
        return comboBox;
    }
}
