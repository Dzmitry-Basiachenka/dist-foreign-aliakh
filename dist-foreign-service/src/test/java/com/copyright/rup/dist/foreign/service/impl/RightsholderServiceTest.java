package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String RIGHTSHOLDER_NAME = "Rightsholder Name";
    private IRightsholderRepository rightsholderRepository;
    private IPrmIntegrationService prmIntegrationService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        rightsholderRepository = createMock(IRightsholderRepository.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        rightsholderService = new RightsholderService();
        Whitebox.setInternalState(rightsholderService, rightsholderRepository);
        Whitebox.setInternalState(rightsholderService, prmIntegrationService);
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
    public void testUpdateRightsholdersInformation() {
        Set<Long> accountNumbers = Collections.singleton(ACCOUNT_NUMBER);
        Rightsholder rightsholder = buildRightsholder();
        expect(rightsholderRepository.findAccountNumbers()).andReturn(accountNumbers).once();
        expect(prmIntegrationService.getRightsholders(accountNumbers))
            .andReturn(Collections.singletonList(rightsholder)).once();
        rightsholderRepository.deleteAll();
        expectLastCall().once();
        rightsholderRepository.insert(rightsholder);
        expectLastCall().once();
        replay(rightsholderRepository, prmIntegrationService);
        assertEquals(1, rightsholderService.updateRightsholdersInformation());
        verify(rightsholderRepository, prmIntegrationService);
    }

    @Test
    public void testUpdateRightsholder() {
        Rightsholder rightsholder = buildRightsholder();
        rightsholderRepository.deleteByAccountNumber(rightsholder.getAccountNumber());
        expectLastCall().once();
        rightsholderRepository.insert(rightsholder);
        expectLastCall().once();
        replay(rightsholderRepository);
        rightsholderService.updateRightsholder(rightsholder);
        verify(rightsholderRepository);
    }

    @Test
    public void testUpdateNullRightsholder() {
        replay(rightsholderRepository);
        rightsholderService.updateRightsholder(null);
        verify(rightsholderRepository);
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(ACCOUNT_NUMBER);
        rightsholder.setName(RIGHTSHOLDER_NAME);
        return rightsholder;
    }
}
