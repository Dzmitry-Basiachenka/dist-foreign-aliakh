package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.ItemClickListener;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmBaselineWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UdmBaselineWidget.class, Windows.class, ForeignSecurityUtils.class})
public class UdmBaselineWidgetTest {

    private static final int DOUBLE_CLICK = 0x00002;
    private UdmBaselineWidget udmBaselineWidget;
    private IUdmBaselineController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        controller = createMock(IUdmBaselineController.class);
        UdmBaselineFilterWidget filterWidget =
            new UdmBaselineFilterWidget(createMock(IUdmBaselineFilterController.class));
        udmBaselineWidget = new UdmBaselineWidget();
        Whitebox.setInternalState(udmBaselineWidget, controller);
        expect(controller.initBaselineFilterWidget()).andReturn(filterWidget).once();
        streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportUdmBaselineUsagesStreamSource()).andReturn(streamSource).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, streamSource, ForeignSecurityUtils.class);
        udmBaselineWidget.init();
        verify(controller, streamSource, ForeignSecurityUtils.class);
        assertTrue(udmBaselineWidget.isLocked());
        assertEquals(270, udmBaselineWidget.getSplitPosition(), 0);
        verifySize(udmBaselineWidget);
        assertTrue(udmBaselineWidget.getFirstComponent() instanceof VerticalLayout);
        Component secondComponent = udmBaselineWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout(layout.getComponent(0), "Delete", "Export");
        verifyGrid((Grid) layout.getComponent(1), Arrays.asList(
            Triple.of("Detail ID", 200.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Usage Origin", 100.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 200.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 100.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 100.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Channel", 100.0, -1),
            Triple.of("TOU", 100.0, -1),
            Triple.of("Annualized Copies", 130.0, -1),
            Triple.of("Created By", 150.0, -1),
            Triple.of("Created Date", 110.0, -1),
            Triple.of("Updated By", 150.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
        verifyGridFooter((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testViewBaselineWindowByDoubleClick() throws Exception {
        mockStatic(Windows.class);
        UdmBaselineDto udmBaselineDto = new UdmBaselineDto();
        udmBaselineDto.setId("121e005a-3fc0-4f65-bc91-1ec3932a86c8");
        ViewBaselineWindow mockWindow = PowerMock.createMock(ViewBaselineWindow.class);
        expectNew(ViewBaselineWindow.class, eq(udmBaselineDto)).andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, ViewBaselineWindow.class, ForeignSecurityUtils.class);
        udmBaselineWidget.init();
        Grid<UdmBaselineDto> grid =
            (Grid<UdmBaselineDto>) ((VerticalLayout) udmBaselineWidget.getSecondComponent()).getComponent(1);
        ItemClickListener<UdmBaselineDto> listener =
            (ItemClickListener) new ArrayList<>(grid.getListeners(ItemClick.class)).get(0);
        Grid.ItemClick<UdmBaselineDto> usageDtoItemClick =
            new ItemClick<>(grid, grid.getColumns().get(0), udmBaselineDto, createMouseEvent(), 0);
        listener.itemClick(usageDtoItemClick);
        verify(controller, streamSource, Windows.class, ViewBaselineWindow.class, ForeignSecurityUtils.class);
    }

    private void verifyGridFooter(Grid grid) {
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Usages Count: 0", footerRow.getCell("detailId").getText());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private MouseEventDetails createMouseEvent() {
        MouseEventDetails mouseEventDetails = new MouseEventDetails();
        mouseEventDetails.setType(DOUBLE_CLICK);
        mouseEventDetails.setButton(MouseButton.LEFT);
        return mouseEventDetails;
    }
}
