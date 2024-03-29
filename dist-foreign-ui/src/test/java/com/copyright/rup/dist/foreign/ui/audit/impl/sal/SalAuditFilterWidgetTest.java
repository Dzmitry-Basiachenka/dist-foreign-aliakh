package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link SalAuditFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalAuditFilterWidgetTest {

    private static final String USAGE_BATCH_ID = "8f8fbc50-4ad9-4103-af71-7d96b558a81a";
    private static final Long RH_ACCOUNT_NUMBER = 1000018380L;
    private CommonAuditFilterWidget widget;
    private ISalAuditFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(ISalAuditFilterController.class);
        widget = new SalAuditFilterWidget(controller);
        widget.setController(controller);
        expect(controller.getProductFamily()).andReturn("SAL").times(2);
        expect(controller.getUsagePeriods()).andReturn(buildUsagePeriods()).once();
    }

    @Test
    public void testLayout() {
        replay(controller);
        widget.init();
        assertTrue(widget.isSpacing());
        assertEquals(new MarginInfo(true), widget.getMargin());
        assertEquals("audit-filter-widget", widget.getStyleName());
        assertEquals(12, widget.getComponentCount());
        Component component = widget.getComponent(0);
        verifyFiltersLabel(component);
        component = widget.getComponent(1);
        assertThat(component, instanceOf(LazyRightsholderFilterWidget.class));
        assertEquals("Rightsholders", Whitebox.getInternalState(component, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(component, IFilterSaveListener.class));
        component = widget.getComponent(2);
        assertThat(component, instanceOf(SalLicenseeFilterWidget.class));
        assertEquals("Licensees", Whitebox.getInternalState(component, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(component, IFilterSaveListener.class));
        component = widget.getComponent(3);
        assertThat(component, instanceOf(UsageBatchFilterWidget.class));
        verifyFilterWidget((UsageBatchFilterWidget) component, "Batches");
        component = widget.getComponent(4);
        assertThat(component, instanceOf(CommonStatusFilterWidget.class));
        verifyFilterWidget((CommonStatusFilterWidget) component, "Statuses");
        verifyComboBox(widget.getComponent(5), "Detail Type", true, SalDetailTypeEnum.values());
        verifyComboBox(widget.getComponent(6), "Usage Period", true, buildUsagePeriods());
        verifyTextField(widget.getComponent(7), "Event ID", "ccc-event-id-filter");
        verifyTextField(widget.getComponent(8), "Dist. Name", "distribution-name-filter");
        component = widget.getComponent(9);
        assertThat(component, instanceOf(HorizontalLayout.class));
        verifyButtonsLayout((HorizontalLayout) component);
        assertEquals(Alignment.MIDDLE_RIGHT, widget.getComponentAlignment(component));
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getRightsholdersByAccountNumbers(Set.of(RH_ACCOUNT_NUMBER)))
            .andReturn(List.of(buildRightsholder())).once();
        expect(controller.getSalLicensees()).andReturn(List.of()).once();
        expect(controller.getUsageBatches()).andReturn(List.of(buildUsageBatch(USAGE_BATCH_ID))).once();
        replay(controller);
        widget.init();
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setProductFamily("SAL");
        assertEquals(auditFilter, widget.getAppliedFilter());
        auditFilter.setRhAccountNumbers(Set.of(RH_ACCOUNT_NUMBER));
        auditFilter.setLicenseeAccountNumbers(Set.of(1114L));
        auditFilter.setBatchesIds(Set.of(USAGE_BATCH_ID));
        auditFilter.setStatuses(Set.of(UsageStatusEnum.ELIGIBLE));
        auditFilter.setCccEventId("53256");
        auditFilter.setDistributionName("SAL December 2020");
        auditFilter.setSearchValue("Limited");
        auditFilter.setUsagePeriod(2020);
        auditFilter.setSalDetailType(SalDetailTypeEnum.IB);
        Whitebox.setInternalState(widget, "filter", auditFilter);
        widget.applyFilter();
        assertEquals(auditFilter, widget.getAppliedFilter());
        verify(controller);
    }

    private void verifyFilterWidget(BaseItemsFilterWidget filterWidget, String caption) {
        assertEquals(caption, Whitebox.getInternalState(filterWidget, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(filterWidget, IFilterSaveListener.class));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        verifySize(layout);
        assertEquals("filter-buttons", layout.getStyleName());
        assertEquals("filter-buttons", layout.getId());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertThat(component, instanceOf(Button.class));
        verifyButton((Button) component, "Apply", false);
        component = layout.getComponent(1);
        assertThat(component, instanceOf(Button.class));
        verifyButton((Button) component, "Clear", true);
    }

    private void verifyButton(Button button, String caption, boolean enabled) {
        assertEquals(caption, button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
        assertEquals(enabled, button.isEnabled());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(-1, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private List<Integer> buildUsagePeriods() {
        return List.of(2020);
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        return rightsholder;
    }

    private UsageBatch buildUsageBatch(String id) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(id);
        return usageBatch;
    }
}
