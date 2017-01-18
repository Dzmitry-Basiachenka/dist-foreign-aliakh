package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link UsagesController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/18/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsagesControllerTest {

    private UsagesController controller;

    @Before
    public void setUp() {
        controller = new UsagesController();
    }

    @Test
    public void testGetSize() {
        assertEquals(0, controller.getSize());
    }

    @Test
    public void testLoadBeans() {
        assertEquals(Collections.emptyList(), controller.loadBeans(0, 150, null));
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testInitUsagesFilterWidget() {
        IUsagesFilterController filterController = createMock(IUsagesFilterController.class);
        UsagesFilterWidget filterWidget = new UsagesFilterWidget();
        Whitebox.setInternalState(controller, "filterController", filterController);
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(filterController);
        assertSame(filterWidget, controller.initUsagesFilterWidget());
        verify(filterController);
    }
}
