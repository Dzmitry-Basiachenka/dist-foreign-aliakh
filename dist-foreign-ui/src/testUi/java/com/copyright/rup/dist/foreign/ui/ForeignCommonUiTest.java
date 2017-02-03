package com.copyright.rup.dist.foreign.ui;

import com.copyright.rup.vaadin.test.CommonUiTest;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

/**
 * Common class for UI tests.
 * Provides functionality for logging in/out, setting up tests, taking screenshots if test fails.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/23/17
 *
 * @author Darya Baraukova
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class ForeignCommonUiTest extends CommonUiTest {

    private static final String APP_URL = "http://localhost:22000/dist-foreign-ui/";
    private static final Dimension WINDOW_DIMENSION = new Dimension(1280, 800);
    private static final String PASSWORD = "`123qwer";

    /**
     * Rule takes screenshot in case of test failure and calls driver.quit() at the end of the test.
     */
    @Rule
    public TestWatcher onFailureWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
            takeScreenshot(description.getClassName() + '_' + description.getMethodName());
        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
            quitDriver();
        }
    };

    @Before
    public void setUp() {
        initDriver();
        getDriver().manage().window().setSize(WINDOW_DIMENSION);
    }

    /**
     * Logs into application with view only permissions.
     */
    protected void loginAsViewOnly() {
        openAppPage(ForeignCredentials.VIEW_ONLY);
    }

    /**
     * Logs out.
     *
     * @return {@code true} if logout button was clicked, {@code false} if logout button was not found.
     */
    protected boolean logOut() {
        WebElement logOutButton = findElementById(Cornerstone.USER_WIDGET_LOGOUT_BUTTON);
        if (null != logOutButton) {
            logOutButton.click();
            return true;
        }
        return false;
    }

    private void openAppPage(ForeignCredentials credentials) {
        openPage(APP_URL, credentials.getUserName(), credentials.getPassword());
    }

    /**
     * Enum with credentials for logging into application.
     */
    protected enum ForeignCredentials {

        VIEW_ONLY("fda_view@copyright.com", PASSWORD);

        private String userName;
        private String password;

        /**
         * Constructor.
         *
         * @param userName user name
         * @param password password
         */
        ForeignCredentials(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        String getUserName() {
            return userName;
        }

        String getPassword() {
            return password;
        }
    }
}
