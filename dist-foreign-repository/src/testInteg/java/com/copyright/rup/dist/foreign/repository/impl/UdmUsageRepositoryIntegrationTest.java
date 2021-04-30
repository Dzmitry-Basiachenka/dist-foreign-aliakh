package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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

    private static final String UDM_USAGE_ORIGIN_UID = "3fb43e60-3352-4db4-9080-c30b8a6f6600";
    private static final String UDM_USAGE_UID = "4b5751aa-6258-44c6-b839-a1ec0edfcf4d";
    private static final String UDM_BATCH_UID = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
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
    // Have to use incorrect ip for testing purposes as PMD disallows hardcoded ips
    private static final String IP_ADDRESS = "ip24.12.119.203";

    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Test
    public void testInsertUsageBatch() {
        udmUsageRepository.insert(buildUdmUsage());
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID));
        assertEquals(1, udmUsages.size());
        UdmUsage udmUsage = udmUsages.get(0);
        assertNotNull(udmUsage);
        assertEquals(UDM_USAGE_UID, udmUsage.getId());
        assertEquals(UDM_BATCH_UID, udmUsage.getBatchId());
        assertEquals(UDM_USAGE_ORIGIN_UID, udmUsage.getOriginId());
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
        assertEquals(REPORTED_TYPE_OF_USE, udmUsage.getReportedTypeOfUse());
        assertEquals(QUANTITY, udmUsage.getQuantity());
        assertEquals(StringUtils.EMPTY, udmUsage.getIneligibleReason());
    }

    @Test
    public void testIsOriginalDetailIdExist() {
        assertTrue(udmUsageRepository.isOriginalDetailIdExist("46754660-b627-46b9-a782-3f703b6853c7"));
        assertFalse(udmUsageRepository.isOriginalDetailIdExist("78754660-cc11-46b9-a756-3f708b6853cc"));
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(UDM_USAGE_UID);
        udmUsage.setBatchId(UDM_BATCH_UID);
        udmUsage.setOriginId(UDM_USAGE_ORIGIN_UID);
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
        udmUsage.setReportedTypeOfUse(REPORTED_TYPE_OF_USE);
        udmUsage.setQuantity(QUANTITY);
        udmUsage.setIneligibleReason(StringUtils.EMPTY);
        return udmUsage;
    }
}
