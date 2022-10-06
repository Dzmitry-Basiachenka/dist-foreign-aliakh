package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
@PrepareForTest({UdmBaselineWidget.class, Windows.class, ForeignSecurityUtils.class, JavaScript.class})
public class UdmBaselineWidgetTest {

    private static final int DOUBLE_CLICK = 0x00002;
    private static final int UDM_RECORD_THRESHOLD = 10000;

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
    public void testGridValues() {
        mockStatic(JavaScript.class);
        List<UdmBaselineDto> udmBaselines = Collections.singletonList(buildUdmBaselineDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.loadBeans(0, Integer.MAX_VALUE, Collections.emptyList())).andReturn(udmBaselines).once();
        expect(controller.getUdmRecordThreshold()).andReturn(UDM_RECORD_THRESHOLD).once();
        replay(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        udmBaselineWidget.init();
        Grid grid = (Grid) ((VerticalLayout) udmBaselineWidget.getSecondComponent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"040ed0ac-a3a5-4e4a-a3c6-262335bb1ed9", 202006, UdmUsageOriginEnum.SS,
                "d47e921a-8581-4d35-a2e7-98c00d22492d", 123456789L, "Brain surgery", 22, "Banks/Ins/RE/Holding Cos",
                26, "Law Firms", "United States", UdmChannelEnum.CCC, "COPY_FOR_MYSELF", "DIGITAL", "10.00",
                "user@copyright.com", "06/01/2020", "wuser@copyright.com", "06/02/2020"}
        };
        verifyGridItems(grid, udmBaselines, expectedCells);
        verify(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        Object[][] expectedFooterColumns = {{"detailId", "Usages Count: 1", null}};
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, streamSource, ForeignSecurityUtils.class);
        udmBaselineWidget.init();
        verify(controller, streamSource, ForeignSecurityUtils.class);
        assertTrue(udmBaselineWidget.isLocked());
        assertEquals(270, udmBaselineWidget.getSplitPosition(), 0);
        verifySize(udmBaselineWidget);
        assertThat(udmBaselineWidget.getFirstComponent(), instanceOf(VerticalLayout.class));
        Component secondComponent = udmBaselineWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout(layout.getComponent(0), "Delete", "Export");
        verifyGrid((Grid) layout.getComponent(1), Arrays.asList(
            Triple.of("Detail ID", 250.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Usage Origin", 100.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 250.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 100.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Channel", 100.0, -1),
            Triple.of("Reported TOU", 120.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Annualized Copies", 130.0, -1),
            Triple.of("Created By", 200.0, -1),
            Triple.of("Created Date", 110.0, -1),
            Triple.of("Updated By", 150.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
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

    private UdmBaselineDto buildUdmBaselineDto() {
        UdmBaselineDto udmBaseline = new UdmBaselineDto();
        udmBaseline.setId("040ed0ac-a3a5-4e4a-a3c6-262335bb1ed9");
        udmBaseline.setPeriod(202006);
        udmBaseline.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmBaseline.setOriginalDetailId("d47e921a-8581-4d35-a2e7-98c00d22492d");
        udmBaseline.setWrWrkInst(123456789L);
        udmBaseline.setSystemTitle("Brain surgery");
        udmBaseline.setDetailLicenseeClassId(22);
        udmBaseline.setDetailLicenseeClassName("Banks/Ins/RE/Holding Cos");
        udmBaseline.setAggregateLicenseeClassId(26);
        udmBaseline.setAggregateLicenseeClassName("Law Firms");
        udmBaseline.setSurveyCountry("United States");
        udmBaseline.setChannel(UdmChannelEnum.CCC);
        udmBaseline.setTypeOfUse("DIGITAL");
        udmBaseline.setReportedTypeOfUse("COPY_FOR_MYSELF");
        udmBaseline.setAnnualizedCopies(new BigDecimal("10.00000"));
        udmBaseline.setCreateUser("user@copyright.com");
        udmBaseline.setCreateDate(
            Date.from(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmBaseline.setUpdateUser("wuser@copyright.com");
        udmBaseline.setUpdateDate(
            Date.from(LocalDate.of(2020, 6, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return udmBaseline;
    }
}
