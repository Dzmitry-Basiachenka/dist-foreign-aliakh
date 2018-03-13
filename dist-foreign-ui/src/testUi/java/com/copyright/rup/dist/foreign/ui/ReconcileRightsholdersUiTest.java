package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.test.integ.db.embedded.UpdateDatabaseForClassTestExecutionListener;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UI test to verify reconcile rightsholders functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/31/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
@TestExecutionListeners(value = UpdateDatabaseForClassTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ReconcileRightsholdersUiTest extends ForeignCommonUiTestProvider {

    private final String[] discrepancy1 =
        {"1000002797", "British Film Institute (BFI)", "1000000001", "Rothchild Consultants", "122235137", "TOMATOES"};
    private final String[] discrepancy2 =
        {"1000008666", "CCH", "1000000001", "Rothchild Consultants", "122235137", "TOMATOES"};
    private final String[] discrepancy3 = {"1000009997", "IEEE - Inst of Electrical and Electronics Engrs",
        "1000000002", "Royal Society of Victoria", "122235139", "TOMATOES"};

    @Test
    // Test case IDs: 1874f014-cb17-479c-a4bc-ec77547e39c0, 4245b194-1cef-42f3-b908-1417c3d74b4d
    public void testReconcileRightsholders() {
        loginAsSpecialist();
        WebElement scenariosTab = selectScenariosTab();
        selectScenario(scenariosTab, "Scenario for reconciliation 1");
        clickElementAndWait(assertWebElement(scenariosTab, "Reconcile_Rightsholders"));
        WebElement discrepanciesWindow = assertWebElement(By.id("rightsholder-discrepancies-window"));
        assertButtonLayout(assertWebElement(discrepanciesWindow, "rightsholder-discrepancies-buttons-layout"));
        WebElement rhDiscrepanciesTable = assertWebElement(discrepanciesWindow, "rightsholder-discrepancies-table");
        verifyDiscrepanciesTable(rhDiscrepanciesTable);
        clickButtonAndWait(discrepanciesWindow, "Approve");
        clickButtonAndWait(assertWebElement(By.id("confirm-dialog-window")), "Yes");
        clickElementAndWait(assertWebElement(scenariosTab, "Reconcile_Rightsholders"));
        assertNotNull(waitAndFindElementByText(assertWebElement(By.id("confirm-dialog-window")), HTML_DIV_TAG_NAME,
            "Scenario for reconciliation 1"));
    }

    @Test
    // Test case IDs: ffa3de5c-0191-4c51-a8a4-8953991cdcdc
    public void testReconcileRightsholdersWithInvalidWrWrkInsts() {
        loginAsSpecialist();
        WebElement scenariosTab = selectScenariosTab();
        selectScenario(scenariosTab, "Scenario for reconciliation 2");
        clickElementAndWait(assertWebElement(scenariosTab, "Reconcile_Rightsholders"));
        clickButtonAndWait(assertWebElement(By.id("rightsholder-discrepancies-window")), "Approve");
        assertNotNull(waitAndFindElementByText(assertWebElement(By.id("notification-window")), HTML_DIV_TAG_NAME,
            "1000008666"));
    }

    private void selectScenario(WebElement scenariosTab, String scenarioName) {
        WebElement scenariosTable = assertWebElement(scenariosTab, "scenarios-table");
        WebElement selectedValue = findElement(scenariosTable, By.className("v-selected"));
        if (Objects.nonNull(selectedValue)) {
            clickElementAndWait(selectedValue);
        }
        clickElementAndWait(assertWebElement(scenariosTable, HTML_DIV_TAG_NAME, scenarioName));
    }

    private void assertButtonLayout(WebElement buttonLayout) {
        Set<String> actualButtons = buttonLayout.findElements(By.className("v-button")).stream()
            .map(webElement -> webElement.getAttribute("id"))
            .collect(Collectors.toSet());
        assertButtonsToolbar(buttonLayout, actualButtons, Sets.newHashSet("Approve", "Cancel"));
    }

    private void verifyDiscrepanciesTable(WebElement table) {
        assertTableHeaderElements(table, "RH Account #", "RH Name", "New RH Account #", "New RH Name", "Wr Wrk Inst",
            "Title");
        assertTableSorting(table, "RH Account #", "RH Name", "New RH Account #", "New RH Name", "Wr Wrk Inst", "Title");
        List<WebElement> rows = assertTableRowElements(table, 3);
        assertTableRowElements(rows.get(0), discrepancy1);
        assertTableRowElements(rows.get(1), discrepancy2);
        assertTableRowElements(rows.get(2), discrepancy3);
    }
}
