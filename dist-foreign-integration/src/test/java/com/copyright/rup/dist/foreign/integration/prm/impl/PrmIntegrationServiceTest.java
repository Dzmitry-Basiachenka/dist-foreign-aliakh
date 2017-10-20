package com.copyright.rup.dist.foreign.integration.prm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRollUpService;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link PrmIntegrationService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public class PrmIntegrationServiceTest {

    private static final long ACCOUNT_NUMBER = 1000001863L;
    private static final String RIGHTSHOLDER_NAME = "CANADIAN CERAMIC SOCIETY";
    private IPrmRightsholderService prmRightsholderService;
    private PrmIntegrationService prmIntegrationService;
    private IPrmRollUpService prmRollUpService;

    @Before
    public void setUp() {
        prmIntegrationService = new PrmIntegrationService();
        prmRightsholderService = createMock(IPrmRightsholderService.class);
        prmRollUpService = createMock(IPrmRollUpService.class);
        Whitebox.setInternalState(prmIntegrationService, "prmRightsholderService", prmRightsholderService);
        Whitebox.setInternalState(prmIntegrationService, "prmRollUpService", prmRollUpService);
    }

    @Test
    public void testGetRightsholders() {
        Set<Long> accountNumbers = Sets.newHashSet(ACCOUNT_NUMBER);
        Rightsholder rightsholder = buildRightsholder(ACCOUNT_NUMBER, RIGHTSHOLDER_NAME);
        expect(prmRightsholderService.getRightsholders(accountNumbers))
            .andReturn(Collections.singletonList(rightsholder)).once();
        replay(prmRightsholderService);
        List<Rightsholder> actualResult = prmIntegrationService.getRightsholders(accountNumbers);
        assertFalse(actualResult.isEmpty());
        assertEquals(1, actualResult.size());
        assertTrue(actualResult.contains(rightsholder));
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRightsholdersForEmptySet() {
        expect(prmRightsholderService.getRightsholders(Sets.newHashSet())).andReturn(Lists.newArrayList()).once();
        replay(prmRightsholderService);
        assertTrue(prmIntegrationService.getRightsholders(Collections.emptySet()).isEmpty());
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRightsholderName() {
        expect(prmRightsholderService.getRightsholders(Sets.newHashSet(ACCOUNT_NUMBER)))
            .andReturn(Collections.singletonList(buildRightsholder(ACCOUNT_NUMBER, RIGHTSHOLDER_NAME))).once();
        replay(prmRightsholderService);
        Rightsholder rightsholder = prmIntegrationService.getRightsholder(ACCOUNT_NUMBER);
        assertEquals(ACCOUNT_NUMBER, rightsholder.getAccountNumber(), 0);
        assertEquals(RIGHTSHOLDER_NAME, rightsholder.getName());
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRightsholderNameNotPresentedInPrm() {
        expect(prmRightsholderService.getRightsholders(Sets.newHashSet(ACCOUNT_NUMBER)))
            .andReturn(Collections.emptyList()).once();
        replay(prmRightsholderService);
        assertNull(prmIntegrationService.getRightsholder(ACCOUNT_NUMBER));
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRollUps() {
        String rightsholderId = RupPersistUtils.generateUuid();
        Set<String> rightsholdersIds = Collections.singleton(rightsholderId);
        HashBasedTable<String, String, Long> result = HashBasedTable.create(1, 1);
        result.put(rightsholderId, "FAS", ACCOUNT_NUMBER);
        expect(prmRollUpService.getRollUps(rightsholdersIds)).andReturn(result).once();
        replay(prmRollUpService);
        assertEquals(result, prmIntegrationService.getRollUps(rightsholdersIds));
        verify(prmRollUpService);
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RupPersistUtils.generateUuid());
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
