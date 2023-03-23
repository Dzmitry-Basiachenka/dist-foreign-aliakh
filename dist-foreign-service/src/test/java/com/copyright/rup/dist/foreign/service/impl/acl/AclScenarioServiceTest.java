package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclScenarioService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class AclScenarioServiceTest {

    private static final String SCENARIO_UID = "732f1f1f-1d63-45a4-9f07-357cba3429fc";
    private static final String SCENARIO_NAME = "ACL Scenario";
    private static final String FUND_POOL_ID = "8395a319-2369-4b77-9290-e26e7489fe7c";
    private static final String GRANT_SET_ID = "f91d6b51-4475-49e8-bca1-fea2ff28118f";
    private static final String BATCH_UID = "50cc0539-d390-4b8a-8067-409368fff896";
    private static final String FUND_POOL_UID = "d90e2183-1a50-403b-9834-a283a8f568d0";
    private static final String GRANT_SET_UID = "35cea075-edf1-4355-a2e2-ff9dc2ce5db1";
    private static final String USER_NAME = "SYSTEM";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final String PRINT_TOU = "PRINT";
    private static final String LICENSE_TYPE = "ACL";

    private IAclScenarioService aclScenarioService;
    private IAclScenarioUsageService aclScenarioUsageService;
    private IAclScenarioRepository aclScenarioRepository;
    private IAclFundPoolService aclFundPoolService;
    private IAclUsageService aclUsageService;
    private IAclScenarioAuditService aclScenarioAuditService;
    private ILmIntegrationService lmIntegrationService;

    @Before
    public void setUp() {
        aclScenarioRepository = createMock(IAclScenarioRepository.class);
        aclFundPoolService = createMock(IAclFundPoolService.class);
        aclUsageService = createMock(IAclUsageService.class);
        aclScenarioAuditService = createMock(IAclScenarioAuditService.class);
        aclScenarioUsageService = createMock(IAclScenarioUsageService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        aclScenarioService = new AclScenarioService();
        Whitebox.setInternalState(aclScenarioService, aclScenarioRepository);
        Whitebox.setInternalState(aclScenarioService, aclFundPoolService);
        Whitebox.setInternalState(aclScenarioService, aclUsageService);
        Whitebox.setInternalState(aclScenarioService, aclScenarioUsageService);
        Whitebox.setInternalState(aclScenarioService, aclScenarioAuditService);
        Whitebox.setInternalState(aclScenarioService, lmIntegrationService);
        Whitebox.setInternalState(aclScenarioService, "batchSize", 1);
    }

    @Test
    public void testGetScenarios() {
        AclScenarioFilter filter = new AclScenarioFilter();
        List<AclScenario> scenarios = List.of(buildAclScenario());
        expect(aclScenarioRepository.findByFilter(filter)).andReturn(scenarios).once();
        replay(aclScenarioRepository);
        assertSame(scenarios, aclScenarioService.getScenarios(filter));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetScenariosByPeriod() {
        List<AclScenario> scenarios = List.of(buildAclScenario());
        expect(aclScenarioRepository.findByPeriod(202012)).andReturn(scenarios).once();
        replay(aclScenarioRepository);
        assertSame(scenarios, aclScenarioService.getScenariosByPeriod(202012));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetScenarioPeriods() {
        List<Integer> periods = List.of(202112);
        expect(aclScenarioRepository.findPeriods()).andReturn(periods).once();
        replay(aclScenarioRepository);
        assertSame(periods, aclScenarioService.getScenarioPeriods());
        verify(aclScenarioRepository);
    }

    @Test
    public void testInsertAclScenarioPubTypeWeights() {
        AclPublicationType publicationType = buildPublicationType("Book", new BigDecimal("3.12"));
        aclScenarioRepository.insertAclScenarioPubTypeWeight(publicationType, SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioRepository);
        aclScenarioService.insertAclScenarioPubTypeWeights(List.of(publicationType), SCENARIO_UID, USER_NAME);
        verify(aclScenarioRepository);
    }

    @Test
    public void testInsertAclScenarioLicenseeClasses() {
        DetailLicenseeClass licenseeClass = buildDetailLicenseeClass(108, 141);
        aclScenarioRepository.insertAclScenarioLicenseeClass(licenseeClass, SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioRepository);
        aclScenarioService.insertAclScenarioLicenseeClasses(List.of(licenseeClass), SCENARIO_UID, USER_NAME);
        verify(aclScenarioRepository);
    }

    @Test
    public void testInsertAclScenarioUsageAgeWeights() {
        UsageAge usageAge = buildUsageAge(0, new BigDecimal("1.00"));
        aclScenarioRepository.insertAclScenarioUsageAgeWeight(usageAge, SCENARIO_UID, USER_NAME);
        expectLastCall().once();
        replay(aclScenarioRepository);
        aclScenarioService.insertAclScenarioUsageAgeWeights(List.of(usageAge), SCENARIO_UID, USER_NAME);
        verify(aclScenarioRepository);
    }

    @Test
    public void testAclScenarioExists() {
        expect(aclScenarioRepository.findCountByName("ACL Scenario 2022")).andReturn(1).once();
        replay(aclScenarioRepository);
        assertTrue(aclScenarioService.aclScenarioExists("ACL Scenario 2022"));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetScenarioNamesByUsageBatchId() {
        List<String> scenarioNames = List.of(SCENARIO_NAME);
        expect(aclScenarioRepository.findScenarioNamesByUsageBatchId(BATCH_UID)).andReturn(scenarioNames).once();
        replay(aclScenarioRepository);
        assertSame(scenarioNames, aclScenarioRepository.findScenarioNamesByUsageBatchId(BATCH_UID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetScenarioNamesByFundPoolId() {
        List<String> scenarioNames = List.of(SCENARIO_NAME);
        expect(aclScenarioRepository.findScenarioNamesByFundPoolId(FUND_POOL_ID)).andReturn(scenarioNames).once();
        replay(aclScenarioRepository);
        assertSame(scenarioNames, aclScenarioRepository.findScenarioNamesByFundPoolId(FUND_POOL_ID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetScenarioNamesByGrantSetId() {
        List<String> scenarioNames = List.of(SCENARIO_NAME);
        expect(aclScenarioRepository.findScenarioNamesByGrantSetId(GRANT_SET_ID)).andReturn(scenarioNames).once();
        replay(aclScenarioRepository);
        assertSame(scenarioNames, aclScenarioRepository.findScenarioNamesByGrantSetId(GRANT_SET_ID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetFundPoolDetailsNotToBeDistributedValidCase() {
        List<DetailLicenseeClass> mapping =
            List.of(buildDetailLicenseeClass(1, 1), buildDetailLicenseeClass(2, 51), buildDetailLicenseeClass(3, 51));
        List<AclFundPoolDetailDto> fundPoolDetails =
            List.of(buildAclFundPoolDetailDto(1, 1, PRINT_TOU), buildAclFundPoolDetailDto(2, 51, DIGITAL_TOU));
        expect(aclFundPoolService.getDetailDtosByFundPoolId(FUND_POOL_UID)).andReturn(fundPoolDetails).once();
        expect(aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(BATCH_UID, GRANT_SET_UID,
            Set.of(1), PRINT_TOU)).andReturn(true).once();
        expect(aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(BATCH_UID, GRANT_SET_UID,
            Set.of(2, 3), DIGITAL_TOU)).andReturn(true).once();
        replay(aclFundPoolService, aclUsageService);
        Set<AclFundPoolDetailDto> invalidDetails =
            aclScenarioService.getFundPoolDetailsNotToBeDistributed(BATCH_UID, FUND_POOL_UID, GRANT_SET_UID, mapping);
        assertEquals(0, invalidDetails.size());
        verify(aclFundPoolService, aclUsageService);
    }

    @Test
    public void testGetFundPoolDetailsNotToBeDistributedInvalidNoUsages() {
        List<DetailLicenseeClass> mapping =
            List.of(buildDetailLicenseeClass(1, 1), buildDetailLicenseeClass(2, 51), buildDetailLicenseeClass(3, 51));
        AclFundPoolDetailDto noUsagesDetail = buildAclFundPoolDetailDto(2, 51, DIGITAL_TOU);
        List<AclFundPoolDetailDto> fundPoolDetails = List.of(buildAclFundPoolDetailDto(1, 1, PRINT_TOU),
            noUsagesDetail, buildAclFundPoolDetailDto(3, 51, DIGITAL_TOU));
        expect(aclFundPoolService.getDetailDtosByFundPoolId(FUND_POOL_UID)).andReturn(fundPoolDetails).once();
        expect(aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(BATCH_UID, GRANT_SET_UID,
            Set.of(1), PRINT_TOU)).andReturn(true).once();
        expect(aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(BATCH_UID, GRANT_SET_UID,
            Set.of(2, 3), DIGITAL_TOU)).andReturn(false).once();
        replay(aclFundPoolService, aclUsageService);
        Set<AclFundPoolDetailDto> invalidDetails =
            aclScenarioService.getFundPoolDetailsNotToBeDistributed(BATCH_UID, FUND_POOL_UID, GRANT_SET_UID, mapping);
        assertEquals(1, invalidDetails.size());
        assertTrue(invalidDetails.contains(noUsagesDetail));
        verify(aclFundPoolService, aclUsageService);
    }

    @Test
    public void testGetScenarioById() {
        AclScenario scenario = buildAclScenario();
        expect(aclScenarioRepository.findById(SCENARIO_UID)).andReturn(scenario).once();
        replay(aclScenarioRepository);
        assertSame(scenario, aclScenarioService.getScenarioById(SCENARIO_UID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetUsageAgeWeightsByScenarioId() {
        List<UsageAge> usageAges = List.of();
        expect(aclScenarioRepository.findUsageAgeWeightsByScenarioId(SCENARIO_UID)).andReturn(usageAges).once();
        replay(aclScenarioRepository);
        assertSame(usageAges, aclScenarioService.getUsageAgeWeightsByScenarioId(SCENARIO_UID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetAclPublicationTypesByScenarioId() {
        List<AclPublicationType> publicationTypes = List.of();
        expect(aclScenarioRepository.findAclPublicationTypesByScenarioId(SCENARIO_UID)).andReturn(publicationTypes)
            .once();
        replay(aclScenarioRepository);
        assertSame(publicationTypes, aclScenarioService.getAclPublicationTypesByScenarioId(SCENARIO_UID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetDetailLicenseeClassesByScenarioId() {
        List<DetailLicenseeClass> licenseeClasses = List.of();
        expect(aclScenarioRepository.findDetailLicenseeClassesByScenarioId(SCENARIO_UID)).andReturn(licenseeClasses)
            .once();
        replay(aclScenarioRepository);
        assertSame(licenseeClasses, aclScenarioService.getDetailLicenseeClassesByScenarioId(SCENARIO_UID));
        verify(aclScenarioRepository);
    }

    @Test
    public void testDeleteScenario() {
        aclScenarioAuditService.deleteActions(SCENARIO_UID);
        expectLastCall().once();
        aclScenarioRepository.deleteScenarioData(SCENARIO_UID);
        expectLastCall().once();
        aclScenarioRepository.deleteScenario(SCENARIO_UID);
        expectLastCall().once();
        replay(aclScenarioAuditService, aclScenarioRepository);
        aclScenarioService.deleteAclScenario(buildAclScenario());
        verify(aclScenarioAuditService, aclScenarioRepository);
    }

    @Test
    public void testGetAclScenariosByStatuses() {
        List<Scenario> scenarios = List.of(new Scenario());
        expect(aclScenarioRepository.findAclScenariosByStatuses(EnumSet.of(ScenarioStatusEnum.IN_PROGRESS)))
            .andReturn(scenarios).once();
        replay(aclScenarioRepository);
        assertSame(scenarios, aclScenarioService.getAclScenariosByStatuses(EnumSet.of(ScenarioStatusEnum.IN_PROGRESS)));
        verify(aclScenarioRepository);
    }

    @Test
    public void testIsExistsScenarioInSubmitStatus() {
        AclScenario scenario = buildAclScenario();
        expect(aclScenarioRepository.submittedScenarioExistWithLicenseTypeAndPeriod(LICENSE_TYPE, 202212))
            .andReturn(false).once();
        replay(aclScenarioRepository);
        assertTrue(aclScenarioService.isNotExistsSubmittedScenario(scenario));
        verify(aclScenarioRepository);
    }

    @Test
    public void testChangeScenarioState() {
        mockStatic(RupContextUtils.class);
        AclScenario scenario = buildAclScenario();
        expect(RupContextUtils.getUserName()).andReturn("SYSTEM").once();
        aclScenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        aclScenarioAuditService.logAction(SCENARIO_UID, ScenarioActionTypeEnum.SUBMITTED, "reason");
        expectLastCall().once();
        replay(aclScenarioRepository, aclScenarioAuditService, RupContextUtils.class);
        aclScenarioService.changeScenarioState(scenario, ScenarioStatusEnum.SUBMITTED, ScenarioActionTypeEnum.SUBMITTED,
            "reason");
        verify(aclScenarioRepository, aclScenarioAuditService, RupContextUtils.class);
    }

    @Test
    public void testSendToLm() {
        AclScenarioLiabilityDetail liabilityDetail = buildLiabilityDetail();
        AclScenario aclScenario = buildAclScenario();
        expect(aclScenarioUsageService.getLiabilityDetailsForSendToLmByIds(aclScenario.getId()))
            .andReturn(List.of(liabilityDetail)).once();
        lmIntegrationService.sendToLm(List.of(new ExternalUsage(liabilityDetail)), aclScenario.getId(),
            aclScenario.getName(), "ACL", 1);
        expectLastCall().once();
        aclScenarioAuditService.logAction(aclScenario.getId(), ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        expectLastCall().once();
        aclScenarioRepository.updateStatus(aclScenario);
        expectLastCall().once();
        aclScenarioAuditService.logAction(aclScenario.getId(), ScenarioActionTypeEnum.ARCHIVED, StringUtils.EMPTY);
        expectLastCall().once();
        replay(aclScenarioRepository, aclScenarioAuditService, aclScenarioUsageService, lmIntegrationService,
            RupContextUtils.class);
        aclScenarioService.sendToLm(aclScenario);
        assertEquals(ScenarioStatusEnum.ARCHIVED, aclScenario.getStatus());
        verify(aclScenarioRepository, aclScenarioAuditService, aclScenarioUsageService, lmIntegrationService,
            RupContextUtils.class);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("Description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType(LICENSE_TYPE);
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private AclPublicationType buildPublicationType(String name, BigDecimal weight) {
        AclPublicationType publicationType = new AclPublicationType();
        publicationType.setName(name);
        publicationType.setWeight(weight);
        return publicationType;
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detailClassId, Integer aggregateClassId) {
        DetailLicenseeClass detailClass = new DetailLicenseeClass();
        detailClass.setId(detailClassId);
        detailClass.getAggregateLicenseeClass().setId(aggregateClassId);
        return detailClass;
    }

    private AclFundPoolDetailDto buildAclFundPoolDetailDto(Integer detailClassId, Integer aggregateClassId,
                                                           String typeOfUse) {
        AclFundPoolDetailDto detail = new AclFundPoolDetailDto();
        detail.getDetailLicenseeClass().setId(detailClassId);
        detail.getAggregateLicenseeClass().setId(aggregateClassId);
        detail.setTypeOfUse(typeOfUse);
        return detail;
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
        liabilityDetail.setLicenseType(LICENSE_TYPE);
        liabilityDetail.setAggregateLicenseeClassName("Materials");
        return liabilityDetail;
    }
}
