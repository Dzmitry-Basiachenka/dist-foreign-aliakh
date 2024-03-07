package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getTextField;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.setTextFieldValue;
import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

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
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;

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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AaclFundPoolUploadWindowTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String FUND_POOL_NAME = "Fund Pool";
    private static final String FUND_POOL_NAME_LABEL = "Fund Pool Name";
    private static final String SPACES_STRING = "    ";
    private static final String STRING_50_CHARACTERS = StringUtils.repeat('a', 50);
    private static final String STRING_51_CHARACTERS = StringUtils.repeat('a', 51);
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String VALUE_EXCEED_50_CHARACTERS_MESSAGE = "Field value should not exceed 50 characters";
    private static final String USAGE_BATCH_EXISTS_MESSAGE = "Fund Pool with such name already exists";

    private AaclFundPoolUploadWindow window;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new AaclFundPoolUploadWindow(controller);
        verifyWindow(window, "Upload Fund Pool", "500px", "340px", Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
        verify(controller);
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/aacl-fund-pool-upload-window.json",
            new AaclFundPoolUploadWindow(controller));
    }

    @Test
    public void testIsValid() {
        expect(controller.fundPoolExists(FUND_POOL_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new AaclFundPoolUploadWindow(controller);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.setInternalState(uploadField, "fileName", "test.csv");
        assertFalse(window.isValid());
        setTextFieldValue(window, FUND_POOL_NAME_LABEL, FUND_POOL_NAME);
        assertTrue(window.isValid());
        verify(controller);
    }

    @Test
    public void testOnUploadClicked() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile", "getValue");
        AaclFundPoolCsvProcessor processor = createMock(AaclFundPoolCsvProcessor.class);
        var result = buildProcessingResult();
        window = createPartialMock(AaclFundPoolUploadWindow.class, new String[]{"isValid"}, controller);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.getAaclFundPoolCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(result).once();
        expect(controller.fundPoolExists(FUND_POOL_NAME)).andReturn(false).once();
        expect(controller.insertFundPool(buildFundPool(), result.get())).andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, window, controller, processor, uploadField);
        setTextFieldValue(window, FUND_POOL_NAME_LABEL, FUND_POOL_NAME);
        window.onUploadClicked();
        verify(Windows.class, window, controller, processor, uploadField);
    }

    @Test
    public void testFundPoolNameFieldValidation() {
        var fundPoolName = "existing Fund Pool";
        expect(controller.fundPoolExists(STRING_50_CHARACTERS)).andReturn(false).times(2);
        expect(controller.fundPoolExists(fundPoolName)).andReturn(true).times(2);
        expect(controller.fundPoolExists(FUND_POOL_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new AaclFundPoolUploadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        var fundPoolNameField = getTextField(window, FUND_POOL_NAME_LABEL);
        assertFieldValidationMessage(fundPoolNameField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(fundPoolNameField, SPACES_STRING, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(fundPoolNameField, STRING_50_CHARACTERS, binder, null, true);
        assertFieldValidationMessage(fundPoolNameField, STRING_51_CHARACTERS, binder,
            VALUE_EXCEED_50_CHARACTERS_MESSAGE, false);
        assertFieldValidationMessage(fundPoolNameField, fundPoolName, binder, USAGE_BATCH_EXISTS_MESSAGE, false);
        assertFieldValidationMessage(fundPoolNameField, FUND_POOL_NAME, binder, null, true);
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(2, rootLayout.getComponentCount());
        verifyFundPoolNameComponent(rootLayout.getComponentAt(0));
        verifyUploadComponent(rootLayout.getComponentAt(1), "100%");
        verifyButtonsLayout(getFooterLayout(window));
    }

    private void verifyFundPoolNameComponent(Component component) {
        var textField = verifyTextField(component, "Fund Pool Name", "aacl-fund-pool-name-field");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var buttonsLayout = (HorizontalLayout) component;
        assertEquals(2, buttonsLayout.getComponentCount());
        var uploadButton = verifyButton(buttonsLayout.getComponentAt(0), "Upload", true);
        verifyLoadClickListener(uploadButton);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true);
    }

    private FundPool buildFundPool() {
        var fundPool = new FundPool();
        fundPool.setProductFamily(AACL_PRODUCT_FAMILY);
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
