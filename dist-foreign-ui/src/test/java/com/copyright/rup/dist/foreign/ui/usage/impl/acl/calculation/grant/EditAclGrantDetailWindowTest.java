package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link EditAclGrantDetailWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/09/2022
 *
 * @author Anton Azarenka
 */
public class EditAclGrantDetailWindowTest {

    private IAclGrantDetailController controller;
    private ClickListener saveButtonClickListener;
    private EditAclGrantDetailWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclGrantDetailController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        window = new EditAclGrantDetailWindow(Collections.EMPTY_SET, controller, saveButtonClickListener);
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "Edit Grants", 350, 270, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testSaveButtonClickListener() {
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId("884c8968-28fa-48ef-b13e-01571a8902fa");
        grantDetailDto.setEditable(true);
        controller.updateAclGrants(Collections.singleton(grantDetailDto), false);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, saveButtonClickListener);
        window =
            new EditAclGrantDetailWindow(Collections.singleton(grantDetailDto), controller, saveButtonClickListener);
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(4);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, saveButtonClickListener);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDiscardButtonClickListener() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        ComboBox<String> grantStatusField = (ComboBox<String>) content.getComponent(0);
        grantStatusField.setValue("GRANT");
        HorizontalLayout rhLayout = (HorizontalLayout) content.getComponent(1);
        TextField rhAccountNumberField = (TextField) rhLayout.getComponent(0);
        rhAccountNumberField.setValue("123456789");
        TextField rhName = (TextField) content.getComponent(2);
        rhName.setValue("Rightsholder name");
        ComboBox<String> eligibleFlagField = (ComboBox<String>) content.getComponent(3);
        eligibleFlagField.setValue("Y");
        replay(controller, saveButtonClickListener);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(4);
        Button discardButton = (Button) buttonsLayout.getComponent(1);
        discardButton.click();
        assertNull(grantStatusField.getValue());
        assertTrue(rhAccountNumberField.getValue().isEmpty());
        assertTrue(rhName.getValue().isEmpty());
        assertNull(eligibleFlagField.getValue());
        verify(controller, saveButtonClickListener);
    }

    @Test
    public void testVerifyButtonClickListener() {
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId("884c8968-28fa-48ef-b13e-01571a8902fa");
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(1000006530L);
        rightsholder.setName("Hachette Books Group");
        expect(controller.getRightsholder(1000006530L)).andReturn(rightsholder).once();
        replay(controller);
        window =
            new EditAclGrantDetailWindow(Collections.singleton(grantDetailDto), controller, saveButtonClickListener);
        HorizontalLayout rhLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        TextField rhAccountNumberField = (TextField) rhLayout.getComponent(0);
        Button verifyButton = (Button) rhLayout.getComponent(1);
        rhAccountNumberField.setValue("");
        verifyButton.click();
        rhAccountNumberField.setValue("1000006530");
        verifyButton.click();
        TextField rhNameField = (TextField) ((VerticalLayout) window.getContent()).getComponent(2);
        assertEquals("Hachette Books Group", rhNameField.getValue());
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyComboBox(((VerticalLayout) component).getComponent(0), "Grant Status", true, "GRANT", "DENY");
        verifyRhAccountNumberLayout(((VerticalLayout) component).getComponent(1));
        verifyTextField(((VerticalLayout) component).getComponent(2), "RH Name");
        verifyComboBox(((VerticalLayout) component).getComponent(3), "Eligible", true, "Y", "N");
        verifyButtonsLayout(((VerticalLayout) component).getComponent(4), "Save", "Discard", "Close");
    }

    private void verifyRhAccountNumberLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), "RH Account #");
        verifyButton(layout.getComponent(1), "Verify");
    }

    private void verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        assertEquals(caption, component.getCaption());
    }
}
