package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ViewAaclFundPoolDetailsWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 2/5/20
 *
 * @author Stanislau Rudak
 */
public class ViewAaclFundPoolDetailsWindowTest {

    private static final String FUND_POOL_ID = "3a1af07a-93d3-4411-a3e3-b77984457ea4";

    private ViewAaclFundPoolDetailsWindow window;

    @Before
    public void setUp() {
        IAaclUsageController controllerMock = createMock(IAaclUsageController.class);
        expect(controllerMock.getFundPoolDetails(eq(FUND_POOL_ID)))
            .andReturn(Collections.singletonList(buildFundPoolDetail())).once();
        replay(controllerMock);
        window = new ViewAaclFundPoolDetailsWindow(buildFundPool(), controllerMock);
        verify(controllerMock);
    }

    @Test
    public void testStructure() {
        assertEquals("AACL Fund Pool", window.getCaption());
        assertEquals("view-aacl-fund-pool-details-window", window.getStyleName());
        verifySize(window);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        assertEquals("view-aacl-fund-pool-details-buttons", buttonsLayout.getStyleName());
        assertEquals(1, buttonsLayout.getComponentCount());
        Button closeButton = (Button) buttonsLayout.getComponent(0);
        assertEquals("Close", closeButton.getCaption());
    }

    private void verifySize(Component component) {
        assertEquals(600, component.getWidth(), 0);
        assertEquals(600, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Aggregate Licensee Class ID", "Aggregate Licensee Class Name", "Gross Amount"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(200.0, -1.0, 110.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
    }

    private AaclFundPool buildFundPool() {
        AaclFundPool fundPool = new AaclFundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName("AACL Fund Pool");
        fundPool.setTotalGrossAmount(BigDecimal.ONE);
        return fundPool;
    }

    private AaclFundPoolDetail buildFundPoolDetail() {
        AaclFundPoolDetail detail = new AaclFundPoolDetail();
        detail.setId(RupPersistUtils.generateUuid());
        detail.setFundPoolId(FUND_POOL_ID);
        detail.setAggregateLicenseeClass(buildAggregateLicenseeClass(108, "EXGP - Life Sciences"));
        detail.setGrossAmount(BigDecimal.ONE);
        return detail;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String name) {
        AggregateLicenseeClass licenseeClass = new AggregateLicenseeClass();
        licenseeClass.setId(id);
        licenseeClass.setName(name);
        return licenseeClass;
    }
}
