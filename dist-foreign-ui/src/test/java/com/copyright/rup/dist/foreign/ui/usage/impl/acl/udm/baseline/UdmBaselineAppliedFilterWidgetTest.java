package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Collections;

/**
 * Test for {@link UdmBaselineAppliedFilterWidget}.
 *
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/19/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineAppliedFilterWidgetTest {

    private UdmBaselineAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new UdmBaselineAppliedFilterWidget();
        UdmBaselineFilter filter = buildUdmFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(11, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Periods", "202012");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Usage Origin", "SS");
        verifyLabel(((VerticalLayout) component).getComponent(2), "Channel", "CCC");
        verifyLabel(((VerticalLayout) component).getComponent(3), "Detail Licensee Classes", "22 - Book series");
        verifyLabel(((VerticalLayout) component).getComponent(4), "Types of Use", "EMAIL_COPY");
        verifyLabel(((VerticalLayout) component).getComponent(5), "Aggregate Licensee Classes", "56 - Financial");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(6),
            "<li><b><i>Wr Wrk Inst From: </i></b>20008506</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(7),
            "<li><b><i>System Title: </i></b>Medical journal</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(8), "<li><b><i>Usage Detail ID: </i></b>" +
            "2fc665eb-6b75-4e12-afbe-d2e1e1bab69d</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(9),
            "<li><b><i>Survey Country: </i></b>United States</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(10),
            "<li><b><i>Annualized Copies From: </i></b>5</li><li><b><i>Operator: </i></b>LESS_THAN</li>");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabelWithOperator(Component component, String caption) {
        UiTestHelper.verifyLabel(component, caption, ContentMode.HTML, -1.0f);
    }

    private UdmBaselineFilter buildUdmFilter() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass()));
        filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass()));
        filter.setReportedTypeOfUses(Collections.singleton("EMAIL_COPY"));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "United States", null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 20008506L, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "Medical journal", null));
        filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "2fc665eb-6b75-4e12-afbe-d2e1e1bab69d", null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null));
        return filter;
    }

    private DetailLicenseeClass buildDetailLicenseeClass() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(22);
        detailLicenseeClass.setDescription("Book series");
        return detailLicenseeClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass() {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(56);
        aggregateLicenseeClass.setDescription("Financial");
        return aggregateLicenseeClass;
    }
}
