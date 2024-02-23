package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyTextField;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link NtsAuditFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/17/20
 *
 * @author Anton Azarenka
 */
public class NtsAuditFilterWidgetTest implements IVaadinComponentFinder {

    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String APPLY_BUTTON = "Apply";
    private static final Long ACCOUNT_NUMBER = 2000017000L;

    private CommonAuditFilterWidget widget;
    private INtsAuditFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(INtsAuditFilterController.class);
        expect(controller.getProductFamily()).andReturn(NTS_PRODUCT_FAMILY).times(2);
        widget = new NtsAuditFilterWidget(controller);
        widget.setController(controller);
        widget.setFilterSaveAction(() -> {
        });
    }

    @Test
    public void testInit() {
        replay(controller);
        widget.init();
        assertEquals(9, widget.getComponentCount());
        assertEquals("audit-filter-widget", widget.getId().orElseThrow());
        assertEquals("audit-filter-widget", widget.getClassName());
        verifyFilters();
        verifyButtonsLayout(widget.getComponentAt(6));
        verifyFiltersLabel(widget.getComponentAt(7), "Applied Filters:");
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER)))
            .andReturn(List.of(buildRightsholder())).once();
        expect(controller.getUsageBatches()).andReturn(List.of()).once();
        replay(controller);
        widget.init();
        var applyButton = getButton(widget, APPLY_BUTTON);
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        widget.getFilter().setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        verify(controller);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        replay(controller);
        widget.init();
        var applyButton = getButton(widget, APPLY_BUTTON);
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(controller);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        expect(controller.getProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER)))
            .andReturn(List.of(buildRightsholder())).once();
        expect(controller.getUsageBatches()).andReturn(List.of()).once();
        replay(controller);
        widget.init();
        var applyButton = getButton(widget, APPLY_BUTTON);
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(NTS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(NTS_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        widget.getFilter().setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
        widget.getFilter().setProductFamily(NTS_PRODUCT_FAMILY);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(NTS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(NTS_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        widget.clearFilter();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(NTS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(NTS_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        assertFalse(applyButton.isEnabled());
        verify(controller);
    }

    @Test
    public void verifyClearButtonClickListener() {
        expect(controller.getProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, controller);
        widget.init();
        Set<Long> accountNumbers = Set.of(ACCOUNT_NUMBER);
        var applyButton = getButton(widget, APPLY_BUTTON);
        widget.getFilter().setRhAccountNumbers(accountNumbers);
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        var clearButton = getButton(widget, "Clear");
        clearButton.click();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent, controller);
    }

    private void verifyFilters() {
        verifyFiltersLabel(widget.getComponentAt(0));
        var component = widget.getComponentAt(1);
        assertThat(component, instanceOf(LazyRightsholderFilterWidget.class));
        verifyItemsFilterWidget(component, "Rightsholders");
        component = widget.getComponentAt(2);
        assertThat(component, instanceOf(UsageBatchFilterWidget.class));
        verifyItemsFilterWidget(component, "Batches");
        component = widget.getComponentAt(3);
        assertThat(component, instanceOf(CommonStatusFilterWidget.class));
        verifyItemsFilterWidget(component, "Statuses");
        verifyTextField(widget.getComponentAt(4), "Event ID", "ccc-event-id-filter");
        verifyTextField(widget.getComponentAt(5), "Dist. Name", "distribution-name-filter");
    }

    private void verifyButtonsLayout(Component component) {
        UiTestHelper.verifyButtonsLayout(component, true, "Apply", "Clear");
        var layout = (HorizontalLayout) component;
        assertEquals("filter-buttons", layout.getId().orElseThrow());
        assertEquals("filter-buttons", layout.getClassName());
    }

    private Rightsholder buildRightsholder() {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(ACCOUNT_NUMBER);
        rightsholder.setName("CANADIAN CERAMIC SOCIETY");
        return rightsholder;
    }
}
