package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Verifies login/logout functionality.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/23/17
 *
 * @author Darya Baraukova
 */
public class LoginTest extends ForeignCommonUiTest {

    @Test
    public void testLoginAsViewOnly() {
        loginAsViewOnly();
        WebElement userNameElement = assertElement(By.id(Cornerstone.USER_WIDGET_USERNAME_LABEL));
        assertEquals(ForeignCredentials.VIEW_ONLY.getUserName(), userNameElement.getText());
        assertTrue(logOut());
    }

    @Test
    public void testLoginAsDistributionManager() {
        loginAsManager();
        WebElement userNameElement = assertElement(By.id(Cornerstone.USER_WIDGET_USERNAME_LABEL));
        assertEquals(ForeignCredentials.MANAGER.getUserName(), userNameElement.getText());
        assertTrue(logOut());
    }

    @Test
    public void testLoginAsDistributionSpecialist() {
        loginAsSpecialist();
        WebElement userNameElement = assertElement(By.id(Cornerstone.USER_WIDGET_USERNAME_LABEL));
        assertEquals(ForeignCredentials.SPECIALIST.getUserName(), userNameElement.getText());
        assertTrue(logOut());
    }
}
