package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ErrorWindow;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.ErrorEvent;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verify {@link Buttons}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 07/13/2023
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ExceptionUtils.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class CommonErrorHandlerTest {

    private static final String ERROR_MESSAGE = "Test";
    private CommonErrorHandler commonErrorHandler;

    @Before
    public void setUp() {
        mockStatic(ExceptionUtils.class);
        expect(ExceptionUtils.getStackTrace(anyObject(Throwable.class))).andReturn(ERROR_MESSAGE).once();
        commonErrorHandler = new CommonErrorHandler();
    }

    @Test
    public void testInitWindow() {
        ErrorEvent event = new ErrorEvent(new Throwable(ERROR_MESSAGE));
        Dialog widget = createMock(ErrorWindow.class);
        replay(ErrorWindow.class, ExceptionUtils.class, widget);
        assertEquals(ERROR_MESSAGE, ((ErrorWindow) commonErrorHandler.initErrorWindow(event)).getStackTrace());
        verify(ErrorWindow.class, widget, ExceptionUtils.class);
    }
}
