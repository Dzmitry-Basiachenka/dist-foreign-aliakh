package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyAppliedFiltersLabel;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditFilterController;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AaclAuditAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/16/2023
 *
 * @author Stepan Karakhanov
 */
public class AaclAuditAppliedFilterWidgetTest {

    private static final String BATCH_ID_1 = "2f4fabf5-e154-4421-972f-cdc7b357725a";
    private static final String BATCH_ID_2 = "e7b3efe7-6036-41aa-889d-590e211bad7f";
    private static final String NAME_1 = "Name 1";
    private static final String NAME_2 = "Name 2";
    private static final String EVENT_ID = "500";
    private static final String DISTRIBUTION_NAME = "Dist. name";
    private static final Long ACCOUNT_NUMBER = 1000000001L;
    private static final Integer USAGE_PERIOD = 2023;

    private AaclAuditAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        IAaclAuditFilterController controller = createMock(IAaclAuditFilterController.class);
        widget = new AaclAuditAppliedFilterWidget(controller);
        expect(controller.getUsageBatches()).andReturn(
            List.of(buildBatch(BATCH_ID_1, NAME_1), buildBatch(BATCH_ID_2, NAME_2))).once();
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER))).andReturn(
            List.of(buildRightsholder(ACCOUNT_NUMBER, NAME_1))).once();
        replay(controller);
        var filter = buildFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        assertEquals("100%", widget.getWidth());
        assertEquals("100%", widget.getHeight());
        var component = widget.getChildren().findFirst().orElseThrow();
        assertThat(component, instanceOf(VerticalLayout.class));
        var verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(0), "Rightsholders",
            ACCOUNT_NUMBER + " - " + NAME_1);
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(1), "Batches", NAME_1 + ", " + NAME_2);
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(2), "Statuses",
            String.valueOf(UsageStatusEnum.ELIGIBLE));
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(3), "Usage Period",
            String.valueOf(USAGE_PERIOD));
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(4), "Event ID", EVENT_ID);
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(5), "Dist. Name", DISTRIBUTION_NAME);
    }

    private AuditFilter buildFilter() {
        var filter = new AuditFilter();
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
        filter.setBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setStatuses(Set.of(UsageStatusEnum.ELIGIBLE));
        filter.setUsagePeriod(USAGE_PERIOD);
        filter.setCccEventId(EVENT_ID);
        filter.setDistributionName(DISTRIBUTION_NAME);
        return filter;
    }

    private UsageBatch buildBatch(String id, String name) {
        var batch = new UsageBatch();
        batch.setId(id);
        batch.setName(name);
        return batch;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
