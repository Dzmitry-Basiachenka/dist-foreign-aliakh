package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageDataCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
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
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageDataUploadWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/29/20
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, ItemBankUploadWindow.class})
public class UsageDataUploadWindowTest {

    private static final String BATCH_ID = "249bb125-c6e8-4e60-a798-854a79c6f2f7";
    private static final String BATCH_NAME = "Item bank 2000";
    private static final String FILE_NAME = "test.csv";

    private UsageDataUploadWindow window;
    private ISalUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(ISalUsageController.class);
    }

    @Test
    public void testConstructor() {
        UsageBatch batch = buildUsageBatch();
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        window = new UsageDataUploadWindow(controller);
        verify(controller);
        assertEquals("Upload Usage Data", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(170, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testIsValidOnNewlyOpenedWindow() {
        UsageBatch batch = buildUsageBatch();
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        window = new UsageDataUploadWindow(controller);
        verify(controller);
        assertFalse(window.isValid());
    }

    @Test
    public void testIsValidWithValidFields() {
        UsageBatch batch = buildUsageBatch();
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        window = new UsageDataUploadWindow(controller);
        verify(controller);
        setUploadFileName(FILE_NAME);
        selectBatch(batch);
        assertTrue(window.isValid());
    }

    @Test
    public void testIsValidWithEmptyItemBank() {
        UsageBatch batch = buildUsageBatch();
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        window = new UsageDataUploadWindow(controller);
        verify(controller);
        setUploadFileName(FILE_NAME);
        selectBatch(null);
        assertFalse(window.isValid());
    }

    @Test
    public void testIsValidWithEmptyUploadFile() {
        UsageBatch batch = buildUsageBatch();
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        window = new UsageDataUploadWindow(controller);
        verify(controller);
        setUploadFileName(StringUtils.EMPTY);
        selectBatch(batch);
        assertFalse(window.isValid());
    }

    @Test
    public void testOnUploadClickedWithInvalidFields() {
        mockStatic(Windows.class);
        window = createWindowMock(buildUsageBatch());
        expect(window.isValid()).andReturn(false).once();
        Windows.showValidationErrorWindow(anyObject(List.class));
        expectLastCall().once();
        replay(window, controller, Windows.class);
        window.onUploadClicked();
        verify(window, controller, Windows.class);
    }

    @Test
    public void testOnUploadClickedWithBatchWithUsageData() {
        mockStatic(Windows.class);
        UsageBatch batch = buildUsageBatch();
        window = createWindowMock(batch);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.usageDataExists(BATCH_ID)).andReturn(true).once();
        Windows.showNotificationWindow("Selected Item Bank already contains usage data");
        expectLastCall().once();
        replay(window, controller, Windows.class);
        selectBatch(batch);
        window.onUploadClicked();
        verify(window, controller, Windows.class);
    }

    @Test
    public void testOnUploadClickedWithIneligibleUsages() {
        mockStatic(Windows.class);
        UsageBatch batch = buildUsageBatch();
        window = createWindowMock(batch);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.usageDataExists(BATCH_ID)).andReturn(false).once();
        expect(controller.getIneligibleBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(List.of(BATCH_NAME)).once();
        Windows.showNotificationWindow("Selected Item Bank has usages that are not in ELIGIBLE status");
        expectLastCall().once();
        replay(window, controller, Windows.class);
        selectBatch(batch);
        window.onUploadClicked();
        verify(window, controller, Windows.class);
    }

    @Test
    public void testOnUploadClickedWithValidFields() {
        mockStatic(Windows.class);
        UsageBatch batch = buildUsageBatch();
        ProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        SalUsageDataCsvProcessor processor = createMock(SalUsageDataCsvProcessor.class);
        window = createWindowMock(batch, uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.usageDataExists(BATCH_ID)).andReturn(false).once();
        expect(controller.getIneligibleBatchesNames(Collections.singleton(BATCH_ID)))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getSalUsageDataCsvProcessor(BATCH_ID)).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        controller.loadUsageData(batch, processingResult.get());
        expectLastCall().once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, controller, Windows.class, processor, uploadField);
        selectBatch(batch);
        window.onUploadClicked();
        verify(window, controller, Windows.class, processor, uploadField);
    }

    private void setUploadFileName(String fileName) {
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue(fileName);
    }

    @SuppressWarnings("unchecked")
    private void selectBatch(UsageBatch batch) {
        ((ComboBox<UsageBatch>) Whitebox.getInternalState(window, "itemBankComboBox")).setSelectedItem(batch);
    }

    private UsageDataUploadWindow createWindowMock(UsageBatch batch) {
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        UsageDataUploadWindow windowMock = createPartialMock(UsageDataUploadWindow.class,
            List.of("isValid").toArray(new String[]{}), controller);
        verify(controller);
        reset(controller);
        return windowMock;
    }

    private UsageDataUploadWindow createWindowMock(UsageBatch batch, UploadField uploadField) {
        expect(controller.getBatchesNotAttachedToScenario()).andReturn(List.of(batch)).once();
        replay(controller);
        UsageDataUploadWindow windowMock = createPartialMock(UsageDataUploadWindow.class,
            List.of("isValid").toArray(new String[]{}), controller);
        Whitebox.setInternalState(windowMock, "uploadField", uploadField);
        verify(controller);
        reset(controller);
        return windowMock;
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyUploadComponent(verticalLayout.getComponent(0));
        verifyComboBox(verticalLayout.getComponent(1), "Item Bank", true, buildUsageBatch());
        verifyButtonsLayout(verticalLayout.getComponent(2), "Upload", "Close");
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
        usageBatch.setId(BATCH_ID);
        usageBatch.setName(BATCH_NAME);
        usageBatch.setProductFamily("SAL");
        usageBatch.setPaymentDate(LocalDate.of(2000, 6, 30));
        SalFields salFields = new SalFields();
        salFields.setLicenseeName("RGS Energy Group, Inc.");
        salFields.setLicenseeAccountNumber(5588L);
        usageBatch.setSalFields(salFields);
        return usageBatch;
    }
}
