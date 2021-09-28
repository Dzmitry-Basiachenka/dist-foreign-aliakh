package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class UdmValueControllerTest {

    private IUdmBaselineService baselineService;
    private IUdmValueService valueService;
    private UdmValueController controller;

    @Before
    public void setUp() {
        controller = new UdmValueController();
        baselineService = createMock(IUdmBaselineService.class);
        valueService = createMock(IUdmValueService.class);
        Whitebox.setInternalState(controller, baselineService);
        Whitebox.setInternalState(controller, valueService);
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
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).once();
        replay(ForeignSecurityUtils.class);
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

    @Test
    public void testAssignValues() {
        Set<String> valueIds = Collections.singleton("303e2ed4-5ade-41e8-a989-2d73482209fd");
        valueService.assignValues(valueIds);
        expectLastCall().once();
        replay(valueService);
        controller.assignValues(valueIds);
        verify(valueService);
    }

    @Test
    public void testUnassignValues() {
        Set<String> valueIds = Collections.singleton("303e2ed4-5ade-41e8-a989-2d73482209fd");
        valueService.unassignValues(valueIds);
        expectLastCall().once();
        replay(valueService);
        controller.unassignValues(valueIds);
        verify(valueService);
    }
}
