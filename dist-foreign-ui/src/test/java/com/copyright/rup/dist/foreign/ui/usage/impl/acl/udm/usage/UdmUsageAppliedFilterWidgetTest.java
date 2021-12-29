package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
                buildUdmBatch("a8711022-8b30-4fa9-be39-c3e25378fd9a", "Udm Batch 2022")));
        replay(controller);
        UdmUsageFilter filter = buildUdmFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(23, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Batches", "Udm Batch 2021, Udm Batch 2022");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Periods", "202106");
        verifyLabel(((VerticalLayout) component).getComponent(2), "Status", "ELIGIBLE");
        verifyLabel(((VerticalLayout) component).getComponent(3), "Usage Origin", "SS");
        verifyLabel(((VerticalLayout) component).getComponent(4), "Assignees", "user@copyright.com");
        verifyLabel(((VerticalLayout) component).getComponent(5), "Reported Pub Types", "Not Shared");
        verifyLabel(((VerticalLayout) component).getComponent(6), "Publication Formats", "Digital");
        verifyLabel(((VerticalLayout) component).getComponent(7), "Detail Licensee Classes", "22 - Book series");
        verifyLabel(((VerticalLayout) component).getComponent(8), "Types of Use", "COPY_FOR_MYSELF");
        verifyLabel(((VerticalLayout) component).getComponent(9), "Usage Date From", "04/12/2020");
        verifyLabel(((VerticalLayout) component).getComponent(10), "Usage Date To", "06/20/2020");
        verifyLabel(((VerticalLayout) component).getComponent(11), "Survey Start Date From", "03/12/2020");
        verifyLabel(((VerticalLayout) component).getComponent(12), "Survey Start Date To", "05/20/2020");
        verifyLabel(((VerticalLayout) component).getComponent(13), "Channel", "CCC");
        verifyLabel(((VerticalLayout) component).getComponent(14), "Wr Wrk Inst", "254327612");
        verifyLabel(((VerticalLayout) component).getComponent(15), "Company ID", "1136");
        verifyLabel(((VerticalLayout) component).getComponent(16), "Company Name", "Company name");
        verifyLabel(((VerticalLayout) component).getComponent(17), "Survey Country", "United States");
        verifyLabel(((VerticalLayout) component).getComponent(18), "Language", "English");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(19),
            "<li><b><i>Annual Multiplier From: </i></b>25</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(20),
            "<li><b><i>Annualized Copies From: </i></b>425</li><li><b><i>Operator: </i></b>LESS_THAN</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(21),
            "<li><b><i>Statistical Multiplier From: </i></b>1</li><li><b><i>Operator: </i></b>GREATER_THAN</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(22),
            "<li><b><i>Quantity From: </i></b>2</li><li><b><i>Quantity To: </i></b>400</li><li><b><i>Operator: " +
                "</i></b>BETWEEN</li>");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        Label label = (Label) component;
        assertEquals(String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue), label.getValue());
    }

    private void verifyLabelWithOperator(Component component, String expectedValue) {
        Label label = (Label) component;
        assertEquals(expectedValue, label.getValue());
    }

    private UdmUsageFilter buildUdmFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(new HashSet<>(
            Arrays.asList("d7780576-2903-459c-a9ee-75a8d95cd4df", "a8711022-8b30-4fa9-be39-c3e25378fd9a")));
        filter.setPeriods(Collections.singleton(202106));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        filter.setAssignees(Collections.singleton("user@copyright.com"));
        filter.setReportedPubTypes(Collections.singleton("Not Shared"));
        filter.setPubFormats(Collections.singleton("Digital"));
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass()));
        filter.setReportedTypeOfUses(Collections.singleton("COPY_FOR_MYSELF"));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
        filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
        filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        filter.setSurveyCountry("United States");
        filter.setLanguage("English");
        filter.setCompanyName("Skadden, Arps, Slate, Meagher & Flom LLP");
        filter.setCompanyId(1136L);
        filter.setCompanyName("Company name");
        filter.setWrWrkInst(254327612L);
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

    private DetailLicenseeClass buildDetailLicenseeClass() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(22);
        detailLicenseeClass.setDescription("Book series");
        return detailLicenseeClass;
    }
}
