package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private static final String SCENARIO_UID = "c3077cca-09a0-454f-8b9f-bf6ecb2fbe66";
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
        List<AclRightsholderTotalsHolder> holders = List.of(new AclRightsholderTotalsHolder());
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
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(new AclScenarioDetailDto());
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
    public void testGetByScenarioId() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(new AclScenarioDetailDto());
        Pageable pageable = new Pageable(0, 2);
        expect(aclScenarioUsageRepository.findByScenarioId(SCENARIO_UID, SEARCH_VALUE, pageable, null))
            .andReturn(scenarioDetailDtos).once();
        replay(aclScenarioUsageRepository);
        assertSame(scenarioDetailDtos, aclScenarioUsageService.getByScenarioId(SCENARIO_UID, SEARCH_VALUE,
            pageable, null));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetCountByScenarioId() {
        expect(aclScenarioUsageRepository.findCountByScenarioId(SCENARIO_UID, SEARCH_VALUE)).andReturn(1).once();
        replay(aclScenarioUsageRepository);
        assertEquals(1, aclScenarioUsageService.getCountByScenarioId(SCENARIO_UID, SEARCH_VALUE));
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
        List<AclRightsholderTotalsHolderDto> holderDtos = List.of(buildAclRightsholderTotalsHolderDto());
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageRepository.findRightsholderTitleResults(filter)).andReturn(holderDtos).once();
        replay(aclScenarioUsageRepository);
        assertSame(holderDtos, aclScenarioUsageService.getRightsholderTitleResults(filter));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testFindRightsholderAggLcClassResults() {
        List<AclRightsholderTotalsHolderDto> holderDtos = List.of(buildAclRightsholderTotalsHolderDto());
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(aclScenarioUsageRepository.findRightsholderAggLcClassResults(filter)).andReturn(holderDtos).once();
        replay(aclScenarioUsageRepository);
        assertSame(holderDtos, aclScenarioUsageService.getRightsholderAggLcClassResults(filter));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetRightsholderPayeeProductFamilyHoldersByAclScenarioIds() {
        Set<String> scenarioIds = Set.of(SCENARIO_UID);
        List<RightsholderPayeeProductFamilyHolder> holders = List.of(new RightsholderPayeeProductFamilyHolder());
        expect(aclScenarioUsageRepository.findRightsholderPayeeProductFamilyHoldersByAclScenarioIds(scenarioIds))
            .andReturn(holders).once();
        replay(aclScenarioUsageRepository);
        assertSame(holders,
            aclScenarioUsageService.getRightsholderPayeeProductFamilyHoldersByAclScenarioIds(scenarioIds));
        verify(aclScenarioUsageRepository);
    }

    @Test
    public void testGetArchivedLiabilityDetailsForSendToLmByIds() {
        AclScenarioLiabilityDetail liabilityDetail = buildLiabilityDetail();
        expect(aclScenarioUsageRepository.findForSendToLmByScenarioId(SCENARIO_UID)).andReturn(
            List.of(liabilityDetail)).once();
        replay(aclScenarioUsageRepository);
        assertEquals(liabilityDetail,
            aclScenarioUsageService.getLiabilityDetailsForSendToLmByIds(SCENARIO_UID).get(0));
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

    private AclScenarioLiabilityDetail buildLiabilityDetail() {
        AclScenarioLiabilityDetail liabilityDetail = new AclScenarioLiabilityDetail();
        liabilityDetail.setLiabilityDetailId("0128f1fd-e2ae-4179-8e7e-12fd2a1c590d");
        liabilityDetail.setRightsholderId("07529566-6ce4-11e9-a923-1681be663d3e");
        liabilityDetail.setNetAmount(new BigDecimal("100.00"));
        liabilityDetail.setServiceFeeAmount(new BigDecimal("20.00"));
        liabilityDetail.setGrossAmount(new BigDecimal("120.00"));
        liabilityDetail.setSystemTitle("System title");
        liabilityDetail.setWrWrkInst(123456789L);
        liabilityDetail.setProductFamily("ACLPRINT");
        liabilityDetail.setTypeOfUse("PRINT");
        liabilityDetail.setLicenseType("ACL");
        liabilityDetail.setAggregateLicenseeClassName("Materials");
        return liabilityDetail;
    }
}
