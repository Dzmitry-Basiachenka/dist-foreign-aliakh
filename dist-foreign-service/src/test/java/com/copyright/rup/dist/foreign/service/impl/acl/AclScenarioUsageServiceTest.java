package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
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
    private static final String RH_NAME = "CANADIAN CERAMIC SOCIETY";
    private static final String RH_UID = "378cafcf-cf21-4036-b223-5bd48b09c41f";
    private static final String SCENARIO_UID = "c3077cca-09a0-454f-8b9f-bf6ecb2fbe66";
    private static final String SEARCH_VALUE = "search";
    private static final String USER_NAME = "SYSTEM";

    private IAclScenarioUsageService aclScenarioUsageService;
    private IAclScenarioUsageRepository aclScenarioUsageRepository;
    private IRightsholderService rightsholderService;
    private IPrmIntegrationService prmIntegrationService;

    @Before
    public void setUp() {
        aclScenarioUsageService = new AclScenarioUsageService();
        aclScenarioUsageRepository = createMock(IAclScenarioUsageRepository.class);
        rightsholderService = createMock(IRightsholderService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(aclScenarioUsageService, aclScenarioUsageRepository);
        Whitebox.setInternalState(aclScenarioUsageService, rightsholderService);
        Whitebox.setInternalState(aclScenarioUsageService, prmIntegrationService);
    }

    @Test
    public void testPopulatePayees() {
        RightsholderTypeOfUsePair rightsholderTypeOfUsePair = buildRightsholderTypeOfUsePair(2000073957L);
        expect(rightsholderService.getByAclScenarioId(SCENARIO_UID)).andReturn(
            Collections.singletonList(rightsholderTypeOfUsePair)).once();
        expect(prmIntegrationService.getRollUps(Collections.singleton(RH_UID)))
            .andReturn(Collections.emptyMap()).once();
        aclScenarioUsageRepository.updatePayeeByAccountNumber(2000073957L, SCENARIO_UID, 2000073957L, "PRINT");
        expectLastCall().once();
        rightsholderService.updateRighstholdersAsync(Collections.singleton(2000073957L));
        expectLastCall().once();
        replay(aclScenarioUsageRepository, rightsholderService, prmIntegrationService);
        aclScenarioUsageService.populatePayees(SCENARIO_UID);
        verify(aclScenarioUsageRepository, rightsholderService, prmIntegrationService);
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
        expect(aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(SCENARIO_UID))
            .andReturn(holders).once();
        replay(aclScenarioUsageRepository);
        assertSame(holders, aclScenarioUsageService.getAclRightsholderTotalsHoldersByScenarioId(SCENARIO_UID));
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
    public void testGetRightsholderDetailsResults() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        List<AclScenarioDetailDto> scenarioDetails = new ArrayList<>();
        expect(aclScenarioUsageRepository.findRightsholderDetailsResults(filter)).andReturn(scenarioDetails).once();
        replay(aclScenarioUsageRepository);
        assertSame(scenarioDetails, aclScenarioUsageService.getRightsholderDetailsResults(filter));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testFindRightsholderTitleResults() {
        List<AclRightsholderTotalsHolderDto> holderDtos =
            Collections.singletonList(buildAclRightsholderTotalsHolderDto());
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageRepository.findRightsholderTitleResults(filter)).andReturn(holderDtos).once();
        replay(aclScenarioUsageRepository);
        assertSame(holderDtos, aclScenarioUsageService.getRightsholderTitleResults(filter));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testFindRightsholderAggLcClassResults() {
        List<AclRightsholderTotalsHolderDto> holderDtos =
            Collections.singletonList(buildAclRightsholderTotalsHolderDto());
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageRepository.findRightsholderAggLcClassResults(filter)).andReturn(holderDtos).once();
        replay(aclScenarioUsageRepository);
        assertSame(holderDtos, aclScenarioUsageService.getRightsholderAggLcClassResults(filter));
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

    private AclRightsholderTotalsHolderDto buildAclRightsholderTotalsHolderDto() {
        AclRightsholderTotalsHolderDto holder = new AclRightsholderTotalsHolderDto();
        holder.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        holder.getRightsholder().setName(RH_NAME);
        holder.setGrossTotalPrint(new BigDecimal("2000.0000000000"));
        holder.setNetTotalPrint(new BigDecimal("8300.0000000000"));
        holder.setGrossTotalDigital(new BigDecimal("1000.0000000000"));
        holder.setNetTotalDigital(new BigDecimal("2000.0000000000"));
        holder.setWrWrkInst(127778306L);
        holder.setSystemTitle("Adaptations");
        holder.setGrossTotal(new BigDecimal("3000.0000000000"));
        holder.setNetTotal(new BigDecimal("10300.0000000000"));
        return holder;
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        return filter;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RH_UID);
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(RH_NAME);
        return rightsholder;
    }

    private RightsholderTypeOfUsePair buildRightsholderTypeOfUsePair(Long accountNumber) {
        RightsholderTypeOfUsePair rightsholderTypeOfUsePair = new RightsholderTypeOfUsePair();
        rightsholderTypeOfUsePair.setRightsholder(buildRightsholder(accountNumber));
        rightsholderTypeOfUsePair.setTypeOfUse("PRINT");
        return rightsholderTypeOfUsePair;
    }
}
