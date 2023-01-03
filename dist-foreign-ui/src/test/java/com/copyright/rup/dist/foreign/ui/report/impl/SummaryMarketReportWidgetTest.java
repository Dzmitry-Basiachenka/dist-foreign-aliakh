package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Verifies {@link SummaryMarketReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportWidgetTest {

    private SummaryMarketReportWidget widget;

    @Before
    public void setUp() {
        ISummaryMarketReportController controller = createMock(ISummaryMarketReportController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getUsageBatches()).andReturn(Collections.emptyList()).once();
        replay(controller, streamSource);
        widget = new SummaryMarketReportWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, streamSource);
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 350, 400, Unit.PIXELS);
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertThat(firstComponent, instanceOf(SearchWidget.class));
        Component secondComponent = content.getComponent(1);
        assertThat(secondComponent, instanceOf(Panel.class));
        Panel panel = (Panel) secondComponent;
        assertThat(panel.getContent(), instanceOf(CheckBoxGroup.class));
        verifyButtonsLayout(content.getComponent(2), "Export", "Clear", "Close");
        assertEquals("summary-market-report-window", widget.getStyleName());
        assertEquals("summary-market-report-window", widget.getId());
    }
}
