package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;

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

    private IAclUsageController aclUsageController;
    private IAclGrantDetailController aclGrantDetailController;
    private IAclFundPoolController aclFundPoolController;
    private IAclScenariosController aclScenariosController;

    @Before
    public void setUp() {
        aclUsageController = createMock(IAclUsageController.class);
        aclGrantDetailController = createMock(IAclGrantDetailController.class);
        aclFundPoolController = createMock(IAclFundPoolController.class);
        aclScenariosController = createMock(IAclScenariosController.class);
        Whitebox.setInternalState(controller, aclUsageController);
        Whitebox.setInternalState(controller, aclGrantDetailController);
        Whitebox.setInternalState(controller, aclFundPoolController);
        Whitebox.setInternalState(controller, aclScenariosController);
    }

    @Test
    public void testInstantiateWidget() {
        IAclCalculationWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclCalculationWidget.class, widget.getClass());
    }

    @Test
    public void testGetAclUsageController() {
        assertSame(aclUsageController, controller.getAclUsageController());
    }

    @Test
    public void testGetAclGrantDetailController() {
        assertSame(aclGrantDetailController, controller.getAclGrantDetailController());
    }

    @Test
    public void testGetAclFundPoolController() {
        assertSame(aclFundPoolController, controller.getAclFundPoolController());
    }

    @Test
    public void testGetAclScenariosController() {
        assertSame(aclScenariosController, controller.getAclScenariosController());
    }
}
