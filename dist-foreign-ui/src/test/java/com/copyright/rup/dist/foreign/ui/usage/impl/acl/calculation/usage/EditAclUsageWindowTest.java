package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
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
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link EditAclUsageWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Dzmitry Basiachenka
 */
public class EditAclUsageWindowTest {

    private static final String ACL_USAGE_UID = "e6c08b81-83be-4442-a7ce-a871df372de8";
    private static final String ACL_USAGE_BATCH_UID = "b7724aad-4719-4279-ac2d-39313d254b00";
    private static final String ACL_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0025";
    private static final String SYSTEM_TITLE = "Brain surgery";
    private static final String SURVEY_COUNTRY = "United States";
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long NEW_WR_WRK_INST = 1234567L;
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("1.00000");
    private static final BigDecimal NEW_CONTENT_UNIT_PRICE = new BigDecimal("0.10000");
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal("10.00000");
    private static final BigDecimal NEW_ANNUALIZED_COPIES = new BigDecimal("5.00000");
    private static final Integer DET_LC_ID = 26;
    private static final String DET_LC_NAME = "Law Firms";
    private static final DetailLicenseeClass LICENSEE_CLASS = new DetailLicenseeClass(DET_LC_ID, DET_LC_NAME);
    private static final DetailLicenseeClass NEW_LICENSEE_CLASS = new DetailLicenseeClass(2, "Textiles, Apparel, etc.");
    private static final PublicationType PUBLICATION_TYPE;
    private static final PublicationType NEW_PUBLICATION_TYPE;
    private static final String VALID_INTEGER = "25";
    private static final String VALID_DECIMAL = "0.1";
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String POSITIVE_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private static final String BINDER_NAME = "binder";
    private static final String SELECTED_USAGES = "selectedUsages";

    static {
        PUBLICATION_TYPE = new PublicationType();
        PUBLICATION_TYPE.setName("BK");
        PUBLICATION_TYPE.setDescription("Book");

        NEW_PUBLICATION_TYPE = new PublicationType();
        NEW_PUBLICATION_TYPE.setName("BK2");
        NEW_PUBLICATION_TYPE.setDescription("Book series");
    }

    private EditAclUsageWindow window;
    private Binder<AclUsageDto> binder;
    private IAclUsageController controller;
    private Set<AclUsageDto> aclUsages;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        controller = createMock(IAclUsageController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        expect(controller.getPublicationTypes()).andReturn(List.of(PUBLICATION_TYPE)).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(List.of(LICENSEE_CLASS)).once();
    }

