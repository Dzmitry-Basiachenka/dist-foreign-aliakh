package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioShareDetail;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link AclScenarioUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/22/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclScenarioUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-scenario-usage-repository-integration-test/";
    private static final String FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID =
        FOLDER_NAME + "find-acl-rh-totals-holders-by-scenario-id.groovy";
    private static final String FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER =
        FOLDER_NAME + "find-by-scenario-id-and-rh-account-number.groovy";
    private static final String FIND_BY_SCENARIO_ID = FOLDER_NAME + "find-by-scenario-id.groovy";
    private static final String FIND_RIGHTSHOLDER_TITLE_RESULTS =
        FOLDER_NAME + "find-rightsholder-title-results.groovy";
    private static final String SCENARIO_UID_1 = "0d0041a3-833e-463e-8ad4-f28461dc961d";
    private static final String SCENARIO_UID_2 = "d18d7cab-8a69-4b60-af5a-0a0c99b8a4d3";
    private static final String SCENARIO_UID_3 = "53a1c4e8-f1fe-4b17-877e-2d721b2059b5";
    private static final String SCENARIO_UID_4 = "f473fa64-12ea-4db6-9d30-94087fe500fd";
    private static final String SCENARIO_UID_5 = "2a75aa1c-7bfa-4d8b-9e27-d2e17f3bd8da";
    private static final String SCENARIO_UID_6 = "8e93510e-a90c-4808-8d7c-f0b151f2c4a1";
    private static final String LICENSE_TYPE_ACL = "ACL";
    private static final Long RH_ACCOUNT_NUMBER_1 = 1000002859L;
    private static final Long RH_ACCOUNT_NUMBER_2 = 1000000001L;
    private static final String RH_ACCOUNT_NUMBER = "1000002859";
    private static final String INCOMPLETE_RH_ACCOUNT_NUMBER = "1000002";
    private static final String RH_NAME = "John Wiley & Sons - Books";
    private static final String SYSTEM_TITLE = "Technology review";
    private static final String USER_NAME = "user@copyright.com";
    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "add-to-scenario.groovy")
    public void testAddToAclScenario() {
        AclScenario scenario = buildAclScenario("dec62df4-6a8f-4c59-ad65-2a5e06b3924d",
            "11c6590a-cea4-4cb6-a3ce-0f23a6f2e81c", "0f65b9b0-308f-4f73-b232-773a98baba2e",
            "17970e6b-c020-4c84-9282-045ca465a8af", "ACL Scenario 202212", "Description",
            ScenarioStatusEnum.IN_PROGRESS, true, 202212, LICENSE_TYPE_ACL, USER_NAME, "2022-02-14T12:00:00+00:00");
        aclScenarioUsageRepository.addToAclScenario(scenario, "SYSTEM");
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("dec62df4-6a8f-4c59-ad65-2a5e06b3924d");
        assertEquals(1, scenarioDetails.size());
        AclScenarioDetail expectedScenarioDetail =
            loadExpectedAclScenarioDetails("json/acl/acl_scenario_detail_add_to_scenario.json").get(0);
        verifyAclScenarioDetail(expectedScenarioDetail, scenarioDetails.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "add-scenario-shares.groovy")
    public void testAddScenarioShares() {
        AclScenario scenario = buildAclScenario("17d43251-6637-41cb-8831-1bce47a7da85",
            "f74d4355-2f86-4168-a85d-9233f98ce0eb", "2a8c042c-1d66-469f-b4df-0987de0e308c",
            "6b821963-c0d1-41f4-8e97-a63f737c34fb", "ACL Scenario 202212", "Description",
            ScenarioStatusEnum.IN_PROGRESS, true, 202212, LICENSE_TYPE_ACL, USER_NAME, "2022-02-14T12:00:00+00:00");
        aclScenarioUsageRepository.addScenarioShares(scenario, USER_NAME);
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("17d43251-6637-41cb-8831-1bce47a7da85");
        assertEquals(2, scenarioDetails.size());
        Map<String, List<AclScenarioShareDetail>> detailSharesMap = new HashMap<>();
        detailSharesMap.put("df038efe-72c1-4081-88e7-17fa4fa5ff6a", List.of(
            buildAclScenarioShareDetail(1000028511L, PRINT_TOU, 6.0, 3.0)));
        detailSharesMap.put("8827d6c6-16d8-4102-b257-ce861ce77491", List.of(
            buildAclScenarioShareDetail(1000028511L, DIGITAL_TOU, 9.5, 5.0),
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 9.5, 5.0)));
        scenarioDetails.forEach(actualDetail -> {
            List<AclScenarioShareDetail> actualShareDetails = actualDetail.getScenarioShareDetails();
            List<AclScenarioShareDetail> expectedShareDetails = detailSharesMap.get(actualDetail.getId());
            assertEquals(expectedShareDetails.size(), actualShareDetails.size());
            IntStream.range(0, actualDetail.getScenarioShareDetails().size()).forEach(
                i -> verifyAclScenarioShareDetails(expectedShareDetails.get(i), actualShareDetails.get(i)));
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "populate-pub-type-weights.groovy")
    public void testPopulatePubTypeWeights() {
        aclScenarioUsageRepository.populatePubTypeWeights("66facb16-29aa-46ab-b99a-cdf303d4bb7d", USER_NAME);
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("66facb16-29aa-46ab-b99a-cdf303d4bb7d");
        assertEquals(5, scenarioDetails.size());
        assertEquals(new BigDecimal("1.00"), scenarioDetails.get(0).getPublicationType().getWeight());
        assertEquals(new BigDecimal("3.60"), scenarioDetails.get(1).getPublicationType().getWeight());
        assertEquals(new BigDecimal("2.50"), scenarioDetails.get(2).getPublicationType().getWeight());
        assertEquals(new BigDecimal("2.50"), scenarioDetails.get(3).getPublicationType().getWeight());
        assertEquals(new BigDecimal("1.90"), scenarioDetails.get(4).getPublicationType().getWeight());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "calculate-scenario-shares.groovy")
    public void testCalculateScenarioShares() {
        aclScenarioUsageRepository.calculateScenarioShares("8c855d2e-5bb6-435d-9da2-7a937c74cb6d", USER_NAME);
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("8c855d2e-5bb6-435d-9da2-7a937c74cb6d");
        assertEquals(4, scenarioDetails.size());
        Map<String, List<AclScenarioShareDetail>> detailSharesMap = new HashMap<>();
        detailSharesMap.put("7edfc465-4588-4c70-b62b-4c9f194e5d06", List.of(
            buildAclScenarioShareDetail(1000028511L, PRINT_TOU, 6.0, 3.0, 1.0, 1.0, 1.0)));
        detailSharesMap.put("fe8ba41d-01af-42e4-b400-d88733b3271f", List.of(
            buildAclScenarioShareDetail(1000028511L, DIGITAL_TOU, 59.85, 5.0, 1.0, 1.0, 1.0),
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 59.85, 5.0, 0.2861035422, 0.5, 0.3930517711)));
        detailSharesMap.put("7cb1ebeb-ee71-4ec4-bd0c-611d078dbe4b", List.of(
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 119.7, 3.0, 0.5722070845, 0.3, 0.4361035422)));
        detailSharesMap.put("d7e066bb-2df4-45fe-b767-c716954e5af5", List.of(
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 29.64, 2.0, 0.1416893733, 0.2, 0.1708446866)));
        scenarioDetails.forEach(actualDetail -> {
            List<AclScenarioShareDetail> actualShareDetails = actualDetail.getScenarioShareDetails();
            List<AclScenarioShareDetail> expectedShareDetails = detailSharesMap.get(actualDetail.getId());
            assertEquals(expectedShareDetails.size(), actualShareDetails.size());
            IntStream.range(0, actualDetail.getScenarioShareDetails().size()).forEach(
                i -> verifyAclScenarioShareDetails(expectedShareDetails.get(i), actualShareDetails.get(i)));
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "calculate-scenario-amounts.groovy")
    public void testCalculateScenarioAmounts() {
        aclScenarioUsageRepository.calculateScenarioAmounts("742c3061-50b6-498a-a440-17c3ba5bf7eb", USER_NAME);
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("742c3061-50b6-498a-a440-17c3ba5bf7eb");
        assertEquals(4, scenarioDetails.size());
        Map<String, List<AclScenarioShareDetail>> detailSharesMap = new HashMap<>();
        detailSharesMap.put("9a30d051-24e8-4fb2-8e9a-31fce9653be7", List.of(
            buildAclScenarioShareDetail(1000028511L, PRINT_TOU, 6.0, 3.0, 1.0, 1.0, 1.0, 1550.51, 1200.26, 350.25)));
        detailSharesMap.put("fdac5a59-0e8f-4416-bee6-f6883a80a917", List.of(
            buildAclScenarioShareDetail(1000028511L, DIGITAL_TOU, 59.85, 5.0, 1.0, 1.0, 1.0, 27895.51, 20500.26,
                7395.25),
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 59.85, 5.0, 0.2861035422, 0.5, 0.3930517711,
                71846.5581916850, 55813.4772727668, 16033.0809189183)));
        detailSharesMap.put("f1a8dd33-2a1d-4a5a-8354-5b15eb39ec9a", List.of(
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 119.7, 3.0, 0.5722070845, 0.3, 0.4361035422,
                79716.0598833701, 61926.8425455335, 17789.2173378366)));
        detailSharesMap.put("e8ebc3f7-9075-4bf4-bdb3-00e9d3f780f4", List.of(
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 29.64, 2.0, 0.1416893733, 0.2, 0.1708446866,
                31228.9719066657, 24260.0001674997, 6968.9717391660)));
        scenarioDetails.forEach(actualDetail -> {
            List<AclScenarioShareDetail> actualShareDetails = actualDetail.getScenarioShareDetails();
            List<AclScenarioShareDetail> expectedShareDetails = detailSharesMap.get(actualDetail.getId());
            assertEquals(expectedShareDetails.size(), actualShareDetails.size());
            IntStream.range(0, actualDetail.getScenarioShareDetails().size()).forEach(
                i -> verifyAclScenarioShareDetails(expectedShareDetails.get(i), actualShareDetails.get(i)));
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-zero-amount-shares.groovy")
    public void testDeleteZeroAmountShares() {
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf");
        assertEquals(2, scenarioDetails.size());
        assertEquals(1, scenarioDetails.get(0).getScenarioShareDetails().size());
        assertEquals(2, scenarioDetails.get(1).getScenarioShareDetails().size());
        aclScenarioUsageRepository.deleteZeroAmountShares("3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf");
        scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("3f55f25f-c2cb-4e7b-aaa9-3c25f7a879cf");
        assertEquals(2, scenarioDetails.size());
        assertEquals(0, scenarioDetails.get(0).getScenarioShareDetails().size());
        assertEquals(1, scenarioDetails.get(1).getScenarioShareDetails().size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-zero-amount-usages.groovy")
    public void testDeleteZeroAmountUsages() {
        List<AclScenarioDetail> scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("9fab9b5f-86cd-487a-a5e1-aab5ea8df175");
        assertEquals(3, scenarioDetails.size());
        aclScenarioUsageRepository.deleteZeroAmountUsages("9fab9b5f-86cd-487a-a5e1-aab5ea8df175");
        scenarioDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId("9fab9b5f-86cd-487a-a5e1-aab5ea8df175");
        assertEquals(1, scenarioDetails.size());
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders =
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(SCENARIO_UID_1);
        assertEquals(2, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 7000873612L,
            "Brewin Books Ltd", 1000000001L, "Rothchild Consultants", 150.00, 24.00, 126.00, 220.00, 35.00, 178.00,
            3, 3), holders.get(0));
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000000026L, null, 7000873612L,
            "Brewin Books Ltd", null, null, 20.00, 3.00, 10.00, 0.00, 0.00, 0.00, 1, 1), holders.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-with-amounts-and-last-action.groovy")
    public void testFindWithAmountsAndLastAction() {
        AclScenarioDto scenario = aclScenarioUsageRepository.findWithAmountsAndLastAction(SCENARIO_UID_2);
        assertNotNull(scenario);
        assertEquals(SCENARIO_UID_2, scenario.getId());
        assertEquals("1b48301c-e953-4af1-8ccb-8b3f9ed31544", scenario.getFundPoolId());
        assertEquals("30d8a41f-9b01-42cd-8041-ce840512a040", scenario.getUsageBatchId());
        assertEquals("b175a252-2fb9-47da-8d40-8ad82107f546", scenario.getGrantSetId());
        assertEquals("ACL Scenario 202012", scenario.getName());
        assertEquals("some description", scenario.getDescription());
        assertEquals("Another Scenario", scenario.getCopiedFrom());
        assertEquals(ScenarioStatusEnum.SUBMITTED, scenario.getStatus());
        assertTrue(scenario.isEditableFlag());
        assertEquals(202012, scenario.getPeriodEndDate().intValue());
        assertEquals(LICENSE_TYPE_ACL, scenario.getLicenseType());
        ScenarioAuditItem auditItem = scenario.getAuditItem();
        assertNotNull(auditItem);
        assertEquals(ScenarioActionTypeEnum.SUBMITTED, auditItem.getActionType());
        assertEquals("Scenario submitted for approval", auditItem.getActionReason());
        assertEquals(new BigDecimal("300.0000000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("100.0000000000"), scenario.getGrossTotalPrint());
        assertEquals(new BigDecimal("200.0000000000"), scenario.getGrossTotalDigital());
        assertEquals(new BigDecimal("48.0000000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("16.0000000000"), scenario.getServiceFeeTotalPrint());
        assertEquals(new BigDecimal("32.0000000000"), scenario.getServiceFeeTotalDigital());
        assertEquals(new BigDecimal("252.0000000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("84.0000000000"), scenario.getNetTotalPrint());
        assertEquals(new BigDecimal("168.0000000000"), scenario.getNetTotalDigital());
        assertEquals(1, scenario.getNumberOfRhsPrint());
        assertEquals(1, scenario.getNumberOfRhsDigital());
        assertEquals(1, scenario.getNumberOfWorksPrint());
        assertEquals(1, scenario.getNumberOfWorksDigital());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-with-amounts-and-last-action.groovy")
    public void testFindWithAmountsAndLastActionEmpty() {
        AclScenarioDto scenario = aclScenarioUsageRepository.findWithAmountsAndLastAction(SCENARIO_UID_3);
        assertNotNull(scenario);
        assertEquals(SCENARIO_UID_3, scenario.getId());
        assertEquals("e8a591d8-2803-4f9e-8cf5-4cd6257917e8", scenario.getFundPoolId());
        assertEquals("794481d7-41e5-44b5-929b-87f379b28ffa", scenario.getUsageBatchId());
        assertEquals("fb637adf-04a6-4bee-b195-8cbde93bf672", scenario.getGrantSetId());
        assertEquals("ACL Scenario 202112", scenario.getName());
        assertEquals("another description", scenario.getDescription());
        assertEquals("Another Scenario", scenario.getCopiedFrom());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertFalse(scenario.isEditableFlag());
        assertEquals(202112, scenario.getPeriodEndDate().intValue());
        assertEquals(LICENSE_TYPE_ACL, scenario.getLicenseType());
        ScenarioAuditItem auditItem = scenario.getAuditItem();
        assertNotNull(auditItem);
        assertNull(auditItem.getActionType());
        assertNull(auditItem.getActionReason());
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotal());
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotalPrint());
        assertEquals(BigDecimal.ZERO, scenario.getGrossTotalDigital());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotal());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotalPrint());
        assertEquals(BigDecimal.ZERO, scenario.getServiceFeeTotalDigital());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotal());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotalPrint());
        assertEquals(BigDecimal.ZERO, scenario.getNetTotalDigital());
        assertEquals(0, scenario.getNumberOfRhsPrint());
        assertEquals(0, scenario.getNumberOfRhsDigital());
        assertEquals(0, scenario.getNumberOfWorksPrint());
        assertEquals(0, scenario.getNumberOfWorksDigital());
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberNullSearchValue() {
        assertEquals(0, aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(1000009997L, SCENARIO_UID_4, null,
            null, null).size());
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER_1, SCENARIO_UID_4, null, null, null);
        assertEquals(2, scenarioDetailDtos.size());
        verifyAclScenarioDetailDto(buildDigitalAclScenarioDetailDto(), scenarioDetailDtos.get(0));
        verifyAclScenarioDetailDto(buildPrintDigitalAclScenarioDetailDto(), scenarioDetailDtos.get(1));
        scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            1000002901L, SCENARIO_UID_4, null, null, null);
        assertEquals(1, scenarioDetailDtos.size());
        verifyAclScenarioDetailDto(buildDifferentRhDetailDtoFindByScenarioId(), scenarioDetailDtos.get(0));
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberNullSearchValue() {
        assertEquals(0, aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(1000009997L,
            SCENARIO_UID_4, null));
        assertEquals(2, aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER_1,
            SCENARIO_UID_4, null));
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchByUsageDetailId() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "OGN674GHHSB108");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "ogN674ghhSB108");
        verifyFindByScenarioIdAndRhSearch(List.of(), "ogN674gh hSB108");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberSearchByUsageDetailId() {
        verifyFindCountByScenarioIdAndRhSearch(1, "OGN674GHHSB108");
        verifyFindCountByScenarioIdAndRhSearch(1, "ogN674ghhSB108");
        verifyFindCountByScenarioIdAndRhSearch(0, "ogN674gh hSB108");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "122820638");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "12282");
        verifyFindByScenarioIdAndRhSearch(List.of(), "12282 0638");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        verifyFindCountByScenarioIdAndRhSearch(1, "122820638");
        verifyFindCountByScenarioIdAndRhSearch(1, "12282");
        verifyFindCountByScenarioIdAndRhSearch(0, "12282 0638");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchSystemTitle() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, SYSTEM_TITLE);
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "TeCHNoLOGY REV");
        verifyFindByScenarioIdAndRhSearch(List.of(), "Techn ology review");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberSearchSystemTitle() {
        verifyFindCountByScenarioIdAndRhSearch(1, SYSTEM_TITLE);
        verifyFindCountByScenarioIdAndRhSearch(1, "TeCHNoLOGY REV");
        verifyFindCountByScenarioIdAndRhSearch(0, "Techn ology review");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchRhAccountNumber() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildDigitalAclScenarioDetailDto(),
            buildPrintDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "1000002859");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "1000002");
        verifyFindByScenarioIdAndRhSearch(List.of(), "10000 02859");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberSearchRhAccountNumber() {
        verifyFindCountByScenarioIdAndRhSearch(2, "1000002859");
        verifyFindCountByScenarioIdAndRhSearch(2, "1000002");
        verifyFindCountByScenarioIdAndRhSearch(0, "10000 02859");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchRhName() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildDigitalAclScenarioDetailDto(),
            buildPrintDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "John Wiley & Sons - Books");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "JOHN Wiley");
        verifyFindByScenarioIdAndRhSearch(List.of(), "John Wi ley & Sons - Books");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberSearchRhName() {
        verifyFindCountByScenarioIdAndRhSearch(2, "John Wiley & Sons - Books");
        verifyFindCountByScenarioIdAndRhSearch(2, "JOHN Wiley");
        verifyFindCountByScenarioIdAndRhSearch(0, "John Wi ley & Sons - Books");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testSortingFindByScenarioIdAndRhAccountNumber() {
        AclScenarioDetailDto scenarioDetailDto1 = buildDigitalAclScenarioDetailDto();
        AclScenarioDetailDto scenarioDetailDto2 = buildPrintDigitalAclScenarioDetailDto();
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "detailId");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "usageDetailId");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "productFamily");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "usageBatchName");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "periodEndDate");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "wrWrkInst");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "systemTitle");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "rhAccountNumberPrint");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "rhNamePrint");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "rhAccountNumberDigital");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "rhNameDigital");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "usagePeriod");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "usageAgeWeight");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailLicenseeClassId");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailLicenseeClassName");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "aggregateLicenseeClassId");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "aggregateLicenseeClassName");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "surveyCountry");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "reportedTypeOfUse");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "typeOfUse");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "numberOfCopies");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "weightedCopies");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "publicationType");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "pubTypeWeight");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "price");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "priceFlag");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "content");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "contentFlag");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "contentUnitPrice");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "contentUnitPriceFlag");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "valueSharePrint");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "volumeSharePrint");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailSharePrint");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "netAmountPrint");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "valueShareDigital");
        assertSortingAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "volumeShareDigital");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailShareDigital");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "netAmountDigital");
        assertSortingAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "combinedNetAmount");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdNullSearchValue() {
        assertEquals(0, aclScenarioUsageRepository.findByScenarioId(SCENARIO_UID_1, null,
            null, null).size());
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioId(
            SCENARIO_UID_6, null, null, null);
        assertEquals(2, scenarioDetailDtos.size());
        AclScenarioDetailDto scenarioDetailDto1 = buildAclScenarioDetailDto1FindByScenarioId();
        AclScenarioDetailDto scenarioDetailDto2 = buildAclScenarioDetailDto2FindByScenarioId();
        verifyAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDtos.get(0));
        verifyAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDtos.get(1));
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdNullSearchValue() {
        assertEquals(0, aclScenarioUsageRepository.findCountByScenarioId(SCENARIO_UID_1, null));
        assertEquals(2, aclScenarioUsageRepository.findCountByScenarioId(SCENARIO_UID_6, null));
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchUsageDetailId() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "OGN674GHHSB107");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "ogN674ghhSB107");
        verifyFindByScenarioIdSearch(List.of(), "ogN674gh hSB107");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchUsageDetailId() {
        verifyFindCountByScenarioIdSearch(1, "OGN674GHHSB107");
        verifyFindCountByScenarioIdSearch(1, "ogN674ghhSB107");
        verifyFindCountByScenarioIdSearch(0, "ogN674gh hSB107");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchWrWrkInst() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "122813964");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "12281");
        verifyFindByScenarioIdSearch(List.of(), "12281 3964");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchWrWrkInst() {
        verifyFindCountByScenarioIdSearch(1, "122813964");
        verifyFindCountByScenarioIdSearch(1, "12281");
        verifyFindCountByScenarioIdSearch(0, "12281 3964");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchSystemTitle() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "Aerospace America");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "AeROspAce AmeRICa");
        verifyFindByScenarioIdSearch(List.of(), "AeR OpAce AmeRICa");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchSystemTitle() {
        verifyFindCountByScenarioIdSearch(1, "Aerospace America");
        verifyFindCountByScenarioIdSearch(1, "AeROspAce AmeRICa");
        verifyFindCountByScenarioIdSearch(0, "AeR OpAce AmeRICa");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchRhAccountNumberPrint() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, RH_ACCOUNT_NUMBER);
        verifyFindByScenarioIdSearch(scenarioDetailDtos, INCOMPLETE_RH_ACCOUNT_NUMBER);
        verifyFindByScenarioIdSearch(List.of(), "10 00002859");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchRhAccountNumberPrint() {
        verifyFindCountByScenarioIdSearch(1, RH_ACCOUNT_NUMBER);
        verifyFindCountByScenarioIdSearch(1, INCOMPLETE_RH_ACCOUNT_NUMBER);
        verifyFindCountByScenarioIdSearch(0, "10 00002859");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchRhNamePrint() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, RH_NAME);
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "John WILEY");
        verifyFindByScenarioIdSearch(List.of(), "Jo hn Wiley & Sons - Books");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchRhNamePrint() {
        verifyFindCountByScenarioIdSearch(1, RH_NAME);
        verifyFindCountByScenarioIdSearch(1, "John WILEY");
        verifyFindCountByScenarioIdSearch(0, "Jo hn Wiley & Sons - Books");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchPayeeAccountNumberPrint() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "1000005413");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "1000005");
        verifyFindByScenarioIdSearch(List.of(), "100 0005413");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchPayeeAccountNumberPrint() {
        verifyFindCountByScenarioIdSearch(1, "1000005413");
        verifyFindCountByScenarioIdSearch(1, "1000005");
        verifyFindCountByScenarioIdSearch(0, "100 0005413");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchPayeeNamePrint() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "Kluwer Academic Publishers - Dordrecht");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "KluWer ACADemic");
        verifyFindByScenarioIdSearch(List.of(), "Klu wer Academic Publishers - Dordrecht");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchPayeeNamePrint() {
        verifyFindCountByScenarioIdSearch(1, "Kluwer Academic Publishers - Dordrecht");
        verifyFindCountByScenarioIdSearch(1, "KluWer ACADemic");
        verifyFindCountByScenarioIdSearch(0, "Klu wer Academic Publishers - Dordrecht");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchRhAccountNumberDigital() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "1000000001");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "1000000");
        verifyFindByScenarioIdSearch(List.of(), "10000 00001");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchRhAccountNumberDigital() {
        verifyFindCountByScenarioIdSearch(1, "1000000001");
        verifyFindCountByScenarioIdSearch(1, "1000000");
        verifyFindCountByScenarioIdSearch(0, "10000 00001");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchRhNameDigital() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "Rothchild Consultants");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "RoTHchild");
        verifyFindByScenarioIdSearch(List.of(), "Roth child Consultants");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchRhNameDigital() {
        verifyFindCountByScenarioIdSearch(1, "Rothchild Consultants");
        verifyFindCountByScenarioIdSearch(1, "RoTHchild");
        verifyFindCountByScenarioIdSearch(0, "Roth child Consultants");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchPayeeAccountNumberDigital() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "2000017010");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "2000");
        verifyFindByScenarioIdSearch(List.of(), "20000 17010");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchPayeeAccountNumberDigital() {
        verifyFindCountByScenarioIdSearch(1, "2000017010");
        verifyFindCountByScenarioIdSearch(1, "2000");
        verifyFindCountByScenarioIdSearch(0, "20000 17010");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindByScenarioIdSearchPayeeNameDigital() {
        List<AclScenarioDetailDto> scenarioDetailDtos = List.of(buildAclScenarioDetailDto1FindByScenarioId());
        verifyFindByScenarioIdSearch(scenarioDetailDtos,
            "JAC, Japan Academic Association for Copyright Clearance, Inc.");
        verifyFindByScenarioIdSearch(scenarioDetailDtos, "JAC, JaPan Academic AsSOCiation");
        verifyFindByScenarioIdSearch(List.of(), "JAC, Jap an Academic Association for Copyright Clearance, Inc.");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testFindCountByScenarioIdSearchPayeeNameDigital() {
        verifyFindCountByScenarioIdSearch(1, "JAC, Japan Academic Association for Copyright Clearance, Inc.");
        verifyFindCountByScenarioIdSearch(1, "JAC, JaPan Academic AsSOCiation");
        verifyFindCountByScenarioIdSearch(0, "JAC, Jap an Academic Association for Copyright Clearance, Inc.");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID)
    public void testSortingFindByScenarioId() {
        AclScenarioDetailDto detail1 = buildAclScenarioDetailDto1FindByScenarioId();
        AclScenarioDetailDto detail2 = buildAclScenarioDetailDto2FindByScenarioId();
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "detailId");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "usageDetailId");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail1, "productFamily");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail1, "usageBatchName");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail1, "periodEndDate");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "wrWrkInst");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "systemTitle");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "rhAccountNumberPrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "rhNamePrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "payeeAccountNumberPrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "payeeNamePrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "rhAccountNumberDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "rhNameDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "payeeAccountNumberDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "payeeNameDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "usagePeriod");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "usageAgeWeight");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "detailLicenseeClassId");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "detailLicenseeClassName");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "aggregateLicenseeClassId");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "aggregateLicenseeClassName");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "surveyCountry");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "reportedTypeOfUse");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "typeOfUse");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "numberOfCopies");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "weightedCopies");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "publicationType");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "pubTypeWeight");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "price");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "priceFlag");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "content");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "contentFlag");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "contentUnitPrice");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "contentUnitPriceFlag");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "valueSharePrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "volumeSharePrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail2, detail1, "detailSharePrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "netAmountPrint");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "valueShareDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "volumeShareDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "detailShareDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "netAmountDigital");
        assertSortingAclScenarioDetailDtoFindByScenarioId(detail1, detail2, "combinedNetAmount");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-rightsholder-details-results.groovy")
    public void testFindRightsholderDetailsResults() {
        List<AclScenarioDetailDto> expectedScenarioDetails =
            loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_2c13581a.json");
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId("ae790945-f541-4149-9ac2-9a5d29516c38");
        filter.setRhAccountNumber(1000002859L);
        filter.setWrWrkInst(122813964L);
        filter.setAggregateLicenseeClassId(12);
        List<AclScenarioDetailDto> actualScenarioDetails =
            aclScenarioUsageRepository.findRightsholderDetailsResults(filter);
        assertEquals(expectedScenarioDetails.size(), actualScenarioDetails.size());
        IntStream.range(0, expectedScenarioDetails.size()).forEach(index ->
            verifyAclScenarioDetailDto(expectedScenarioDetails.get(index), actualScenarioDetails.get(index)));
    }

    @Test
    @TestData(fileName = FIND_RIGHTSHOLDER_TITLE_RESULTS)
    public void testFindRightsholderTitleResultsAggLcClassIdNull() {
        List<AclRightsholderTotalsHolderDto> expectedRhTotalsHolderDtos = loadExpectedAclRightsholderTotalsHolderDto(
            "json/acl/acl_rightsholder_totals_holder_dto_1.json");
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID_5);
        filter.setRhAccountNumber(1000002859L);
        List<AclRightsholderTotalsHolderDto> actualRhTotalsHolderDtos =
            aclScenarioUsageRepository.findRightsholderTitleResults(filter);
        assertEquals(expectedRhTotalsHolderDtos.size(), actualRhTotalsHolderDtos.size());
        IntStream.range(0, expectedRhTotalsHolderDtos.size()).forEach(i ->
            verifyAclRightsholderTotalsHolderDto(expectedRhTotalsHolderDtos.get(i), actualRhTotalsHolderDtos.get(i)));
    }

    @Test
    @TestData(fileName = FIND_RIGHTSHOLDER_TITLE_RESULTS)
    public void testFindRightsholderTitleResultsAggLcClassIdNotNull() {
        List<AclRightsholderTotalsHolderDto> expectedRhTotalsHolderDtos = loadExpectedAclRightsholderTotalsHolderDto(
            "json/acl/acl_rightsholder_totals_holder_dto_2.json");
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID_5);
        filter.setRhAccountNumber(1000002859L);
        filter.setAggregateLicenseeClassId(12);
        List<AclRightsholderTotalsHolderDto> actualRhTotalsHolderDtos =
            aclScenarioUsageRepository.findRightsholderTitleResults(filter);
        assertEquals(expectedRhTotalsHolderDtos.size(), actualRhTotalsHolderDtos.size());
        verifyAclRightsholderTotalsHolderDto(expectedRhTotalsHolderDtos.get(0), actualRhTotalsHolderDtos.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-agg-lc-class-results.groovy")
    public void testFindRightsholderAggLcClassResultsSystemTitleNull() {
        List<AclRightsholderTotalsHolderDto> expected = loadExpectedAclRightsholderTotalsHolderDto(
            "json/acl/acl_scenario_detail_dto_for_find_rightsholder_agg_lc_cl_results1.json");
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId("7ae1468e-ae4d-4846-b20d-46f28b75c82c");
        filter.setRhAccountNumber(1000028511L);
        List<AclRightsholderTotalsHolderDto> actual =
            aclScenarioUsageRepository.findRightsholderAggLcClassResults(filter);
        assertEquals(expected.size(), actual.size());
        IntStream.range(0, expected.size()).forEach(i ->
            verifyAclRightsholderTotalsHolderDto(expected.get(i), actual.get(i)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-agg-lc-class-results.groovy")
    public void testFindRightsholderAggLcClassResultsSystemTitleNotNull() {
        List<AclRightsholderTotalsHolderDto> expected = loadExpectedAclRightsholderTotalsHolderDto(
            "json/acl/acl_scenario_detail_dto_for_find_rightsholder_agg_lc_cl_results2.json");
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId("7ae1468e-ae4d-4846-b20d-46f28b75c82c");
        filter.setRhAccountNumber(1000028511L);
        filter.setWrWrkInst(122825555L);
        List<AclRightsholderTotalsHolderDto> actual =
            aclScenarioUsageRepository.findRightsholderAggLcClassResults(filter);
        assertEquals(expected.size(), actual.size());
        verifyAclRightsholderTotalsHolderDto(expected.get(0), actual.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-payee-by-account-number.groovy")
    public void testUpdatePayeeByAccountNumber() {
        String scenarioId = "f5808122-3784-4055-90f8-1c420e67fd0a";
        List<AclScenarioShareDetail> expectedShareDetailsWithoutPayee = loadExpectedAclScenarioShareDetailDto(
            "json/acl/acl_scenario_share_detail_without_payee.json");
        List<AclScenarioShareDetail> actualShareDetails =
            aclScenarioUsageRepository.findScenarioDetailsByScenarioId(scenarioId).stream()
                .flatMap(e -> e.getScenarioShareDetails().stream()).collect(Collectors.toList());
        assertEquals(4, actualShareDetails.size());
        assertEquals(expectedShareDetailsWithoutPayee.size(), actualShareDetails.size());
        IntStream.range(0, expectedShareDetailsWithoutPayee.size()).forEach(i ->
            verifyAclScenarioShareDetails(expectedShareDetailsWithoutPayee.get(i), actualShareDetails.get(i)));
        aclScenarioUsageRepository.updatePayeeByAccountNumber(RH_ACCOUNT_NUMBER_1, scenarioId, 2015489976L, "PRINT");
        aclScenarioUsageRepository.updatePayeeByAccountNumber(RH_ACCOUNT_NUMBER_1, scenarioId, 1000489976L, "DIGITAL");
        aclScenarioUsageRepository.updatePayeeByAccountNumber(RH_ACCOUNT_NUMBER_2, scenarioId, 2878895976L, "PRINT");
        aclScenarioUsageRepository.updatePayeeByAccountNumber(RH_ACCOUNT_NUMBER_2, scenarioId, 1526489976L, "DIGITAL");
        List<AclScenarioShareDetail> expectedShareDetails = loadExpectedAclScenarioShareDetailDto(
            "json/acl/acl_scenario_share_detail.json");
        IntStream.range(0, expectedShareDetails.size()).forEach(
            i -> verifyAclScenarioShareDetails(expectedShareDetails.get(i),
                aclScenarioUsageRepository.findScenarioDetailsByScenarioId(scenarioId).stream()
                    .flatMap(e -> e.getScenarioShareDetails().stream()).collect(Collectors.toList()).get(i)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-rightsholder-payee-product-family-holders.groovy")
    public void testFindRightsholderPayeeProductFamilyHoldersByAclScenarioIds() {
        Comparator<RightsholderPayeeProductFamilyHolder> comparator = Comparator
            .comparing(RightsholderPayeeProductFamilyHolder::getRightsholder,
                Comparator.comparing(Rightsholder::getAccountNumber))
            .thenComparing(RightsholderPayeeProductFamilyHolder::getPayee,
                Comparator.comparing(Rightsholder::getAccountNumber))
            .thenComparing(RightsholderPayeeProductFamilyHolder::getProductFamily);
        Set<String> scenarioIds = Set.of("f146c64c-6cf7-4d8d-9011-132ee6bf2f3b");
        List<RightsholderPayeeProductFamilyHolder> actual =
            aclScenarioUsageRepository.findRightsholderPayeeProductFamilyHoldersByAclScenarioIds(scenarioIds)
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        verifyRightsholderPayeeProductFamilyHolders(
            loadExpectedRightsholderPayeeProductFamilyHolders("json/acl/rh_payee_product_family_holders.json"),
            actual);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-for-send-to-lm-by-scenario-id.groovy")
    public void testFindForSendToLmByScenarioId() {
        List<AclScenarioLiabilityDetail> expectedDetails =
            loadExpectedAclScenarioLiabilityDetails("json/acl/acl_scenario_liability_details_find_by_id.json");
        List<AclScenarioLiabilityDetail> actualDetails =
            aclScenarioUsageRepository.findForSendToLmByScenarioId("3ac016af-eb68-41b0-a031-7477e623a443");
        assertEquals(3, actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> verifyAclScenarioLiabilityDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    private void verifyAclScenarioDetail(AclScenarioDetail expectedScenarioDetail,
                                         AclScenarioDetail actualScenarioDetail) {
        assertEquals(expectedScenarioDetail.getScenarioId(), actualScenarioDetail.getScenarioId());
        assertEquals(expectedScenarioDetail.getPeriod(), actualScenarioDetail.getPeriod());
        assertEquals(expectedScenarioDetail.getOriginalDetailId(), actualScenarioDetail.getOriginalDetailId());
        assertEquals(expectedScenarioDetail.getWrWrkInst(), actualScenarioDetail.getWrWrkInst());
        assertEquals(expectedScenarioDetail.getSystemTitle(), actualScenarioDetail.getSystemTitle());
        assertEquals(expectedScenarioDetail.getDetailLicenseeClass().getId(),
            actualScenarioDetail.getDetailLicenseeClass().getId());
        assertEquals(expectedScenarioDetail.getDetailLicenseeClass().getDescription(),
            actualScenarioDetail.getDetailLicenseeClass().getDescription());
        assertEquals(expectedScenarioDetail.getAggregateLicenseeClassId(),
            actualScenarioDetail.getAggregateLicenseeClassId());
        assertEquals(expectedScenarioDetail.getAggregateLicenseeClassName(),
            actualScenarioDetail.getAggregateLicenseeClassName());
        assertEquals(expectedScenarioDetail.getPublicationType().getId(),
            actualScenarioDetail.getPublicationType().getId());
        assertEquals(expectedScenarioDetail.getPublicationType().getWeight(),
            actualScenarioDetail.getPublicationType().getWeight());
        assertEquals(expectedScenarioDetail.getPrice(), actualScenarioDetail.getPrice());
        assertEquals(expectedScenarioDetail.getPriceFlag(), actualScenarioDetail.getPriceFlag());
        assertEquals(expectedScenarioDetail.getContent(), actualScenarioDetail.getContent());
        assertEquals(expectedScenarioDetail.getContentFlag(), actualScenarioDetail.getContentFlag());
        assertEquals(expectedScenarioDetail.getContentUnitPrice(), actualScenarioDetail.getContentUnitPrice());
        assertEquals(expectedScenarioDetail.getContentUnitPriceFlag(), actualScenarioDetail.getContentUnitPriceFlag());
        assertEquals(expectedScenarioDetail.getNumberOfCopies(), actualScenarioDetail.getNumberOfCopies());
        assertEquals(expectedScenarioDetail.getUsageAgeWeight(), actualScenarioDetail.getUsageAgeWeight());
        assertEquals(expectedScenarioDetail.getWeightedCopies(), actualScenarioDetail.getWeightedCopies());
        assertEquals(expectedScenarioDetail.getSurveyCountry(), actualScenarioDetail.getSurveyCountry());
        assertEquals(expectedScenarioDetail.getReportedTypeOfUse(), actualScenarioDetail.getReportedTypeOfUse());
        assertEquals(expectedScenarioDetail.getTypeOfUse(), actualScenarioDetail.getTypeOfUse());
    }

    private void verifyAclScenarioDetailDto(AclScenarioDetailDto expectedDetail,
                                            AclScenarioDetailDto actualDetail) {
        assertEquals(expectedDetail.getId(), actualDetail.getId());
        assertEquals(expectedDetail.getOriginalDetailId(), actualDetail.getOriginalDetailId());
        assertEquals(expectedDetail.getProductFamily(), actualDetail.getProductFamily());
        assertEquals(expectedDetail.getUsageBatchName(), actualDetail.getUsageBatchName());
        assertEquals(expectedDetail.getPeriodEndPeriod(), actualDetail.getPeriodEndPeriod());
        assertEquals(expectedDetail.getWrWrkInst(), actualDetail.getWrWrkInst());
        assertEquals(expectedDetail.getSystemTitle(), actualDetail.getSystemTitle());
        assertEquals(expectedDetail.getRhAccountNumberPrint(), actualDetail.getRhAccountNumberPrint());
        assertEquals(expectedDetail.getRhNamePrint(), actualDetail.getRhNamePrint());
        assertEquals(expectedDetail.getPayeeAccountNumberPrint(), actualDetail.getPayeeAccountNumberPrint());
        assertEquals(expectedDetail.getPayeeNamePrint(), actualDetail.getPayeeNamePrint());
        assertEquals(expectedDetail.getRhAccountNumberDigital(), actualDetail.getRhAccountNumberDigital());
        assertEquals(expectedDetail.getRhNameDigital(), actualDetail.getRhNameDigital());
        assertEquals(expectedDetail.getPayeeAccountNumberDigital(), actualDetail.getPayeeAccountNumberDigital());
        assertEquals(expectedDetail.getPayeeNameDigital(), actualDetail.getPayeeNameDigital());
        assertEquals(expectedDetail.getUsagePeriod(), actualDetail.getUsagePeriod());
        assertEquals(expectedDetail.getUsageAgeWeight(), actualDetail.getUsageAgeWeight());
        assertEquals(expectedDetail.getDetailLicenseeClassId(), actualDetail.getDetailLicenseeClassId());
        assertEquals(expectedDetail.getDetailLicenseeClassName(), actualDetail.getDetailLicenseeClassName());
        assertEquals(expectedDetail.getAggregateLicenseeClassId(), actualDetail.getAggregateLicenseeClassId());
        assertEquals(expectedDetail.getAggregateLicenseeClassName(), actualDetail.getAggregateLicenseeClassName());
        assertEquals(expectedDetail.getSurveyCountry(), actualDetail.getSurveyCountry());
        assertEquals(expectedDetail.getReportedTypeOfUse(), actualDetail.getReportedTypeOfUse());
        assertEquals(expectedDetail.getNumberOfCopies(), actualDetail.getNumberOfCopies());
        assertEquals(expectedDetail.getWeightedCopies(), actualDetail.getWeightedCopies());
        assertEquals(expectedDetail.getPublicationType(), actualDetail.getPublicationType());
        assertEquals(expectedDetail.getPrice(), actualDetail.getPrice());
        assertEquals(expectedDetail.isPriceFlag(), actualDetail.isPriceFlag());
        assertEquals(expectedDetail.getContent(), actualDetail.getContent());
        assertEquals(expectedDetail.isContentFlag(), actualDetail.isContentFlag());
        assertEquals(expectedDetail.getContentUnitPrice(), actualDetail.getContentUnitPrice());
        assertEquals(expectedDetail.isContentUnitPriceFlag(), actualDetail.isContentUnitPriceFlag());
        assertEquals(expectedDetail.getValueSharePrint(), actualDetail.getValueSharePrint());
        assertEquals(expectedDetail.getVolumeSharePrint(), actualDetail.getVolumeSharePrint());
        assertEquals(expectedDetail.getDetailSharePrint(), actualDetail.getDetailSharePrint());
        assertEquals(expectedDetail.getNetAmountPrint(), actualDetail.getNetAmountPrint());
        assertEquals(expectedDetail.getValueShareDigital(), actualDetail.getValueShareDigital());
        assertEquals(expectedDetail.getVolumeShareDigital(), actualDetail.getVolumeShareDigital());
        assertEquals(expectedDetail.getDetailShareDigital(), actualDetail.getDetailShareDigital());
        assertEquals(expectedDetail.getNetAmountDigital(), actualDetail.getNetAmountDigital());
        assertEquals(expectedDetail.getCombinedNetAmount(), actualDetail.getCombinedNetAmount());
    }

    private void verifyAclRightsholderTotalsHolderDto(AclRightsholderTotalsHolderDto expected,
                                                      AclRightsholderTotalsHolderDto actual) {
        assertEquals(expected.getWrWrkInst(), actual.getWrWrkInst());
        assertEquals(expected.getSystemTitle(), actual.getSystemTitle());
        assertEquals(expected.getAggregateLicenseeClass().getId(), actual.getAggregateLicenseeClass().getId());
        assertEquals(expected.getAggregateLicenseeClass().getDescription(),
            actual.getAggregateLicenseeClass().getDescription());
        assertEquals(expected.getGrossTotalPrint(), actual.getGrossTotalPrint());
        assertEquals(expected.getNetTotalPrint(), actual.getNetTotalPrint());
        assertEquals(expected.getGrossTotalDigital(), actual.getGrossTotalDigital());
        assertEquals(expected.getNetTotalDigital(), actual.getNetTotalDigital());
        assertEquals(expected.getGrossTotal(), actual.getGrossTotal());
        assertEquals(expected.getNetTotal(), actual.getNetTotal());
    }

    private void verifyAclScenarioLiabilityDetail(AclScenarioLiabilityDetail expectedDetail,
                                                  AclScenarioLiabilityDetail actualDetail) {
        assertEquals(expectedDetail.getLiabilityDetailId(), actualDetail.getLiabilityDetailId());
        assertEquals(expectedDetail.getRightsholderId(), actualDetail.getRightsholderId());
        assertEquals(expectedDetail.getWrWrkInst(), actualDetail.getWrWrkInst());
        assertEquals(expectedDetail.getSystemTitle(), actualDetail.getSystemTitle());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getLicenseType(), actualDetail.getLicenseType());
        assertEquals(expectedDetail.getAggregateLicenseeClassName(), actualDetail.getAggregateLicenseeClassName());
        assertEquals(expectedDetail.getProductFamily(), actualDetail.getProductFamily());
        assertEquals(expectedDetail.getNetAmount(), actualDetail.getNetAmount());
        assertEquals(expectedDetail.getServiceFeeAmount(), actualDetail.getServiceFeeAmount());
        assertEquals(expectedDetail.getGrossAmount(), actualDetail.getGrossAmount());
    }

    private AclScenario buildAclScenario(String id, String fundPoolId, String usageBatchId, String grantSetId,
                                         String name, String description, ScenarioStatusEnum status, boolean editable,
                                         Integer periodEndDate, String licenseType, String user, String date) {
        AclScenario scenario = new AclScenario();
        scenario.setId(id);
        scenario.setFundPoolId(fundPoolId);
        scenario.setUsageBatchId(usageBatchId);
        scenario.setGrantSetId(grantSetId);
        scenario.setName(name);
        scenario.setDescription(description);
        scenario.setStatus(status);
        scenario.setEditableFlag(editable);
        scenario.setPeriodEndDate(periodEndDate);
        scenario.setLicenseType(licenseType);
        scenario.setCreateUser(user);
        scenario.setUpdateUser(user);
        scenario.setCreateDate(Date.from(OffsetDateTime.parse(date).toInstant()));
        scenario.setUpdateDate(Date.from(OffsetDateTime.parse(date).toInstant()));
        return scenario;
    }

    private AclScenarioShareDetail buildAclScenarioShareDetail(Long rhAccountNumber, String typeOfUse,
                                                               Double valueWeight, Double volumeWeight) {
        AclScenarioShareDetail aclScenarioShareDetail = new AclScenarioShareDetail();
        aclScenarioShareDetail.setRhAccountNumber(rhAccountNumber);
        aclScenarioShareDetail.setValueWeight(BigDecimal.valueOf(valueWeight).setScale(10, RoundingMode.HALF_UP));
        aclScenarioShareDetail.setVolumeWeight(BigDecimal.valueOf(volumeWeight).setScale(10, RoundingMode.HALF_UP));
        aclScenarioShareDetail.setTypeOfUse(typeOfUse);
        return aclScenarioShareDetail;
    }

    private AclScenarioShareDetail buildAclScenarioShareDetail(Long rhAccountNumber, String typeOfUse,
                                                               Double valueWeight, Double volumeWeight,
                                                               Double valueShare, Double volumeShare,
                                                               Double detailShare) {
        AclScenarioShareDetail aclScenarioShareDetail =
            buildAclScenarioShareDetail(rhAccountNumber, typeOfUse, valueWeight, volumeWeight);
        aclScenarioShareDetail.setValueShare(BigDecimal.valueOf(valueShare).setScale(10, RoundingMode.HALF_UP));
        aclScenarioShareDetail.setVolumeShare(BigDecimal.valueOf(volumeShare).setScale(10, RoundingMode.HALF_UP));
        aclScenarioShareDetail.setDetailShare(BigDecimal.valueOf(detailShare).setScale(10, RoundingMode.HALF_UP));
        return aclScenarioShareDetail;
    }

    private AclScenarioShareDetail buildAclScenarioShareDetail(Long rhAccountNumber, String typeOfUse,
                                                               Double valueWeight, Double volumeWeight,
                                                               Double valueShare, Double volumeShare,
                                                               Double detailShare, Double grossAmount, Double netAmount,
                                                               Double serviceFeeAmount) {
        AclScenarioShareDetail aclScenarioShareDetail = buildAclScenarioShareDetail(rhAccountNumber, typeOfUse,
            valueWeight, volumeWeight, valueShare, volumeShare, detailShare);
        aclScenarioShareDetail.setGrossAmount(BigDecimal.valueOf(grossAmount).setScale(10, RoundingMode.HALF_UP));
        aclScenarioShareDetail.setNetAmount(BigDecimal.valueOf(netAmount).setScale(10, RoundingMode.HALF_UP));
        aclScenarioShareDetail.setServiceFeeAmount(
            BigDecimal.valueOf(serviceFeeAmount).setScale(10, RoundingMode.HALF_UP));
        return aclScenarioShareDetail;
    }

    private void verifyAclScenarioShareDetails(AclScenarioShareDetail expectedDetail,
                                               AclScenarioShareDetail actualDetail) {
        assertEquals(expectedDetail.getRhAccountNumber(), actualDetail.getRhAccountNumber());
        assertEquals(expectedDetail.getPayeeAccountNumber(), actualDetail.getPayeeAccountNumber());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getVolumeWeight(), actualDetail.getVolumeWeight());
        assertEquals(expectedDetail.getValueWeight(), actualDetail.getValueWeight());
        assertEquals(expectedDetail.getVolumeShare(), actualDetail.getVolumeShare());
        assertEquals(expectedDetail.getValueShare(), actualDetail.getValueShare());
        assertEquals(expectedDetail.getDetailShare(), actualDetail.getDetailShare());
        assertEquals(expectedDetail.getGrossAmount(), actualDetail.getGrossAmount());
        assertEquals(expectedDetail.getNetAmount(), actualDetail.getNetAmount());
        assertEquals(expectedDetail.getServiceFeeAmount(), actualDetail.getServiceFeeAmount());
    }

    private AclRightsholderTotalsHolder buildAclRightsholderTotalsHolder(Long rhAccountNumber, String rhName,
                                                                         Long printPayeeAccountNumber,
                                                                         String printPayeeName,
                                                                         Long digitalPayeeAccountNumber,
                                                                         String digitalPayeeName,
                                                                         Double grossTotalPrint,
                                                                         Double serviceFeeTotalPrint,
                                                                         Double netTotalPrint, Double grossTotalDigital,
                                                                         Double serviceFeeTotalDigital,
                                                                         Double netTotalDigital,
                                                                         int numberOfTitles, int numberOfAggLcClasses) {
        AclRightsholderTotalsHolder holder = new AclRightsholderTotalsHolder();
        holder.getRightsholder().setAccountNumber(rhAccountNumber);
        holder.getRightsholder().setName(rhName);
        holder.setPrintPayeeAccountNumber(printPayeeAccountNumber);
        holder.setPrintPayeeName(printPayeeName);
        holder.setDigitalPayeeAccountNumber(digitalPayeeAccountNumber);
        holder.setDigitalPayeeName(digitalPayeeName);
        holder.setGrossTotalPrint(BigDecimal.valueOf(grossTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setServiceFeeTotalPrint(
            BigDecimal.valueOf(serviceFeeTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNetTotalPrint(BigDecimal.valueOf(netTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setGrossTotalDigital(
            BigDecimal.valueOf(grossTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setServiceFeeTotalDigital(
            BigDecimal.valueOf(serviceFeeTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNetTotalDigital(BigDecimal.valueOf(netTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNumberOfTitles(numberOfTitles);
        holder.setNumberOfAggLcClasses(numberOfAggLcClasses);
        holder.setLicenseType(LICENSE_TYPE_ACL);
        return holder;
    }

    private void verifyAclRightsholderTotalsHolder(AclRightsholderTotalsHolder expectedHolder,
                                                   AclRightsholderTotalsHolder actualHolder) {
        assertEquals(
            expectedHolder.getRightsholder().getAccountNumber(), actualHolder.getRightsholder().getAccountNumber());
        assertEquals(expectedHolder.getRightsholder().getName(), actualHolder.getRightsholder().getName());
        assertEquals(expectedHolder.getPrintPayeeAccountNumber(), actualHolder.getPrintPayeeAccountNumber());
        assertEquals(expectedHolder.getPrintPayeeName(), actualHolder.getPrintPayeeName());
        assertEquals(expectedHolder.getDigitalPayeeAccountNumber(), actualHolder.getDigitalPayeeAccountNumber());
        assertEquals(expectedHolder.getDigitalPayeeName(), actualHolder.getDigitalPayeeName());
        assertEquals(expectedHolder.getGrossTotalPrint(),
            actualHolder.getGrossTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getServiceFeeTotalPrint(),
            actualHolder.getServiceFeeTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNetTotalPrint(),
            actualHolder.getNetTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getGrossTotalDigital(),
            actualHolder.getGrossTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getServiceFeeTotalDigital(),
            actualHolder.getServiceFeeTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNetTotalDigital(),
            actualHolder.getNetTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNumberOfTitles(), actualHolder.getNumberOfTitles());
        assertEquals(expectedHolder.getNumberOfAggLcClasses(), actualHolder.getNumberOfAggLcClasses());
        assertEquals(expectedHolder.getLicenseType(), actualHolder.getLicenseType());
    }

    private void verifyFindByScenarioIdAndRhSearch(List<AclScenarioDetailDto> expectedScenarioDetailDtos,
                                                   String searchValue) {
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER_1, SCENARIO_UID_4, searchValue, null, null);
        assertEquals(expectedScenarioDetailDtos.size(), scenarioDetailDtos.size());
        if (!expectedScenarioDetailDtos.isEmpty()) {
            for (int i = 0; i < scenarioDetailDtos.size(); i++) {
                verifyAclScenarioDetailDto(expectedScenarioDetailDtos.get(i), scenarioDetailDtos.get(i));
            }
        }
    }

    private void verifyFindCountByScenarioIdAndRhSearch(int expectedSize, String searchValue) {
        assertEquals(expectedSize, aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER_1,
            SCENARIO_UID_4, searchValue));
    }

    private void assertSortingAclScenarioDetailDto(AclScenarioDetailDto detailAsc, AclScenarioDetailDto detailDesc,
                                                   String sortProperty) {
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER_1, SCENARIO_UID_4, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.ASC));
        verifyAclScenarioDetailDto(detailAsc, scenarioDetailDtos.get(0));
        scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER_1, SCENARIO_UID_4, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.DESC));
        verifyAclScenarioDetailDto(detailDesc, scenarioDetailDtos.get(0));
    }

    private void verifyFindByScenarioIdSearch(List<AclScenarioDetailDto> expectedScenarioDetailDtos,
                                              String searchValue) {
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioId(
            SCENARIO_UID_6, searchValue, null, null);
        assertEquals(expectedScenarioDetailDtos.size(), scenarioDetailDtos.size());
        if (!expectedScenarioDetailDtos.isEmpty()) {
            for (int i = 0; i < scenarioDetailDtos.size(); i++) {
                verifyAclScenarioDetailDto(expectedScenarioDetailDtos.get(i), scenarioDetailDtos.get(i));
            }
        }
    }

    private void verifyFindCountByScenarioIdSearch(int expectedSize, String searchValue) {
        assertEquals(expectedSize, aclScenarioUsageRepository.findCountByScenarioId(SCENARIO_UID_6, searchValue));
    }

    private void assertSortingAclScenarioDetailDtoFindByScenarioId(AclScenarioDetailDto detailAsc,
                                                                   AclScenarioDetailDto detailDesc,
                                                                   String sortProperty) {
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioId(
            SCENARIO_UID_6, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.ASC));
        verifyAclScenarioDetailDto(detailAsc, scenarioDetailDtos.get(0));
        scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioId(
            SCENARIO_UID_6, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.DESC));
        verifyAclScenarioDetailDto(detailDesc, scenarioDetailDtos.get(0));
    }

    private void verifyRightsholderPayeeProductFamilyHolders(List<RightsholderPayeeProductFamilyHolder> expected,
                                                             List<RightsholderPayeeProductFamilyHolder> actual) {
        assertEquals(expected.size(), actual.size());
        IntStream.range(0, expected.size())
            .forEach(index -> verifyRightsholderPayeeProductFamilyHolder(expected.get(index), actual.get(index)));
    }

    private void verifyRightsholderPayeeProductFamilyHolder(RightsholderPayeeProductFamilyHolder expected,
                                                            RightsholderPayeeProductFamilyHolder actual) {
        assertEquals(expected.getRightsholder().getId(), actual.getRightsholder().getId());
        assertEquals(expected.getRightsholder().getAccountNumber(), actual.getRightsholder().getAccountNumber());
        assertEquals(expected.getRightsholder().getName(), actual.getRightsholder().getName());
        assertEquals(expected.getPayee().getId(), actual.getPayee().getId());
        assertEquals(expected.getPayee().getAccountNumber(), actual.getPayee().getAccountNumber());
        assertEquals(expected.getPayee().getName(), actual.getPayee().getName());
        assertEquals(expected.getProductFamily(), actual.getProductFamily());
    }

    private AclScenarioDetailDto buildDigitalAclScenarioDetailDto() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_digital.json").get(0);
    }

    private AclScenarioDetailDto buildPrintDigitalAclScenarioDetailDto() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_print_digital.json").get(0);
    }

    private AclScenarioDetailDto buildDifferentRhDetailDtoFindByScenarioId() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_different_rhs.json").get(0);
    }

    private AclScenarioDetailDto buildAclScenarioDetailDto1FindByScenarioId() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_1_find_by_scenario.json").get(0);
    }

    private AclScenarioDetailDto buildAclScenarioDetailDto2FindByScenarioId() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_2_find_by_scenario.json").get(0);
    }

    private List<AclScenarioDetail> loadExpectedAclScenarioDetails(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclScenarioDetail>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private List<AclScenarioDetailDto> loadExpectedAclScenarioDetailDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclScenarioDetailDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private List<AclRightsholderTotalsHolderDto> loadExpectedAclRightsholderTotalsHolderDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclRightsholderTotalsHolderDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private List<AclScenarioShareDetail> loadExpectedAclScenarioShareDetailDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclScenarioShareDetail>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private List<RightsholderPayeeProductFamilyHolder> loadExpectedRightsholderPayeeProductFamilyHolders(
        String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<RightsholderPayeeProductFamilyHolder>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private List<AclScenarioLiabilityDetail> loadExpectedAclScenarioLiabilityDetails(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclScenarioLiabilityDetail>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
