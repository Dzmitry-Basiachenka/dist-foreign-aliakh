package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsagesByStatusReportController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmCommonStatusReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Ihar Suvorau
 */
public class UdmCommonStatusReportWidgetTest {

    @Test
    public void testInit() {
        IUdmUsagesByStatusReportController controller = createMock(IUdmUsagesByStatusReportController.class);
        UdmCommonStatusReportWidget widget = new UdmCommonStatusReportWidget();
        widget.setController(controller);
        expect(controller.getPeriods()).andReturn(List.of(202012)).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        widget.init();
        verifyWindow(widget, StringUtils.EMPTY, 280, 120, Sizeable.Unit.PIXELS);
        assertEquals("udm-report-window", widget.getStyleName());
        assertEquals("udm-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        verifyComboBox(content.getComponent(0), "Period", true, 202012);
        verifyButtonsLayout(content.getComponent(1), "Export", "Close");
        verify(controller, streamSource);
    }
}
