package com.copyright.rup.dist.foreign.ui.audit.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

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
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link NtsAuditAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/16/2023
 *
 * @author Stepan Karakhanov
 */
public class NtsAuditAppliedFilterWidgetTest {

    private static final String BATCH_ID_1 = "2f4fabf5-e154-4421-972f-cdc7b357725a";
    private static final String BATCH_ID_2 = "e7b3efe7-6036-41aa-889d-590e211bad7f";
    private static final String NAME_1 = "Name 1";
    private static final String NAME_2 = "Name 2";
    private static final String EVENT_ID = "500";
    private static final String DISTRIBUTION_NAME = "Dist. name";
    private static final Long ACCOUNT_NUMBER = 1000000001L;

    private NtsAuditAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        INtsAuditFilterController controller = createMock(INtsAuditFilterController.class);
        widget = new NtsAuditAppliedFilterWidget(controller);
        expect(controller.getUsageBatches()).andReturn(
            List.of(buildBatch(BATCH_ID_1, NAME_1), buildBatch(BATCH_ID_2, NAME_2))).once();
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER))).andReturn(
            List.of(buildRightsholder(ACCOUNT_NUMBER, NAME_1))).once();
        replay(controller);
        AuditFilter filter = buildFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Rightsholders", ACCOUNT_NUMBER + " - " + NAME_1);
        verifyLabel(((VerticalLayout) component).getComponent(1), "Batches", NAME_1 + ", " + NAME_2);
        verifyLabel(((VerticalLayout) component).getComponent(2), "Statuses", String.valueOf(UsageStatusEnum.ELIGIBLE));
        verifyLabel(((VerticalLayout) component).getComponent(3), "Event ID", EVENT_ID);
        verifyLabel(((VerticalLayout) component).getComponent(4), "Dist. Name", DISTRIBUTION_NAME);
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private AuditFilter buildFilter() {
        AuditFilter filter = new AuditFilter();
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
        filter.setBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setStatuses(Set.of(UsageStatusEnum.ELIGIBLE));
        filter.setCccEventId(EVENT_ID);
        filter.setDistributionName(DISTRIBUTION_NAME);
        return filter;
    }

    private UsageBatch buildBatch(String id, String name) {
        UsageBatch batch = new UsageBatch();
        batch.setId(id);
        batch.setName(name);
        return batch;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
