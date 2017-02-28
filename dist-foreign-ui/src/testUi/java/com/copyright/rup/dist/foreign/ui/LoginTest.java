package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

/**
 * Verifies login/logout functionality.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/23/17
 *
 * @author Darya Baraukova
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class LoginTest extends ForeignCommonUiTest {

    @Test
    public void testLoginAsViewOnly() {
        loginAsViewOnly();
        WebElement userNameElement = findElementById(Cornerstone.USER_WIDGET_USERNAME_LABEL);
        assertNotNull(userNameElement);
        assertEquals(ForeignCredentials.VIEW_ONLY.getUserName(), userNameElement.getText());
        assertTrue(logOut());
    }

    @Test
    public void testLoginAsDistributionManager() {
        loginAsManager();
        WebElement userNameElement = findElementById(Cornerstone.USER_WIDGET_USERNAME_LABEL);
        assertNotNull(userNameElement);
        assertEquals(ForeignCredentials.MANAGER.getUserName(), userNameElement.getText());
        assertTrue(logOut());
    }

    @Test
    public void testLoginAsDistributionSpecialist() {
        loginAsSpecialist();
        WebElement userNameElement = findElementById(Cornerstone.USER_WIDGET_USERNAME_LABEL);
        assertNotNull(userNameElement);
        assertEquals(ForeignCredentials.SPECIALIST.getUserName(), userNameElement.getText());
        assertTrue(logOut());
    }
}
