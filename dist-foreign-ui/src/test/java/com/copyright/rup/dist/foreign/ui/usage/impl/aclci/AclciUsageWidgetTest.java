package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclciUsageWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageWidgetTest {

    private AclciUsageWidget widget;
    private IAclciUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclciUsageController.class);
        widget = new AclciUsageWidget();
        widget.setController(controller);
    }

    @Test
    public void testWidgetStructure() {
        //TODO: implement
    }

    @Test
    public void testGetController() {
        assertSame(controller, widget.getController());
    }

    @Test
    public void testRefresh() {
        //TODO: implement
    }

    @Test
    public void testInitMediator() {
        //TODO: implement
    }
}
