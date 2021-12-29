package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmProxyValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(
    {UdmProxyValueWidget.class, ForeignSecurityUtils.class, Windows.class, RupContextUtils.class, JavaScript.class})
public class UdmProxyValueWidgetTest {

    private IUdmProxyValueController controller;
    private UdmProxyValueWidget valueWidget;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        mockStatic(RupContextUtils.class);
        controller = createMock(IUdmProxyValueController.class);
        streamSource = createMock(IStreamSource.class);
        UdmProxyValueFilterWidget filterWidget = new UdmProxyValueFilterWidget();
        expect(controller.initProxyValueFilterWidget()).andReturn(filterWidget).once();
        expect(controller.getExportUdmProxyValuesStreamSource()).andReturn(streamSource).once();
        expect(streamSource.getSource()).andReturn(new AbstractMap.SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        initWidget();
        verify(controller, ForeignSecurityUtils.class, RupContextUtils.class, streamSource);
        assertTrue(valueWidget.isLocked());
        assertEquals(200, valueWidget.getSplitPosition(), 0);
        verifySize(valueWidget);
        assertTrue(valueWidget.getFirstComponent() instanceof UdmProxyValueFilterWidget);
        Component secondComponent = valueWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout(layout.getComponent(0), "Export");
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Value Period", -1.0, -1),
            Triple.of("Pub Type Code", -1.0, -1),
            Triple.of("Content Unit Price", -1.0, -1),
            Triple.of("Content Unit Price Count", -1.0, -1)
        ));
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Proxy Values Count: 0", footerRow.getCell("period").getText());
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void initWidget() {
        valueWidget = new UdmProxyValueWidget();
        valueWidget.setController(controller);
        valueWidget.init();
    }
}
