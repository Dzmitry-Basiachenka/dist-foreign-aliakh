package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;

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

    private static final String FUND_POOL_NAME = "FundPoolName";
    private static final String FUND_NAME_FIELD = "fundNameField";
    private static final String COMMENT_AREA = "commentArea";
    private static final String BINDER_FIELD = "binder";
    private static final String STRING_EXCEED_2000_CHARACTERS = StringUtils.repeat("A", 2001);
    private static final String STRING_EXCEED_50_CHARACTERS = StringUtils.repeat("A", 51);
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "Field value should be specified";
    private static final String FUND_POOL_EXISTS_ERROR_MESSAGE = "Fund with such name already exists";
    private static final String VALUE_EXCEED_2000_CHARACTERS_ERROR_MESSAGE = "Field value should not exceed 2000 " +
        "characters";
    private static final String VALUE_EXCEED_50_CHARACTERS_ERROR_MESSAGE = "Field value should not exceed 50 " +
        "characters";

    private INtsUsageController usagesController;
    private CreateAdditionalFundWindow window;

    @Before
    public void setUp() {
        usagesController = createMock(INtsUsageController.class);
        window = new CreateAdditionalFundWindow(usagesController,
            Collections.emptySet(), BigDecimal.ONE, createMock(AdditionalFundBatchesFilterWindow.class),
            createMock(AdditionalFundFilteredBatchesWindow.class));
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "Create NTS Pre-Service Fee Funds", 320, -1, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(3, content.getComponentCount());
        verifyPreServiceFeeFundNameField(content.getComponent(0));
        verifyCommentArea(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2), "Confirm", "Cancel", "Close");
    }

    @Test
    public void testIsValid() {
        expect(usagesController.additionalFundExists(FUND_POOL_NAME)).andReturn(false).anyTimes();
        replay(usagesController);
        Binder<FundPool> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        assertFalse(binder.isValid());
        setTextFieldValue(FUND_NAME_FIELD, StringUtils.EMPTY);
        assertFalse(binder.isValid());
        setTextFieldValue(FUND_NAME_FIELD, FUND_POOL_NAME);
        assertFalse(binder.isValid());
        setTextAreaValue(COMMENT_AREA, StringUtils.EMPTY);
        assertFalse(binder.isValid());
        setTextFieldValue(FUND_NAME_FIELD, FUND_POOL_NAME);
        setTextAreaValue(COMMENT_AREA, "Comment");
        assertTrue(binder.isValid());
        verify(usagesController);
    }

    @Test
    public void testVerifyFundNameFieldWithErrorMessage() {
        expect(usagesController.additionalFundExists(FUND_POOL_NAME)).andReturn(true).times(2);
        expect(usagesController.additionalFundExists(FUND_POOL_NAME)).andReturn(false).times(2);
        replay(usagesController);
        Binder<FundPool> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(FUND_NAME_FIELD, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, FUND_NAME_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(FUND_NAME_FIELD, FUND_POOL_NAME);
        verifyFieldErrorMessage(binder, FUND_NAME_FIELD, FUND_POOL_EXISTS_ERROR_MESSAGE);
        setTextFieldValue(FUND_NAME_FIELD, STRING_EXCEED_50_CHARACTERS);
        verifyFieldErrorMessage(binder, FUND_NAME_FIELD, VALUE_EXCEED_50_CHARACTERS_ERROR_MESSAGE);
        setTextFieldValue(FUND_NAME_FIELD, FUND_POOL_NAME);
        verifyFieldIsValid(binder, FUND_NAME_FIELD);
        verify(usagesController);
    }

    @Test
    public void testVerifyCommentAreaWithErrorMessage() {
        replay(usagesController);
        Binder<FundPool> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextAreaValue(COMMENT_AREA, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, COMMENT_AREA, EMPTY_FIELD_ERROR_MESSAGE);
        setTextAreaValue(COMMENT_AREA, STRING_EXCEED_2000_CHARACTERS);
        verifyFieldErrorMessage(binder, COMMENT_AREA, VALUE_EXCEED_2000_CHARACTERS_ERROR_MESSAGE);
        verify(usagesController);
    }

    private void verifyPreServiceFeeFundNameField(Component component) {
        assertNotNull(component);
        TextField fundNameField = (TextField) component;
        assertEquals("Fund Name", fundNameField.getCaption());
        assertEquals(StringUtils.EMPTY, fundNameField.getValue());
    }

    private void verifyCommentArea(Component component) {
        assertNotNull(component);
        TextArea commentArea = (TextArea) component;
        assertEquals("Comment", commentArea.getCaption());
        assertEquals(StringUtils.EMPTY, commentArea.getValue());
    }

    private void setTextFieldValue(String field, String value) {
        ((TextField) Whitebox.getInternalState(window, field)).setValue(value);
    }

    private void setTextAreaValue(String field, String value) {
        ((TextArea) Whitebox.getInternalState(window, field)).setValue(value);
    }

    private void verifyFieldErrorMessage(Binder binder, String fieldName, String message) {
        binder.validate();
        AbstractComponent textField = Whitebox.getInternalState(window, fieldName);
        assertEquals(message, textField.getErrorMessage().toString());
    }

    private void verifyFieldIsValid(Binder binder, String fieldName) {
        binder.validate();
        AbstractComponent textField = Whitebox.getInternalState(window, fieldName);
        assertNull(textField.getErrorMessage());
    }
}
