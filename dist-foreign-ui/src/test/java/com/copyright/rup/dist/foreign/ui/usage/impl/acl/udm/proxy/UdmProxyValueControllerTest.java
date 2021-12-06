package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueWidget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

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

    private IUdmProxyValueService udmProxyValueService;
    private IUdmProxyValueFilterController udmProxyValueFilterController;
    private IUdmProxyValueFilterWidget udmProxyValueFilterWidget;
    private UdmProxyValueController controller;

    @Before
    public void setUp() {
        controller = new UdmProxyValueController();
        udmProxyValueService = createMock(IUdmProxyValueService.class);
        udmProxyValueFilterController = createMock(IUdmProxyValueFilterController.class);
        udmProxyValueFilterWidget = createMock(IUdmProxyValueFilterWidget.class);
        Whitebox.setInternalState(controller, udmProxyValueService);
        Whitebox.setInternalState(controller, udmProxyValueFilterController);
    }

    @Test
    public void testGetProxyValues() {
        List<UdmProxyValueDto> valueDtos = Arrays.asList(new UdmProxyValueDto(), new UdmProxyValueDto());
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        expect(udmProxyValueFilterController.getWidget()).andReturn(udmProxyValueFilterWidget).once();
        expect(udmProxyValueFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(udmProxyValueService.getDtosByFilter(filter)).andReturn(valueDtos).once();
        replay(udmProxyValueService, udmProxyValueFilterController, udmProxyValueFilterWidget);
        assertEquals(valueDtos, controller.getProxyValues());
        verify(udmProxyValueService, udmProxyValueFilterController, udmProxyValueFilterWidget);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmProxyValueWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmProxyValueWidget.class, widget.getClass());
    }
}
