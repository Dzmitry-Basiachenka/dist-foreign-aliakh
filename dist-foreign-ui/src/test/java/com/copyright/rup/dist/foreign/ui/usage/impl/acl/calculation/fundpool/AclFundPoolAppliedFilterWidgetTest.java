package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link AclFundPoolAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Mikita Maistrenka
 */
public class AclFundPoolAppliedFilterWidgetTest {

    private static final String FUND_POOL_NAME = "Fund Pool name";
    private static final String LICENSE_TYPE = "ACL";
    private static final String FUND_POOL_TYPE = "Print";

    private AclFundPoolAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new AclFundPoolAppliedFilterWidget();
        AclFundPoolDetailFilter filter = buildAclFundPoolDetailFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyLabel(verticalLayout.getComponent(0), "Fund Pool Names", FUND_POOL_NAME);
        verifyLabel(verticalLayout.getComponent(1), "Periods", "202212");
        verifyLabel(verticalLayout.getComponent(2), "Aggregate Licensee Classes", "57 - Communications");
        verifyLabel(verticalLayout.getComponent(3), "Detail Licensee Classes", "4 - Publishing");
        verifyLabel(verticalLayout.getComponent(4), "License Type", LICENSE_TYPE);
        verifyLabel(verticalLayout.getComponent(5), "Fund Pool Type", FUND_POOL_TYPE);
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private AclFundPoolDetailFilter buildAclFundPoolDetailFilter() {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        filter.setFundPoolNames(Collections.singleton(FUND_POOL_NAME));
        filter.setPeriods(Collections.singleton(202212));
        filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(57, "Communications")));
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(4, "Publishing")));
        filter.setLicenseType(LICENSE_TYPE);
        filter.setFundPoolType(FUND_POOL_TYPE);
        return filter;
    }


    private DetailLicenseeClass buildDetailLicenseeClass(int id, String description) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        detailLicenseeClass.setDescription(description);
        return detailLicenseeClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(int id, String description) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        aggregateLicenseeClass.setDescription(description);
        return aggregateLicenseeClass;
    }
}
