package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
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
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;

/**
 * Verifies {@link ResearchedUsagesUploadWindow}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, ResearchedUsagesUploadWindow.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ResearchedUsagesUploadWindowTest {

    private ResearchedUsagesUploadWindow window;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new ResearchedUsagesUploadWindow(controller);
        verifyWindow(window, "Upload Researched Details", "520px", "230px", Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
        verify(controller);
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/fas/researched-usages-upload-window.json",
            new ResearchedUsagesUploadWindow(controller));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        ResearchedUsagesCsvProcessor processor = createMock(ResearchedUsagesCsvProcessor.class);
        Binder<String> binder = createMock(Binder.class);
        ProcessingResult<ResearchedUsage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(ResearchedUsagesUploadWindow.class, "isValid", "close");
        Whitebox.setInternalState(window, controller);
        Whitebox.setInternalState(window, uploadField);
        Whitebox.setInternalState(window, binder);
        BinderValidationStatus<String> validationStatus = createMock(BinderValidationStatus.class);
        expect(binder.validate()).andReturn(validationStatus).once();
        expect(validationStatus.isOk()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        expect(controller.getResearchedUsagesCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        controller.loadResearchedUsages(processingResult.get());
        expectLastCall().once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, window, controller, uploadField, processor, binder, validationStatus);
        window.onUploadClicked();
        verify(Windows.class, window, controller, uploadField, processor, binder, validationStatus);
    }

    private ProcessingResult<ResearchedUsage> buildCsvProcessingResult() {
        ProcessingResult<ResearchedUsage> processingResult = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(processingResult, "addRecord", new ResearchedUsage());
        } catch (Exception e) {
            fail();
        }
        return processingResult;
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
}
