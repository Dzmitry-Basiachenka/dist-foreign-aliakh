package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class ReportWidgetTest {

    private ReportWidget reportWidget;
    private IReportController reportController;

    @Before
    public void setUp() {
        reportWidget = new ReportWidget();
        reportController = createMock(IReportController.class);
        reportWidget.setController(reportController);
    }

    @Test
    public void testInit() {
        expect(reportController.getUndistributedLiabilitiesReportController())
            .andReturn(new UndistributedLiabilitiesReportController()).once();
        replay(reportController);
        reportWidget.init();
        assertEquals("reports-menu", reportWidget.getStyleName());
        verify(reportController);
    }

    @Test
    public void testReportSelectCommandMenuSelected() {
        mockStatic(Windows.class);
        IController controller = createMock(IController.class);
        UndistributedLiabilitiesReportWidget widget = createMock(UndistributedLiabilitiesReportWidget.class);
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        widget.setCaption("Undistributed Liabilities Reconciliation Report");
        expectLastCall().once();
        replay(controller, widget, Windows.class);
        ReportWidget.ReportSelectCommand selectCommand =
            new ReportWidget.ReportSelectCommand(controller, "Undistributed Liabilities Reconciliation Report");
        selectCommand.menuSelected(null);
        verify(controller, widget, Windows.class);
    }
}
