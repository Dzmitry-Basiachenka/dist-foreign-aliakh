package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
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
import java.util.List;
import java.util.Set;

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
        verifyWindow(window, "Upload Classified Details", 400, 135, Unit.PIXELS);
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
        validateFieldAndVerifyErrorMessage(uploadField, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(uploadField, "   ", binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(uploadField, "classification_usages.dox", binder,
            fileExtensionValidationMessage, false);
        validateFieldAndVerifyErrorMessage(uploadField, "classification_usages", binder, fileExtensionValidationMessage,
            false);
        validateFieldAndVerifyErrorMessage(uploadField, "classification_usages.csv", binder, null, true);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyUploadComponent(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1), "Upload", "Close");
        Button loadButton = (Button) ((HorizontalLayout) verticalLayout.getComponent(1)).getComponent(0);
        verifyLoadClickListener(loadButton, Set.of((UploadField) Whitebox.getInternalState(window, "uploadField")));
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
}
