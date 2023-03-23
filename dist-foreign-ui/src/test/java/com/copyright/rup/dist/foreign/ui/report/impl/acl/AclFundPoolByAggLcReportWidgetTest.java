package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool.AclFundPoolItemFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Set;
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

    @Test
    public void testFilterSaveListener() {
        FilterSaveEvent<AclFundPool> filterSaveEvent = createMock(FilterSaveEvent.class);
        String fundPoolId = "638147fd-03ea-4aac-834b-2bdc1ec2b62e";
        AclFundPool fundPool = new AclFundPool();
        fundPool.setId(fundPoolId);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(fundPool)).once();
        replay(controller, filterSaveEvent, streamSource);
        widget.init();
        widget.setController(controller);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Button exportButton = (Button) ((HorizontalLayout) content.getComponent(2)).getComponent(0);
        assertFalse(exportButton.isEnabled());
        AclFundPoolItemFilterWidget fundPoolItemFilterWidget = (AclFundPoolItemFilterWidget) content.getComponent(1);
        IFilterSaveListener<AclFundPool> listener = Whitebox.getInternalState(fundPoolItemFilterWidget, "saveListener");
        listener.onSave(filterSaveEvent);
        assertEquals(Set.of(fundPoolId), widget.getFundPoolIds());
        verify(controller, filterSaveEvent, streamSource);
    }
}
