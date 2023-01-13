package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;

import static org.easymock.EasyMock.anyObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterController;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclScenariosFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenariosFilterWidgetTest {

    private static final String LICENSE_TYPE = "ACL";
    private AclScenariosFilterWidget widget;
    private AclScenariosAppliedFilterWidget appliedFilterWidget;

    @Before
    public void setUp() {
        IAclScenariosFilterController controller = createMock(IAclScenariosFilterController.class);
        appliedFilterWidget = createMock(AclScenariosAppliedFilterWidget.class);
        widget = new AclScenariosFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyLabel(widget.getComponent(2), "Applied Filters:", ContentMode.TEXT, -1.0f);
        assertThat(widget.getComponent(3), instanceOf(AclScenariosAppliedFilterWidget.class));
    }

    @Test
    public void testApplyFilter() {
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall();
        replay(appliedFilterWidget);
        widget.init();
        widget.clearFilter();
        Whitebox.setInternalState(widget, appliedFilterWidget);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertTrue(widget.getFilter().isEmpty());
        widget.getFilter().setLicenseTypes(Set.of(LICENSE_TYPE));
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().isEmpty());
        verify(appliedFilterWidget);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall().times(2);
        replay(appliedFilterWidget);
        widget.init();
        Whitebox.setInternalState(widget, appliedFilterWidget);
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setLicenseTypes(Set.of(LICENSE_TYPE));
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertFalse(applyButton.isEnabled());
        verify(appliedFilterWidget);
    }

    private void verifyFiltersLayout(Component layout) {
        assertThat(layout, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Periods");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "License Types");
        verifyComboBox(verticalLayout.getComponent(3), "Editable", true,
            List.of(FilterOperatorEnum.Y, FilterOperatorEnum.N));
        verifyComboBox(verticalLayout.getComponent(4), "Status", true,
            ScenarioStatusEnum.IN_PROGRESS, ScenarioStatusEnum.SUBMITTED, ScenarioStatusEnum.APPROVED,
            ScenarioStatusEnum.ARCHIVED);
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
