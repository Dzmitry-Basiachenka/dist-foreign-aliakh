package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link AclFundPoolWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolWidgetTest {

    private AclFundPoolWidget widget;
    private IAclFundPoolController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        controller = createMock(IAclFundPoolController.class);
        widget = new AclFundPoolWidget();
        AclFundPoolFilterWidget filterWidget =
            new AclFundPoolFilterWidget(createMock(IAclFundPoolFilterController.class));
        Whitebox.setInternalState(widget, controller);
        expect(controller.initAclFundPoolFilterWidget()).andReturn(filterWidget).once();
        expect(controller.getDtos()).andReturn(List.of(buildAclFundPoolDetailDto())).once();
        streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportAclFundPoolDetailsStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        widget.init();
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) widget.getSecondComponent()).getComponent(1);
        Object[][] expectedCells = {
            {"ACL Fund Pool", 202212, "ACL", "Manual", 22, "Banks/Ins/RE/Holding Cos", 56, "Financial", "PRINT",
                "1,000.00", "700.00"}
        };
        verifyGridItems(grid, List.of(buildAclFundPoolDetailDto()), expectedCells);
        verify(controller);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(widget.isLocked());
        assertEquals(270, widget.getSplitPosition(), 0);
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        VerticalLayout layout = (VerticalLayout) widget.getSecondComponent();
        verifyMenuBar(((HorizontalLayout) layout.getComponent(0)).getComponent(0), "Fund Pool", true,
            List.of("Create", "View"));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, List.of(
            Triple.of("Fund Pool Name", 250.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("License Type", 100.0, -1),
            Triple.of("Source", 100.0, -1),
            Triple.of("Det LC ID", 150.0, -1),
            Triple.of("Det LC Name", 200.0, -1),
            Triple.of("Agg LC ID", 150.0, -1),
            Triple.of("Agg LC Name", 200.0, -1),
            Triple.of("Fund Pool Type", 150.0, -1),
            Triple.of("Gross Amount", 150.0, -1),
            Triple.of("Net Amount", 150.0, -1)));
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(controller, streamSource);
    }

    private AclFundPoolDetailDto buildAclFundPoolDetailDto() {
        AclFundPoolDetailDto fundPoolDetail = new AclFundPoolDetailDto();
        fundPoolDetail.setFundPoolName("ACL Fund Pool");
        fundPoolDetail.setPeriod(202212);
        fundPoolDetail.setLicenseType("ACL");
        fundPoolDetail.setLdmtFlag(false);
        fundPoolDetail.setDetailLicenseeClass(buildDetailLicenseeClass());
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseeClass());
        fundPoolDetail.setTypeOfUse("PRINT");
        fundPoolDetail.setGrossAmount(new BigDecimal("1000.00"));
        fundPoolDetail.setNetAmount(new BigDecimal("700.00"));
        return fundPoolDetail;
    }

    private DetailLicenseeClass buildDetailLicenseeClass() {
        DetailLicenseeClass detLicClass = new DetailLicenseeClass();
        detLicClass.setId(22);
        detLicClass.setDescription("Banks/Ins/RE/Holding Cos");
        return detLicClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass() {
        AggregateLicenseeClass aggLicClass = new AggregateLicenseeClass();
        aggLicClass.setId(56);
        aggLicClass.setDescription("Financial");
        return aggLicClass;
    }
}
