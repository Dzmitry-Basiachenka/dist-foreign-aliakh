package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Verifies {@link AclCommonReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
public class AclCommonReportWidgetTest {

    @Test
    public void testInit() {
        IAclCommonReportController controller = createMock(IAclCommonReportController.class);
        AclCommonReportWidget widget = new AclCommonReportWidget();
        widget.setController(controller);
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        widget.init();
        verifyWindow(widget, StringUtils.EMPTY, 270, 145, Sizeable.Unit.PIXELS);
        assertEquals("acl-report-window", widget.getStyleName());
        assertEquals("acl-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        verifyItemsFilterWidget(content.getComponent(0), "Scenarios");
        verifyComboBox(content.getComponent(1), "Period", true, 202012);
        verifyButtonsLayout(content.getComponent(2), "Export", "Close");
        verify(controller, streamSource);
    }
}
