package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyAppliedFiltersLabel;

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
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditFilterController;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

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

    private static final String BATCH_ID_1 = "42575c34-9fe8-4652-b885-f6da23946bd1";
    private static final String BATCH_ID_2 = "7b1ef7e1-8269-4929-a5f4-6a17bb7cbca6";
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
            List.of(buildRightsholder())).once();
        replay(controller);
        widget.refreshFilterPanel(buildFilter());
        verifyLayout();
        verify(controller);
    }

    private void verifyLayout() {
        assertEquals("100%", widget.getWidth());
        assertEquals("100%", widget.getHeight());
        var component = widget.getChildren().findFirst().orElseThrow();
        assertThat(component, instanceOf(VerticalLayout.class));
        var verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(0), "Rightsholders",
            ACCOUNT_NUMBER + " - " + NAME_1);
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(1), "Batches", NAME_1 + ", " + NAME_2);
        verifyAppliedFiltersLabel(
            ((VerticalLayout) component).getComponentAt(2), "Statuses", String.valueOf(UsageStatusEnum.ELIGIBLE));
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(3), "Event ID", EVENT_ID);
        verifyAppliedFiltersLabel(((VerticalLayout) component).getComponentAt(4), "Dist. Name", DISTRIBUTION_NAME);
    }

    private AuditFilter buildFilter() {
        var filter = new AuditFilter();
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
        filter.setBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setStatuses(Set.of(UsageStatusEnum.ELIGIBLE));
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

    private Rightsholder buildRightsholder() {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(ACCOUNT_NUMBER);
        rightsholder.setName(NAME_1);
        return rightsholder;
    }
}
