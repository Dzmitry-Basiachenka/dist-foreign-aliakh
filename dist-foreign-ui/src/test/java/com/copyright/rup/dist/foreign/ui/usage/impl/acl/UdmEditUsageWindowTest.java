package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link UdmEditUsageWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/2021
 *
 * @author Ihar Suvorau
 */
public class UdmEditUsageWindowTest {

    private UdmEditUsageWindow window;

    @Before
    public void setUp() {
        IUdmUsageController controller = createMock(IUdmUsageController.class);
        expect(controller.getActionReasons()).andReturn(Collections.singletonList(
            new UdmActionReason("d7258aa1-801c-408f-8fff-685e5519a8db", "Metadata does not match Wr Wrk Inst"))).once();
        expect(controller.getDetailLicenseeClasses())
            .andReturn(Collections.singletonList(buildDetailLicenseeClass())).once();
        expect(controller.getIneligibleReasons()).andReturn(Collections.singletonList(
            new UdmIneligibleReason("ccbd22af-32bf-4162-8145-d49eae14c800", "User is not reporting a Mkt Rsch Rpt")))
            .once();
        replay(controller);
        window = new UdmEditUsageWindow(controller, new UdmUsageDto());
        verify(controller);
    }

    @Test
    public void testConstructor() {
        assertEquals("Edit UDM Usage", window.getCaption());
        assertEquals(650, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(700, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyPanel(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(39, verticalLayout.getComponentCount());
        verifyTextFieldLayout(verticalLayout.getComponent(0), "Detail ID", true);
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Period", true);
        verifyTextFieldLayout(verticalLayout.getComponent(2), "Usage Origin", true);
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Usage Detail ID", true);
        verifyComboBoxLayout(verticalLayout.getComponent(4), "Detail Status");
        verifyTextFieldLayout(verticalLayout.getComponent(5), "Assignee", true);
        verifyTextFieldLayout(verticalLayout.getComponent(6), "RH Account #", true);
        verifyTextFieldLayout(verticalLayout.getComponent(7), "RH Name", true);
        verifyTextFieldLayout(verticalLayout.getComponent(8), "Wr Wrk Inst", false);
        verifyTextFieldLayout(verticalLayout.getComponent(9), "Reported Title", false);
        verifyTextFieldLayout(verticalLayout.getComponent(10), "System Title", true);
        verifyTextFieldLayout(verticalLayout.getComponent(11), "Reported Standard Number", false);
        verifyTextFieldLayout(verticalLayout.getComponent(12), "Standard Number", true);
        verifyTextFieldLayout(verticalLayout.getComponent(13), "Reported Pub Type", false);
        verifyTextFieldLayout(verticalLayout.getComponent(14), "Publication Format", true);
        verifyTextFieldLayout(verticalLayout.getComponent(15), "Article", true);
        verifyTextFieldLayout(verticalLayout.getComponent(16), "Language", true);
        verifyComboBoxLayout(verticalLayout.getComponent(17), "Action Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(18), "Comment", false);
        verifyTextFieldLayout(verticalLayout.getComponent(19), "Research URL", false);
        verifyComboBoxLayout(verticalLayout.getComponent(20), "Detail Licensee Class");
        verifyCompanyIdLayout(verticalLayout.getComponent(21));
        verifyTextFieldLayout(verticalLayout.getComponent(22), "Company Name", true);
        verifyTextFieldLayout(verticalLayout.getComponent(23), "Survey Respondent", true);
        verifyTextFieldLayout(verticalLayout.getComponent(24), "IP Address", true);
        verifyTextFieldLayout(verticalLayout.getComponent(25), "Survey Country", true);
        verifyTextFieldLayout(verticalLayout.getComponent(26), "Channel", true);
        verifyTextFieldLayout(verticalLayout.getComponent(27), "Usage Date", true);
        verifyTextFieldLayout(verticalLayout.getComponent(28), "Survey Start Date", true);
        verifyTextFieldLayout(verticalLayout.getComponent(29), "Survey End Date", true);
        verifyTextFieldLayout(verticalLayout.getComponent(30), "Annual Multiplier", false);
        verifyTextFieldLayout(verticalLayout.getComponent(31), "Statistical Multiplier", false);
        verifyTextFieldLayout(verticalLayout.getComponent(32), "Reported TOU", true);
        verifyTextFieldLayout(verticalLayout.getComponent(33), "Quantity", false);
        verifyTextFieldLayout(verticalLayout.getComponent(34), "Annualized Copies", true);
        verifyComboBoxLayout(verticalLayout.getComponent(35), "Ineligible Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(36), "Load Date", true);
        verifyTextFieldLayout(verticalLayout.getComponent(37), "Updated By", true);
        verifyTextFieldLayout(verticalLayout.getComponent(38), "Updated Date", true);
    }

    private void verifyTextFieldLayout(Component component, String labelCaption, boolean isReadOnly) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), labelCaption);
        verifyTextField(layout.getComponent(1), isReadOnly);
    }

    private void verifyComboBoxLayout(Component component, String labelCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), labelCaption);
        verifyComboBoxField(layout.getComponent(1));
    }

    private void verifyCompanyIdLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "Company ID");
        verifyTextField(layout.getComponent(1), false);
        verifyButton(layout.getComponent(2), "Verify");
    }

    private void verifyLabel(Component component, String caption) {
        assertTrue(component instanceof Label);
        assertEquals(165, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(caption, ((Label) component).getValue());
    }

    private void verifyTextField(Component component, boolean isReadOnly) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertNull(component.getCaption());
        assertEquals(isReadOnly, ((TextField) component).isReadOnly());
    }

    private void verifyComboBoxField(Component component) {
        assertTrue(component instanceof ComboBox);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertNull(component.getCaption());
        assertFalse(((ComboBox) component).isReadOnly());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Discard");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private DetailLicenseeClass buildDetailLicenseeClass() {
        DetailLicenseeClass licenseeClass = new DetailLicenseeClass();
        licenseeClass.setId(26);
        licenseeClass.setDescription("Law Firms");
        return licenseeClass;
    }
}
