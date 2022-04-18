package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;

import com.vaadin.shared.ui.MarginInfo;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclFundPoolFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolFilterWidgetTest {

    private final AclFundPoolFilterWidget widget = new AclFundPoolFilterWidget();
    private IAclFundPoolFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclFundPoolFilterController.class);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(1, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verify(controller);
    }
}
