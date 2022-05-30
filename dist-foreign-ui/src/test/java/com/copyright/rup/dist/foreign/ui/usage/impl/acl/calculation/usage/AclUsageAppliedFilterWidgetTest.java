package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

/**
 * Verifies {@link AclUsageAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/21/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageAppliedFilterWidgetTest {

    private static final String NAME_VALUE_FORMAT = "<li><b><i>%s: </i></b>%s</li>";
    private static final String OPERATOR_FORMAT = "<li><b><i>Operator: </i></b>%s</li>";

    @Test
    public void testRefreshFilterPanel() {
        AclUsageAppliedFilterWidget widget = new AclUsageAppliedFilterWidget();
        AclUsageFilter filter = buildAclUsageFilter();
        widget.refreshFilterPanel(filter);
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(14, verticalLayout.getComponentCount());
        verifyLabel(verticalLayout.getComponent(0), "Usage Batch Name", "ACL Usage Batch 2021");
        verifyLabel(verticalLayout.getComponent(1), "Usage Origin", "RFA");
        verifyLabel(verticalLayout.getComponent(2), "Channel", "CCC");
        verifyLabel(verticalLayout.getComponent(3), "Periods", "202212, 202112, 201506");
        verifyLabel(verticalLayout.getComponent(4), "Detail Licensee Classes",
            "1 - Food and Tobacco, 4 - Publishing, 22 - Book series");
        verifyLabel(verticalLayout.getComponent(5), "Aggregate Licensee Classes",
            "1 - Food and Tobacco, 12 - Machinery, 57 - Communications");
        verifyLabel(verticalLayout.getComponent(6), "Pub Types",
            "BK - Book, BK2 - Book series, SJ - Scholarly Journal");
        verifyLabel(verticalLayout.getComponent(7), "Types of Use", "DIGITAL, PRINT");
        verifyLabel(verticalLayout.getComponent(8), "Usage Detail ID", "EQUALS", "OGN674GHHHB0153");
        verifyLabel(verticalLayout.getComponent(9), "Wr Wrk Inst From", "Wr Wrk Inst To", "BETWEEN", "1", "100000000");
        verifyLabel(verticalLayout.getComponent(10), "System Title", "CONTAINS", "journal");
        verifyLabel(verticalLayout.getComponent(11), "Survey Country", "DOES_NOT_EQUAL", "Portugal");
        verifyLabel(verticalLayout.getComponent(12), "Content Unit Price From", "GREATER_THAN", "1");
        verifyLabel(verticalLayout.getComponent(13), "Annualized Copies From", "LESS_THAN", "2");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format(NAME_VALUE_FORMAT, labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabel(Component component, String labelName, String operator, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format(NAME_VALUE_FORMAT + OPERATOR_FORMAT,
            labelName, labelValue, operator),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabel(Component component, String labelName1, String labelName2, String operator,
                             String labelValue1, String labelValue2) {
        UiTestHelper.verifyLabel(component, String.format(NAME_VALUE_FORMAT + NAME_VALUE_FORMAT + OPERATOR_FORMAT,
            labelName1, labelValue1, labelName2, labelValue2, operator),
            ContentMode.HTML, -1.0f);
    }

    private AclUsageFilter buildAclUsageFilter() {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName("ACL Usage Batch 2021");
        filter.setUsageOrigin(UdmUsageOriginEnum.RFA);
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setPeriods(Sets.newHashSet(202112, 201506, 202212));
        filter.setDetailLicenseeClasses(Sets.newHashSet(buildDetailLicenseeClass(22, "Book series"),
            buildDetailLicenseeClass(1, "Food and Tobacco"),
            buildDetailLicenseeClass(4, "Publishing")));
        filter.setAggregateLicenseeClasses(Sets.newHashSet(
            buildAggregateLicenseeClass(12, "Machinery"),
            buildAggregateLicenseeClass(1, "Food and Tobacco"),
            buildAggregateLicenseeClass(57, "Communications")));
        filter.setPubTypes(Sets.newHashSet(buildPubType("BK2", "Book series"), buildPubType("SJ", "Scholarly Journal"),
            buildPubType("BK", "Book")));
        filter.setTypeOfUses(Sets.newHashSet("PRINT", "DIGITAL"));
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "OGN674GHHHB0153", null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1, 100000000));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, "journal", null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, "Portugal", null));
        filter.setContentUnitPriceExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 1, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 2, null));
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

    private PublicationType buildPubType(String name, String description) {
        PublicationType publicationType = new PublicationType();
        publicationType.setName(name);
        publicationType.setDescription(description);
        return publicationType;
    }
}
