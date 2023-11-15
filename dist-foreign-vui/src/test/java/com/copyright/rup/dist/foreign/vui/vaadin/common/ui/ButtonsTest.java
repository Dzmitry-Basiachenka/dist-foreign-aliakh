package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;

/**
 * Verify {@link Buttons}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 07/12/2023
 *
 * @author Anton Azarenka
 */
public class ButtonsTest {

    private static final String BUTTON_CAPTION = "Test caption";

    @Test
    public void testCreateOkButton() {
        verifyButton(Buttons.createOkButton(new Dialog()), "Ok");
    }

    @Test(expected = NullPointerException.class)
    public void testCreateButtonWithNullCaption() {
        Buttons.createButton(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateButtonWithNullWindow() {
        Buttons.createButton(BUTTON_CAPTION, null);
    }

    @Test
    public void testCreateButton() {
        Button result = Buttons.createButton(BUTTON_CAPTION, new Dialog());
        verifyButton(result, BUTTON_CAPTION);
    }

    @Test
    public void testCreateRefreshIcon() {
        Button button = Buttons.createRefreshIcon();
        assertNotNull(button);
        assertEquals("Refresh", button.getTooltip().getText());
        assertEquals("button-refresh", button.getClassName());
        assertEquals("button-refresh", button.getId().orElseThrow());
    }

    @Test
    public void testCreateLinkButton() {
        Button result = Buttons.createLinkButton(createMock(ComponentEventListener.class), BUTTON_CAPTION);
        verifyButton(result, BUTTON_CAPTION);
        assertTrue(result.getThemeNames().containsAll(List.of("small", "tertiary-inline")));
    }

    @Test
    public void testCreateMaximizeButton() {
        Button button = Buttons.createMaximizeWindowIcon(new Dialog());
        assertNotNull(button);
        assertEquals("Maximize", button.getTooltip().getText());
        assertEquals("button-resize v-window-maximizebox v-window-restorebox", button.getClassName());
        assertEquals("button-resize", button.getId().orElseThrow());
    }

    private void verifyButton(Button button, String buttonCaption) {
        assertNotNull(button);
        assertEquals(buttonCaption, button.getText());
        String buttonId = StringUtils.replace(buttonCaption, " ", "_");
        assertEquals(buttonId, button.getId().orElseThrow());
        assertEquals(buttonId, button.getClassName());
    }
}
