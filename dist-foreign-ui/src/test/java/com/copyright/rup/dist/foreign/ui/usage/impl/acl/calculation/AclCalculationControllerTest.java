package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link AclCalculationController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclCalculationControllerTest {

    private final AclCalculationController controller = new AclCalculationController();

    private IAclGrantDetailController aclGrantDetailController;

    @Before
    public void setUp() {
        aclGrantDetailController = createMock(IAclGrantDetailController.class);
        Whitebox.setInternalState(controller, aclGrantDetailController);
    }

    @Test
    public void testInstantiateWidget() {
        IAclCalculationWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclCalculationWidget.class, widget.getClass());
    }

    @Test
    public void testGetAclGrantDetailController() {
        assertSame(aclGrantDetailController, controller.getAclGrantDetailController());
    }
}
