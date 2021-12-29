package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
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
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
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

/**
 * Verifies {@link AaclFundPoolUploadWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, AaclFundPoolUploadWindow.class})
public class AaclFundPoolUploadWindowTest {

    private static final String FUND_POOL_NAME = "fund pool name";
    private static final String FUND_POOL_NAME_FIELD = "fundPoolNameField";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";

    private AaclFundPoolUploadWindow window;
    private IAaclUsageController aaclUsageController;

    @Before
    public void setUp() {
        aaclUsageController = createMock(IAaclUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(aaclUsageController);
        window = new AaclFundPoolUploadWindow(aaclUsageController);
        verifyWindow(window, "Upload Fund Pool", 380, 165, Unit.PIXELS);
        verifyRootLayout(window.getContent());
        verify(aaclUsageController);
    }

    @Test
    public void testIsValid() {
        expect(aaclUsageController.fundPoolExists(FUND_POOL_NAME)).andReturn(false).times(2);
        replay(aaclUsageController);
        window = new AaclFundPoolUploadWindow(aaclUsageController);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("aacl_fund_pool.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD)).setValue(FUND_POOL_NAME);
        assertTrue(window.isValid());
        verify(aaclUsageController);
    }

    @Test
    public void testOnUploadClicked() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile", "getValue");
        AaclFundPoolCsvProcessor processor = createMock(AaclFundPoolCsvProcessor.class);
        ProcessingResult<FundPoolDetail> result = buildProcessingResult();
        window = createPartialMock(AaclFundPoolUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "aaclUsageController", aaclUsageController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        Whitebox.setInternalState(window, FUND_POOL_NAME_FIELD, new TextField("Fund Pool Name", FUND_POOL_NAME));
        expect(window.isValid()).andReturn(true).once();
        expect(aaclUsageController.getAaclFundPoolCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(result).once();
        expect(aaclUsageController.insertFundPool(buildFundPool(), result.get())).andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, window, aaclUsageController, processor, uploadField);
        window.onUploadClicked();
        verify(Windows.class, window, aaclUsageController, processor, uploadField);
    }

    @Test
    public void testFundPoolNameFieldValidation() {
        expect(aaclUsageController.fundPoolExists(FUND_POOL_NAME)).andReturn(true).times(2);
        expect(aaclUsageController.fundPoolExists(FUND_POOL_NAME)).andReturn(false).once();
        replay(aaclUsageController);
        window = new AaclFundPoolUploadWindow(aaclUsageController);
        Binder binder = Whitebox.getInternalState(window, "fundPoolBinder");
        TextField fundPoolName = Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(fundPoolName, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE,
            false);
        validateFieldAndVerifyErrorMessage(fundPoolName, "   ", binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fundPoolName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
        validateFieldAndVerifyErrorMessage(fundPoolName, FUND_POOL_NAME, binder,
            "Fund Pool with such name already exists", false);
        validateFieldAndVerifyErrorMessage(fundPoolName, FUND_POOL_NAME, binder, null, true);
        verify(aaclUsageController);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyFundPoolNameComponent(verticalLayout.getComponent(0));
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2), "Upload", "Close");
        Button loadButton = (Button) ((HorizontalLayout) verticalLayout.getComponent(2)).getComponent(0);
        verifyLoadClickListener(loadButton, Arrays.asList(
            Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD),
            Whitebox.getInternalState(window, "uploadField")));
    }

    private void verifyFundPoolNameComponent(Component component) {
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

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        fundPool.setName(FUND_POOL_NAME);
        return fundPool;
    }

    private ProcessingResult<FundPoolDetail> buildProcessingResult() {
        ProcessingResult<FundPoolDetail> result = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(result, "addRecord", new FundPoolDetail());
        } catch (Exception e) {
            fail();
        }
        return result;
    }
}
