package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Verifies {@link UdmUsageAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Anton Azarenka
 */
public class UdmUsageAppliedFilterWidgetTest {

    private UdmUsageAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        IUdmUsageFilterController controller = createMock(IUdmUsageFilterController.class);
        widget = new UdmUsageAppliedFilterWidget(controller);
        expect(controller.getUdmBatches()).andReturn(
            Arrays.asList(buildUdmBatch("d7780576-2903-459c-a9ee-75a8d95cd4df", "Udm Batch 2021"),
                buildUdmBatch("a8711022-8b30-4fa9-be39-c3e25378fd9a", "Udm Batch 2022"),
                buildUdmBatch("10bacf4f-8b51-48fa-b16e-b1f3968f0381", "batch 2022")));
        replay(controller);
        UdmUsageFilter filter = buildUdmFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(28, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Batches",
            "batch 2022, Udm Batch 2021, Udm Batch 2022");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Periods", "202312, 202206, 202106");
        verifyLabel(((VerticalLayout) component).getComponent(2), "Status", "ELIGIBLE");
        verifyLabel(((VerticalLayout) component).getComponent(3), "Usage Origin", "SS");
        verifyLabel(((VerticalLayout) component).getComponent(4), "Assignees",
            "Auser@copyright.com, buser@copyright.com, User@copyright.com");
        verifyLabel(((VerticalLayout) component).getComponent(5), "Detail Licensee Classes",
            "1 - Food and Tobacco, 22 - Book series, 26 - Law Firms");
        verifyLabel(((VerticalLayout) component).getComponent(6), "Reported Pub Types", "Book, Journal, Not Shared");
        verifyLabel(((VerticalLayout) component).getComponent(7), "Reported Types of Use",
            "COPY_FOR_MYSELF, PRINT_COPIES");
        verifyLabel(((VerticalLayout) component).getComponent(8), "Publication Formats", "Digital, Print");
        verifyLabel(((VerticalLayout) component).getComponent(9), "Usage Date From", "04/12/2020");
        verifyLabel(((VerticalLayout) component).getComponent(10), "Usage Date To", "06/20/2020");
        verifyLabel(((VerticalLayout) component).getComponent(11), "Survey Start Date From", "03/12/2020");
        verifyLabel(((VerticalLayout) component).getComponent(12), "Survey Start Date To", "05/20/2020");
        verifyLabel(((VerticalLayout) component).getComponent(13), "Type of Use", "PRINT");
        verifyLabel(((VerticalLayout) component).getComponent(14), "Channel", "CCC");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(15),
            "<li><b><i>Wr Wrk Inst From: </i></b>254327612</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(16),
            "<li><b><i>Reported Title: </i></b>The New York times</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(17),
            "<li><b><i>System Title: </i></b>New York times</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(18),
            "<li><b><i>Usage Detail ID: </i></b>b989e02b-1f1d-4637-b89e-dc99938a51b9</li>" +
                "<li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(19),
            "<li><b><i>Company ID From: </i></b>1136</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(20),
            "<li><b><i>Company Name: </i></b>Albany International Corp.</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(21),
            "<li><b><i>Survey Respondent: </i></b>fa0276c3-55d6-42cd-8ffe-e9124acae02f</li>" +
                "<li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(22),
            "<li><b><i>Survey Country: </i></b>United States</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(23),
            "<li><b><i>Language: </i></b>English</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(24),
            "<li><b><i>Annual Multiplier From: </i></b>25</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(25),
            "<li><b><i>Annualized Copies From: </i></b>425</li><li><b><i>Operator: </i></b>LESS_THAN</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(26),
            "<li><b><i>Statistical Multiplier From: </i></b>1</li><li><b><i>Operator: </i></b>GREATER_THAN</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(27),
            "<li><b><i>Quantity From: </i></b>2</li><li><b><i>Quantity To: </i></b>400</li>" +
                "<li><b><i>Operator: </i></b>BETWEEN</li>");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabelWithOperator(Component component, String caption) {
        UiTestHelper.verifyLabel(component, caption, ContentMode.HTML, -1.0f);
    }

    private UdmUsageFilter buildUdmFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(new HashSet<>(Arrays.asList("d7780576-2903-459c-a9ee-75a8d95cd4df",
            "a8711022-8b30-4fa9-be39-c3e25378fd9a", "10bacf4f-8b51-48fa-b16e-b1f3968f0381")));
        filter.setPeriods(Sets.newHashSet(202106, 202206, 202312));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        filter.setAssignees(Sets.newHashSet("buser@copyright.com", "User@copyright.com", "Auser@copyright.com"));
        filter.setReportedPubTypes(Sets.newHashSet("Not Shared", "Book", "Journal"));
        filter.setPubFormats(Sets.newHashSet("Print", "Digital"));
        filter.setDetailLicenseeClasses(Sets.newHashSet(buildDetailLicenseeClass(22, "Book series"),
            buildDetailLicenseeClass(1, "Food and Tobacco"),
            buildDetailLicenseeClass(26, "Law Firms")));
        filter.setReportedTypeOfUses(Sets.newHashSet("PRINT_COPIES", "COPY_FOR_MYSELF"));
        filter.setTypeOfUse("PRINT");
        filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
        filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
        filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 254327612L, null));
        filter.setReportedTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "The New York times",
            null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "New York times",
            null));
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS,
            "b989e02b-1f1d-4637-b89e-dc99938a51b9", null));
        filter.setCompanyIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1136L, null));
        filter.setCompanyNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "Albany International Corp.",
            null));
        filter.setSurveyRespondentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS,
            "fa0276c3-55d6-42cd-8ffe-e9124acae02f", null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "United States", null));
        filter.setLanguageExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "English", null));
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null));
        filter.setStatisticalMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 1, null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400));
        return filter;
    }

    private UdmBatch buildUdmBatch(String id, String name) {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(id);
        udmBatch.setName(name);
        return udmBatch;
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer id, String description) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        detailLicenseeClass.setDescription(description);
        return detailLicenseeClass;
    }
}
