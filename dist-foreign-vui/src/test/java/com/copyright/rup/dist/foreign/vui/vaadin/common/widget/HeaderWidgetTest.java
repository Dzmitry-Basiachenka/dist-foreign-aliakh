package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.server.VaadinSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verify {@link HeaderWidget}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 07/12/2023
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({VaadinSession.class, UI.class})
public class HeaderWidgetTest {

    private static final String APPLICATION_TITLE = "Dist Sharefeeder";
    private static final String USER_NAME = "user@copyright.com";
    private static final String FULL_PATH_TO_LOGO = "./themes/dist/img/cccLogo.svg";
    private static final String LOGOUT_BUTTON_TEXT = "log out";
    private static final String LOGOUT_BUTTON_ID = "logout-layout-logout-button";
    private static final String APPLICATION_LOGOUT_URL_FRAGMENT = "j_spring_security_logout";

    private HeaderWidget headerWidget;

    @Before
    public void init() {
        headerWidget = new HeaderWidget(APPLICATION_TITLE, USER_NAME);
    }

    @Test
    public void testInit() {
        assertEquals(JustifyContentMode.CENTER, headerWidget.getJustifyContentMode());
        assertEquals(Alignment.CENTER, headerWidget.getDefaultVerticalComponentAlignment());
        verifyComponents();
    }

    @Test
    public void testLogoutClickListener() {
        mockStatic(VaadinSession.class);
        mockStatic(UI.class);
        VaadinSession vaadinSession = createMock(VaadinSession.class);
        Page page = createMock(Page.class);
        UI ui = createMock(UI.class);
        expect(VaadinSession.getCurrent()).andReturn(vaadinSession).once();
        vaadinSession.close();
        expectLastCall().once();
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getPage()).andReturn(page).once();
        page.setLocation(APPLICATION_LOGOUT_URL_FRAGMENT);
        expectLastCall().once();
        Button logOutButton = (Button) headerWidget.getComponentAt(3);
        replay(vaadinSession, page, ui, UI.class, VaadinSession.class);
        logOutButton.click();
        verify(vaadinSession, page, ui, UI.class, VaadinSession.class);
    }

    private void verifyComponents() {
        assertEquals(4, headerWidget.getComponentCount());
        assertTrue(headerWidget.getComponentAt(0) instanceof Image);
        Image image = (Image) headerWidget.getComponentAt(0);
        assertEquals(FULL_PATH_TO_LOGO, image.getSrc());
        assertTrue(headerWidget.getComponentAt(1) instanceof Span);
        Span span = (Span) headerWidget.getComponentAt(1);
        assertEquals(APPLICATION_TITLE, span.getText());
        assertTrue(headerWidget.getComponentAt(2) instanceof Label);
        var label = (Label) headerWidget.getComponentAt(2);
        assertEquals(USER_NAME, label.getText());
        assertTrue(headerWidget.getComponentAt(3) instanceof Button);
        verifyButton((Button) headerWidget.getComponentAt(3));
    }

    private void verifyButton(Button button) {
        assertTrue(button.getIcon().isVisible());
        assertEquals(LOGOUT_BUTTON_TEXT, button.getText());
        assertEquals(LOGOUT_BUTTON_ID, button.getId().orElseThrow());
    }
}
