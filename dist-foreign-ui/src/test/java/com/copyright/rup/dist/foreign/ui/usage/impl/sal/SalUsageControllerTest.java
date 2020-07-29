package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;

import com.vaadin.ui.HorizontalLayout;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link SalUsageController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageControllerTest {

    private SalUsageController controller;
    private ISalUsageWidget usagesWidget;

    @Before
    public void setUp() {
        controller = new SalUsageController();
        usagesWidget = createMock(ISalUsageWidget.class);
        Whitebox.setInternalState(controller, usagesWidget);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testOnFilterChanged() {
        usagesWidget.refresh();
        expectLastCall().once();
        replay(usagesWidget);
        controller.onFilterChanged(new FilterChangedEvent(new HorizontalLayout()));
        verify(usagesWidget);
    }
}
