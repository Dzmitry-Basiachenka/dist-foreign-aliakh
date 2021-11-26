package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueWidget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link UdmProxyValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class UdmProxyValueControllerTest {

    private final UdmProxyValueController controller = new UdmProxyValueController();

    @Test
    public void testGetProxyValues() {
        //TODO: add implementation
    }

    @Test
    public void testInstantiateWidget() {
        IUdmProxyValueWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmProxyValueWidget.class, widget.getClass());
    }
}
