package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Verifies {@link ClassifiedUsagesUploadWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, ClassifiedUsagesUploadWindow.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ClassifiedUsagesUploadWindowTest {

    private static final String SPACES_STRING = "    ";
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String INVALID_FILE_EXTENSION_MESSAGE = "File extension is incorrect";

    private ClassifiedUsagesUploadWindow window;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new ClassifiedUsagesUploadWindow(controller);
        verifyWindow(window, "Upload Classified Details", "500px", "230px", Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
        verify(controller);
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/classified-usages-upload-window.json",
            new ClassifiedUsagesUploadWindow(controller));
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        ClassifiedUsageCsvProcessor processor = createMock(ClassifiedUsageCsvProcessor.class);
        Binder<String> binder = createMock(Binder.class);
        var classifiedUsages = List.of(new AaclClassifiedUsage(), new AaclClassifiedUsage(), new AaclClassifiedUsage());
        var processingResult = buildCsvProcessingResult(classifiedUsages);
        window = createPartialMock(ClassifiedUsagesUploadWindow.class, "isValid", "close");
        Whitebox.setInternalState(window, controller);
        Whitebox.setInternalState(window, uploadField);
        Whitebox.setInternalState(window, binder);
        BinderValidationStatus<String> validationStatus = createMock(BinderValidationStatus.class);
        expect(binder.validate()).andReturn(validationStatus).once();
        expect(validationStatus.isOk()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        expect(controller.getClassifiedUsageCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(controller.loadClassifiedUsages(classifiedUsages)).andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow(
            "Upload completed: 1 record(s) were updated successfully, 2 disqualified record(s) were deleted");
        expectLastCall().once();
        replay(Windows.class, window, controller, uploadField, processor, binder, validationStatus);
        window.onUploadClicked();
        verify(Windows.class, window, controller, uploadField, processor, binder, validationStatus);
    }

    @Test
    public void testUploadFieldValidation() {
        window = new ClassifiedUsagesUploadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, "uploadBinder");
        UploadField uploadField = Whitebox.getInternalState(window, "uploadField");
        assertFieldValidationMessage(uploadField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(uploadField, SPACES_STRING, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(uploadField, "test.txt", binder, INVALID_FILE_EXTENSION_MESSAGE, false);
        assertFieldValidationMessage(uploadField, "test", binder, INVALID_FILE_EXTENSION_MESSAGE, false);
        assertFieldValidationMessage(uploadField, "test.csv", binder, null, true);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(1, rootLayout.getComponentCount());
        verifyUploadComponent(rootLayout.getComponentAt(0), "100%");
        var buttonsLayout = getFooterLayout(window);
        verifyButtonsLayout(buttonsLayout, true, "Upload", "Close");
        var loadButton = (Button) buttonsLayout.getComponentAt(0);
        verifyLoadClickListener(loadButton);
    }

    private ProcessingResult<AaclClassifiedUsage> buildCsvProcessingResult(List<AaclClassifiedUsage> classifiedUsages) {
        var processingResult = new ProcessingResult<AaclClassifiedUsage>();
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
