package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link UdmEditMultipleUsagesWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/10/21
 *
 * @author Anton Azarenka
 */
public class UdmEditMultipleUsagesWindowTest {

    private static final DetailLicenseeClass LICENSEE_CLASS = new DetailLicenseeClass();
    private static final UdmActionReason ACTION_REASON =
        new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
    private static final UdmIneligibleReason INELIGIBLE_REASON =
        new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey");
    private IUdmUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        expect(controller.getAllActionReasons()).andReturn(Collections.singletonList(ACTION_REASON)).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(Collections.singletonList(LICENSEE_CLASS)).once();
        expect(controller.getAllIneligibleReasons()).andReturn(Collections.singletonList(INELIGIBLE_REASON)).once();
    }

    @Test
    public void testConstructor() {
        replay(controller);
        UdmEditMultipleUsagesWindow window = new UdmEditMultipleUsagesWindow(controller);
        verify(controller);
        assertEquals("Edit multiple UDM Usages", window.getCaption());
        assertEquals(650, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(530, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyFields(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyFields(Component component) {
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(14, verticalLayout.getComponentCount());
        verifyComboBoxLayout(verticalLayout.getComponent(0), "Detail Status");
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Period");
        verifyComboBoxLayout(verticalLayout.getComponent(2), "Detail Licensee Class");
        verifyCompanyIdLayout(verticalLayout.getComponent(3));
        verifyTextFieldLayout(verticalLayout.getComponent(4), "Company Name");
        verifyTextFieldLayout(verticalLayout.getComponent(5), "Wr Wrk Inst");
        verifyTextFieldLayout(verticalLayout.getComponent(6), "Reported Standard Number");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "Reported Title");
        verifyTextFieldLayout(verticalLayout.getComponent(8), "Annual Multiplier");
        verifyTextFieldLayout(verticalLayout.getComponent(9), "Statistical Multiplier");
        verifyTextFieldLayout(verticalLayout.getComponent(10), "Quantity");
        verifyComboBoxLayout(verticalLayout.getComponent(11), "Action Reason");
        verifyComboBoxLayout(verticalLayout.getComponent(12), "Ineligible Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(13), "Comment");
    }

    private void verifyComboBoxLayout(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        verifyComboBoxField(layout.getComponent(1), caption);
    }

    private void verifyTextFieldLayout(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        verifyTextField(layout.getComponent(1), caption);
    }

    private void verifyLabel(Component component, String caption) {
        assertTrue(component instanceof Label);
        assertEquals(165, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(caption, ((Label) component).getValue());
    }

    private void verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
    }

    private void verifyComboBoxField(Component component, String caption) {
        assertTrue(component instanceof ComboBox);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
        assertFalse(((ComboBox) component).isReadOnly());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private void verifyCompanyIdLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "Company ID");
        verifyTextField(layout.getComponent(1), "Company ID");
        verifyButton(layout.getComponent(2), "Verify");
    }
}
