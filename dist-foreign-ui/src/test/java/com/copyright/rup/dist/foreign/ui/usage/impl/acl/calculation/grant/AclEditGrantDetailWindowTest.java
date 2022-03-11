package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link AclEditGrantDetailWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/09/2022
 *
 * @author Anton Azarenka
 */
public class AclEditGrantDetailWindowTest {

    private IAclGrantDetailController controller;
    private ClickListener saveButtonClickListener;
    private AclEditGrantDetailWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclGrantDetailController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        window = new AclEditGrantDetailWindow(Collections.EMPTY_SET, controller, saveButtonClickListener);
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
            new AclEditGrantDetailWindow(Collections.singleton(grantDetailDto), controller, saveButtonClickListener);
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(4);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.setEnabled(true);
        saveButton.click();
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
            new AclEditGrantDetailWindow(Collections.singleton(grantDetailDto), controller, saveButtonClickListener);
        HorizontalLayout rhLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        TextField rhAccountNumberField = (TextField) rhLayout.getComponent(0);
        rhAccountNumberField.setValue("1000006530");
        Button verifyButton = (Button) rhLayout.getComponent(1);
        verifyButton.click();
        TextField rhNameField = (TextField) ((VerticalLayout) window.getContent()).getComponent(2);
        assertEquals("Hachette Books Group", rhNameField.getValue());
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyComboBox(((VerticalLayout) component).getComponent(0), "Grant Status", true, "GRANT", "DENY");
        verifyRhAccountNumberLayout(((VerticalLayout) component).getComponent(1));
        verifyTextField(((VerticalLayout) component).getComponent(2), "RH Name");
        verifyComboBox(((VerticalLayout) component).getComponent(3), "Eligible", true, "Y", "N");
        verifyButtonsLayout(((VerticalLayout) component).getComponent(4), "Save", "Discard", "Close");
    }

    private void verifyRhAccountNumberLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), "RH Account #");
        verifyButton(layout.getComponent(1), "Verify");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }
}
