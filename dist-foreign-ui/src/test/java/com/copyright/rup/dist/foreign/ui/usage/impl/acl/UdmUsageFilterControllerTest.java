package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/04/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilterControllerTest {

    private final UdmUsageFilterController controller = new UdmUsageFilterController();

    private IUdmBatchService udmBatchService;

    @Before
    public void setUp() {
        udmBatchService = createMock(IUdmBatchService.class);
        Whitebox.setInternalState(controller, udmBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202006);
        expect(udmBatchService.getPeriods()).andReturn(periods).once();
        replay(udmBatchService);
        assertEquals(periods, controller.getPeriods());
        verify(udmBatchService);
    }

    @Test
    public void testGetUdmBatchesForFilter() {
        List<UdmBatch> udmBatches = Collections.singletonList(new UdmBatch());
        expect(udmBatchService.getUdmBatches()).andReturn(udmBatches).once();
        replay(udmBatchService);
        assertEquals(udmBatches, controller.getUdmBatches());
        verify(udmBatchService);
    }
}
