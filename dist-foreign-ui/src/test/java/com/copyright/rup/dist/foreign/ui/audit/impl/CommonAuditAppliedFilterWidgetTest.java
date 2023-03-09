package com.copyright.rup.dist.foreign.ui.audit.impl;

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
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.sal.SalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link CommonAuditAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/07/2023
 *
 * @author Stepan Karakhanov
 */
public class CommonAuditAppliedFilterWidgetTest {

    private static final Long ACCOUNT_NUMBER_1 = 1000000001L;
    private static final Long ACCOUNT_NUMBER_2 = 1000000002L;
    private static final String BATCH_ID_1 = "2f4fabf5-e154-4421-972f-cdc7b357725a";
    private static final String BATCH_ID_2 = "e7b3efe7-6036-41aa-889d-590e211bad7f";
    private static final String NAME_1 = "Name 1";
    private static final String NAME_2 = "Name 2";
    private static final String EVENT_ID = "007";
    private static final String DISTRIBUTION_NAME = "Dist 1";
    private static final Integer USAGE_PERIOD = 2023;

    private CommonAuditAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        ICommonAuditFilterController controller = createMock(SalAuditFilterController.class);
        widget = new CommonAuditAppliedFilterWidget(controller);
        expect(controller.getUsageBatches()).andReturn(
            List.of(buildBatch(BATCH_ID_1, NAME_1), buildBatch(BATCH_ID_2, NAME_2))).once();
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER_1))).andReturn(
            List.of(buildRightsholder(NAME_1, ACCOUNT_NUMBER_1))).once();
        expect(((SalAuditFilterController) controller).getSalLicensees()).andReturn(
            List.of(buildSalLicensee(ACCOUNT_NUMBER_2, NAME_2))).once();
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
        assertEquals(8, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Rightsholders", ACCOUNT_NUMBER_1 + " - " + NAME_1);
        verifyLabel(((VerticalLayout) component).getComponent(1), "Licensees", ACCOUNT_NUMBER_2 + " - " + NAME_2);
        verifyLabel(((VerticalLayout) component).getComponent(2), "Batches", NAME_1);
        verifyLabel(((VerticalLayout) component).getComponent(3), "Statuses", String.valueOf(UsageStatusEnum.ELIGIBLE));
        verifyLabel(((VerticalLayout) component).getComponent(4), "Detail Type", String.valueOf(SalDetailTypeEnum.IB));
        verifyLabel(((VerticalLayout) component).getComponent(5), "Usage Period", String.valueOf(USAGE_PERIOD));
        verifyLabel(((VerticalLayout) component).getComponent(6), "Event ID", EVENT_ID);
        verifyLabel(((VerticalLayout) component).getComponent(7), "Dist. Name", DISTRIBUTION_NAME);
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private AuditFilter buildFilter() {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER_1));
        auditFilter.setLicenseeAccountNumbers(Set.of(ACCOUNT_NUMBER_2));
        auditFilter.setBatchesIds(Set.of(BATCH_ID_1));
        auditFilter.setStatuses(Set.of(UsageStatusEnum.ELIGIBLE));
        auditFilter.setSalDetailType(SalDetailTypeEnum.IB);
        auditFilter.setUsagePeriod(USAGE_PERIOD);
        auditFilter.setCccEventId(EVENT_ID);
        auditFilter.setDistributionName(DISTRIBUTION_NAME);
        return auditFilter;
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

    private SalLicensee buildSalLicensee(Long accountNumber, String name) {
        SalLicensee salLicensee = new SalLicensee();
        salLicensee.setAccountNumber(accountNumber);
        salLicensee.setName(name);
        return salLicensee;
    }
}
