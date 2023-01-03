package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueControllerTest {

    private final UdmBaselineValueController controller = new UdmBaselineValueController();
    private final UdmBaselineValueFilter udmValueFilter = new UdmBaselineValueFilter();

    private IUdmBaselineValueFilterController filterController;
    private IUdmBaselineValueFilterWidget filterWidget;
    private IUdmBaselineValueService udmBaselineValueService;
    private IUdmBaselineValueWidget udmBaselineValueWidget;

    @Before
    public void setUp() {
        filterController = createMock(IUdmBaselineValueFilterController.class);
        filterWidget = createMock(IUdmBaselineValueFilterWidget.class);
        udmBaselineValueWidget = createMock(IUdmBaselineValueWidget.class);
        udmBaselineValueService = createMock(IUdmBaselineValueService.class);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, filterWidget);
        Whitebox.setInternalState(controller, udmBaselineValueWidget);
        Whitebox.setInternalState(controller, udmBaselineValueService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(UdmBaselineValueWidget.class));
    }

    @Test
    public void testInitBaselineFilterWidget() {
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(filterController);
        controller.initBaselineValuesFilterWidget();
        verify(filterController);
    }

    @Test
    public void testLoadBeans() {
        List<UdmValueBaselineDto> udmValues = Collections.singletonList(new UdmValueBaselineDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(udmBaselineValueService.getValueDtos(eq(udmValueFilter), capture(pageableCapture), isNull()))
            .andReturn(udmValues).once();
        replay(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
        assertSame(udmValues, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
    }

    @Test
    public void testGetBeansCount() {
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(udmBaselineValueService.getBaselineValueCount(udmValueFilter)).andReturn(10).once();
        replay(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
        assertEquals(10, controller.getBeansCount());
        verify(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
    }
}
