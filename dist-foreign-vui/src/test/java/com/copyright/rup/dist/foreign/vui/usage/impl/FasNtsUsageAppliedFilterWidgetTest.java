package com.copyright.rup.dist.foreign.vui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link FasNtsUsageAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/13/2023
 *
 * @author Stepan Karakhanov
 */
public class FasNtsUsageAppliedFilterWidgetTest {

    private static final String BATCH_ID_1 = "2f4fabf5-e154-4421-972f-cdc7b357725a";
    private static final String BATCH_ID_2 = "e7b3efe7-6036-41aa-889d-590e211bad7f";
    private static final String BATCH_NAME_1 = "Usage Batch 1";
    private static final String BATCH_NAME_2 = "Usage Batch 2";
    private static final Long ACCOUNT_NUMBER_1 = 1000000001L;
    private static final Long ACCOUNT_NUMBER_2 = 1000000002L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2023, 5, 23);
    private static final UsageStatusEnum USAGE_STATUS = UsageStatusEnum.RH_FOUND;
    private static final Integer FISCAL_YEAR = 2023;

    private CommonUsageAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        ICommonUsageFilterController controller = createMock(ICommonUsageFilterController.class);
        widget = new FasNtsUsageAppliedFilterWidget(controller);
        expect(controller.getUsageBatches()).andReturn(
            List.of(buildBatch(BATCH_ID_1, BATCH_NAME_1), buildBatch(BATCH_ID_2, BATCH_NAME_2)));
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2))).andReturn(
            List.of(buildRightsholder(BATCH_NAME_1, ACCOUNT_NUMBER_1),
                buildRightsholder(BATCH_NAME_2, ACCOUNT_NUMBER_2))
        );
        replay(controller);
        UsageFilter filter = buildFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        assertEquals("100%", widget.getWidth());
        assertEquals("100%", widget.getHeight());
        Component component = widget.getChildren().findFirst().orElseThrow();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponentAt(0), "Batches", BATCH_NAME_1 + ", " + BATCH_NAME_2);
        verifyLabel(((VerticalLayout) component).getComponentAt(1), "RROs",
            ACCOUNT_NUMBER_1 + " - " + BATCH_NAME_1 + ", " + ACCOUNT_NUMBER_2 + " - " + BATCH_NAME_2);
        verifyLabel(((VerticalLayout) component).getComponentAt(2), "Payment Date To", "05/23/2023");
        verifyLabel(((VerticalLayout) component).getComponentAt(3), "Status", USAGE_STATUS.name());
        verifyLabel(((VerticalLayout) component).getComponentAt(4), "Fiscal Year To", FISCAL_YEAR.toString());
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        assertThat(component, instanceOf(Label.class));
        Label label = (Label) component;
        Html html = (Html) label.getChildren().findFirst().orElseThrow();
        String actualText = html.getElement().toString();
        String expectedText = String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue);
        assertEquals(expectedText, actualText);
    }

    private UsageFilter buildFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2));
        filter.setPaymentDate(PAYMENT_DATE);
        filter.setUsageStatus(USAGE_STATUS);
        filter.setFiscalYear(FISCAL_YEAR);
        return filter;
    }

    private UsageBatch buildBatch(String id, String name) {
        UsageBatch batch = new UsageBatch();
        batch.setId(id);
        batch.setName(name);
        return batch;
    }

    private Rightsholder buildRightsholder(String name, Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
