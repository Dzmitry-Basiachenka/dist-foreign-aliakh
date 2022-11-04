package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioShareDetail;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageArchiveRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Integration test for {@link AclScenarioUsageArchiveRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclScenarioUsageArchiveRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-scenario-usage-archived-repository-integration-test/";
    private static final String FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID =
        FOLDER_NAME + "find-acl-rh-totals-holders-by-scenario-id.groovy";
    private static final String SCENARIO_UID = "326d59f3-bdea-4579-9100-63d0e76386fe";
    private static final String SCENARIO_UID_1 = "3ac016af-eb68-41b0-a031-7477e623a443";
    private static final String SCENARIO_UID_2 = "0d0041a3-833e-463e-8ad4-f28461dc961d";
    private static final String SCENARIO_UID_3 = "53a1c4e8-f1fe-4b17-877e-2d721b2059b5";
    private static final String SCENARIO_UID_4 = "d18d7cab-8a69-4b60-af5a-0a0c99b8a4d3";
    private static final String LICENSE_TYPE_ACL = "ACL";
    private static final String RH_NAME = "John Wiley & Sons - Books";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAclScenarioUsageArchiveRepository archiveRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "copy-scenario-details-to-archive.groovy")
    public void testCopyScenarioDetailsToArchiveByScenarioId() {
        List<AclScenarioDetail> expectedDetails =
            loadExpectedAclScenarioDetails("json/acl/acl_scenario_archived_details_copy_details.json");
        List<String> detailIds = archiveRepository.copyScenarioDetailsToArchiveByScenarioId(SCENARIO_UID, "SYSTEM");
        assertEquals(2, detailIds.size());
        assertEquals("0451320c-60b6-4570-b6d3-12e82f62f21c", detailIds.get(0));
        assertEquals("a5c179d5-7f49-4b12-b006-4ebb088238a4", detailIds.get(1));
        List<AclScenarioDetail> actualDetails = archiveRepository.findByScenarioId(SCENARIO_UID);
        IntStream.range(0, actualDetails.size()).forEach(i ->
            verifyAclScenarioDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "copy-scenario-shares-to-archive.groovy")
    public void testCopyScenarioSharesToArchiveByScenarioId() {
        List<AclScenarioDetail> expectedDetails =
            loadExpectedAclScenarioDetails("json/acl/acl_scenario_archived_details_shares.json");
        archiveRepository.copyScenarioDetailsToArchiveByScenarioId(SCENARIO_UID,
            "SYSTEM");
        List<String> sharesIds = archiveRepository.copyScenarioSharesToArchiveByScenarioId(SCENARIO_UID, "SYSTEM");
        assertEquals(3, sharesIds.size());
        assertEquals("2f55e6df-7be6-4a34-966d-6b53222f4e4b", sharesIds.get(0));
        assertEquals("a7cf7f7c-8884-48e1-a6d8-a1315f27dc6f", sharesIds.get(1));
        assertEquals("f250cc16-e95b-4e66-a31a-13068b67d247", sharesIds.get(2));
        List<AclScenarioDetail> actualDetails = archiveRepository.findByScenarioId(SCENARIO_UID);
        IntStream.range(0, actualDetails.size()).forEach(i -> {
            AclScenarioDetail expectedScenarioDetail = expectedDetails.get(i);
            AclScenarioDetail actualScenarioDetail = actualDetails.get(i);
            verifyAclScenarioDetail(expectedDetails.get(i), actualDetails.get(i));
            IntStream.range(0, expectedScenarioDetail.getScenarioShareDetails().size()).forEach(r ->
                verifyAclScenarioShareDetails(expectedScenarioDetail.getScenarioShareDetails().get(r),
                    actualScenarioDetail.getScenarioShareDetails().get(r)));
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
    public void testFindDetailByScenarioId() {
        List<AclScenarioDetail> expectedDetails =
            loadExpectedAclScenarioDetails("json/acl/acl_scenario_archived_details_find_by_id.json");
        List<AclScenarioDetail> actualDetails = archiveRepository.findByScenarioId(SCENARIO_UID);
        IntStream.range(0, actualDetails.size()).forEach(i -> {
            AclScenarioDetail expectedScenarioDetail = expectedDetails.get(i);
            AclScenarioDetail actualScenarioDetail = actualDetails.get(i);
            verifyAclScenarioDetail(expectedDetails.get(i), actualDetails.get(i));
            IntStream.range(0, expectedScenarioDetail.getScenarioShareDetails().size()).forEach(r ->
                verifyAclScenarioShareDetails(expectedScenarioDetail.getScenarioShareDetails().get(r),
                    actualScenarioDetail.getScenarioShareDetails().get(r)));
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-for-send-to-lm-by-scenario-id.groovy")
    public void testFindForSendToLmByScenarioId() {
        List<AclScenarioLiabilityDetail> expectedDetails =
            loadExpectedAclScenarioLiabilityDetails("json/acl/acl_scenario_archived_liability_details_find_by_id.json");
        List<AclScenarioLiabilityDetail> actualDetails = archiveRepository.findForSendToLmByScenarioId(SCENARIO_UID_1);
        assertEquals(3, actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> verifyAclScenarioLiabilityDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders =
            archiveRepository.findAclRightsholderTotalsHoldersByScenarioId(SCENARIO_UID_2);
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
        AclScenarioDto scenario = archiveRepository.findWithAmountsAndLastAction(SCENARIO_UID_4);
        assertNotNull(scenario);
        assertEquals(SCENARIO_UID_4, scenario.getId());
        assertEquals("1b48301c-e953-4af1-8ccb-8b3f9ed31544", scenario.getFundPoolId());
        assertEquals("30d8a41f-9b01-42cd-8041-ce840512a040", scenario.getUsageBatchId());
        assertEquals("b175a252-2fb9-47da-8d40-8ad82107f546", scenario.getGrantSetId());
        assertEquals("ACL Scenario 202012", scenario.getName());
        assertEquals("some description", scenario.getDescription());
        assertEquals("Another Scenario", scenario.getCopiedFrom());
        assertEquals(ScenarioStatusEnum.ARCHIVED, scenario.getStatus());
        assertFalse(scenario.isEditableFlag());
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
        AclScenarioDto scenario = archiveRepository.findWithAmountsAndLastAction(SCENARIO_UID_3);
        assertNotNull(scenario);
        assertEquals(SCENARIO_UID_3, scenario.getId());
        assertEquals("e8a591d8-2803-4f9e-8cf5-4cd6257917e8", scenario.getFundPoolId());
        assertEquals("794481d7-41e5-44b5-929b-87f379b28ffa", scenario.getUsageBatchId());
        assertEquals("fb637adf-04a6-4bee-b195-8cbde93bf672", scenario.getGrantSetId());
        assertEquals("ACL Scenario 202112", scenario.getName());
        assertEquals("another description", scenario.getDescription());
        assertEquals("Another Scenario", scenario.getCopiedFrom());
        assertEquals(ScenarioStatusEnum.ARCHIVED, scenario.getStatus());
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

    private List<AclScenarioDetail> loadExpectedAclScenarioDetails(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclScenarioDetail>>() {
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
