package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.report.api.fas.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

/**
 * Verifies {@link SummaryMarketReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class SummaryMarketReportWidgetTest {

    private SummaryMarketReportWidget widget;

    @Before
    public void setUp() {
        ISummaryMarketReportController controller = createMock(ISummaryMarketReportController.class);
        expect(controller.getUsageBatches()).andReturn(List.of()).once();
        replay(controller);
        widget = new SummaryMarketReportWidget();
        widget.setController(controller);
        widget.init();
        verify(controller);
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, "400px", "450px", Unit.PIXELS, false);
        assertThat(UiTestHelper.getDialogContent(widget), instanceOf(VerticalLayout.class));
        var content = (VerticalLayout) UiTestHelper.getDialogContent(widget);
        assertEquals(2, content.getComponentCount());
        var firstComponent = content.getComponentAt(0);
        assertThat(firstComponent, instanceOf(SearchWidget.class));
        var secondComponent = content.getComponentAt(1);
        assertThat(secondComponent, instanceOf(Scroller.class));
        var panel = (Scroller) secondComponent;
        assertThat(panel.getContent(), instanceOf(Div.class));
        assertThat(((Div) panel.getContent()).getComponentAt(0), instanceOf(Section.class));
        var section = (Section) ((Div) panel.getContent()).getComponentAt(0);
        assertEquals(1, section.getComponentCount());
        assertThat(section.getComponentAt(0), instanceOf(CheckboxGroup.class));
        verifyButtonsLayout(UiTestHelper.getFooterComponent(widget, 1));
        assertEquals("summary-market-report-window", widget.getClassName());
        assertEquals("summary-market-report-window", widget.getId().orElseThrow());
    }

    public void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        var fileDownloader = layout.getComponentAt(0);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().get()).getText());
        var clearButton = layout.getComponentAt(1);
        assertThat(clearButton, instanceOf(Button.class));
        assertEquals("Clear", ((Button) clearButton).getText());
        var closeButton = layout.getComponentAt(2);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }
}
