package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link ViewAaclFundPoolDetailsWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/05/2020
 *
 * @author Stanislau Rudak
 */
public class ViewAaclFundPoolDetailsWindowTest {

    private static final String FUND_POOL_ID = "3a1af07a-93d3-4411-a3e3-b77984457ea4";
    private static final String FUND_POOL_NAME = "Fund Pool";
    private static final String FUND_POOL_DETAIL_ID = "4bb9e7c1-8b41-488e-b21c-86dc8af3d960";

    private ViewAaclFundPoolDetailsWindow window;

    @Before
    public void setUp() {
        window = new ViewAaclFundPoolDetailsWindow(buildFundPool(), List.of(buildFundPoolDetail()));
    }

    @Test
    public void testStructure() {
        verifyWindow(window, FUND_POOL_NAME, "700px", "600px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(1, content.getComponentCount());
        verifyGrid((Grid<?>) content.getComponentAt(0), List.of(
            Pair.of("Agg LC ID", "110px"),
            Pair.of("Agg LC Enrollment", "190px"),
            Pair.of("Agg LC Discipline", "230px"),
            Pair.of("Gross Amount", "160px")));
        verifyButtonsLayout(getFooterLayout(window), true, "Close");
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/view-aacl-fund-pool-details-window.json", window);
    }

    private FundPool buildFundPool() {
        var fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName(FUND_POOL_NAME);
        fundPool.setTotalAmount(BigDecimal.ONE);
        return fundPool;
    }

    private FundPoolDetail buildFundPoolDetail() {
        var fundPoolDetail = new FundPoolDetail();
        fundPoolDetail.setId(FUND_POOL_DETAIL_ID);
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseeClass(108, "EXGP", "Life Sciences"));
        fundPoolDetail.setGrossAmount(BigDecimal.ONE);
        return fundPoolDetail;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String enrollmentProfile,
                                                               String discipline) {
        var aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        aggregateLicenseeClass.setEnrollmentProfile(enrollmentProfile);
        aggregateLicenseeClass.setDiscipline(discipline);
        return aggregateLicenseeClass;
    }
}
