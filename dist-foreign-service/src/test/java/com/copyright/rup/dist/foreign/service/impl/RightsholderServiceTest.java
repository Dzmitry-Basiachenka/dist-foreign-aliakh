package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
 */
public class RightsholderServiceTest {

    private static final Long ACCOUNT_NUMBER_1 = 7000813806L;
    private static final Long ACCOUNT_NUMBER_2 = 2000017004L;
    private static final String RIGHTSHOLDER_NAME = "Rightsholder Name";
    private IRightsholderRepository rightsholderRepository;
    private IPrmIntegrationService prmIntegrationService;
    private RightsholderService rightsholderService;
    private ExecutorService executorService;

    @Before
    public void setUp() {
        rightsholderRepository = createMock(IRightsholderRepository.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        executorService = createMock(ExecutorService.class);
        rightsholderService = new RightsholderService();
        Whitebox.setInternalState(rightsholderService, rightsholderRepository);
        Whitebox.setInternalState(rightsholderService, prmIntegrationService);
        Whitebox.setInternalState(rightsholderService, executorService);
    }

    @Test
    public void testGetRros() {
        List<Rightsholder> rros = Collections.singletonList(new Rightsholder());
        expect(rightsholderRepository.findRros()).andReturn(rros).once();
        replay(rightsholderRepository);
        assertEquals(rros, rightsholderService.getRros());
        verify(rightsholderRepository);
    }

    @Test
    public void testUpdateRightsholders() {
        Set<Long> accountNumbers = Collections.singleton(ACCOUNT_NUMBER_1);
        Rightsholder rightsholder = buildRightsholder(ACCOUNT_NUMBER_1);
        List<Rightsholder> rightsholders = Lists.newArrayList(rightsholder);
        expect(rightsholderRepository.findAccountNumbers()).andReturn(accountNumbers).once();
        expect(prmIntegrationService.getRightsholders(accountNumbers)).andReturn(rightsholders).once();
        rightsholderRepository.deleteAll();
        expectLastCall().once();
        rightsholderRepository.insert(rightsholder);
        expectLastCall().once();
        replay(rightsholderRepository, prmIntegrationService);
        rightsholderService.updateRightsholders();
        verify(rightsholderRepository, prmIntegrationService);
    }

    @Test
    public void testUpdateRightsholdersEmptyRightsholdersList() {
        Set<Long> accountNumbers = Collections.singleton(ACCOUNT_NUMBER_1);
        expect(rightsholderRepository.findAccountNumbers()).andReturn(accountNumbers).once();
        expect(prmIntegrationService.getRightsholders(accountNumbers)).andReturn(Collections.emptyList()).once();
        replay(rightsholderRepository, prmIntegrationService);
        rightsholderService.updateRightsholders();
        verify(rightsholderRepository, prmIntegrationService);
    }

    @Test
    public void testDoUpdateRightsholders() {
        Rightsholder rightsholder = buildRightsholder(ACCOUNT_NUMBER_1);
        Capture<Set<Long>> accountsCapture = new Capture<>();
        Set<Long> accountNumbers = Sets.newHashSet(ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2);
        List<Rightsholder> rightsholders = Collections.singletonList(rightsholder);
        expect(rightsholderRepository.findRightsholdersByAccountNumbers(accountNumbers))
            .andReturn(Lists.newArrayList(buildRightsholder(ACCOUNT_NUMBER_2))).once();
        expect(prmIntegrationService.getRightsholders(capture(accountsCapture))).andReturn(rightsholders).once();
        rightsholderRepository.insert(rightsholder);
        expectLastCall().once();
        replay(rightsholderRepository, prmIntegrationService);
        rightsholderService.doUpdateRightsholders(accountNumbers);
        verify(rightsholderRepository, prmIntegrationService);
        Set<Long> value = accountsCapture.getValue();
        assertNotNull(value);
        assertEquals(1, value.size());
        assertTrue(value.contains(ACCOUNT_NUMBER_1));
    }

    @Test
    public void testGetRightsholders() throws Exception {
        Set<Long> accountNumbers = Sets.newHashSet(ACCOUNT_NUMBER_1);
        Capture<Runnable> taskCapture = new Capture<>();
        executorService.execute(capture(taskCapture));
        expectLastCall().once();
        replay(executorService);
        rightsholderService.updateRightsholders(accountNumbers);
        verify(executorService);
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

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(RIGHTSHOLDER_NAME);
        return rightsholder;
    }
}
