package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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
        expect(rightsholderRepository.findRros()).andReturn(rros).once();
        replay(rightsholderRepository);
        assertEquals(rros, rightsholderService.getRros());
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
        expect(rightsholderRepository.findFromUsages()).andReturn(Collections.emptyList()).once();
        replay(rightsholderRepository);
        assertEquals(Collections.emptyList(), rightsholderService.getFromUsages());
        verify(rightsholderRepository);
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(RIGHTSHOLDER_NAME);
        return rightsholder;
    }
}
