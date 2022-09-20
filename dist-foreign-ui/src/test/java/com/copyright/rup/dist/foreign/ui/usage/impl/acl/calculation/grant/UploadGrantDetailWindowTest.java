package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.impl.csv.AclGrantDetailCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link UploadGrantDetailWindow}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/30/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, UploadGrantDetailWindow.class})
public class UploadGrantDetailWindowTest {

    private final IAclGrantDetailController controller = createMock(IAclGrantDetailController.class);
    private final AclGrantSet grantSet = buildGrantSet();
    private UploadGrantDetailWindow window;

    @Test
    public void testConstructor() {
        AclGrantSet nonEditableGrantSet = buildGrantSet();
        nonEditableGrantSet.setId("0a789e01-7444-42f3-a135-ab259bdf41ff");
        nonEditableGrantSet.setEditable(false);
        expect(controller.getAllAclGrantSets()).andReturn(Arrays.asList(nonEditableGrantSet, grantSet)).once();
        replay(controller);
        window = new UploadGrantDetailWindow(controller);
        verify(controller);
        verifyWindow(window, "Upload Grant Details", 350, 170, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsValid() {
        expect(controller.getAllAclGrantSets()).andReturn(Collections.singletonList(grantSet)).once();
        replay(controller);
        window = new UploadGrantDetailWindow(controller);
        verify(controller);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, ComboBox.class).setValue(grantSet);
        assertTrue(window.isValid());
    }

    @Test
    public void testOnUploadClicked() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile", "getValue");
        AclGrantDetailCsvProcessor processor = createMock(AclGrantDetailCsvProcessor.class);
        ProcessingResult<AclGrantDetailDto> result = buildProcessingResult();
        expect(controller.getAllAclGrantSets()).andReturn(Collections.emptyList()).once();
        replay(controller);
        window = createPartialMock(UploadGrantDetailWindow.class, new String[]{"isValid"}, controller);
        verify(controller);
        reset(controller);
        Whitebox.setInternalState(window, uploadField);
        ComboBox<AclGrantSet> grantSetComboBox = new ComboBox<>();
        grantSetComboBox.setSelectedItem(grantSet);
        Whitebox.setInternalState(window, grantSetComboBox);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.getCsvProcessor(grantSet.getId())).andReturn(processor).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        expect(processor.process(anyObject())).andReturn(result).once();
        controller.insertAclGrantDetails(grantSet, result.get());
        expectLastCall().once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, window, controller, processor, uploadField);
        window.onUploadClicked();
        verify(Windows.class, window, controller, processor, uploadField);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyComboBox(verticalLayout.getComponent(0), "Grant Set", false, grantSet);
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2), "Upload", "Close");
        Button loadButton = (Button) ((HorizontalLayout) (verticalLayout.getComponent(2))).getComponent(0);
        verifyLoadClickListener(loadButton, Arrays.asList(Whitebox.getInternalState(window, "comboBox"),
            Whitebox.getInternalState(window, "uploadField")));
    }

    private AclGrantSet buildGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId("670f75dc-f5ef-43be-9a46-9b3a82a25ef7");
        aclGrantSet.setName("Grant Set 202106");
        aclGrantSet.setEditable(true);
        return aclGrantSet;
    }

    private ProcessingResult<AclGrantDetailDto> buildProcessingResult() {
        ProcessingResult<AclGrantDetailDto> result = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(result, "addRecord", new AclGrantDetailDto());
        } catch (Exception e) {
            fail();
        }
        return result;
    }
}
