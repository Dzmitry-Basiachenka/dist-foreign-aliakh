package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ClassifiedUsagesUploadWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 1/28/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, ClassifiedUsagesUploadWindow.class})
public class ClassifiedUsagesUploadWindowTest {

    private ClassifiedUsagesUploadWindow window;
    private IAaclUsageController usageController;

    @Before
    public void setUp() {
        usageController = createMock(IAaclUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(usageController);
        window = new ClassifiedUsagesUploadWindow(usageController);
        assertEquals("Upload Classified Details", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(135, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(usageController);
    }

    @Test
    public void testIsValid() {
        replay(usageController);
        window = new ClassifiedUsagesUploadWindow(usageController);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertTrue(window.isValid());
        verify(usageController);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        List<AaclClassifiedUsage> classifiedUsages =
            Arrays.asList(new AaclClassifiedUsage(), new AaclClassifiedUsage(), new AaclClassifiedUsage());
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        ClassifiedUsageCsvProcessor processor = createMock(ClassifiedUsageCsvProcessor.class);
        ProcessingResult<AaclClassifiedUsage> processingResult = buildCsvProcessingResult(classifiedUsages);
        window = createPartialMock(ClassifiedUsagesUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usageController", usageController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(usageController.getClassifiedUsageCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(usageController.loadClassifiedUsages(classifiedUsages)).andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow(
            "Upload completed: 1 record(s) were updated successfully, 2 disqualified record(s) were deleted");
        expectLastCall().once();
        replay(window, usageController, Windows.class, processor, uploadField);
        window.onUploadClicked();
        verify(window, usageController, Windows.class, processor, uploadField);
    }

    @Test
    public void testUploadFieldValidation() {
        window = new ClassifiedUsagesUploadWindow(usageController);
        Binder binder = Whitebox.getInternalState(window, "uploadBinder");
        UploadField uploadField = Whitebox.getInternalState(window, "uploadField");
        String emptyFieldValidationMessage = "Field value should be specified";
        String fileExtensionValidationMessage = "File extension is incorrect";
        verifyField(uploadField, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        verifyField(uploadField, "   ", binder, emptyFieldValidationMessage, false);
        verifyField(uploadField, "classification_usages.dox", binder, fileExtensionValidationMessage, false);
        verifyField(uploadField, "classification_usages", binder, fileExtensionValidationMessage, false);
        verifyField(uploadField, "classification_usages.csv", binder, null, true);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyUploadComponent(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
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

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifyLoadClickListener(Button loadButton) {
        mockStatic(Windows.class);
        Collection<? extends AbstractField<?>> fields =
            Collections.singleton(Whitebox.getInternalState(window, "uploadField"));
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(Windows.class);
        loadButton.click();
        verify(Windows.class);
        reset(Windows.class);
    }

    private ProcessingResult<AaclClassifiedUsage> buildCsvProcessingResult(List<AaclClassifiedUsage> classifiedUsages) {
        ProcessingResult<AaclClassifiedUsage> processingResult = new ProcessingResult<>();
        classifiedUsages.forEach(usage -> {
            try {
                Whitebox.invokeMethod(processingResult, "addRecord", usage);
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        });
        return processingResult;
    }

    @SuppressWarnings("unchecked")
    private void verifyField(AbstractField field, String value, Binder binder, String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }
}
