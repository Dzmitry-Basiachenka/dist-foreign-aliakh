package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;

import org.junit.Test;

/**
 * Verifies {@link UdmValueFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFilterControllerTest {

    private final UdmValueFilterController controller = new UdmValueFilterController();

    @Test
    public void testInstantiateWidget() {
        IUdmValueFilterWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmValueFilterWidget.class, widget.getClass());
    }

    @Test
    public void testGetAssignees() {
        // TODO add implementation
    }
}
