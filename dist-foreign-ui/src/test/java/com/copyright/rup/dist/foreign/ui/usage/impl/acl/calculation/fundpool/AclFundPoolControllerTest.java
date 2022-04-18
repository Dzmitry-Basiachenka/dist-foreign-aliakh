package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;

import org.junit.Test;

/**
 * Verifies {@link AclFundPoolController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolControllerTest {

    private final AclFundPoolController controller = new AclFundPoolController();

    @Test
    public void testInstantiateWidget() {
        IAclFundPoolWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclFundPoolWidget.class, widget.getClass());
    }
}
