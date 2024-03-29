package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * Verifies {@link AaclExcludePayeeFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
public class AaclExcludePayeeFilterWidgetTest {

    private AaclExcludePayeeFilterWidget widget;

    @Before
    public void setUp() {
        widget = new AaclExcludePayeeFilterWidget();
        widget.setController(createMock(IAaclExcludePayeeFilterController.class));
    }

    @Test
    public void testStructure() {
        assertSame(widget, widget.init());
        assertEquals(3, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLabel(widget.getComponent(0));
        verifyMinimumNetThresholdComponent(widget.getComponent(1));
        verifyButtonsLayout(widget.getComponent(2));
    }

    @Test
    public void testMinimumNetThresholdChangeValueListener() {
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
    }

    @Test
    public void testMinimumNetThresholdFieldValidation() {
        widget.init();
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField minimumNetThreshold = Whitebox.getInternalState(widget, "minimumNetThreshold");
        String positiveNumberErrorMessage = "Field value should be positive number and should not exceed 10 digits";
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "   ", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "  99  ", binder, null, true);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "  0.005  ", binder, null, true);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "  1.00  ", binder, null, true);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "  9999999999.99  ", binder, null, true);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "123a4567", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "0", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "0.00", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, ".01", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "0.004", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "01.01", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "1.01.", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "1,01", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(minimumNetThreshold, "-1", binder, positiveNumberErrorMessage, false);
        validateFieldAndVerifyErrorMessage(
            minimumNetThreshold, "  99999999999.99  ", binder, positiveNumberErrorMessage, false);
    }

    private void setThresholdAndValidateFilterAndApplyButton(TextField minimumNetThreshold, Button applyButton,
                                                             ExcludePayeeFilter filter, String valueToSet,
                                                             BigDecimal filterValue, boolean applyEnabled) {
        minimumNetThreshold.setValue(valueToSet);
        if (Objects.nonNull(minimumNetThreshold.getErrorMessage())) {
            assertEquals("Field&#32;value&#32;should&#32;be&#32;positive&#32;number&#32;and&#32;should&#32;not&#32;" +
                "exceed&#32;10&#32;digits", minimumNetThreshold.getErrorMessage().getFormattedHtmlMessage());
        }
        assertEquals(applyEnabled, applyButton.isEnabled());
        assertEquals(filterValue, filter.getNetAmountMinThreshold());
    }

    private void verifyMinimumNetThresholdComponent(Component component) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField textField = (TextField) component;
        assertEquals("Minimum Net Threshold", textField.getCaption());
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.isSpacing());
        verifyButton(layout.getComponent(0), "Apply");
        verifyButton(layout.getComponent(1), "Clear");
    }

    private void verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(100, button.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, button.getWidthUnits());
        verifyButtonClickListener(button);
    }

    private void verifyButtonClickListener(Button button) {
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        assertNotNull(listeners.iterator().next());
    }
}
