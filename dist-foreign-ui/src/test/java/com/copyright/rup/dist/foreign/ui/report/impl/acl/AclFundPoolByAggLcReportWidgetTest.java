package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.function.Supplier;

/**
 * Verifies {@link AclFundPoolByAggLcReportWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolByAggLcReportWidgetTest {

    private final AclFundPoolByAggLcReportWidget widget = new AclFundPoolByAggLcReportWidget();
    private final IAclFundPoolByAggLcReportController controller =
        createMock(IAclFundPoolByAggLcReportController.class);
    private final IStreamSource streamSource = createMock(IStreamSource.class);

    @Before
    public void setUp() {
        widget.setController(controller);
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInit() {
        replay(controller, streamSource);
        widget.init();
        verifyWindow(widget, StringUtils.EMPTY, 350, 125, Sizeable.Unit.PIXELS);
        assertEquals("acl-report-window", widget.getStyleName());
        assertEquals("acl-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        verifyItemsFilterWidget(content.getComponent(0), true, "Periods");
        verifyItemsFilterWidget(content.getComponent(1), false, "Fund Pool Names");
        verifyButtonsLayout(content.getComponent(2), "Export", "Close");
        verify(controller, streamSource);
    }
}
