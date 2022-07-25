package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link AclScenarioUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Ihar Suvorau
 */
public class AclScenarioUsageServiceTest {

    private static final String SCENARIO_UID = "c3077cca-09a0-454f-8b9f-bf6ecb2fbe66";
    private static final String USER_NAME = "SYSTEM";

    private IAclScenarioUsageService aclScenarioUsageService;
    private IAclUsageRepository aclUsageRepository;
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    @Before
    public void setUp() {
        aclScenarioUsageService = new AclScenarioUsageService();
        aclUsageRepository = createMock(IAclUsageRepository.class);
        aclScenarioUsageRepository = createMock(IAclScenarioUsageRepository.class);
        Whitebox.setInternalState(aclScenarioUsageService, aclUsageRepository, aclScenarioUsageRepository);
    }

    @Test
    public void testAddUsagesToAclScenario() {
        AclScenario scenario = buildAclScenario();
        aclUsageRepository.addToAclScenario(scenario, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
         aclScenarioUsageService.addUsagesToAclScenario(scenario, USER_NAME);
        verify(aclUsageRepository);
    }

    @Test
    public void testAddScenarioShares() {
        AclScenario scenario = buildAclScenario();
        aclUsageRepository.addScenarioShares(scenario, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
        aclScenarioUsageService.addScenarioShares(scenario, USER_NAME);
        verify(aclUsageRepository);
    }

    @Test
    public void testPopulatePubTypeWeights() {
        aclUsageRepository.populatePubTypeWeights(SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
        aclScenarioUsageService.populatePubTypeWeights(SCENARIO_UID, USER_NAME);
        verify(aclUsageRepository);
    }

    @Test
    public void testCalculateScenarioShares() {
        aclUsageRepository.calculateScenarioShares(SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
        aclScenarioUsageService.calculateScenarioShares(SCENARIO_UID, USER_NAME);
        verify(aclUsageRepository);
    }

    @Test
    public void testCalculateScenarioAmounts() {
        aclUsageRepository.calculateScenarioAmounts(SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
        aclScenarioUsageService.calculateScenarioAmounts(SCENARIO_UID, USER_NAME);
        verify(aclUsageRepository);
    }

    @Test
    public void testGetAclRightsholderTotalsHoldersByScenarioId() {
        List<AclRightsholderTotalsHolder> holders = Collections.singletonList(new AclRightsholderTotalsHolder());
        expect(aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(SCENARIO_UID, StringUtils.EMPTY,
            null, null)).andReturn(holders).once();
        replay(aclScenarioUsageRepository);
        assertSame(holders, aclScenarioUsageService.getAclRightsholderTotalsHoldersByScenarioId(SCENARIO_UID,
            StringUtils.EMPTY, null, null));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetAclRightsholderTotalsHolderCountByScenarioId() {
        expect(aclScenarioUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(
            SCENARIO_UID, StringUtils.EMPTY)).andReturn(5).once();
        replay(aclScenarioUsageRepository);
        assertEquals(5, aclScenarioUsageService.getAclRightsholderTotalsHolderCountByScenarioId(SCENARIO_UID,
            StringUtils.EMPTY));
        verify(aclScenarioUsageRepository);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("Description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType("ACL");
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }
}
