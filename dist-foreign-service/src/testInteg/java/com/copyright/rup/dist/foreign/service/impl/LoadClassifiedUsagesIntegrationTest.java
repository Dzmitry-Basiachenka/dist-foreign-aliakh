package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=load-classified-usages-data-init.groovy"})
@Transactional
public class LoadClassifiedUsagesIntegrationTest {

    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    private ServiceTestHelper testHelper;

    private static final String USAGE_ID_1 = "d5973116-2ea0-4808-80f5-93016e24cfa4";
    private static final String USAGE_ID_2 = "833aa413-ee36-4f1c-bea1-ec7a0f6e507d";
    private static final String UPLOADED_REASON = "Uploaded in 'Test Batch'";

    @Test
    public void testLoadClassifiedUsages() throws IOException {
        List<AaclClassifiedUsage> usages = Arrays.asList(
            buildUsage(USAGE_ID_1, "booK", "life sciences", "exgp"),
            buildUsage(USAGE_ID_2, "DisQualified", "engineering", "Mu"));
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 7001413934 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 122825976 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_2, buildUsageAuditItems(USAGE_ID_2, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 7001413934 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 122825976 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        aaclUsageService.updateClassifiedUsages(usages);
        assertUsages();
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usages has become eligible after classification",
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 7001413934 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 122825976 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        assertEquals(Collections.emptyList(), auditService.getUsageAudit(USAGE_ID_2));
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
        List<Usage> actualUsages = aaclUsageRepository.findByIds(Arrays.asList(USAGE_ID_1, USAGE_ID_2));
        List<Usage> expectedUsages =
            loadExpectedUsages("usage/aacl/classified/expected_classified_usages.json");
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size()).forEach(i -> assertUsage(expectedUsages.get(i), actualUsages.get(i)));
    }

    private List<Usage> loadExpectedUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<Usage>>() {
        });
    }

    private AaclClassifiedUsage buildUsage(String id, String pubType, String discipline, String enrollmentProfile) {
        AaclClassifiedUsage usage = new AaclClassifiedUsage();
        usage.setDetailId(id);
        usage.setPublicationType(pubType);
        usage.setDiscipline(discipline);
        usage.setEnrollmentProfile(enrollmentProfile);
        usage.setWrWrkInst(122825976L);
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
        assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getPublicationType(), actualAaclUsage.getPublicationType());
        assertEquals(expectedAaclUsage.getDiscipline(), actualAaclUsage.getDiscipline());
        assertEquals(expectedAaclUsage.getEnrollmentProfile(), actualAaclUsage.getEnrollmentProfile());
        assertEquals(expectedAaclUsage.getDetailLicenseeClassId(), actualAaclUsage.getDetailLicenseeClassId());
    }
}