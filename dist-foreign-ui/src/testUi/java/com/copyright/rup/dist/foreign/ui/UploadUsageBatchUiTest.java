package com.copyright.rup.dist.foreign.ui;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

/**
 * UI test for Upload Usage Batch window.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/01/2017
 *
 * @author Mikalai Bezmen
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class UploadUsageBatchUiTest extends ForeignCommonUiTest {

    @Test
    public void testUsageBatchNameFieldValidation() {
        openUploadUsageBatchWindow();
        // TODO {mbezmen, dbaraukova} Implement test
    }

    private WebElement openUploadUsageBatchWindow() {
        loginAsSpecialist();
        WebElement usagesTab = getUsagesTab();
        WebElement usagesLayout = assertElement(usagesTab, USAGE_LAYOUT_ID);
        WebElement buttonsLayout = assertElement(usagesLayout, "usages-buttons");
        clickElementAndWait(findElement(buttonsLayout, By.id(LOAD_USAGE_BUTTON_ID)));
        return waitAndFindElement(By.id("usage-upload-window"));
    }
}
