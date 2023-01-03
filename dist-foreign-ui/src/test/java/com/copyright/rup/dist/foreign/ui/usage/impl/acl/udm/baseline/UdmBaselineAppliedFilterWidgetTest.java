package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

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
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(12, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Periods", "202106, 202012, 201506");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Usage Origin", "SS");
        verifyLabel(((VerticalLayout) component).getComponent(2), "Channel", "CCC");
        verifyLabel(((VerticalLayout) component).getComponent(3), "Detail Licensee Classes",
            "1 - Food and Tobacco, 22 - Book series, 26 - Law Firms");
        verifyLabel(((VerticalLayout) component).getComponent(4), "Aggregate Licensee Classes",
            "1 - Food and Tobacco, 12 - Machinery, 56 - Financial");
        verifyLabel(((VerticalLayout) component).getComponent(5), "Reported Types of Use",
            "FAX_PHOTOCOPIES, PRINT_COPIES");
        verifyLabel(((VerticalLayout) component).getComponent(6), "Type of Use", "PRINT");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(7),
            "<li><b><i>Wr Wrk Inst From: </i></b>20008506</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(8),
            "<li><b><i>System Title: </i></b>Medical journal</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(9), "<li><b><i>Usage Detail ID: </i></b>" +
            "2fc665eb-6b75-4e12-afbe-d2e1e1bab69d</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(10),
            "<li><b><i>Survey Country: </i></b>United States</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(11),
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
        filter.setPeriods(Sets.newHashSet(202012, 202106, 201506));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        filter.setDetailLicenseeClasses(Sets.newHashSet(buildDetailLicenseeClass(22, "Book series"),
            buildDetailLicenseeClass(1, "Food and Tobacco"),
            buildDetailLicenseeClass(26, "Law Firms")));
        filter.setAggregateLicenseeClasses(Sets.newHashSet(
            buildAggregateLicenseeClass(1, "Food and Tobacco"),
            buildAggregateLicenseeClass(12, "Machinery"),
            buildAggregateLicenseeClass(56, "Financial")));
        filter.setReportedTypeOfUses(Sets.newHashSet("PRINT_COPIES", "FAX_PHOTOCOPIES"));
        filter.setTypeOfUse("PRINT");
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "United States", null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 20008506L, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "Medical journal", null));
        filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "2fc665eb-6b75-4e12-afbe-d2e1e1bab69d", null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null));
        return filter;
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer id, String description) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        detailLicenseeClass.setDescription(description);
        return detailLicenseeClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String description) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        aggregateLicenseeClass.setDescription(description);
        return aggregateLicenseeClass;
    }
}
