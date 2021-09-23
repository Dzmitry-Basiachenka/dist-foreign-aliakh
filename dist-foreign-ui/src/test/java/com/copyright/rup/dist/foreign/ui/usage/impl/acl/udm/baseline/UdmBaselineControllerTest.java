package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Verifies {@link UdmBaselineController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineControllerTest {

    private IUdmBaselineService udmBaselineService;
    private UdmBaselineController udmBaselineController;
    private UdmBaselineFilter baselineFilter;
    private IUdmBaselineFilterWidget udmUsageFilterWidget;
    private IUdmBaselineFilterController udmBaselineFilterController;

    @Before
    public void setUp() {
        udmBaselineFilterController = createMock(IUdmBaselineFilterController.class);
        udmUsageFilterWidget = createMock(IUdmBaselineFilterWidget.class);
        udmBaselineService = createMock(IUdmBaselineService.class);
        udmBaselineController = new UdmBaselineController();
        baselineFilter = new UdmBaselineFilter();
        Whitebox.setInternalState(udmBaselineController, udmBaselineService);
        Whitebox.setInternalState(udmBaselineController, udmBaselineFilterController);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmBaselineFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(baselineFilter).once();
        expect(udmBaselineService.getBaselineUsagesCount(baselineFilter)).andReturn(5).once();
        replay(udmBaselineService, udmBaselineFilterController, udmUsageFilterWidget);
        assertEquals(5, udmBaselineController.getBeansCount());
        verify(udmBaselineService, udmBaselineFilterController, udmUsageFilterWidget);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmBaselineFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(baselineFilter).once();
        expect(udmBaselineService.getBaselineUsageDtos(eq(baselineFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.emptyList()).once();
        replay(udmBaselineService, udmBaselineFilterController, udmUsageFilterWidget);
        assertEquals(new ArrayList<>(), udmBaselineController.loadBeans(0, 10, null));
        verify(udmBaselineService, udmBaselineFilterController, udmUsageFilterWidget);
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(udmBaselineController.instantiateWidget());
    }
}
