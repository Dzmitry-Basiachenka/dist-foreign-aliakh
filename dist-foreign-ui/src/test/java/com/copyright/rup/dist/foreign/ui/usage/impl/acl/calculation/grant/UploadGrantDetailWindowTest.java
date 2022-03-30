package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;

/**
 * Verifies {@link UploadGrantDetailWindow}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/30/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, UploadGrantDetailWindow.class})
public class UploadGrantDetailWindowTest {

    private UploadGrantDetailWindow window;

    @Test
    public void testConstructor() {
        window = new UploadGrantDetailWindow();
        verifyWindow(window, "Upload Grant Details", 350, 170, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsValid() {
        window = new UploadGrantDetailWindow();
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, ComboBox.class).setValue("Grant Set");
        assertTrue(window.isValid());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyComboBox(verticalLayout.getComponent(0), "Grant Set", false);
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2), "Upload", "Close");
        Button loadButton = (Button) ((HorizontalLayout) (verticalLayout.getComponent(2))).getComponent(0);
        verifyLoadClickListener(loadButton, Arrays.asList(Whitebox.getInternalState(window, "comboBox"),
            Whitebox.getInternalState(window, "uploadField")));
    }
}
