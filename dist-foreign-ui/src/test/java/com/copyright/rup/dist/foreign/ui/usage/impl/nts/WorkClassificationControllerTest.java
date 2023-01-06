package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.IWorkClassificationController;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
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

    private static final String SEARCH_VALUE = "test";
    private static final Set<String> BATCHES_IDS = Collections.singleton(RupPersistUtils.generateUuid());
    private IWorkClassificationService workClassificationService;
    private IWorkClassificationController controller;

    @Before
    public void setUp() {
        controller = new WorkClassificationController();
        workClassificationService = createMock(IWorkClassificationService.class);
        Whitebox.setInternalState(controller, workClassificationService);
    }

    @Test
    public void testGetWorkClassificationThreshold() {
        expect(workClassificationService.getWorkClassificationThreshold()).andReturn(10).once();
        replay(workClassificationService);
        assertEquals(10, controller.getWorkClassificationThreshold());
        verify(workClassificationService);
    }

    @Test
    public void testGetClassificationCount() {
        expect(workClassificationService.getClassificationCount(BATCHES_IDS, SEARCH_VALUE))
            .andReturn(1).once();
        replay(workClassificationService);
        assertEquals(1, controller.getClassificationCount(BATCHES_IDS, SEARCH_VALUE));
        verify(workClassificationService);
    }

    @Test
    public void testGetClassifications() {
        Capture<Pageable> pageableCapture = newCapture();
        List<WorkClassification> classifications = List.of(new WorkClassification());
        expect(workClassificationService.getClassifications(eq(BATCHES_IDS), eq(SEARCH_VALUE),
            capture(pageableCapture), isNull())).andReturn(classifications).once();
        replay(workClassificationService);
        assertSame(classifications, controller.getClassifications(BATCHES_IDS, SEARCH_VALUE, 10, 20, null));
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(20, pageable.getLimit());
        verify(workClassificationService);
    }
}
