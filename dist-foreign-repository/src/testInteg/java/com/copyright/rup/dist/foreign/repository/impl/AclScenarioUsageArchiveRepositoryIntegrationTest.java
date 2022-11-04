package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioShareDetail;
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
    private static final String SCENARIO_UID = "326d59f3-bdea-4579-9100-63d0e76386fe";
    private static final String SCENARIO_UID_1 = "3ac016af-eb68-41b0-a031-7477e623a443";
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
