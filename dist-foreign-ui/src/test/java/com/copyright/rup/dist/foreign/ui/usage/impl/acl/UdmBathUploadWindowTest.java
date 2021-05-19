package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

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
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
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

/**
 * Verifies {@link UdmBathUploadWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/2021
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, UdmBathUploadWindow.class})
public class UdmBathUploadWindowTest {

    private static final String PERIOD_YEAR_FIELD = "2020";
    private static final String MONTH_FIELD = "12";
    private static final String METHOD_NAME = "getValue";

    private IUdmUsageController controller;
    private UdmBathUploadWindow window;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, METHOD_NAME, "getStreamToUploadedFile");
        UdmCsvProcessor processor = createMock(UdmCsvProcessor.class);
        ComboBox periodMonth = createPartialMock(ComboBox.class, METHOD_NAME);
        ComboBox channelField = createPartialMock(ComboBox.class, METHOD_NAME);
        ComboBox originField = createPartialMock(ComboBox.class, METHOD_NAME);
        ProcessingResult<UdmUsage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(UdmBathUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "udmUsageController", controller);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        Whitebox.setInternalState(window, "periodYearField", new TextField("Period Year", PERIOD_YEAR_FIELD));
        Whitebox.setInternalState(window, "monthField", periodMonth);
        Whitebox.setInternalState(window, "channelField", channelField);
        Whitebox.setInternalState(window, "usageOriginField", originField);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.getCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(uploadField.getValue()).andReturn("test.csv").once();
        expect(periodMonth.getValue()).andReturn("12").once();
        expect(originField.getValue()).andReturn(UdmUsageOriginEnum.RFA).once();
        expect(channelField.getValue()).andReturn(UdmChannelEnum.CCC).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        expect(controller.loadUdmBatch(buildUdmBatch(), processingResult.get())).andReturn(1).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, controller, Windows.class, processor, uploadField, periodMonth, originField, channelField);
        window.onUploadClicked();
        verify(window, controller, Windows.class, processor, uploadField, periodMonth, originField, channelField);
    }

    @Test
    public void testConstructor() {
        window = new UdmBathUploadWindow(controller);
        assertEquals("Upload UDM Usage Batch", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(211, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsValid() {
        window = new UdmBathUploadWindow(controller);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "periodYearField")).setValue(PERIOD_YEAR_FIELD);
        assertFalse(window.isValid());
        ((ComboBox<String>) Whitebox.getInternalState(window, "monthField")).setValue(MONTH_FIELD);
        assertFalse(window.isValid());
        ((ComboBox<UdmChannelEnum>) Whitebox.getInternalState(window, "channelField")).setValue(UdmChannelEnum.CCC);
        assertFalse(window.isValid());
        ((ComboBox<UdmUsageOriginEnum>) Whitebox.getInternalState(window, "usageOriginField")).setValue(
            UdmUsageOriginEnum.RFA);
        assertTrue(window.isValid());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyUploadComponent(verticalLayout.getComponent(0));
        verifyPeriodYearAndPeriodMonthComponents(verticalLayout.getComponent(1));
        verifyChannelAndUsageOriginComponents(verticalLayout.getComponent(2));
        verifyButtonsLayout(verticalLayout.getComponent(3));
    }

    private void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyPeriodYearAndPeriodMonthComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.getComponent(0) instanceof TextField);
        assertTrue(horizontalLayout.getComponent(1) instanceof ComboBox);
        TextField textField = (TextField) horizontalLayout.getComponent(0);
        assertEquals("Period Year", textField.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, textField.getWidthUnits());
        ComboBox<String> comboBox = (ComboBox<String>) horizontalLayout.getComponent(1);
        assertEquals("Period Month", comboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
    }

    private void verifyChannelAndUsageOriginComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.getComponent(0) instanceof ComboBox);
        assertTrue(horizontalLayout.getComponent(1) instanceof ComboBox);
        ComboBox<UdmUsageOriginEnum> originComboBox = (ComboBox<UdmUsageOriginEnum>) horizontalLayout.getComponent(0);
        assertEquals("Usage Origin", originComboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, originComboBox.getWidthUnits());
        ComboBox<UdmChannelEnum> channelComboBox = (ComboBox<UdmChannelEnum>) horizontalLayout.getComponent(1);
        assertEquals("Channel", channelComboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, channelComboBox.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private ProcessingResult<UdmUsage> buildCsvProcessingResult() {
        ProcessingResult<UdmUsage> processingResult = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(processingResult, "addRecord", new UdmUsage());
        } catch (Exception e) {
            fail();
        }
        return processingResult;
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setName("test");
        udmBatch.setPeriod(202012);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.RFA);
        return udmBatch;
    }
}