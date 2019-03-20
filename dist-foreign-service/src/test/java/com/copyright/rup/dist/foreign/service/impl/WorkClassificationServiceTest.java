package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link WorkClassificationService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
public class WorkClassificationServiceTest {

    private WorkClassificationService workClassificationService;
    private IWorkClassificationRepository workClassificationRepositoryMock;

    @Before
    public void setUp() {
        workClassificationService = new WorkClassificationService();
        workClassificationRepositoryMock = createMock(IWorkClassificationRepository.class);
        workClassificationService.setWorkClassificationRepository(workClassificationRepositoryMock);
    }

    @Test
    public void testGetClassification() {
        expect(workClassificationRepositoryMock.findClassificationByWrWrkInst(1123123213L)).andReturn("NON-STM").once();
        replay(workClassificationRepositoryMock);
        assertEquals("NON-STM", workClassificationService.getClassification(1123123213L));
        verify(workClassificationRepositoryMock);
    }
}
