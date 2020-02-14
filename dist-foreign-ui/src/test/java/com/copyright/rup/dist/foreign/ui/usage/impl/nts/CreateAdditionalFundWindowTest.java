package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
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
    private static final String FUND_POOL_NAME_INVALID = " FundPoolName ";
    private INtsUsageController usagesController;
    private CreateAdditionalFundWindow window;

    @Test
    public void testConstructor() {
        usagesController = createMock(INtsUsageController.class);
        window = new CreateAdditionalFundWindow(usagesController,
            Collections.emptySet(), BigDecimal.ONE, createMock(AdditionalFundBatchesFilterWindow.class),
            createMock(AdditionalFundFilteredBatchesWindow.class));
        assertEquals("Create NTS Pre-Service Fee Funds", window.getCaption());
        verifySize(window);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(3, content.getComponentCount());
        verifyPreServiceFeeFundNameField(content.getComponent(0));
        verifyCommentsArea(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyPreServiceFeeFundNameField(Component component) {
        expect(usagesController.additionalFundExists(FUND_POOL_NAME)).andReturn(false).anyTimes();
        replay(usagesController);
        window = new CreateAdditionalFundWindow(usagesController,
            Collections.emptySet(), BigDecimal.ONE, createMock(AdditionalFundBatchesFilterWindow.class),
            createMock(AdditionalFundFilteredBatchesWindow.class));
        assertNotNull(component);
        TextField fundNameField = (TextField) component;
        assertEquals("Fund Name", fundNameField.getCaption());
        assertEquals(StringUtils.EMPTY, fundNameField.getValue());
        Binder<FundPool> binder  = Whitebox.getInternalState(window, "binder");
        assertFalse(binder.isValid());
        ((TextField) Whitebox.getInternalState(window, "fundNameField")).setValue(FUND_POOL_NAME_INVALID);
        assertTrue(binder.isValid());
        verify(usagesController);
    }

    private void verifyCommentsArea(Component component) {
        assertNotNull(component);
        TextArea commentsArea = (TextArea) component;
        assertEquals("Comments", commentsArea.getCaption());
    }

    private void verifyButtonsLayout(Component component) {
        assertNotNull(component);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        assertEquals(3, layout.getComponentCount());
        Button confirmButton = verifyButton(layout.getComponent(0), "Confirm");
        Button cancelButton = verifyButton(layout.getComponent(1), "Cancel");
        Button closeButton = verifyButton(layout.getComponent(2), "Close");
        assertEquals(1, confirmButton.getListeners(ClickEvent.class).size());
        assertEquals(1, cancelButton.getListeners(ClickEvent.class).size());
        assertEquals(1, closeButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifySize(Component component) {
        assertEquals(320, component.getWidth(), 0);
        assertEquals(-1, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(Unit.PIXELS, component.getWidthUnits());
    }
}
