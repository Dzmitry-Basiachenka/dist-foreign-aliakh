package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;

import org.junit.Test;

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

    @Test
    public void testInstantiateWidget() {
        IAclCalculationWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclCalculationWidget.class, widget.getClass());
    }
}
