package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioShareDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
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
    private static final String SCENARIO_UID_1 = "0d0041a3-833e-463e-8ad4-f28461dc961d";
    private static final String SCENARIO_UID_2 = "d18d7cab-8a69-4b60-af5a-0a0c99b8a4d3";
    private static final String SCENARIO_UID_3 = "53a1c4e8-f1fe-4b17-877e-2d721b2059b5";
    private static final String SCENARIO_UID_4 = "f473fa64-12ea-4db6-9d30-94087fe500fd";
    private static final String LICENSE_TYPE_ACL = "ACL";
    private static final Long RH_ACCOUNT_NUMBER = 1000002859L;
    private static final String RH_NAME = "John Wiley & Sons - Books";
    private static final String USER_NAME = "user@copyright.com";
    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";

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
        AclScenarioDetail expectedScenarioDetail = buildAclScenarioDetail();
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
        detailSharesMap.put("df038efe-72c1-4081-88e7-17fa4fa5ff6a", Collections.singletonList(
            buildAclScenarioShareDetail(1000028511L, PRINT_TOU, 6.0, 3.0)));
        detailSharesMap.put("8827d6c6-16d8-4102-b257-ce861ce77491", Arrays.asList(
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
        detailSharesMap.put("7edfc465-4588-4c70-b62b-4c9f194e5d06", Collections.singletonList(
            buildAclScenarioShareDetail(1000028511L, PRINT_TOU, 6.0, 3.0, 1.0, 1.0, 1.0)));
        detailSharesMap.put("fe8ba41d-01af-42e4-b400-d88733b3271f", Arrays.asList(
            buildAclScenarioShareDetail(1000028511L, DIGITAL_TOU, 59.85, 5.0, 1.0, 1.0, 1.0),
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 59.85, 5.0, 0.2861035422, 0.5, 0.3930517711)));
        detailSharesMap.put("7cb1ebeb-ee71-4ec4-bd0c-611d078dbe4b", Collections.singletonList(
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 119.7, 3.0, 0.5722070845, 0.3, 0.4361035422)));
        detailSharesMap.put("d7e066bb-2df4-45fe-b767-c716954e5af5", Collections.singletonList(
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
        detailSharesMap.put("9a30d051-24e8-4fb2-8e9a-31fce9653be7", Collections.singletonList(
            buildAclScenarioShareDetail(1000028511L, PRINT_TOU, 6.0, 3.0, 1.0, 1.0, 1.0, 1550.51, 1200.26, 350.25)));
        detailSharesMap.put("fdac5a59-0e8f-4416-bee6-f6883a80a917", Arrays.asList(
            buildAclScenarioShareDetail(1000028511L, DIGITAL_TOU, 59.85, 5.0, 1.0, 1.0, 1.0, 27895.51, 20500.26,
                7395.25),
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 59.85, 5.0, 0.2861035422, 0.5, 0.3930517711,
                71846.5581916850, 55813.4772727668, 16033.0809189183)));
        detailSharesMap.put("f1a8dd33-2a1d-4a5a-8354-5b15eb39ec9a", Collections.singletonList(
            buildAclScenarioShareDetail(2580011451L, PRINT_TOU, 119.7, 3.0, 0.5722070845, 0.3, 0.4361035422,
                79716.0598833701, 61926.8425455335, 17789.2173378366)));
        detailSharesMap.put("e8ebc3f7-9075-4bf4-bdb3-00e9d3f780f4", Collections.singletonList(
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
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
                SCENARIO_UID_1, StringUtils.EMPTY, null, null);
        assertEquals(2, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2), holders.get(0));
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000000026L, null, 20.00, 3.00, 10.00,
            0.00, 0.00, 0.00, 1, 1), holders.get(1));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders =
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
                SCENARIO_UID_1, "JohN", null, null);
        assertEquals(1, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2), holders.get(0));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testSortingFindAclRightsholderTotalsHoldersByScenarioId() {
        AclRightsholderTotalsHolder holder1 = buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2);
        AclRightsholderTotalsHolder holder2 = buildAclRightsholderTotalsHolder(1000000026L, null, 20.00, 3.00, 10.00,
            0.00, 0.00, 0.00, 1, 1);
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "rightsholder.accountNumber");
        assertSortingAclRightsholderTotalsHolder(holder1, holder2, "rightsholder.name");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "grossTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "serviceFeeTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "netTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "grossTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "serviceFeeTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "netTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "numberOfTitles");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "numberOfAggLcClasses");
        assertSortingAclRightsholderTotalsHolder(holder1, holder1, "licenseType");
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHolderCountByScenarioIdEmptySearchValue() {
        assertEquals(2, aclScenarioUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(SCENARIO_UID_1,
            StringUtils.EMPTY));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHolderCountByScenarioIdNullSearchValue() {
        assertEquals(2,
            aclScenarioUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(SCENARIO_UID_1, null));
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
            RH_ACCOUNT_NUMBER, SCENARIO_UID_4, null, null, null);
        assertEquals(2, scenarioDetailDtos.size());
        AclScenarioDetailDto scenarioDetailDto1 = buildDigitalAclScenarioDetailDto();
        AclScenarioDetailDto scenarioDetailDto2 = buildPrintDigitalAclScenarioDetailDto();
        verifyAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDtos.get(0));
        verifyAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDtos.get(1));
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberNullSearchValue() {
        assertEquals(0, aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(1000009997L,
            SCENARIO_UID_4, null));
        assertEquals(2, aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER,
            SCENARIO_UID_4, null));
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchByUsageDetailId() {
        List<AclScenarioDetailDto> scenarioDetailDtos = Collections.singletonList(buildDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "OGN674GHHSB108");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "ogN674ghhSB108");
        verifyFindByScenarioIdAndRhSearch(Collections.emptyList(), "ogN674gh hSB108");
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
        List<AclScenarioDetailDto> scenarioDetailDtos = Collections.singletonList(buildDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "122820638");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "12282");
        verifyFindByScenarioIdAndRhSearch(Collections.emptyList(), "12282 0638");
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
        List<AclScenarioDetailDto> scenarioDetailDtos = Collections.singletonList(buildDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "Technology review");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "TeCHNoLOGY REV");
        verifyFindByScenarioIdAndRhSearch(Collections.emptyList(), "Techn ology review");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindCountByScenarioIdAndRhAccountNumberSearchSystemTitle() {
        verifyFindCountByScenarioIdAndRhSearch(1, "Technology review");
        verifyFindCountByScenarioIdAndRhSearch(1, "TeCHNoLOGY REV");
        verifyFindCountByScenarioIdAndRhSearch(0, "Techn ology review");
    }

    @Test
    @TestData(fileName = FIND_BY_SCENARIO_ID_AND_RH_ACCOUNT_NUMBER)
    public void testFindByScenarioIdAndRhAccountNumberSearchRhAccountNumber() {
        List<AclScenarioDetailDto> scenarioDetailDtos = Arrays.asList(buildDigitalAclScenarioDetailDto(),
            buildPrintDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "1000002859");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "1000002");
        verifyFindByScenarioIdAndRhSearch(Collections.emptyList(), "10000 02859");
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
        List<AclScenarioDetailDto> scenarioDetailDtos = Arrays.asList(buildDigitalAclScenarioDetailDto(),
            buildPrintDigitalAclScenarioDetailDto());
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "John Wiley & Sons - Books");
        verifyFindByScenarioIdAndRhSearch(scenarioDetailDtos, "JOHN Wiley");
        verifyFindByScenarioIdAndRhSearch(Collections.emptyList(), "John Wi ley & Sons - Books");
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
    public void testSortingFindFindByScenarioIdAndRhAccountNumber() {
        AclScenarioDetailDto scenarioDetailDto1 = buildDigitalAclScenarioDetailDto();
        AclScenarioDetailDto scenarioDetailDto2 = buildPrintDigitalAclScenarioDetailDto();
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "detailId");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "usageDetailId");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "productFamily");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "usageBatchName");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "periodEndDate");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "wrWrkInst");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "systemTitle");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "rhAccountNumberPrint");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "rhNamePrint");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "rhAccountNumberDigital");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto1, "rhNameDigital");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "usagePeriod");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "usageAgeWeight");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailLicenseeClassId");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailLicenseeClassName");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "aggregateLicenseeClassId");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "aggregateLicenseeClassName");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "surveyCountry");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "reportedTypeOfUse");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "numberOfCopies");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "weightedCopies");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "publicationType");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "pubTypeWeight");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "price");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "priceFlag");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "content");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "contentFlag");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "contentUnitPrice");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "contentUnitPriceFlag");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "valueSharePrint");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "volumeSharePrint");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailSharePrint");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "netAmountPrint");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "valueShareDigital");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto1, scenarioDetailDto2, "volumeShareDigital");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "detailShareDigital");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "netAmountDigital");
        assertSortingAclAclScenarioDetailDto(scenarioDetailDto2, scenarioDetailDto1, "combinedNetAmount");
    }

    private AclScenarioDetail buildAclScenarioDetail() {
        AclScenarioDetail scenarioDetail = new AclScenarioDetail();
        scenarioDetail.setScenarioId("dec62df4-6a8f-4c59-ad65-2a5e06b3924d");
        scenarioDetail.setPeriod(202112);
        scenarioDetail.setOriginalDetailId("OGN674GHHHB0110");
        scenarioDetail.setWrWrkInst(122820638L);
        scenarioDetail.setSystemTitle("Technology review");
        scenarioDetail.setDetailLicenseeClass(buildDetailLicenseeClass(43, "Other - Govt"));
        scenarioDetail.setAggregateLicenseeClassId(1);
        scenarioDetail.setAggregateLicenseeClassName("Food and Tobacco");
        scenarioDetail.setPublicationType(buildPubType());
        scenarioDetail.setContentUnitPrice(new BigDecimal("11.0000000000"));
        scenarioDetail.setQuantity(10L);
        scenarioDetail.setUsageAgeWeight(new BigDecimal("0.50000"));
        scenarioDetail.setWeightedCopies(new BigDecimal("5.0000000000"));
        scenarioDetail.setSurveyCountry("Germany");
        return scenarioDetail;
    }

    private static DetailLicenseeClass buildDetailLicenseeClass(int id, String description) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        detailLicenseeClass.setDescription(description);
        return detailLicenseeClass;
    }

    private static PublicationType buildPubType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setId("73876e58-2e87-485e-b6f3-7e23792dd214");
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        return publicationType;
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
        assertEquals(expectedScenarioDetail.getContentUnitPrice(), actualScenarioDetail.getContentUnitPrice());
        assertEquals(expectedScenarioDetail.getQuantity(), actualScenarioDetail.getQuantity());
        assertEquals(expectedScenarioDetail.getUsageAgeWeight(), actualScenarioDetail.getUsageAgeWeight());
        assertEquals(expectedScenarioDetail.getWeightedCopies(), actualScenarioDetail.getWeightedCopies());
        assertEquals(expectedScenarioDetail.getSurveyCountry(), actualScenarioDetail.getSurveyCountry());
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
        assertEquals(expectedDetail.getRhAccountNumberDigital(), actualDetail.getRhAccountNumberDigital());
        assertEquals(expectedDetail.getRhNameDigital(), actualDetail.getRhNameDigital());
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
                                                                         Double grossTotalPrint,
                                                                         Double serviceFeeTotalPrint,
                                                                         Double netTotalPrint, Double grossTotalDigital,
                                                                         Double serviceFeeTotalDigital,
                                                                         Double netTotalDigital,
                                                                         int numberOfTitles, int numberOfAggLcClasses) {
        AclRightsholderTotalsHolder holder = new AclRightsholderTotalsHolder();
        holder.getRightsholder().setAccountNumber(rhAccountNumber);
        holder.getRightsholder().setName(rhName);
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

    private void assertSortingAclRightsholderTotalsHolder(AclRightsholderTotalsHolder holderAsc,
                                                          AclRightsholderTotalsHolder holderDesc, String sortProperty) {
        List<AclRightsholderTotalsHolder> holders =
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
                SCENARIO_UID_1, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.ASC));
        verifyAclRightsholderTotalsHolder(holderAsc, holders.get(0));
        holders = aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            SCENARIO_UID_1, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.DESC));
        verifyAclRightsholderTotalsHolder(holderDesc, holders.get(0));
    }

    private void verifyFindByScenarioIdAndRhSearch(List<AclScenarioDetailDto> expectedScenarioDetailDtos,
                                                   String searchValue) {
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER, SCENARIO_UID_4, searchValue, null, null);
        assertEquals(expectedScenarioDetailDtos.size(), scenarioDetailDtos.size());
        if (!expectedScenarioDetailDtos.isEmpty()) {
            for (int i = 0; i < scenarioDetailDtos.size(); i++) {
                verifyAclScenarioDetailDto(expectedScenarioDetailDtos.get(i), scenarioDetailDtos.get(i));
            }
        }
    }

    private void verifyFindCountByScenarioIdAndRhSearch(int expectedSize, String searchValue) {
        assertEquals(expectedSize, aclScenarioUsageRepository.findCountByScenarioIdAndRhAccountNumber(RH_ACCOUNT_NUMBER,
            SCENARIO_UID_4, searchValue));
    }

    private void assertSortingAclAclScenarioDetailDto(AclScenarioDetailDto detailAsc, AclScenarioDetailDto detailDesc,
                                                      String sortProperty) {
        List<AclScenarioDetailDto> scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER, SCENARIO_UID_4, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.ASC));
        verifyAclScenarioDetailDto(detailAsc, scenarioDetailDtos.get(0));
        scenarioDetailDtos = aclScenarioUsageRepository.findByScenarioIdAndRhAccountNumber(
            RH_ACCOUNT_NUMBER, SCENARIO_UID_4, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.DESC));
        verifyAclScenarioDetailDto(detailDesc, scenarioDetailDtos.get(0));
    }

    private AclScenarioDetailDto buildDigitalAclScenarioDetailDto() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_digital.json");
    }

    private AclScenarioDetailDto buildPrintDigitalAclScenarioDetailDto() {
        return loadExpectedAclScenarioDetailDto("json/acl/acl_scenario_detail_dto_print_digital.json");
    }

    private AclScenarioDetailDto loadExpectedAclScenarioDetailDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<AclScenarioDetailDto>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
