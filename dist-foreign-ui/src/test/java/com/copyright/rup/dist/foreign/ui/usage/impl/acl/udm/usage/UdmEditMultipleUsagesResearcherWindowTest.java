package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmUsageAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long NEW_WR_WRK_INST = 1234567L;
    private static final String COMMENT = "Should be reviewed by Specialist";
    private static final String NEW_COMMENT = "Should be reviewed by Manager";
    private static final String NEW_REASON = "Should be improved";
    private static final String DETAIL_STATUS_VALIDATION_MESSAGE = "Please set the status to NEW in order to save";
    private static final String STATUS_COMBOBOX = "statusComboBox";

    private UdmEditMultipleUsagesResearcherWindow window;
    private IUdmUsageController controller;
    private Binder<UdmUsageDto> binder;
    private Set<UdmUsageDto> udmUsages;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        udmUsages = Set.of();
        expect(controller.getAllActionReasons()).andReturn(List.of(ACTION_REASON)).once();
    }

    @Test
    public void testConstructor() {
        initEditWindow();
        verifyWindow(window, "Edit multiple UDM Usages", 650, 215, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testUdmUsagesStatusValidation() {
        initEditWindow();
        ComboBox<UsageStatusEnum> detailsStatus = Whitebox.getInternalState(window, STATUS_COMBOBOX);
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        detailsStatus.setValue(UsageStatusEnum.OPS_REVIEW);
        verifyBinderStatusAndValidationMessage(DETAIL_STATUS_VALIDATION_MESSAGE, true);
        wrWrkInstField.setValue(Objects.toString(123L));
        verifyBinderStatusAndValidationMessage(DETAIL_STATUS_VALIDATION_MESSAGE, false);
        detailsStatus.setValue(UsageStatusEnum.NEW);
        verifyBinderStatusAndValidationMessage(DETAIL_STATUS_VALIDATION_MESSAGE, true);
        detailsStatus.setValue(UsageStatusEnum.OPS_REVIEW);
        wrWrkInstField.setValue(StringUtils.EMPTY);
        verifyBinderStatusAndValidationMessage(DETAIL_STATUS_VALIDATION_MESSAGE, true);
    }

    @Test
    public void testWrWrkInstValidation() {
        initEditWindow();
        ComboBox<UsageStatusEnum> detailsStatus = Whitebox.getInternalState(window, STATUS_COMBOBOX);
        detailsStatus.setValue(UsageStatusEnum.NEW);
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
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Detail Status", UsageStatusEnum.RH_FOUND.name(),
            UsageStatusEnum.INELIGIBLE.name());
        fieldToValuesMap.putFieldWithValues("Wr Wrk Inst", "122825347", "1234567");
        fieldToValuesMap.putFieldWithValues("Action Reason", ACTION_REASON.getReason(), NEW_REASON);
        fieldToValuesMap.putFieldWithValues("Comment", COMMENT, NEW_COMMENT);
        UdmUsageDto expectedUdmUsages = buildExpectedUdmUsageDto();
        Map<UdmUsageDto, UdmUsageAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap =
            ImmutableMap.of(expectedUdmUsages, fieldToValuesMap);
        udmUsages = Set.of(buildUdmUsageDto());
        binder = createMock(Binder.class);
        binder.writeBean(expectedUdmUsages);
        expectLastCall().once();
        controller.updateUsages(UdmUsageAuditFieldToValuesMap.getDtoToAuditReasonsMap(udmUsageDtoToFieldValuesMap),
            true, StringUtils.EMPTY);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesResearcherWindow(controller, udmUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, expectedUdmUsages);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        Map<UdmUsageDto, UdmUsageAuditFieldToValuesMap> map =
            Whitebox.getInternalState(window, "udmUsageDtoToFieldValuesMap");
        map.forEach((usage, audit) -> verifyUpdatedUdmUsage(expectedUdmUsages, usage));
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
    }

    @Test
    //TODO{aazarenka} rewrite test
    public void testUpdateStatusField() throws ValidationException {
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Detail Status", UsageStatusEnum.RH_FOUND.name(),
            UsageStatusEnum.INELIGIBLE.name());
        UdmUsageDto expectedUdmUsage = buildUdmUsageDto();
        expectedUdmUsage.setStatus(UsageStatusEnum.INELIGIBLE);
        Map<UdmUsageDto, UdmUsageAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap =
            ImmutableMap.of(expectedUdmUsage, fieldToValuesMap);
        udmUsages = Set.of(buildUdmUsageDto());
        binder = createMock(Binder.class);
        binder.writeBean(expectedUdmUsage);
        expectLastCall().once();
        controller.updateUsages(UdmUsageAuditFieldToValuesMap.getDtoToAuditReasonsMap(udmUsageDtoToFieldValuesMap),
            true, StringUtils.EMPTY);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesResearcherWindow(controller, udmUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, expectedUdmUsage);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        Map<UdmUsageDto, UdmUsageAuditFieldToValuesMap> map =
            Whitebox.getInternalState(window, "udmUsageDtoToFieldValuesMap");
        map.forEach((usage, audit) -> verifyUpdatedUdmUsage(expectedUdmUsage, usage));
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
    }

    private void verifyUpdatedUdmUsage(UdmUsageDto expectedUdmUsageDto, UdmUsageDto actualUdmUsageDto) {
        assertEquals(expectedUdmUsageDto.getStatus(), actualUdmUsageDto.getStatus());
        assertEquals(expectedUdmUsageDto.getWrWrkInst(), actualUdmUsageDto.getWrWrkInst());
        assertEquals(expectedUdmUsageDto.getActionReason(), actualUdmUsageDto.getActionReason());
        assertEquals(expectedUdmUsageDto.getComment(), actualUdmUsageDto.getComment());
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
        return StringUtils.repeat('a', length);
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
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyComboBoxLayout(verticalLayout.getComponent(0), "Detail Status", false,
            List.of(UsageStatusEnum.NEW, UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW));
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Wr Wrk Inst");
        verifyComboBoxLayout(verticalLayout.getComponent(2), "Action Reason", true, List.of(ACTION_REASON));
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Comment");
        verifyButtonsLayout(verticalLayout.getComponent(4), "Save", "Discard", "Close");
    }

    private <T> void verifyComboBoxLayout(Component component, String caption, boolean emptySelectionAllowed,
                                          Collection<T> expectedItems) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 110);
        verifyComboBox(layout.getComponent(1), caption, emptySelectionAllowed, expectedItems);
    }

    private void verifyTextFieldLayout(Component component, String caption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 110);
        verifyTextField(layout.getComponent(1), caption);
    }

    private void initEditWindow() {
        replay(controller, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesResearcherWindow(controller, udmUsages, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, "binder");
        verify(controller, ForeignSecurityUtils.class);
    }

    private UdmUsageDto buildUdmUsageDto() {
        UdmUsageDto udmUsage = new UdmUsageDto();
        udmUsage.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.setWrWrkInst(WR_WRK_INST);
        udmUsage.setActionReason(ACTION_REASON);
        udmUsage.setComment(COMMENT);
        return udmUsage;
    }

    private UdmUsageDto buildExpectedUdmUsageDto() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsageDto.setStatus(UsageStatusEnum.INELIGIBLE);
        udmUsageDto.setWrWrkInst(NEW_WR_WRK_INST);
        udmUsageDto.setComment(NEW_COMMENT);
        udmUsageDto.setActionReason(buildActionReason(NEW_REASON));
        return udmUsageDto;
    }

    private UdmActionReason buildActionReason(String reason) {
        UdmActionReason actionReason = new UdmActionReason();
        actionReason.setId("d658136a-1e6b-4c45-9d6e-d93ccedd36f7");
        actionReason.setReason(reason);
        return actionReason;
    }
}
