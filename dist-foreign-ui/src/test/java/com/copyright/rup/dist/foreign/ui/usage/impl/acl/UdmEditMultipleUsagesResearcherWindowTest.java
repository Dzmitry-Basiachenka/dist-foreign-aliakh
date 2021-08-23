package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmEditMultipleUsagesResearcherWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/11/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmEditMultipleUsagesResearcherWindowTest {

    private static final UdmActionReason ACTION_REASON =
        new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
    private static final String VALID_INTEGER = "25";
    private static final String VALID_DECIMAL = "0.1";
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long NEW_WR_WRK_INST = 1234567L;
    private static final String COMMENT = "Should be reviewed by Specialist";
    private static final String NEW_COMMENT = "Should be reviewed by Manager";
    private static final String REASON = "Should be improverd";

    private UdmEditMultipleUsagesResearcherWindow window;
    private IUdmUsageController controller;
    private Binder<UdmUsageDto> binder;
    private Set<UdmUsageDto> udmUsages;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        udmUsages = Collections.EMPTY_SET;
        expect(controller.getAllActionReasons()).andReturn(Collections.singletonList(ACTION_REASON)).once();
    }

    @Test
    public void testConstructor() {
        initEditWindow();
        assertEquals("Edit multiple UDM Usages", window.getCaption());
        assertEquals(650, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(215, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testWrWrkInstValidation() {
        initEditWindow();
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        verifyTextFieldValidationMessage(wrWrkInstField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, "1234567890",
            "Field value should not exceed 9 digits", false);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, SPACES_STRING, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testCommentsLengthValidation() {
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "commentField"), 4000);
    }

    @Test
    public void testDiscardButtonClickListener() {
        binder = createMock(Binder.class);
        binder.readBean(null);
        expectLastCall().once();
        replay(controller, binder, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesResearcherWindow(controller, udmUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        HorizontalLayout buttonsLayout = getButtonsLayout();
        ((Button) buttonsLayout.getComponent(1)).click();
        verify(controller, binder, ForeignSecurityUtils.class);
    }

    @Test
    public void testSaveButtonClickListener() throws Exception {
        udmUsages = Collections.singleton(buildUdmUsageDto());
        binder = createMock(Binder.class);
        binder.writeBean(buildExpectedUdmUsageDto());
        expectLastCall();
        controller.updateUsages(udmUsages, true);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesResearcherWindow(controller, udmUsages, saveButtonClickListener);
        UdmUsageDto udmUsageDto = buildExpectedUdmUsageDto();
        Whitebox.setInternalState(window, udmUsageDto);
        updateFields();
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        udmUsages.forEach(this::verifyUpdatedUdmUsages);
    }

    @SuppressWarnings("unchecked")
    private void updateFields() {
        ComboBox<UsageStatusEnum> statusEnumComboBox = (ComboBox<UsageStatusEnum>) getComponent(0).getComponent(1);
        statusEnumComboBox.setValue(UsageStatusEnum.NEW);
        TextField wrWrkInstField = (TextField) getComponent(1).getComponent(1);
        wrWrkInstField.setValue("1234567");
        ComboBox<UdmActionReason> actionReasonComboBox = (ComboBox<UdmActionReason>) getComponent(2).getComponent(1);
        actionReasonComboBox.setValue(buildActionReason());
        TextField commentField = (TextField) getComponent(3).getComponent(1);
        commentField.setValue(NEW_COMMENT);
    }

    private HorizontalLayout getComponent(int number) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        return (HorizontalLayout) verticalLayout.getComponent(number);
    }

    private void verifyUpdatedUdmUsages(UdmUsageDto actualUdmUsageDto) {
        assertEquals(UsageStatusEnum.NEW, actualUdmUsageDto.getStatus());
        assertEquals(NEW_WR_WRK_INST, actualUdmUsageDto.getWrWrkInst());
        assertEquals(buildActionReason(), actualUdmUsageDto.getActionReason());
        assertEquals(NEW_COMMENT, actualUdmUsageDto.getComment());
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(4);
    }

    private void verifyLengthValidation(TextField textField, int maxSize) {
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
        verifyTextFieldValidationMessage(textField, StringUtils.EMPTY, StringUtils.EMPTY, true);
    }

    private String buildStringWithExpectedLength(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append('a');
        }
        return result.toString();
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, String message, boolean isValid) {
        field.setValue(value);
        verifyBinderStatusAndValidationMessage(message, isValid);
    }

    private void verifyBinderStatusAndValidationMessage(String message, boolean isValid) {
        BinderValidationStatus<UdmUsageDto> binderStatus = binder.validate();
        assertEquals(isValid, binderStatus.isOk());
        if (!isValid) {
            List<ValidationResult> errors = binderStatus.getValidationErrors();
            List<String> errorMessages =
                errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
            assertTrue(errorMessages.contains(message));
        }
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyComboBoxLayout(verticalLayout.getComponent(0), "Detail Status");
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Wr Wrk Inst");
        verifyComboBoxLayout(verticalLayout.getComponent(2), "Action Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Comment");
        verifyButtonsLayout(verticalLayout.getComponent(4));
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
        assertEquals(110, component.getWidth(), 0);
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
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Discard");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void initEditWindow() {
        replay(controller, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesResearcherWindow(controller, udmUsages, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, "binder");
        verify(controller, ForeignSecurityUtils.class);
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private UdmUsageDto buildUdmUsageDto() {
        UdmUsageDto udmUsage = new UdmUsageDto();
        udmUsage.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.setAssignee(ASSIGNEE);
        udmUsage.setWrWrkInst(WR_WRK_INST);
        udmUsage.setActionReason(ACTION_REASON);
        udmUsage.setComment(COMMENT);
        return udmUsage;
    }

    private UdmUsageDto buildExpectedUdmUsageDto() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setStatus(UsageStatusEnum.NEW);
        udmUsageDto.setWrWrkInst(NEW_WR_WRK_INST);
        udmUsageDto.setComment(NEW_COMMENT);
        udmUsageDto.setActionReason(buildActionReason());
        return udmUsageDto;
    }

    private UdmActionReason buildActionReason() {
        UdmActionReason actionReason = new UdmActionReason();
        actionReason.setId("d658136a-1e6b-4c45-9d6e-d93ccedd36f7");
        actionReason.setReason(REASON);
        return actionReason;
    }
}