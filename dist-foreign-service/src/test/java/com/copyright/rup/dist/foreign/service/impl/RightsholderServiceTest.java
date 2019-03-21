package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Verifies {@link RightsholderService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
public class RightsholderServiceTest {

    private static final Long ACCOUNT_NUMBER_1 = 7000813806L;
    private static final String RIGHTSHOLDER_NAME = "Rightsholder Name";
    private IRightsholderRepository rightsholderRepository;
    private RightsholderService rightsholderService;

    @Before
    public void setUp() {
        rightsholderRepository = createMock(IRightsholderRepository.class);
        IPrmRightsholderService prmRightsholderService = createMock(IPrmRightsholderService.class);
        rightsholderService = new RightsholderService(rightsholderRepository, prmRightsholderService);
    }

    @Test
    public void testGetRros() {
        List<Rightsholder> rros = Collections.singletonList(new Rightsholder());
        expect(rightsholderRepository.findRros("FAS")).andReturn(rros).once();
        replay(rightsholderRepository);
        assertEquals(rros, rightsholderService.getRros("FAS"));
        verify(rightsholderRepository);
    }

    @Test
    public void testUpdateRightsholder() {
        Rightsholder rightsholder = buildRightsholder(ACCOUNT_NUMBER_1);
        rightsholderRepository.deleteByAccountNumber(rightsholder.getAccountNumber());
        expectLastCall().once();
        rightsholderRepository.insert(rightsholder);
        expectLastCall().once();
        replay(rightsholderRepository);
        rightsholderService.updateRightsholder(rightsholder);
        verify(rightsholderRepository);
    }

    @Test
    public void testGetFromUsages() {
        Pageable pageable = new Pageable(0, 10);
        expect(rightsholderRepository.findFromUsages("10001", pageable, null))
            .andReturn(Collections.emptyList()).once();
        replay(rightsholderRepository);
        assertEquals(Collections.emptyList(), rightsholderService.getFromUsages("10001", pageable, null));
        verify(rightsholderRepository);
    }

    @Test
    public void testGetCountFromUsages() {
        expect(rightsholderRepository.findCountFromUsages("Rightsholder")).andReturn(5).once();
        replay(rightsholderRepository);
        assertEquals(5, rightsholderService.getCountFromUsages("Rightsholder"));
        verify(rightsholderRepository);
    }

    @Test
    public void testUpdateRighstholdersAsync() {
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(rightsholderService, "executorService", executorService);
        Capture<Runnable> runnableCapture = new Capture<>();
        Set<Long> accountNumbers = Collections.singleton(541236485L);
        executorService.execute(capture(runnableCapture));
        expectLastCall().once();
        replay(executorService);
        rightsholderService.updateRighstholdersAsync(accountNumbers);
        assertNotNull(runnableCapture.getValue());
        verify(executorService);
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(RIGHTSHOLDER_NAME);
        return rightsholder;
    }
}
