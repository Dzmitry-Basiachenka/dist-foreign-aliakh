package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replayAll;

import com.vaadin.ui.MenuBar.MenuItem;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Verifies {@link AclReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/07/2022
 *
 * @author Anton Azarenka
 */
public class AclReportWidgetTest {

    private AclReportWidget reportWidget;

    @Before
    public void setUp() {
        reportWidget = new AclReportWidget();
    }

    @Test
    public void testInit() {
        replayAll();
        reportWidget.init();
        assertEquals("reports-menu", reportWidget.getStyleName());
        assertReportsMenu();
    }

    private void assertReportsMenu() {
        assertEquals(1, CollectionUtils.size(reportWidget.getItems()));
        List<MenuItem> menuItems = reportWidget.getItems().get(0).getChildren();
        assertEquals(2, CollectionUtils.size(menuItems));
        assertEquals("Liabilities By Aggregate Licensee Class Report", menuItems.get(0).getText());
        assertEquals("Liability Details Report", menuItems.get(1).getText());
    }
}
