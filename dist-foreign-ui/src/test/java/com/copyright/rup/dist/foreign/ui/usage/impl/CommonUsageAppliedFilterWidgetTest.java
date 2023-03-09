package com.copyright.rup.dist.foreign.ui.usage.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link CommonUsageAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/01/2023
 *
 * @author Stepan Karakhanov
 */
public class CommonUsageAppliedFilterWidgetTest {

    private static final LocalDate DATE = LocalDate.of(2023, 5, 23);
    private static final Long ACCOUNT_NUMBER_1 = 1000000001L;
    private static final Long ACCOUNT_NUMBER_2 = 1000000002L;
    private static final String BATCH_ID_1 = "2f4fabf5-e154-4421-972f-cdc7b357725a";
    private static final String BATCH_ID_2 = "e7b3efe7-6036-41aa-889d-590e211bad7f";
    private static final String NAME_1 = "Name 1";
    private static final String NAME_2 = "Name 2";

    private CommonUsageAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        ICommonUsageFilterController controller = createMock(ICommonUsageFilterController.class);
        widget = new CommonUsageAppliedFilterWidget(controller);
        expect(controller.getUsageBatches()).andReturn(
            List.of(buildBatch(BATCH_ID_1, NAME_1), buildBatch(BATCH_ID_2, NAME_2)));
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2))).andReturn(
            List.of(buildRightsholder(NAME_1, ACCOUNT_NUMBER_1), buildRightsholder(NAME_2, ACCOUNT_NUMBER_2))
        );
        replay(controller);
        UsageFilter filter = buildFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(7, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Batches", NAME_1 + ", " + NAME_2);
        verifyLabel(((VerticalLayout) component).getComponent(1), "RROs",
            ACCOUNT_NUMBER_1 + " - " + NAME_1 + ", " + ACCOUNT_NUMBER_2 + " - " + NAME_2);
        verifyLabel(((VerticalLayout) component).getComponent(2), "Payment Date To", "05/23/2023");
        verifyLabel(((VerticalLayout) component).getComponent(3), "Status", String.valueOf(UsageStatusEnum.RH_FOUND));
        verifyLabel(((VerticalLayout) component).getComponent(4), "Fiscal Year To", String.valueOf(2023));
        verifyLabel(((VerticalLayout) component).getComponent(5), "Usage Period", String.valueOf(2023));
        verifyLabel(((VerticalLayout) component).getComponent(6), "Detail Type", String.valueOf(SalDetailTypeEnum.IB));
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private UsageFilter buildFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2));
        filter.setPaymentDate(DATE);
        filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        filter.setFiscalYear(2023);
        filter.setUsagePeriod(2023);
        filter.setSalDetailType(SalDetailTypeEnum.IB);
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
