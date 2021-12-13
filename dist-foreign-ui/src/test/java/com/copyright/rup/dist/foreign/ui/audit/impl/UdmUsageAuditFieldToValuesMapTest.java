package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link UdmUsageAuditFieldToValuesMap}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 12/06/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmUsageAuditFieldToValuesMapTest {

    private static final UsageStatusEnum STATUS_OLD = UsageStatusEnum.OPS_REVIEW;
    private static final UsageStatusEnum STATUS_NEW = UsageStatusEnum.ELIGIBLE;
    private static final Integer PERIOD_OLD = 202106;
    private static final Integer PERIOD_NEW = 202112;
    private static final Long WR_WRK_INST_OLD = 122853920L;
    private static final Long WR_WRK_INST_NEW = 459815489L;
    private static final String REPORTED_TITLE_OLD = "reported title 1";
    private static final String REPORTED_TITLE_NEW = "reported title 2";
    private static final String REPORTED_STANDARD_NUMBER_OLD = "0927-7765";
    private static final String REPORTED_STANDARD_NUMBER_NEW = "1463-9270";
    private static final String REPORTED_PUB_TYPE_OLD = "BK";
    private static final String REPORTED_PUB_TYPE_NEW = "BK2";
    private static final UdmActionReason ACTION_REASON_OLD =
        new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
    private static final UdmActionReason ACTION_REASON_NEW =
        new UdmActionReason("8842c02a-a867-42b0-9533-681122e7478f", "Assigned Rights");
    private static final String COMMENT_OLD = "comment 1";
    private static final String COMMENT_NEW = "comment 2";
    private static final String RESEARCH_URL_OLD = "google.com";
    private static final String RESEARCH_URL_NEW = "bing.com";
    private static final Long COMPANY_ID_OLD = 1136L;
    private static final Long COMPANY_ID_NEW = 1139L;
    private static final String COMPANY_NAME_OLD = "Albany International Corp.";
    private static final String COMPANY_NAME_NEW = "Alcon Laboratories, Inc.";
    private static final DetailLicenseeClass DETAIL_LICENSEE_CLASS_OLD =
        new DetailLicenseeClass(2, "Textiles, Apparel, etc.");
    private static final DetailLicenseeClass DETAIL_LICENSEE_CLASS_NEW =
        new DetailLicenseeClass(3, "Lumber, Paper, etc.");
    private static final Integer ANNUAL_MULTIPLIER_OLD = 25;
    private static final Integer ANNUAL_MULTIPLIER_NEW = 12;
    private static final BigDecimal STATISTICAL_MULTIPLIER_OLD = new BigDecimal("1.00000");
    private static final BigDecimal STATISTICAL_MULTIPLIER_NEW = new BigDecimal("0.90000");
    private static final Long QUANTITY_OLD = 10L;
    private static final Long QUANTITY_NEW = 20L;
    private static final BigDecimal ANNUALIZED_COPIES_OLD = new BigDecimal("250.00000");
    private static final BigDecimal ANNUALIZED_COPIES_NEW = new BigDecimal("450.00000");
    private static final UdmIneligibleReason INELIGIBLE_REASON_OLD =
        new UdmIneligibleReason("fd2f2dea-4018-48ee-a630-b8dfedbe857b", "Public Domain");
    private static final UdmIneligibleReason INELIGIBLE_REASON_NEW =
        new UdmIneligibleReason("cf1b711d-8c57-407c-b178-a8a2411c87e5", "Unauthorized use");

    @Test
    public void testStatus() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Detail Status", STATUS_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Detail Status' was edited. " +
            "Old Value is not specified. New Value is 'OPS_REVIEW'"), fieldToValuesMap.getActionReasons());
        usageDto.setStatus(STATUS_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Detail Status", STATUS_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Detail Status' was edited. " +
            "Old Value is 'OPS_REVIEW'. New Value is 'ELIGIBLE'"), fieldToValuesMap.getActionReasons());
        usageDto.setStatus(STATUS_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Detail Status", null);
        assertEquals(Collections.singletonList("The field 'Detail Status' was edited. " +
            "Old Value is 'ELIGIBLE'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testPeriod() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Period", PERIOD_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Period' was edited. " +
            "Old Value is not specified. New Value is '202106'"), fieldToValuesMap.getActionReasons());
        usageDto.setPeriod(PERIOD_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Period", PERIOD_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Period' was edited. " +
            "Old Value is '202106'. New Value is '202112'"), fieldToValuesMap.getActionReasons());
        usageDto.setPeriod(PERIOD_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Period", null);
        assertEquals(Collections.singletonList("The field 'Period' was edited. " +
            "Old Value is '202112'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testWrWrkInst() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Wr Wrk Inst", WR_WRK_INST_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Wr Wrk Inst' was edited. " +
            "Old Value is not specified. New Value is '122853920'"), fieldToValuesMap.getActionReasons());
        usageDto.setWrWrkInst(WR_WRK_INST_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Wr Wrk Inst", WR_WRK_INST_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Wr Wrk Inst' was edited. " +
            "Old Value is '122853920'. New Value is '459815489'"), fieldToValuesMap.getActionReasons());
        usageDto.setWrWrkInst(WR_WRK_INST_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Wr Wrk Inst", null);
        assertEquals(Collections.singletonList("The field 'Wr Wrk Inst' was edited. " +
            "Old Value is '459815489'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testReportedTitle() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Reported Title", REPORTED_TITLE_OLD);
        assertEquals(Collections.singletonList("The field 'Reported Title' was edited. " +
                "Old Value is not specified. New Value is 'reported title 1'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setReportedTitle(REPORTED_TITLE_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Reported Title", REPORTED_TITLE_NEW);
        assertEquals(Collections.singletonList("The field 'Reported Title' was edited. " +
                "Old Value is 'reported title 1'. New Value is 'reported title 2'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setReportedTitle(REPORTED_TITLE_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Reported Title", null);
        assertEquals(Collections.singletonList("The field 'Reported Title' was edited. " +
                "Old Value is 'reported title 2'. New Value is not specified"),
            fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testReportedStandardNumber() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Reported Standard Number", REPORTED_STANDARD_NUMBER_OLD);
        assertEquals(Collections.singletonList("The field 'Reported Standard Number' was edited. " +
            "Old Value is not specified. New Value is '0927-7765'"), fieldToValuesMap.getActionReasons());
        usageDto.setReportedStandardNumber(REPORTED_STANDARD_NUMBER_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Reported Standard Number", REPORTED_STANDARD_NUMBER_NEW);
        assertEquals(Collections.singletonList("The field 'Reported Standard Number' was edited. " +
            "Old Value is '0927-7765'. New Value is '1463-9270'"), fieldToValuesMap.getActionReasons());
        usageDto.setReportedStandardNumber(REPORTED_STANDARD_NUMBER_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Reported Standard Number", null);
        assertEquals(Collections.singletonList("The field 'Reported Standard Number' was edited. " +
            "Old Value is '1463-9270'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testReportedPubType() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Reported Pub Type", REPORTED_PUB_TYPE_OLD);
        assertEquals(Collections.singletonList("The field 'Reported Pub Type' was edited. " +
            "Old Value is not specified. New Value is 'BK'"), fieldToValuesMap.getActionReasons());
        usageDto.setReportedPubType(REPORTED_PUB_TYPE_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Reported Pub Type", REPORTED_PUB_TYPE_NEW);
        assertEquals(Collections.singletonList("The field 'Reported Pub Type' was edited. " +
            "Old Value is 'BK'. New Value is 'BK2'"), fieldToValuesMap.getActionReasons());
        usageDto.setReportedPubType(REPORTED_PUB_TYPE_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Reported Pub Type", null);
        assertEquals(Collections.singletonList("The field 'Reported Pub Type' was edited. " +
            "Old Value is 'BK2'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testActionReason() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Action Reason", ACTION_REASON_OLD.getReason());
        assertEquals(Collections.singletonList("The field 'Action Reason' was edited. " +
                "Old Value is not specified. New Value is 'Aggregated Content'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setActionReason(ACTION_REASON_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Action Reason", ACTION_REASON_NEW.getReason());
        assertEquals(Collections.singletonList("The field 'Action Reason' was edited. " +
                "Old Value is 'Aggregated Content'. New Value is 'Assigned Rights'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setActionReason(ACTION_REASON_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Action Reason", null);
        assertEquals(Collections.singletonList("The field 'Action Reason' was edited. " +
                "Old Value is 'Assigned Rights'. New Value is not specified"),
            fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testComment() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Comment", COMMENT_OLD);
        assertEquals(Collections.singletonList("The field 'Comment' was edited. " +
            "Old Value is not specified. New Value is 'comment 1'"), fieldToValuesMap.getActionReasons());
        valueDto.setComment(COMMENT_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Comment", COMMENT_NEW);
        assertEquals(Collections.singletonList("The field 'Comment' was edited. " +
            "Old Value is 'comment 1'. New Value is 'comment 2'"), fieldToValuesMap.getActionReasons());
        valueDto.setComment(COMMENT_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Comment", null);
        assertEquals(Collections.singletonList("The field 'Comment' was edited. " +
            "Old Value is 'comment 2'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testResearchUrl() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Research URL", RESEARCH_URL_OLD);
        assertEquals(Collections.singletonList("The field 'Research URL' was edited. " +
            "Old Value is not specified. New Value is 'google.com'"), fieldToValuesMap.getActionReasons());
        usageDto.setResearchUrl(RESEARCH_URL_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Research URL", RESEARCH_URL_NEW);
        assertEquals(Collections.singletonList("The field 'Research URL' was edited. " +
            "Old Value is 'google.com'. New Value is 'bing.com'"), fieldToValuesMap.getActionReasons());
        usageDto.setResearchUrl(RESEARCH_URL_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Research URL", null);
        assertEquals(Collections.singletonList("The field 'Research URL' was edited. " +
            "Old Value is 'bing.com'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testCompanyId() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Company ID", COMPANY_ID_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Company ID' was edited. " +
            "Old Value is not specified. New Value is '1136'"), fieldToValuesMap.getActionReasons());
        usageDto.setCompanyId(COMPANY_ID_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Company ID", COMPANY_ID_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Company ID' was edited. " +
            "Old Value is '1136'. New Value is '1139'"), fieldToValuesMap.getActionReasons());
        usageDto.setCompanyId(COMPANY_ID_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Company ID", null);
        assertEquals(Collections.singletonList("The field 'Company ID' was edited. " +
            "Old Value is '1139'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testCompanyName() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Company Name", COMPANY_NAME_OLD);
        assertEquals(Collections.singletonList("The field 'Company Name' was edited. " +
                "Old Value is not specified. New Value is 'Albany International Corp.'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setCompanyName(COMPANY_NAME_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Company Name", COMPANY_NAME_NEW);
        assertEquals(Collections.singletonList("The field 'Company Name' was edited. " +
                "Old Value is 'Albany International Corp.'. New Value is 'Alcon Laboratories, Inc.'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setCompanyName(COMPANY_NAME_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Company Name", null);
        assertEquals(Collections.singletonList("The field 'Company Name' was edited. " +
                "Old Value is 'Alcon Laboratories, Inc.'. New Value is not specified"),
            fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testDetailLicenseeClass() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Detail Licensee Class", DETAIL_LICENSEE_CLASS_OLD.getIdAndDescription());
        assertEquals(Collections.singletonList("The field 'Detail Licensee Class' was edited. " +
                "Old Value is not specified. New Value is '2 - Textiles, Apparel, etc.'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setDetailLicenseeClass(DETAIL_LICENSEE_CLASS_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Detail Licensee Class", DETAIL_LICENSEE_CLASS_NEW.getIdAndDescription());
        assertEquals(Collections.singletonList("The field 'Detail Licensee Class' was edited. " +
                "Old Value is '2 - Textiles, Apparel, etc.'. New Value is '3 - Lumber, Paper, etc.'"),
            fieldToValuesMap.getActionReasons());
        usageDto.setDetailLicenseeClass(DETAIL_LICENSEE_CLASS_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Detail Licensee Class", null);
        assertEquals(Collections.singletonList("The field 'Detail Licensee Class' was edited. " +
                "Old Value is '3 - Lumber, Paper, etc.'. New Value is not specified"),
            fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testAnnualMultiplier() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Annual Multiplier", ANNUAL_MULTIPLIER_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Annual Multiplier' was edited. " +
            "Old Value is not specified. New Value is '25'"), fieldToValuesMap.getActionReasons());
        usageDto.setAnnualMultiplier(ANNUAL_MULTIPLIER_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Annual Multiplier", ANNUAL_MULTIPLIER_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Annual Multiplier' was edited. " +
            "Old Value is '25'. New Value is '12'"), fieldToValuesMap.getActionReasons());
        usageDto.setAnnualMultiplier(ANNUAL_MULTIPLIER_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Annual Multiplier", null);
        assertEquals(Collections.singletonList("The field 'Annual Multiplier' was edited. " +
            "Old Value is '12'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testStatisticalMultiplier() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Statistical Multiplier", STATISTICAL_MULTIPLIER_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Statistical Multiplier' was edited. " +
            "Old Value is not specified. New Value is '1.00000'"), fieldToValuesMap.getActionReasons());
        usageDto.setStatisticalMultiplier(STATISTICAL_MULTIPLIER_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Statistical Multiplier", STATISTICAL_MULTIPLIER_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Statistical Multiplier' was edited. " +
            "Old Value is '1.00000'. New Value is '0.90000'"), fieldToValuesMap.getActionReasons());
        usageDto.setStatisticalMultiplier(STATISTICAL_MULTIPLIER_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Statistical Multiplier", null);
        assertEquals(Collections.singletonList("The field 'Statistical Multiplier' was edited. " +
            "Old Value is '0.90000'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testQuantity() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Quantity", QUANTITY_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Quantity' was edited. " +
            "Old Value is not specified. New Value is '10'"), fieldToValuesMap.getActionReasons());
        usageDto.setQuantity(QUANTITY_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Quantity", QUANTITY_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Quantity' was edited. " +
            "Old Value is '10'. New Value is '20'"), fieldToValuesMap.getActionReasons());
        usageDto.setQuantity(QUANTITY_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Quantity", null);
        assertEquals(Collections.singletonList("The field 'Quantity' was edited. " +
            "Old Value is '20'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testAnnualizedCopies() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Annualized Copies", ANNUALIZED_COPIES_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Annualized Copies' was edited. " +
            "Old Value is not specified. New Value is '250.00000'"), fieldToValuesMap.getActionReasons());
        usageDto.setAnnualizedCopies(ANNUALIZED_COPIES_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Annualized Copies", ANNUALIZED_COPIES_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Annualized Copies' was edited. " +
            "Old Value is '250.00000'. New Value is '450.00000'"), fieldToValuesMap.getActionReasons());
        usageDto.setAnnualizedCopies(ANNUALIZED_COPIES_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Annualized Copies", null);
        assertEquals(Collections.singletonList("The field 'Annualized Copies' was edited. " +
            "Old Value is '450.00000'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }

    @Test
    public void testIneligibleReason() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getActionReasons());
        fieldToValuesMap.updateFieldValue("Ineligible Reason", INELIGIBLE_REASON_OLD.getReason());
        assertEquals(Collections.singletonList("The field 'Ineligible Reason' was edited. " +
            "Old Value is not specified. New Value is 'Public Domain'"), fieldToValuesMap.getActionReasons());
        usageDto.setIneligibleReason(INELIGIBLE_REASON_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Ineligible Reason", INELIGIBLE_REASON_NEW.getReason());
        assertEquals(Collections.singletonList("The field 'Ineligible Reason' was edited. " +
            "Old Value is 'Public Domain'. New Value is 'Unauthorized use'"), fieldToValuesMap.getActionReasons());
        usageDto.setIneligibleReason(INELIGIBLE_REASON_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Ineligible Reason", null);
        assertEquals(Collections.singletonList("The field 'Ineligible Reason' was edited. " +
            "Old Value is 'Unauthorized use'. New Value is not specified"), fieldToValuesMap.getActionReasons());
    }
}
