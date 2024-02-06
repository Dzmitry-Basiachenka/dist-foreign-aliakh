package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.setTextAreaValue;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundBatchesFilterWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundFilteredBatchesWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Verifies {@link CreateAdditionalFundWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/02/2019
 *
 * @author Aliaksandr Liakh
 */
public class CreateAdditionalFundWindowTest {

    private static final String FUND_POOL_NAME = "Fund Pool";
    private static final String FUND_NAME_FIELD = "fundNameField";
    private static final String COMMENT_AREA = "commentArea";
    private static final String BINDER_FIELD = "binder";
    private static final String STRING_50_CHARACTERS = StringUtils.repeat('a', 50);
    private static final String STRING_51_CHARACTERS = StringUtils.repeat('a', 51);
    private static final String STRING_2000_CHARACTERS = StringUtils.repeat('a', 2000);
    private static final String STRING_2001_CHARACTERS = StringUtils.repeat('a', 2001);
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String FUND_POOL_EXISTS_MESSAGE = "Fund with such name already exists";
    private static final String VALUE_EXCEED_50_CHARACTERS_MESSAGE = "Field value should not exceed 50 characters";
    private static final String VALUE_EXCEED_2000_CHARACTERS_MESSAGE = "Field value should not exceed 2000 characters";

    private INtsUsageController controller;
    private CreateAdditionalFundWindow window;

    @Before
    public void setUp() {
        controller = createMock(INtsUsageController.class);
        window = new CreateAdditionalFundWindow(controller, Set.of(), BigDecimal.ONE,
            createMock(IAdditionalFundBatchesFilterWindow.class),
            createMock(IAdditionalFundFilteredBatchesWindow.class));
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "Create NTS Pre-Service Fee Funds", "500px", null, Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
    }

    @Test
    public void testIsValid() {
        expect(controller.additionalFundExists(FUND_POOL_NAME)).andReturn(false).anyTimes();
        replay(controller);
        Binder<FundPool> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        assertFalse(binder.isValid());
        setTextFieldValue(window, FUND_NAME_FIELD, StringUtils.EMPTY);
        assertFalse(binder.isValid());
        setTextFieldValue(window, FUND_NAME_FIELD, FUND_POOL_NAME);
        assertFalse(binder.isValid());
        setTextAreaValue(window, COMMENT_AREA, StringUtils.EMPTY);
        assertFalse(binder.isValid());
        setTextFieldValue(window, FUND_NAME_FIELD, FUND_POOL_NAME);
        setTextAreaValue(window, COMMENT_AREA, "comment");
        assertTrue(binder.isValid());
        verify(controller);
    }

    @Test
    public void testVerifyFundNameField() {
        expect(controller.additionalFundExists(FUND_POOL_NAME)).andReturn(true).times(2);
        expect(controller.additionalFundExists(STRING_50_CHARACTERS)).andReturn(false).times(2);
        expect(controller.additionalFundExists(FUND_POOL_NAME)).andReturn(false).times(2);
        replay(controller);
        TextField fundNameField = Whitebox.getInternalState(window, FUND_NAME_FIELD);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        assertFieldValidationMessage(fundNameField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(fundNameField, FUND_POOL_NAME, binder, FUND_POOL_EXISTS_MESSAGE, false);
        assertFieldValidationMessage(fundNameField, STRING_50_CHARACTERS, binder, null, true);
        assertFieldValidationMessage(fundNameField, STRING_51_CHARACTERS, binder,
            VALUE_EXCEED_50_CHARACTERS_MESSAGE, false);
        assertFieldValidationMessage(fundNameField, FUND_POOL_NAME, binder, null, true);
        verify(controller);
    }

    @Test
    public void testVerifyCommentArea() {
        replay(controller);
        TextArea commentArea = Whitebox.getInternalState(window, COMMENT_AREA);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        assertFieldValidationMessage(commentArea, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(commentArea, STRING_2000_CHARACTERS, binder, null, true);
        assertFieldValidationMessage(commentArea, STRING_2001_CHARACTERS, binder,
            VALUE_EXCEED_2000_CHARACTERS_MESSAGE, false);
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(2, rootLayout.getComponentCount());
        verifyFundNameField(rootLayout.getComponentAt(0));
        verifyCommentArea(rootLayout.getComponentAt(1));
        verifyButtonsLayout(getFooterLayout(window), true, "Confirm", "Cancel", "Close");
    }

    private void verifyFundNameField(Component component) {
        var fundNameField = (TextField) component;
        assertEquals("Fund Name", fundNameField.getLabel());
        assertEquals(StringUtils.EMPTY, fundNameField.getValue());
    }

    private void verifyCommentArea(Component component) {
        var commentArea = (TextArea) component;
        assertEquals("Comment", commentArea.getLabel());
        assertEquals(StringUtils.EMPTY, commentArea.getValue());
    }
}
