package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies functionality for loading classified AACL usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/29/2020
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "load-classified-usages-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class LoadClassifiedUsagesIntegrationTest {

    private static final String USAGE_ID_1 = "d5973116-2ea0-4808-80f5-93016e24cfa4";
    private static final String USAGE_ID_2 = "833aa413-ee36-4f1c-bea1-ec7a0f6e507d";
    private static final String USAGE_ID_3 = "9e4c3be6-fafd-44f3-af7f-915e861e31c7";
    private static final String UPLOADED_REASON = "Uploaded in 'Test Batch'";
    private static final String WORK_FOUND_REASON = "Wr Wrk Inst 122825976 was found in PI";
    private static final String RH_FOUND_REASON = "Rightsholder account 7001413934 was found in RMS";

    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    private ServiceTestHelper testHelper;

    @Test
    public void testLoadClassifiedUsages() throws IOException {
        List<AaclClassifiedUsage> usages = Arrays.asList(
            buildUsage(USAGE_ID_1, 122825976L, "booK", "life sciences", "exgp", "comment"),
            buildUsage(USAGE_ID_2, 122825976L, "DisQualified", "engineering", "Mu", "comment"),
            buildUsage(USAGE_ID_3, 123456789L, "Book", "life sciences", "exgp", "updated comment"));
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON,
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_2, buildUsageAuditItems(USAGE_ID_2, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON,
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_3, buildUsageAuditItems(USAGE_ID_3, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON,
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        aaclUsageService.updateClassifiedUsages(usages);
        assertUsages();
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usages has become eligible after classification",
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON,
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_3, buildUsageAuditItems(USAGE_ID_3, ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usages has become eligible after classification",
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON,
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        assertEquals(List.of(), auditService.getUsageAudit(USAGE_ID_2));
    }

    private List<UsageAuditItem> buildUsageAuditItems(String usageId, Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        map.forEach((actionTypeEnum, detail) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setUsageId(usageId);
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }

    private void assertUsages() throws IOException {
        List<Usage> actualUsages = aaclUsageRepository.findByIds(Arrays.asList(USAGE_ID_1, USAGE_ID_2, USAGE_ID_3))
            .stream()
            .sorted(Comparator.comparing(Usage::getId))
            .collect(Collectors.toList());
        List<Usage> expectedUsages = testHelper
            .loadExpectedUsages("usage/aacl/classified/expected_classified_usages.json")
            .stream()
            .sorted(Comparator.comparing(Usage::getId))
            .collect(Collectors.toList());
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size()).forEach(i -> assertUsage(expectedUsages.get(i), actualUsages.get(i)));
    }

    private AaclClassifiedUsage buildUsage(String id, Long wrWrkInst, String pubType, String discipline,
                                           String enrollmentProfile, String comment) {
        AaclClassifiedUsage usage = new AaclClassifiedUsage();
        usage.setDetailId(id);
        usage.setPublicationType(pubType);
        usage.setDiscipline(discipline);
        usage.setEnrollmentProfile(enrollmentProfile);
        usage.setWrWrkInst(wrWrkInst);
        usage.setComment(comment);
        return usage;
    }

    private void assertUsage(Usage expectedUsage, Usage actualUsage) {
        assertNotNull(actualUsage);
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        AaclUsage expectedAaclUsage = expectedUsage.getAaclUsage();
        AaclUsage actualAaclUsage = actualUsage.getAaclUsage();
        assertEquals(expectedAaclUsage.getBatchPeriodEndDate(), actualAaclUsage.getBatchPeriodEndDate());
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsageAge().getPeriod(), actualAaclUsage.getUsageAge().getPeriod());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getId(), actualAaclUsage.getPublicationType().getId());
        assertEquals(expectedAaclUsage.getPublicationType().getName(), actualAaclUsage.getPublicationType().getName());
        assertEquals(expectedAaclUsage.getPublicationType().getWeight(),
            actualAaclUsage.getPublicationType().getWeight());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getDiscipline(),
            actualAaclUsage.getDetailLicenseeClass().getDiscipline());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getEnrollmentProfile(),
            actualAaclUsage.getDetailLicenseeClass().getEnrollmentProfile());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getId(),
            actualAaclUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
    }
}
