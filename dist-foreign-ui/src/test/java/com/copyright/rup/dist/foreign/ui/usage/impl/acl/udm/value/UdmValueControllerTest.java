package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private IUdmBaselineService baselineService;
    private UdmValueController controller;

    @Before
    public void setUp() {
        controller = new UdmValueController();
        baselineService = createMock(IUdmBaselineService.class);
        Whitebox.setInternalState(controller, baselineService);
    }

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

    @Test
    public void testGetBaselinePeriods() {
        List<Integer> periods = Arrays.asList(200212, 201912);
        expect(baselineService.getPeriods()).andReturn(periods).once();
        replay(baselineService);
        assertEquals(periods, controller.getBaselinePeriods());
        verify(baselineService);
    }
}
