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
        verifyUserNameAndLogout("fda_view@copyright.com");
    }

    @Test
    public void testLoginAsDistributionManager() {
        loginAsManager();
        verifyUserNameAndLogout("fda_manager@copyright.com");
    }

    @Test
    public void testLoginAsDistributionSpecialist() {
        loginAsSpecialist();
        verifyUserNameAndLogout("fda_spec@copyright.com");
    }

    private void verifyUserNameAndLogout(String expectedUserName) {
        WebElement userNameElement = assertWebElement(By.id(Cornerstone.USER_WIDGET_USERNAME_LABEL));
        assertEquals(expectedUserName, userNameElement.getText());
        assertTrue(logOut());
    }
}
