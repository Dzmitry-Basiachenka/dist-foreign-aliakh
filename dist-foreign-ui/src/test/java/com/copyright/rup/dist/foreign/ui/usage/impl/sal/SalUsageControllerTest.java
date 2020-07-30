package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.ISalUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;

import com.vaadin.ui.HorizontalLayout;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalUsageController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageControllerTest {

    private ISalUsageService salUsageService;
    private SalUsageController controller;
    private ISalUsageWidget usagesWidget;
    private ISalUsageFilterController filterController;
    private ISalUsageFilterWidget filterWidget;

    @Before
    public void setUp() {
        controller = new SalUsageController();
        usagesWidget = createMock(ISalUsageWidget.class);
        salUsageService = createMock(ISalUsageService.class);
        filterController = createMock(ISalUsageFilterController.class);
        filterWidget = createMock(ISalUsageFilterWidget.class);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, salUsageService);
        Whitebox.setInternalState(controller, filterController);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testOnFilterChanged() {
        usagesWidget.refresh();
        expectLastCall().once();
        replay(usagesWidget);
        controller.onFilterChanged(new FilterChangedEvent(new HorizontalLayout()));
        verify(usagesWidget);
    }

    @Test
    public void getBeansCount() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setSalDetailType(SalDetailTypeEnum.IB);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(salUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidget, salUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidget, salUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setSalDetailType(SalDetailTypeEnum.IB);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(salUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.singletonList(new UsageDto())).once();
        replay(filterWidget, salUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(filterWidget, salUsageService, filterController);
    }
}
