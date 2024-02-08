package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IWorkClassificationController;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link WorkClassificationController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/15/2019
 *
 * @author Ihar Suvorau
 */
public class WorkClassificationControllerTest {

    private static final Set<String> BATCHES_IDS = Set.of("aa850c45-06db-4c9b-b9db-9e1f76ae6eb8");
    private static final String SEARCH_VALUE = "search";

    private IWorkClassificationService service;
    private IWorkClassificationController controller;

    @Before
    public void setUp() {
        controller = new WorkClassificationController();
        service = createMock(IWorkClassificationService.class);
        Whitebox.setInternalState(controller, service);
    }

    @Test
    public void testGetWorkClassificationThreshold() {
        expect(service.getWorkClassificationThreshold()).andReturn(10).once();
        replay(service);
        assertEquals(10, controller.getWorkClassificationThreshold());
        verify(service);
    }

    @Test
    public void testGetClassificationCount() {
        expect(service.getClassificationCount(BATCHES_IDS, SEARCH_VALUE)).andReturn(1).once();
        replay(service);
        assertEquals(1, controller.getClassificationCount(BATCHES_IDS, SEARCH_VALUE));
        verify(service);
    }

    @Test
    public void testGetClassifications() {
        Capture<Pageable> pageableCapture = newCapture();
        var classifications = List.of(new WorkClassification());
        expect(service.getClassifications(eq(BATCHES_IDS), eq(SEARCH_VALUE), capture(pageableCapture), isNull()))
            .andReturn(classifications).once();
        replay(service);
        assertSame(classifications, controller.getClassifications(BATCHES_IDS, SEARCH_VALUE, 10, 20, null));
        var pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(20, pageable.getLimit());
        verify(service);
    }
}
