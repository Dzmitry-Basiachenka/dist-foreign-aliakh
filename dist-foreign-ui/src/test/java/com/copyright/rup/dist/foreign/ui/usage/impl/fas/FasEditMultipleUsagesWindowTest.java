package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUpdateUsageWindow;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.Validator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;

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
@PrepareForTest(Windows.class)
public class FasEditMultipleUsagesWindowTest {

    private static final List<String> USAGE_IDS = List.of("232eb540-8dc8-41c6-bd6e-b367eaa16cfa");
    private static final Long WR_WRK_INST = 210001899L;
    private static final String REASON = "some reason";
    private static final String EMPTY_FIELD_ERROR = "Field value should be specified";
    private static final String FIELD_LENGTH_EXCEEDS_9_ERROR = "Field value should not exceed 9 digits";
    private static final String INVALID_NUMBER_ERROR = "Field value should contain numeric values only";

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
        verifyWindow(window, "Edit multiple FAS/FAS2 Usages", 280, 120, Unit.PIXELS);
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(2, contentLayout.getComponentCount());
        verifyTextField(contentLayout.getComponent(0), "Wr Wrk Inst", "wr-wrk-inst-field");
        verifyButtonsLayout(contentLayout.getComponent(1), "Save", "Close");
        verify(controller);
    }

    @Test
    public void testWrWrkInstValidation() {
        replay(controller);
        window = new FasEditMultipleUsagesWindow(controller, updateUsageWindow, USAGE_IDS);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        validateFieldAndVerifyErrorMessage(wrWrkInstField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "  ", binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "1", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, " 1 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "123456789", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "1234567890", binder, FIELD_LENGTH_EXCEEDS_9_ERROR, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "0.1", binder, INVALID_NUMBER_ERROR, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "not_a_number", binder, INVALID_NUMBER_ERROR, false);
        verify(controller);
    }

    @Test
    public void testSaveButtonClick() {
        mockStatic(Windows.class);
        Capture<IListener> actionDialogListenerCapture = newCapture();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to update selected usage(s)?"), eq("Yes"), eq("Cancel"),
            capture(actionDialogListenerCapture), anyObject(Validator.class));
        window = new FasEditMultipleUsagesWindow(controller, updateUsageWindow, USAGE_IDS);
        setTextFieldValue(window, "wrWrkInstField", WR_WRK_INST.toString());
        expectLastCall().once();
        controller.updateUsages(USAGE_IDS, WR_WRK_INST, REASON);
        expectLastCall().once();
        controller.refreshWidget();
        expectLastCall().once();
        updateUsageWindow.close();
        expectLastCall().once();
        replay(Windows.class, controller, updateUsageWindow);
        VerticalLayout contentLayout = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) contentLayout.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = saveButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(createMock(ClickEvent.class));
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(Windows.class, controller, updateUsageWindow);
    }
}
