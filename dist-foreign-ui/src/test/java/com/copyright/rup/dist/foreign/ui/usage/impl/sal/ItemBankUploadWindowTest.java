package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.SalFields;
import com.copyright.rup.dist.foreign.service.impl.csv.SalItemBankCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ItemBankUploadWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/20
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, ItemBankUploadWindow.class})
public class ItemBankUploadWindowTest {

    private static final String ACCOUNT_NUMBER = "5588";
    private static final String ITEM_BANK_NAME = "Item bank 2000";
    private static final String LICENSEE_NAME = "RGS Energy Group, Inc.";
    private static final String PERIOD_END_DATE = "2000";
    private static final String PERIOD_END_DATE_FIELD = "periodEndDateField";
    private ItemBankUploadWindow window;
    private ISalUsageController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(ISalUsageController.class);
    }

    @Test
    public void testConstructor() {
        window = new ItemBankUploadWindow(usagesController);
        assertEquals("Upload Item Bank", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(305, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testVerifyButtonListener() {
        window = new ItemBankUploadWindow(usagesController);
        VerticalLayout verticalLayout = (VerticalLayout) ((VerticalLayout) window.getContent()).getComponent(2);
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        TextField accountNumber = (TextField) horizontalLayout.getComponent(0);
        Button verifyButton = (Button) horizontalLayout.getComponent(1);
        TextField licenseeName = (TextField) verticalLayout.getComponent(1);
        expect(usagesController.getLicenseeName(1122L)).andReturn("Orbital ATK, Inc.").once();
        expect(usagesController.getLicenseeName(1111L)).andReturn("Acuson Corporation").once();
        replay(usagesController);
        accountNumber.setValue("value");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, licenseeName.getValue());
        accountNumber.setValue("1122");
        verifyButton.click();
        assertEquals("Orbital ATK, Inc.", licenseeName.getValue());
        accountNumber.setValue("1111");
        verifyButton.click();
        assertEquals("Acuson Corporation", licenseeName.getValue());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        expect(usagesController.usageBatchExists(ITEM_BANK_NAME)).andReturn(false).times(2);
        replay(usagesController);
        window = new ItemBankUploadWindow(usagesController);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "accountNumberField")).setValue(ACCOUNT_NUMBER);
        assertFalse(window.isValid());
        TextField licenseeName = Whitebox.getInternalState(window, "licenseeNameField");
        licenseeName.setReadOnly(false);
        licenseeName.setValue(LICENSEE_NAME);
        licenseeName.setReadOnly(true);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD)).setValue(PERIOD_END_DATE);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "itemBankNameField")).setValue(ITEM_BANK_NAME);
        assertTrue(window.isValid());
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        SalItemBankCsvProcessor processor = createMock(SalItemBankCsvProcessor.class);
        ProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(ItemBankUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usagesController", usagesController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        Whitebox.setInternalState(window, "itemBankNameField", new TextField("Item Bank Name", ITEM_BANK_NAME));
        Whitebox.setInternalState(window, "accountNumberField", new TextField("Licensee Account #", ACCOUNT_NUMBER));
        Whitebox.setInternalState(window, "licenseeNameField", new TextField("Licensee Name", LICENSEE_NAME));
        Whitebox.setInternalState(window, PERIOD_END_DATE_FIELD, new TextField("Period End Date", PERIOD_END_DATE));
        expect(window.isValid()).andReturn(true).once();
        expect(usagesController.getSalItemBankCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        usagesController.loadItemBank(buildUsageBatch(), processingResult.get());
        expectLastCall().once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, usagesController, Windows.class, processor, uploadField);
        window.onUploadClicked();
        verify(window, usagesController, Windows.class, processor, uploadField);
    }

    @Test
    public void testPeriodEndDateValidation() {
        replay(usagesController);
        window = new ItemBankUploadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodEndDate = Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD);
        verifyField(periodEndDate, "null", binder, "Field value should be specified", false);
        verifyField(periodEndDate, "a", binder, "Field value should contain numeric values only", false);
        verifyField(periodEndDate, "1000", binder, "Field value should be in range from 1950 to 2099", false);
        verifyField(periodEndDate, "2100", binder, "Field value should be in range from 1950 to 2099", false);
        verifyField(periodEndDate, "2020", binder, null, true);
        verify(usagesController);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyItemBankNameComponent(verticalLayout.getComponent(0));
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyLicenseeComponents(verticalLayout.getComponent(2));
        verifyPeriodEndDate(verticalLayout.getComponent(3));
        verifyButtonsLayout(verticalLayout.getComponent(4));
    }

    private void verifyItemBankNameComponent(Component component) {
        assertTrue(component instanceof TextField);
        TextField textField = (TextField) component;
        assertEquals(100, component.getWidth(), 0);
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyLicenseeComponents(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        TextField numberField = verifyTextField(horizontalLayout.getComponent(0), "Licensee Account #");
        Collection<?> listeners = numberField.getListeners(ValueChangeEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(2, listeners.size());
        Component verifyComponent = horizontalLayout.getComponent(1);
        assertTrue(verifyComponent instanceof Button);
        assertEquals("Verify", verifyComponent.getCaption());
        TextField nameField = verifyTextField(verticalLayout.getComponent(1), "Licensee Name");
        assertTrue(nameField.isReadOnly());
    }

    private void verifyPeriodEndDate(Component component) {
        TextField periodEndDateField = verifyTextField(component, "Period End Date (YYYY)");
        assertEquals(40, periodEndDateField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, periodEndDateField.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(ClickEvent.class).size());
        verifyLoadClickListener(loadButton);
    }

    private void verifyLoadClickListener(Button loadButton) {
        mockStatic(Windows.class);
        Collection<? extends AbstractField<?>> fields = Arrays.asList(
            Whitebox.getInternalState(window, "itemBankNameField"),
            Whitebox.getInternalState(window, "uploadField"),
            Whitebox.getInternalState(window, "accountNumberField"),
            Whitebox.getInternalState(window, "licenseeNameField"),
            Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD));
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(Windows.class);
        loadButton.click();
        verify(Windows.class);
        reset(Windows.class);
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifyField(TextField field, String value, Binder binder, String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }

    private TextField verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(caption, component.getCaption());
        return (TextField) component;
    }

    private ProcessingResult<Usage> buildCsvProcessingResult() {
        ProcessingResult<Usage> processingResult = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(processingResult, "addRecord", new Usage());
        } catch (Exception e) {
            fail();
        }
        return processingResult;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(ITEM_BANK_NAME);
        usageBatch.setProductFamily("SAL");
        usageBatch.setPaymentDate(LocalDate.of(2000, 6, 30));
        SalFields salFields = new SalFields();
        salFields.setLicenseeName(LICENSEE_NAME);
        salFields.setLicenseeAccountNumber(5588L);
        usageBatch.setSalFields(salFields);
        return usageBatch;
    }
}
