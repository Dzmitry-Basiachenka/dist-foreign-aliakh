package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=udm-usage-repository-test-data-init.groovy"})
@Transactional
public class UdmUsageRepositoryIntegrationTest {

    private static final String UDM_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0025";
    private static final String DETAIL_ID_1 = "cc3269aa-2f56-21c7-b0d1-34dd0edfcf5a";
    private static final String DETAIL_ID_2 = "847dfefd-3cf8-4853-8b1b-d59b5cd163e9";
    private static final String UDM_USAGE_UID_1 = "acd033da-cba1-4e85-adc9-0bcd00687d9d";
    private static final String UDM_USAGE_UID_2 = "0dfba00c-6d72-4a0e-8f84-f7c365432f85";
    private static final String UDM_BATCH_UID_1 = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
    private static final String UDM_BATCH_UID_2 = "bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a";
    private static final String UDM_BATCH_UID_3 = "4b6055be-fc4e-4b49-aeab-28563366c9fd";
    private static final String SURVEY_RESPONDENT = "fa0276c3-55d6-42cd-8ffe-e9124acae02f";
    private static final String REPORTED_STANDARD_NUMBER = "0927-7765";
    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";
    private static final String REPORTED_TITLE = "Colloids and surfaces. B, Biointerfaces";
    private static final String REPORTED_PUB_TYPE = "Journal";
    private static final String PUBLICATION_FORMAT = "Format";
    private static final String ARTICLE = "Green chemistry";
    private static final String LANGUAGE = "English";
    private static final String SURVEY_COUNTRY = "United States";
    private static final LocalDate PERIOD_END_DATE = LocalDate.of(2021, 12, 12);
    private static final LocalDate USAGE_DATE = LocalDate.of(2020, 12, 12);
    private static final LocalDate SURVEY_START_DATE = LocalDate.of(2019, 12, 12);
    private static final LocalDate SURVEY_END_DATE = LocalDate.of(2022, 12, 12);
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long COMPANY_ID = 77000456L;
    private static final Integer QUANTITY = 10;
    private static final Integer ANNUAL_MULTIPLIER = 1;
    private static final BigDecimal STATISTICAL_MULTIPLIER = new BigDecimal("1.00000");
    // Have to use incorrect ip for testing purposes as PMD disallows hardcoded ips
    private static final String IP_ADDRESS = "ip24.12.119.203";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String DIGITAL = "DIGITAL";

    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Test
    public void testInsert() {
        udmUsageRepository.insert(buildUdmUsage());
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_1));
        assertEquals(1, udmUsages.size());
        UdmUsage udmUsage = udmUsages.get(0);
        assertNotNull(udmUsage);
        assertEquals(UDM_USAGE_UID_1, udmUsage.getId());
        assertEquals(UDM_BATCH_UID_1, udmUsage.getBatchId());
        assertEquals(UDM_USAGE_ORIGINAL_DETAIL_UID, udmUsage.getOriginalDetailId());
        assertEquals(UsageStatusEnum.NEW, udmUsage.getStatus());
        assertEquals(PERIOD_END_DATE, udmUsage.getPeriodEndDate());
        assertEquals(USAGE_DATE, udmUsage.getUsageDate());
        assertEquals(WR_WRK_INST, udmUsage.getWrWrkInst());
        assertEquals(REPORTED_STANDARD_NUMBER, udmUsage.getReportedStandardNumber());
        assertEquals(REPORTED_TITLE, udmUsage.getReportedTitle());
        assertEquals(REPORTED_PUB_TYPE, udmUsage.getReportedPubType());
        assertEquals(PUBLICATION_FORMAT, udmUsage.getPubFormat());
        assertEquals(ARTICLE, udmUsage.getArticle());
        assertEquals(LANGUAGE, udmUsage.getLanguage());
        assertEquals(COMPANY_ID, udmUsage.getCompanyId());
        assertEquals(SURVEY_RESPONDENT, udmUsage.getSurveyRespondent());
        assertEquals(IP_ADDRESS, udmUsage.getIpAddress());
        assertEquals(SURVEY_COUNTRY, udmUsage.getSurveyCountry());
        assertEquals(SURVEY_START_DATE, udmUsage.getSurveyStartDate());
        assertEquals(SURVEY_END_DATE, udmUsage.getSurveyEndDate());
        assertEquals(ANNUAL_MULTIPLIER, udmUsage.getAnnualMultiplier());
        assertEquals(STATISTICAL_MULTIPLIER, udmUsage.getStatisticalMultiplier());
        assertEquals(DIGITAL, udmUsage.getTypeOfUse());
        assertEquals(REPORTED_TYPE_OF_USE, udmUsage.getReportedTypeOfUse());
        assertEquals(QUANTITY, udmUsage.getQuantity());
        assertEquals(StringUtils.EMPTY, udmUsage.getIneligibleReason());
    }

    @Test
    public void findIdsByStatus() {
        List<String> udmUsageIds = udmUsageRepository.findIdsByStatus(UsageStatusEnum.WORK_FOUND);
        assertEquals(2, udmUsageIds.size());
        assertTrue(udmUsageIds.contains("587dfefd-3cf8-4853-8b1b-d59b5cd163e9"));
        assertTrue(udmUsageIds.contains("987dfefd-3c24-4853-8b1c-aaab5cd163e1"));
    }

    @Test
    public void testFindByIds() {
        UdmUsage udmUsage = buildUdmUsage();
        List<UdmUsage> expectedUsagesByIds = Collections.singletonList(udmUsage);
        udmUsageRepository.insert(udmUsage);
        List<UdmUsage> actualUsagesByIds = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_1));
        assertEquals(expectedUsagesByIds, actualUsagesByIds);
    }

    @Test
    public void testFindDtosByFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton(UDM_BATCH_UID_2));
        List<UdmUsageDto> usages =
            udmUsageRepository.findDtosByFilter(filter, null, new Sort("detailId", Sort.Direction.ASC));
        assertEquals(1, usages.size());
        verifyUsageDto(buildUdmUsageDto("cc3269aa-2f56-21c7-b0d1-34dd0edfcf5a"), usages.get(0));
    }

    @Test
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "detailId");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "period");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "usageOrigin");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "usageDetailId");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "status");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "assignee");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "rhAccountNumber");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "rhName");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "wrWrkInst");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "reportedTitle");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "systemTitle");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "reportedStandardNumber");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "standardNumber");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "reportedPubType");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "publicationFormat");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "article");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "language");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "companyId");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "companyName");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "surveyRespondent");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "ipAddress");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "surveyCountry");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "channel");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "usageDate");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "surveyStartDate");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "surveyEndDate");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "annualMultiplier");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "statisticalMultiplier");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "reportedTypeOfUse");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "quantity");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "annualizedCopies");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "ineligibleReason");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "createDate");
        assertSortingFindDtosByFilter(DETAIL_ID_1, DETAIL_ID_2, "updateDate");
        assertSortingFindDtosByFilter(DETAIL_ID_2, DETAIL_ID_1, "updateUser");
    }

    @Test
    public void testFindCountByFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Sets.newHashSet(UDM_BATCH_UID_2, UDM_BATCH_UID_3));
        assertEquals(2, udmUsageRepository.findCountByFilter(filter));
    }

    @Test
    public void testIsOriginalDetailIdExist() {
        assertTrue(udmUsageRepository.isOriginalDetailIdExist("OGN674GHHSB001"));
        assertFalse(udmUsageRepository.isOriginalDetailIdExist("OGN674GHHSB101"));
    }

    @Test
    public void testUpdateProcessedUsage() {
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_2));
        assertEquals(1, CollectionUtils.size(udmUsages));
        UdmUsage udmUsage = udmUsages.get(0);
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        udmUsage.setWrWrkInst(5697789789L);
        udmUsage.setSystemTitle("Wissenschaft & Forschung France");
        udmUsage.setStandardNumber(STANDARD_NUMBER);
        assertNotNull(udmUsageRepository.updateProcessedUsage(udmUsage));
        List<UdmUsage> updatedUdmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_2));
        assertEquals(1, CollectionUtils.size(updatedUdmUsages));
        UdmUsage updatedUdmUsage = updatedUdmUsages.get(0);
        assertEquals(RH_ACCOUNT_NUMBER, updatedUdmUsage.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.RH_FOUND, updatedUdmUsage.getStatus());
        assertEquals(STANDARD_NUMBER, updatedUdmUsage.getStandardNumber());
        assertEquals("Wissenschaft & Forschung France", updatedUdmUsage.getSystemTitle());
    }

    private void assertSortingFindDtosByFilter(String detailIdAsc, String detailIdDesc, String sortProperty) {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Sets.newHashSet(UDM_BATCH_UID_2, UDM_BATCH_UID_3));
        List<UdmUsageDto> usageDtos =
            udmUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.ASC));
        assertEquals(detailIdAsc, usageDtos.get(0).getId());
        usageDtos =
            udmUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.DESC));
        assertEquals(detailIdDesc, usageDtos.get(0).getId());
    }

    private void verifyUsageDto(UdmUsageDto expectedUsage, UdmUsageDto actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getPeriod(), actualUsage.getPeriod());
        assertEquals(expectedUsage.getUsageOrigin(), actualUsage.getUsageOrigin());
        assertEquals(expectedUsage.getChannel(), actualUsage.getChannel());
        assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getUsageDate(), actualUsage.getUsageDate());
        assertEquals(expectedUsage.getAssignee(), actualUsage.getAssignee());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getRhName(), actualUsage.getRhName());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getReportedTitle(), actualUsage.getReportedTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getReportedPubType(), actualUsage.getReportedPubType());
        assertEquals(expectedUsage.getPubFormat(), actualUsage.getPubFormat());
        assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
        assertEquals(expectedUsage.getLanguage(), actualUsage.getLanguage());
        assertEquals(expectedUsage.getCompanyId(), actualUsage.getCompanyId());
        assertEquals(expectedUsage.getCompanyName(), actualUsage.getCompanyName());
        assertEquals(expectedUsage.getSurveyRespondent(), actualUsage.getSurveyRespondent());
        assertEquals(expectedUsage.getIpAddress(), actualUsage.getIpAddress());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getSurveyStartDate(), actualUsage.getSurveyStartDate());
        assertEquals(expectedUsage.getStatisticalMultiplier(), actualUsage.getStatisticalMultiplier());
        assertEquals(expectedUsage.getAnnualMultiplier(), actualUsage.getAnnualMultiplier());
        assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertNull(actualUsage.getIneligibleReason());
    }

    private UdmUsageDto buildUdmUsageDto(String usageId) {
        UdmUsageDto udmUsage = new UdmUsageDto();
        udmUsage.setId(usageId);
        udmUsage.setPeriod(202106);
        udmUsage.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsage.setChannel(UdmChannelEnum.CCC);
        udmUsage.setOriginalDetailId("OGN674GHHSB001");
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.setUsageDate(LocalDate.of(2020, 9, 10));
        udmUsage.setAssignee("jjohn@copyright.com");
        udmUsage.setRhAccountNumber(1000002859L);
        udmUsage.setRhName("John Wiley & Sons - Books");
        udmUsage.setWrWrkInst(306985867L);
        udmUsage.setReportedStandardNumber(REPORTED_STANDARD_NUMBER);
        udmUsage.setStandardNumber("1873-7773");
        udmUsage.setReportedTitle(REPORTED_TITLE);
        udmUsage.setSystemTitle("Tenside, surfactants, detergents");
        udmUsage.setReportedPubType("Book");
        udmUsage.setPubFormat("format");
        udmUsage.setArticle("Tenside, surfactants, detergents");
        udmUsage.setLanguage("German");
        udmUsage.setCompanyId(454984566L);
        udmUsage.setCompanyName("Skadden, Arps, Slate, Meagher & Flom LLP");
        udmUsage.setSurveyRespondent("56282dbc-2468-48d4-b926-93d3458a656a");
        udmUsage.setIpAddress(IP_ADDRESS);
        udmUsage.setSurveyCountry("USA");
        udmUsage.setSurveyStartDate(LocalDate.of(2020, 9, 10));
        udmUsage.setSurveyEndDate(LocalDate.of(2021, 9, 10));
        udmUsage.setAnnualMultiplier(5);
        udmUsage.setStatisticalMultiplier(new BigDecimal("0.10000"));
        udmUsage.setReportedTypeOfUse("EMAIL_COPY");
        udmUsage.setQuantity(10);
        udmUsage.setAnnualizedCopies(2);
        udmUsage.setIneligibleReason(StringUtils.EMPTY);
        return udmUsage;
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(UDM_USAGE_UID_1);
        udmUsage.setBatchId(UDM_BATCH_UID_1);
        udmUsage.setOriginalDetailId(UDM_USAGE_ORIGINAL_DETAIL_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
        udmUsage.setPeriodEndDate(PERIOD_END_DATE);
        udmUsage.setUsageDate(USAGE_DATE);
        udmUsage.setWrWrkInst(WR_WRK_INST);
        udmUsage.setReportedStandardNumber(REPORTED_STANDARD_NUMBER);
        udmUsage.setReportedTitle(REPORTED_TITLE);
        udmUsage.setReportedPubType(REPORTED_PUB_TYPE);
        udmUsage.setPubFormat(PUBLICATION_FORMAT);
        udmUsage.setArticle(ARTICLE);
        udmUsage.setLanguage(LANGUAGE);
        udmUsage.setCompanyId(COMPANY_ID);
        udmUsage.setSurveyRespondent(SURVEY_RESPONDENT);
        udmUsage.setIpAddress(IP_ADDRESS);
        udmUsage.setSurveyCountry(SURVEY_COUNTRY);
        udmUsage.setSurveyStartDate(SURVEY_START_DATE);
        udmUsage.setSurveyEndDate(SURVEY_END_DATE);
        udmUsage.setAnnualMultiplier(ANNUAL_MULTIPLIER);
        udmUsage.setStatisticalMultiplier(STATISTICAL_MULTIPLIER);
        udmUsage.setTypeOfUse(DIGITAL);
        udmUsage.setReportedTypeOfUse(REPORTED_TYPE_OF_USE);
        udmUsage.setQuantity(QUANTITY);
        udmUsage.setIneligibleReason(StringUtils.EMPTY);
        return udmUsage;
    }
}
