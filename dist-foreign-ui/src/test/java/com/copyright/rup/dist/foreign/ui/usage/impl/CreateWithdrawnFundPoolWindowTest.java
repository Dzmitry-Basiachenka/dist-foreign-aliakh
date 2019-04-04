package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

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

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link CreateWithdrawnFundPoolWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/02/2019
 *
 * @author Aliaksandr Liakh
 */
public class CreateWithdrawnFundPoolWindowTest {

    @Test
    public void testConstructor() {
        IUsagesController controller = createMock(IUsagesController.class);
        CreateWithdrawnFundPoolWindow window = new CreateWithdrawnFundPoolWindow(controller,
            Collections.emptyList(), BigDecimal.ONE,
            createMock(WithdrawnBatchFilterWindow.class), createMock(WithdrawnFilteredBatchesWindow.class));
        assertEquals("Create NTS Pre-Service Fee Funds", window.getCaption());
        verifySize(window, Unit.PIXELS, 320, Unit.PIXELS, -1);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(3, content.getComponentCount());
        verifyFundPoolNameField(content.getComponent(0));
        verifyCommentsArea(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyFundPoolNameField(Component component) {
        assertNotNull(component);
        TextField fundPoolNameField = (TextField) component;
        assertEquals("Fund Name", fundPoolNameField.getCaption());
        assertEquals(StringUtils.EMPTY, fundPoolNameField.getValue());
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

    private void verifySize(Component component, Unit widthUnit, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }
}
