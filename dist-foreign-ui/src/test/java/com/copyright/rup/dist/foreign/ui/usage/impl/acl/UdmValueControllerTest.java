package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Verifies {@link UdmValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueControllerTest {

    private final UdmValueController controller = new UdmValueController();

    @Test
    public void testGetBeansCount() {
        //TODO add test
        assertEquals(0, controller.getBeansCount());
    }

    @Test
    public void testLoadBeans() {
        //TODO add test
        assertEquals(new ArrayList<>(), controller.loadBeans(0, 10, null));
    }

    @Test
    public void testInstantiateWidget() {
        IUdmValueWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmValueWidget.class, widget.getClass());
    }
}
