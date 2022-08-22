package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

    private static final Long RH_ACCOUNT_NUMBER = 1000009422L;
    private static final String SCENARIO_UID = "c3077cca-09a0-454f-8b9f-bf6ecb2fbe66";
    private static final Long ACCOUNT_NUMBER = 1000001863L;
    private static final String TITLE = "Langmuir";
    private static final Integer AGG_LIC_CLASS_ID = 56;
    private static final String SEARCH_VALUE = "search";
    private static final String USER_NAME = "SYSTEM";

    private IAclScenarioUsageService aclScenarioUsageService;
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    @Before
    public void setUp() {
        aclScenarioUsageService = new AclScenarioUsageService();
        aclScenarioUsageRepository = createMock(IAclScenarioUsageRepository.class);
        Whitebox.setInternalState(aclScenarioUsageService, aclScenarioUsageRepository);
    }

    @Test
    public void testAddUsagesToAclScenario() {
        AclScenario scenario = buildAclScenario();
        aclScenarioUsageRepository.addToAclScenario(scenario, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioUsageRepository);
        aclScenarioUsageService.addUsagesToAclScenario(scenario, USER_NAME);
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testAddScenarioShares() {
        AclScenario scenario = buildAclScenario();
        aclScenarioUsageRepository.addScenarioShares(scenario, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioUsageRepository);
        aclScenarioUsageService.addScenarioShares(scenario, USER_NAME);
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testPopulatePubTypeWeights() {
        aclScenarioUsageRepository.populatePubTypeWeights(SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioUsageRepository);
        aclScenarioUsageService.populatePubTypeWeights(SCENARIO_UID, USER_NAME);
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testCalculateScenarioShares() {
        aclScenarioUsageRepository.calculateScenarioShares(SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioUsageRepository);
        aclScenarioUsageService.calculateScenarioShares(SCENARIO_UID, USER_NAME);
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testCalculateScenarioAmounts() {
        aclScenarioUsageRepository.calculateScenarioAmounts(SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioUsageRepository);
        aclScenarioUsageService.calculateScenarioAmounts(SCENARIO_UID, USER_NAME);
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testDeleteZeroAmountUsages() {
        aclScenarioUsageRepository.deleteZeroAmountShares(SCENARIO_UID);
        expectLastCall().once();
        aclScenarioUsageRepository.deleteZeroAmountUsages(SCENARIO_UID);
        expectLastCall().once();
        replay(aclScenarioUsageRepository);
        aclScenarioUsageService.deleteZeroAmountUsages(SCENARIO_UID);
        verify(aclScenarioUsageRepository);
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

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        AclScenarioDto scenario = new AclScenarioDto();
        scenario.setId(SCENARIO_UID);
        expect(aclScenarioUsageRepository.findWithAmountsAndLastAction(scenario.getId())).andReturn(scenario).once();
        replay(aclScenarioUsageRepository);
        assertSame(scenario, aclScenarioUsageService.getAclScenarioWithAmountsAndLastAction(scenario.getId()));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetByScenarioIdAndRhAccountNumber() {
        List<AclScenarioDetailDto> scenarioDetailDtos = Collections.singletonList(new AclScenarioDetailDto());
        Pageable pageable = new Pageable(0, 2);
        expect(aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_UID,
            SEARCH_VALUE, pageable, null)).andReturn(scenarioDetailDtos).once();
        replay(aclScenarioUsageRepository);
        assertSame(scenarioDetailDtos, aclScenarioUsageService.getByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER,
            SCENARIO_UID, SEARCH_VALUE, pageable, null));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetCountByScenarioIdAndRhAccountNumber() {
        expect(aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER, SCENARIO_UID,
            SEARCH_VALUE)).andReturn(1).once();
        replay(aclScenarioUsageRepository);
        assertEquals(1, aclScenarioUsageService.getCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER,
            SCENARIO_UID, SEARCH_VALUE));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass() {
        List<AclScenarioDetailDto> scenarioDetails = new ArrayList<>();
        expect(aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(
            SCENARIO_UID, ACCOUNT_NUMBER, TITLE, AGG_LIC_CLASS_ID)).andReturn(scenarioDetails).once();
        replay(aclScenarioUsageRepository);
        assertSame(scenarioDetails, aclScenarioUsageService.getByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(
            SCENARIO_UID, ACCOUNT_NUMBER, TITLE, AGG_LIC_CLASS_ID));
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
