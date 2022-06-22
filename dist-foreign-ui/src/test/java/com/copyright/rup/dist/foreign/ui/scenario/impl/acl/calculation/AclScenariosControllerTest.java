package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclScenariosController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiahenka
 */
public class AclScenariosControllerTest {

    private AclScenariosController aclScenariosController;

    @Before
    public void setUp() {
        aclScenariosController = new AclScenariosController();
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenariosWidget widget = aclScenariosController.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenariosWidget.class, widget.getClass());
    }
}
