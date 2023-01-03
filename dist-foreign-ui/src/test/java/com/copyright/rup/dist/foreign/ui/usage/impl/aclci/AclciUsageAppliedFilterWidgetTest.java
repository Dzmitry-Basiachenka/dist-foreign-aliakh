package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Verifies {@link AclciUsageAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciUsageAppliedFilterWidgetTest {

    private AclciUsageAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        IAclciUsageFilterController controller = createMock(IAclciUsageFilterController.class);
        widget = new AclciUsageAppliedFilterWidget(controller);
        expect(controller.getUsageBatches()).andReturn(
            Arrays.asList(buildBatch("2f4fabf5-e154-4421-972f-cdc7b357725a", "ACLCI Batch 2021"),
                buildBatch("e7b3efe7-6036-41aa-889d-590e211bad7f", "ACLCI Batch 2022"),
                buildBatch("6870b76b-2461-4322-9a65-8fede1b7829e", "1 Batch 2022")));
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
        assertEquals(3, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Batches",
            "1 Batch 2022, ACLCI Batch 2021, ACLCI Batch 2022");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Status", "RH_FOUND");
        verifyLabel(((VerticalLayout) component).getComponent(2), "License Types", "CURR_REPUB_HE, CURR_REPUB_K12, " +
                "CURR_REUSE_K12, CURR_SHARE_K12");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private UsageFilter buildFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(new HashSet<>(Arrays.asList("2f4fabf5-e154-4421-972f-cdc7b357725a",
            "e7b3efe7-6036-41aa-889d-590e211bad7f", "6870b76b-2461-4322-9a65-8fede1b7829e")));
        filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        filter.setLicenseTypes(new HashSet<>(Arrays.asList(AclciLicenseTypeEnum.values())));
        return filter;
    }

    private UsageBatch buildBatch(String id, String name) {
        UsageBatch batch = new UsageBatch();
        batch.setId(id);
        batch.setName(name);
        return batch;
    }
}
