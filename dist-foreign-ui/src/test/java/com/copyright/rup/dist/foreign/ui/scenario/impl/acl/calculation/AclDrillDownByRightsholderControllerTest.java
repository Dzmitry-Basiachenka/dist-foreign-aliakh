package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link AclDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AclDrillDownByRightsholderControllerTest {

    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";

    private AclDrillDownByRightsholderController controller;

    @Before
    public void setUp() {
        controller = new AclDrillDownByRightsholderController();
    }

    @Test
    public void testLoadBeans() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testGetSize() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testShowWidget() {
        mockStatic(Windows.class);
        Capture<AclDrillDownByRightsholderWidget> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class);
        controller.showWidget(RH_ACCOUNT_NUMBER, RH_NAME, new AclScenario());
        AclDrillDownByRightsholderWidget widget = windowCapture.getValue();
        assertNotNull(widget);
        assertEquals("Rothchild Consultants (Account #: 100000001)", widget.getCaption());
        verify(Windows.class);
    }
}