    @Test
    public void testConstructor() {
        initEditWindow();
        verifyWindow(window, "Edit ACL Usages", 400, 300, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testPeriodFieldValidation() {
        initEditWindow();
        TextField periodField = Whitebox.getInternalState(window, "periodField");
        String yearValidationMessage = "Year value should be in range from 1950 to 2099";
        String monthValidationMessage = "Month value should be 06 or 12";
        String lengthValidationMessage = "Period value should contain 6 digits";
        validateFieldAndVerifyErrorMessage(periodField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodField, "194912", binder, yearValidationMessage, false);
        validateFieldAndVerifyErrorMessage(periodField, "210006", binder, yearValidationMessage, false);
        validateFieldAndVerifyErrorMessage(periodField, "202101", binder, monthValidationMessage, false);
        validateFieldAndVerifyErrorMessage(periodField, "202105", binder, monthValidationMessage, false);
        validateFieldAndVerifyErrorMessage(periodField, "202111", binder, monthValidationMessage, false);
        validateFieldAndVerifyErrorMessage(periodField, "202106", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodField, "202112", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodField, "2021012", binder, lengthValidationMessage, false);
        validateFieldAndVerifyErrorMessage(periodField, "123", binder, lengthValidationMessage, false);
    }

    @Test
    public void testWrWrkInstValidation() {
        initEditWindow();
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        validateFieldAndVerifyErrorMessage(wrWrkInstField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, VALID_INTEGER, binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, INTEGER_WITH_SPACES_STRING, binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "1234567890", binder,
            "Field value should not exceed 9 digits", false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, SPACES_STRING, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testAnnualizedCopiesValidation() {
        initEditWindow();
        TextField annualizedCopiesField = Whitebox.getInternalState(window, "annualizedCopiesField");
        String scaleValidationMessage = "Field value should not exceed 5 digits after the decimal point";
        verifyCommonNumberValidations(annualizedCopiesField, POSITIVE_AND_LENGTH_ERROR_MESSAGE);
        validateFieldAndVerifyErrorMessage(
            annualizedCopiesField, "0", binder, POSITIVE_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, " .000011 ", binder, scaleValidationMessage, false);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, " 0.000011 ", binder, scaleValidationMessage, false);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.999999", binder, scaleValidationMessage, false);
        validateFieldAndVerifyErrorMessage(
            annualizedCopiesField, "12345678901", binder, POSITIVE_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            annualizedCopiesField, "9999999998.999999", binder, scaleValidationMessage, false);
        validateFieldAndVerifyErrorMessage(
            annualizedCopiesField, "9999999999.999999", binder, POSITIVE_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.00001", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, " 0.00001 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.9", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.99", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.999", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.9999", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "0.99999", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, " 0.99999 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, " 1234567890 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "9999999999", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "123456789.12345", binder, null, true);
        validateFieldAndVerifyErrorMessage(annualizedCopiesField, "9999999999.99999", binder, null, true);
    }

    @Test
    public void testContentUnitPriceValidation() {
        initEditWindow();
        TextField contentUnitPriceField = Whitebox.getInternalState(window, "contentUnitPriceField");
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, "0", binder, null, true);
        verifyCommonNumberValidations(contentUnitPriceField, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE);
        validateFieldAndVerifyErrorMessage(
            contentUnitPriceField, ".123", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            contentUnitPriceField, "0.12345678901", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            contentUnitPriceField, "12345678901", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            contentUnitPriceField, "12345678901.12", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, "0.1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, "123", binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, " 123.12 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, VALID_INTEGER, binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, "1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, "1234567890.1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(contentUnitPriceField, " 1234567890.1234567890 ", binder, null, true);
    }

    @Test
    public void testDiscardButtonClickListener() {
        binder = createMock(Binder.class);
        binder.readBean(null);
        expectLastCall().once();
        replay(controller, binder);
        window = new EditAclUsageWindow(controller, aclUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        HorizontalLayout buttonsLayout = getButtonsLayout();
        ((Button) buttonsLayout.getComponent(1)).click();
        verify(controller, binder);
    }

    @Test
    public void testSaveButtonClickListener() throws Exception {
        aclUsages = Set.of(buildAclUsageDto());
        AclUsageDto expectedAclUsageDto = buildExpectedAclUsageDto();
        binder = createMock(Binder.class);
        binder.writeBean(buildExpectedAclUsageDto());
        expectLastCall();
        controller.updateUsages(aclUsages);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener);
        window = new EditAclUsageWindow(controller, aclUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, "bindedUsageDto", expectedAclUsageDto);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, binder, saveButtonClickListener);
        Set<AclUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        usages.forEach(usage -> verifyUpdatedAclUsages(buildExpectedAclUsageDto(), usage));
    }

    private void initEditWindow() {
        replay(controller);
        window = new EditAclUsageWindow(controller, aclUsages, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        binder.readBean(null);
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(8, verticalLayout.getComponentCount());
        verifyTextFieldLayout(verticalLayout.getComponent(0), "Period (YYYYMM)", "acl-usage-edit-period-field");
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Wr Wrk Inst", "acl-usage-edit-wr-wrk-inst-field");
        verifyComboBoxLayout(verticalLayout.getComponent(2), "Pub Type", true, List.of(PUBLICATION_TYPE));
        verifyComboBoxLayout(verticalLayout.getComponent(3), "Type of Use", true, List.of("PRINT","DIGITAL"));
        verifyComboBoxLayout(verticalLayout.getComponent(4), "Detail Licensee Class", true, List.of(LICENSEE_CLASS));
        verifyTextFieldLayout(verticalLayout.getComponent(5), "Annualized Copies",
            "acl-usage-edit-annualized-copies-field");
        verifyTextFieldLayout(verticalLayout.getComponent(6), "Content Unit Price",
            "acl-usage-edit-content-unit-price-field");
        verifyButtonsLayout(verticalLayout.getComponent(7), "Save", "Discard", "Close");
    }

    private void verifyTextFieldLayout(Component component, String caption, String styleName) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 130);
        verifyTextField(layout.getComponent(1), caption, styleName);
    }

    private <T> void verifyComboBoxLayout(Component component, String caption, boolean emptySelectionAllowed,
                                          Collection<T> expectedItems) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 130);
        verifyComboBox(layout.getComponent(1), caption, emptySelectionAllowed, expectedItems);
    }

    private void verifyCommonNumberValidations(TextField textField, String numberValidationMessage) {
        validateFieldAndVerifyErrorMessage(textField, INVALID_NUMBER, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(textField, "-1", binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, INTEGER_WITH_SPACES_STRING, binder, null, true);
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
    }

    private AclUsageDto buildAclUsageDto() {
        AclUsageDto aclUsageDto = new AclUsageDto();
        aclUsageDto.setId(ACL_USAGE_UID);
        aclUsageDto.setUsageBatchId(ACL_USAGE_BATCH_UID);
        aclUsageDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        aclUsageDto.setChannel(UdmChannelEnum.CCC);
        aclUsageDto.setPeriod(202306);
        aclUsageDto.setOriginalDetailId(ACL_USAGE_ORIGINAL_DETAIL_UID);
        aclUsageDto.setWrWrkInst(WR_WRK_INST);
        aclUsageDto.setSystemTitle(SYSTEM_TITLE);
        aclUsageDto.setDetailLicenseeClass(LICENSEE_CLASS);
        aclUsageDto.setAggregateLicenseeClassId(26);
        aclUsageDto.setAggregateLicenseeClassName("Law Firms");
        aclUsageDto.setSurveyCountry(SURVEY_COUNTRY);
        aclUsageDto.setPublicationType(PUBLICATION_TYPE);
        aclUsageDto.setContentUnitPrice(CONTENT_UNIT_PRICE);
        aclUsageDto.setTypeOfUse("PRINT");
        aclUsageDto.setAnnualizedCopies(ANNUALIZED_COPIES);
        return aclUsageDto;
    }

    private AclUsageDto buildExpectedAclUsageDto() {
        AclUsageDto aclUsageDto = new AclUsageDto();
        aclUsageDto.setId(ACL_USAGE_UID);
        aclUsageDto.setUsageBatchId(ACL_USAGE_BATCH_UID);
        aclUsageDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        aclUsageDto.setChannel(UdmChannelEnum.CCC);
        aclUsageDto.setPeriod(202212);
        aclUsageDto.setOriginalDetailId(ACL_USAGE_ORIGINAL_DETAIL_UID);
        aclUsageDto.setWrWrkInst(NEW_WR_WRK_INST);
        aclUsageDto.setSystemTitle(SYSTEM_TITLE);
        aclUsageDto.setDetailLicenseeClass(NEW_LICENSEE_CLASS);
        aclUsageDto.setAggregateLicenseeClassId(26);
        aclUsageDto.setAggregateLicenseeClassName("Law Firms");
        aclUsageDto.setSurveyCountry(SURVEY_COUNTRY);
        aclUsageDto.setPublicationType(NEW_PUBLICATION_TYPE);
        aclUsageDto.setContentUnitPrice(NEW_CONTENT_UNIT_PRICE);
        aclUsageDto.setTypeOfUse("DIGITAL");
        aclUsageDto.setAnnualizedCopies(NEW_ANNUALIZED_COPIES);
        return aclUsageDto;
    }

    private void verifyUpdatedAclUsages(AclUsageDto expectedAclUsageDto, AclUsageDto actualAclUsageDto) {
        assertEquals(expectedAclUsageDto.getPeriod(), actualAclUsageDto.getPeriod());
        assertEquals(expectedAclUsageDto.getWrWrkInst(), actualAclUsageDto.getWrWrkInst());
        assertEquals(expectedAclUsageDto.getDetailLicenseeClass().getId(),
            actualAclUsageDto.getDetailLicenseeClass().getId());
        assertEquals(expectedAclUsageDto.getDetailLicenseeClass().getDescription(),
            actualAclUsageDto.getDetailLicenseeClass().getDescription());
        assertEquals(expectedAclUsageDto.getPublicationType().getName(),
            actualAclUsageDto.getPublicationType().getName());
        assertEquals(expectedAclUsageDto.getPublicationType().getDescription(),
            actualAclUsageDto.getPublicationType().getDescription());
        assertEquals(expectedAclUsageDto.getContentUnitPrice(), actualAclUsageDto.getContentUnitPrice());
        assertEquals(expectedAclUsageDto.getTypeOfUse(), actualAclUsageDto.getTypeOfUse());
        assertEquals(expectedAclUsageDto.getAnnualizedCopies(), actualAclUsageDto.getAnnualizedCopies());
    }
}
