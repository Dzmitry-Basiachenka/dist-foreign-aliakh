package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
        window = new ViewAaclFundPoolDetailsWindow(buildFundPool(), List.of(buildFundPoolDetail()));
    }

    @Test
    public void testStructure() {
        assertEquals("view-aacl-fund-pool-details-window", window.getStyleName());
        verifyWindow(window, "AACL Fund Pool", 600, 600, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, Arrays.asList(
            Triple.of("Agg LC ID", -1.0, 1),
            Triple.of("Agg LC Enrollment", -1.0, 2),
            Triple.of("Agg LC Discipline", -1.0, 3),
            Triple.of("Gross Amount", -1.0, 2)));
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        assertEquals("view-aacl-fund-pool-details-buttons", buttonsLayout.getStyleName());
        verifyButtonsLayout(buttonsLayout, "Close");
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName("AACL Fund Pool");
        fundPool.setTotalAmount(BigDecimal.ONE);
        return fundPool;
    }

    private FundPoolDetail buildFundPoolDetail() {
        FundPoolDetail detail = new FundPoolDetail();
        detail.setId(RupPersistUtils.generateUuid());
        detail.setAggregateLicenseeClass(buildAggregateLicenseeClass(108, "EXGP", "Life Sciences"));
        detail.setGrossAmount(BigDecimal.ONE);
        return detail;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String enrollmentProfile,
                                                               String discipline) {
        AggregateLicenseeClass licenseeClass = new AggregateLicenseeClass();
        licenseeClass.setId(id);
        licenseeClass.setEnrollmentProfile(enrollmentProfile);
        licenseeClass.setDiscipline(discipline);
        return licenseeClass;
    }
}
