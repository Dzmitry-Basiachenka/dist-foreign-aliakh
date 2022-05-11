package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;

/**
 * Verifies {@link AclFundPoolFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolFilterWidgetTest {

    private static final String LICENSE_TYPE = "ACL";
    private AclFundPoolFilterWidget widget;

    @Before
    public void setUp() {
        IAclFundPoolFilterController controller = createMock(IAclFundPoolFilterController.class);
        widget = new AclFundPoolFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        assertEquals(new MarginInfo(true), widget.getMargin());
    }

    @Test
    public void testApplyFilter() {
        widget.init();
        widget.clearFilter();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        widget.getFilter().setLicenseType(LICENSE_TYPE);
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
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
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setLicenseType(LICENSE_TYPE);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertFalse(applyButton.isEnabled());
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(7, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Fund Pool Name");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Periods");
        verifyItemsFilterWidget(verticalLayout.getComponent(3), "Aggregate Licensee Classes");
        verifyItemsFilterWidget(verticalLayout.getComponent(4), "Detail Licensee Classes");
        verifyComboBox(verticalLayout.getComponent(5), "License Types", true,
            Arrays.asList("ACL", "VGW", "JACDCL", "MACL"));
        verifyComboBox(verticalLayout.getComponent(6), "Fund Pool Type", true,
            Arrays.asList("Print", "Digital"));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
