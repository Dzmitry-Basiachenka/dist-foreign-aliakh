package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

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

    @Test
    public void testConstructor() {
        AclEditGrantDetailWindow window = new AclEditGrantDetailWindow();
        UiTestHelper.verifyWindow(window, "Edit Grant Detail", 450, 220, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyComboBox(((VerticalLayout) component).getComponent(0), "Grant Status", true, "GRANT", "DENY");
        verifyTextField(((VerticalLayout) component).getComponent(1), "RH Account #");
        verifyComboBox(((VerticalLayout) component).getComponent(2), "Eligible", true, "Y", "N");
        //TODO {aazarenka} add verify buttons layout later
    }
}
