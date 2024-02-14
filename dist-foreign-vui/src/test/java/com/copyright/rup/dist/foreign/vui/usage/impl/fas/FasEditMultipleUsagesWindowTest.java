package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.setLongFieldValue;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLongField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUpdateUsageWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies {@link FasEditMultipleUsagesWindow}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/13/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ConfirmWindows.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasEditMultipleUsagesWindowTest {

    private static final List<String> USAGE_IDS = List.of("232eb540-8dc8-41c6-bd6e-b367eaa16cfa");
    private static final Long WR_WRK_INST = 210001899L;
    private static final String REASON = "some reason";
    private static final String INVALID_NUMBER_LENGTH_MESSAGE = "Field value should not exceed 9 digits";

    private IFasUsageController controller;
    private IFasUpdateUsageWindow updateUsageWindow;
    private FasEditMultipleUsagesWindow window;

    @Before
    public void setUp() {
        controller = createMock(IFasUsageController.class);
        updateUsageWindow = createMock(IFasUpdateUsageWindow.class);
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new FasEditMultipleUsagesWindow(controller, updateUsageWindow, USAGE_IDS);
        verifyWindow(window, "Edit multiple FAS/FAS2 Usages", "400px", "215px", Unit.PIXELS, false);
        VerticalLayout contentLayout = (VerticalLayout) getDialogContent(window);
        assertEquals(1, contentLayout.getComponentCount());
        verifyLongField(contentLayout.getComponentAt(0), "Wr Wrk Inst", "100%", "wr-wrk-inst-field");
        verifyButtonsLayout(getFooterLayout(window), true, "Save", "Close");
        verify(controller);
    }

    @Test
    public void testWrWrkInstValidation() {
        replay(controller);
        window = new FasEditMultipleUsagesWindow(controller, updateUsageWindow, USAGE_IDS);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        LongField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        assertFieldValidationMessage(wrWrkInstField, 0L, binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        assertFieldValidationMessage(wrWrkInstField, 1L, binder, null, true);
        assertFieldValidationMessage(wrWrkInstField, 123456789L, binder, null, true);
        assertFieldValidationMessage(wrWrkInstField, 1234567890L, binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testSaveButtonClickSuccess() {
        mockStatic(ConfirmWindows.class);
        Capture<Consumer<String>> actionCapture = newCapture();
        ConfirmWindows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to update selected usage(s)?"), eq("Yes"), eq("Cancel"),
            capture(actionCapture), anyObject(Validator.class));
        expectLastCall().once();
        window = new FasEditMultipleUsagesWindow(controller, updateUsageWindow, USAGE_IDS);
        setLongFieldValue(window, "wrWrkInstField", WR_WRK_INST);
        controller.updateUsages(USAGE_IDS, WR_WRK_INST, REASON);
        expectLastCall().once();
        controller.refreshWidget();
        expectLastCall().once();
        updateUsageWindow.close();
        expectLastCall().once();
        replay(ConfirmWindows.class, controller, updateUsageWindow);
        var buttonsLayout = getFooterLayout(window);
        var saveButton = (Button) buttonsLayout.getComponentAt(0);
        saveButton.click();
        actionCapture.getValue().accept(REASON);
        verify(ConfirmWindows.class, controller, updateUsageWindow);
    }

    @Test
    public void testSaveButtonClickError() {
        mockStatic(Windows.class);
        Windows.showValidationErrorWindow();
        expectLastCall().once();
        window = new FasEditMultipleUsagesWindow(controller, updateUsageWindow, USAGE_IDS);
        replay(Windows.class, controller, updateUsageWindow);
        var buttonsLayout = getFooterLayout(window);
        var saveButton = (Button) buttonsLayout.getComponentAt(0);
        saveButton.click();
        verify(Windows.class, controller, updateUsageWindow);
    }
}
