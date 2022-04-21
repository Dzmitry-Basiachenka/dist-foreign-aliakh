package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;

import com.vaadin.server.Sizeable.Unit;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link AclFundPoolWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolWidgetTest {

    private AclFundPoolWidget widget;
    private IAclFundPoolController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclFundPoolController.class);
        widget = new AclFundPoolWidget();
        Whitebox.setInternalState(widget, controller);
        expect(controller.initAclFundPoolFilterWidget()).andReturn(new AclFundPoolFilterWidget()).once();
    }

    @Test
    public void testWidgetStructure() {
        replay(controller);
        widget.init();
        assertTrue(widget.isLocked());
        assertEquals(270, widget.getSplitPosition(), 0);
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        verify(controller);
    }
}