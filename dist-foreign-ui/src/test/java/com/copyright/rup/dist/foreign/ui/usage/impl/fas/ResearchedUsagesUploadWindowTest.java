package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

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
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

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
public class ResearchedUsagesUploadWindowTest {

    private ResearchedUsagesUploadWindow window;
    private IFasUsageController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(IFasUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(usagesController);
        window = new ResearchedUsagesUploadWindow(usagesController);
        verifyWindow(window, "Upload Researched Details", 400, 135, Unit.PIXELS);
        verifyRootLayout(window.getContent());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        replay(usagesController);
        window = new ResearchedUsagesUploadWindow(usagesController);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertTrue(window.isValid());
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        ResearchedUsagesCsvProcessor processor = createMock(ResearchedUsagesCsvProcessor.class);
        ProcessingResult<ResearchedUsage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(ResearchedUsagesUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usagesController", usagesController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(usagesController.getResearchedUsagesCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        usagesController.loadResearchedUsages(processingResult.get());
        expectLastCall().once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, usagesController, Windows.class, processor, uploadField);
        window.onUploadClicked();
        verify(window, usagesController, Windows.class, processor, uploadField);
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
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyUploadComponent(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1), "Upload", "Close");
        Button loadButton = (Button) ((HorizontalLayout) (verticalLayout.getComponent(1))).getComponent(0);
        verifyLoadClickListener(loadButton, Collections.singleton(Whitebox.getInternalState(window, "uploadField")));
    }
}
