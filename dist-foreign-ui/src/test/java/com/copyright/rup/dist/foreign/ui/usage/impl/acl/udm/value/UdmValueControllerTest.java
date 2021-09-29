package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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

    private final UdmValueController controller = new UdmValueController();
    private final UdmValueFilter udmValueFilter = new UdmValueFilter();
    private IUdmValueFilterController udmValueFilterController;
    private IUdmValueFilterWidget udmValueFilterWidget;
    private IUdmValueService valueService;
    private IUdmValueWidget udmValueWidget;
    private IUdmBaselineService baselineService;

    @Before
    public void setUp() {
        udmValueFilterController = createMock(IUdmValueFilterController.class);
        udmValueFilterWidget = createMock(IUdmValueFilterWidget.class);
        valueService = createMock(IUdmValueService.class);
        udmValueWidget = createMock(IUdmValueWidget.class);
        baselineService = createMock(IUdmBaselineService.class);
        Whitebox.setInternalState(controller, udmValueFilterController);
        Whitebox.setInternalState(controller, udmValueFilterWidget);
        Whitebox.setInternalState(controller, valueService);
        Whitebox.setInternalState(controller, udmValueWidget);
        Whitebox.setInternalState(controller, baselineService);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmValueFilterController.getWidget()).andReturn(udmValueFilterWidget).once();
        expect(udmValueFilterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(valueService.getValueCount(udmValueFilter)).andReturn(10).once();
        replay(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
        assertEquals(10, controller.getBeansCount());
        verify(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
    }

    @Test
    public void testLoadBeans() {
        List<UdmValueDto> udmValues = Collections.singletonList(new UdmValueDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmValueFilterController.getWidget()).andReturn(udmValueFilterWidget).once();
        expect(udmValueFilterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(valueService.getValueDtos(eq(udmValueFilter), capture(pageableCapture), isNull()))
            .andReturn(udmValues).once();
        replay(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
        assertSame(udmValues, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
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
