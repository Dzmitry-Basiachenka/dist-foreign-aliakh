package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

/**
 * Verifies {@link UdmEditValueWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/13/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmEditValueWindowTest {

    private static final String UDM_USAGE_UID = "34a80267-6b25-4365-9977-c829e725cef0";
    private static final String USER_NAME = "user@copyright.com";

    private UdmEditValueWindow window;
    private Binder<UdmValueDto> binder;
    private IUdmValueController controller;
    private UdmValueDto udmValue;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        buildUdmValueDto();
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IUdmValueController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        expect(controller.getPublicationTypes()).andReturn(
            Collections.singletonList(buildPublicationType("Book", "1.00"))).once();
        expect(controller.getCurrencyCodesToCurrencyNamesMap()).andReturn(
            ImmutableMap.of("USD", "US Dollar")).once();
    }

    @Test
    public void testConstructor() {
        setSpecialistExpectations();
        initEditWindow();
        assertEquals("Edit UDM Value", window.getCaption());
        assertEquals(960, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(700, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanelSpecialistAndManager(verticalLayout.getComponent(0));
    }

    // TODO implement tests for the fields when its validators are implemented

    @Test
    public void testSaveButtonClickListener() throws Exception {
        setSpecialistExpectations();
        binder = createMock(Binder.class);
        binder.writeBean(udmValue);
        expectLastCall().once();
        controller.updateValue(udmValue);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditValueWindow(controller, udmValue, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1));
        return verticalLayout;
    }

    private void verifyPanelSpecialistAndManager(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(4, verticalLayout.getComponentCount());
        // TODO verify components when implemented
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Discard");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private void buildUdmValueDto() {
        udmValue = new UdmValueDto();
        udmValue.setId(UDM_USAGE_UID);
        // TODO add other fields when the correspondent components are implemented
        udmValue.setCreateDate(Date.from(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmValue.setUpdateUser(USER_NAME);
        udmValue.setUpdateDate(Date.from(LocalDate.of(2020, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void initEditWindow() {
        replay(controller, ForeignSecurityUtils.class);
        window = new UdmEditValueWindow(controller, udmValue, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, "binder");
        verify(controller, ForeignSecurityUtils.class);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType publicationType = new PublicationType();
        publicationType.setName(name);
        publicationType.setWeight(new BigDecimal(weight));
        return publicationType;
    }
}
