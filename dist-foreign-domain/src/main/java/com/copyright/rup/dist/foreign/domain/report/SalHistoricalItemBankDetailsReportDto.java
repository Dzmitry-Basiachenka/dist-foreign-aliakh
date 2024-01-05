package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

/**
 * Represents SAL Historical Item Bank Details Report record.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 11/26/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalHistoricalItemBankDetailsReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = 1461789612399161523L;

    private String itemBankName;
    private LocalDate periodEndDate;
    private Long licenseeAccountNumber;
    private String licenseeName;
    private Long rhAccountNumber;
    private String rhName;
    private String wrWrkInst;
    private String systemTitle;
    private String standardNumber;
    private String standardNumberType;
    private String assessmentName;
    private String reportedWorkPortionId;
    private String reportedTitle;
    private String reportedArticle;
    private String reportedStandardNumber;
    private String reportedMediaType;
    private String coverageYear;
    private String grade;

    public String getItemBankName() {
        return itemBankName;
    }

    public void setItemBankName(String itemBankName) {
        this.itemBankName = itemBankName;
    }

    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public Long getLicenseeAccountNumber() {
        return licenseeAccountNumber;
    }

    public void setLicenseeAccountNumber(Long licenseeAccountNumber) {
        this.licenseeAccountNumber = licenseeAccountNumber;
    }

    public String getLicenseeName() {
        return licenseeName;
    }

    public void setLicenseeName(String licenseeName) {
        this.licenseeName = licenseeName;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public String getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(String wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    public String getStandardNumberType() {
        return standardNumberType;
    }

    public void setStandardNumberType(String standardNumberType) {
        this.standardNumberType = standardNumberType;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getReportedWorkPortionId() {
        return reportedWorkPortionId;
    }

    public void setReportedWorkPortionId(String reportedWorkPortionId) {
        this.reportedWorkPortionId = reportedWorkPortionId;
    }

    public String getReportedTitle() {
        return reportedTitle;
    }

    public void setReportedTitle(String reportedTitle) {
        this.reportedTitle = reportedTitle;
    }

    public String getReportedArticle() {
        return reportedArticle;
    }

    public void setReportedArticle(String reportedArticle) {
        this.reportedArticle = reportedArticle;
    }

    public String getReportedStandardNumber() {
        return reportedStandardNumber;
    }

    public void setReportedStandardNumber(String reportedStandardNumber) {
        this.reportedStandardNumber = reportedStandardNumber;
    }

    public String getReportedMediaType() {
        return reportedMediaType;
    }

    public void setReportedMediaType(String reportedMediaType) {
        this.reportedMediaType = reportedMediaType;
    }

    public String getCoverageYear() {
        return coverageYear;
    }

    public void setCoverageYear(String coverageYear) {
        this.coverageYear = coverageYear;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        SalHistoricalItemBankDetailsReportDto that = (SalHistoricalItemBankDetailsReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(itemBankName, that.itemBankName)
            .append(periodEndDate, that.periodEndDate)
            .append(licenseeAccountNumber, that.licenseeAccountNumber)
            .append(licenseeName, that.licenseeName)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(standardNumber, that.standardNumber)
            .append(standardNumberType, that.standardNumberType)
            .append(assessmentName, that.assessmentName)
            .append(reportedWorkPortionId, that.reportedWorkPortionId)
            .append(reportedTitle, that.reportedTitle)
            .append(reportedArticle, that.reportedArticle)
            .append(reportedStandardNumber, that.reportedStandardNumber)
            .append(reportedMediaType, that.reportedMediaType)
            .append(coverageYear, that.coverageYear)
            .append(grade, that.grade)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(itemBankName)
            .append(periodEndDate)
            .append(licenseeAccountNumber)
            .append(licenseeName)
            .append(rhAccountNumber)
            .append(rhName)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(standardNumber)
            .append(standardNumberType)
            .append(assessmentName)
            .append(reportedWorkPortionId)
            .append(reportedTitle)
            .append(reportedArticle)
            .append(reportedStandardNumber)
            .append(reportedMediaType)
            .append(coverageYear)
            .append(grade)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("itemBankName", itemBankName)
            .append("periodEndDate", periodEndDate)
            .append("licenseeAccountNumber", licenseeAccountNumber)
            .append("licenseeName", licenseeName)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("standardNumber", standardNumber)
            .append("standardNumberType", standardNumberType)
            .append("assessmentName", assessmentName)
            .append("reportedWorkPortionId", reportedWorkPortionId)
            .append("reportedTitle", reportedTitle)
            .append("reportedArticle", reportedArticle)
            .append("reportedStandardNumber", reportedStandardNumber)
            .append("reportedMediaType", reportedMediaType)
            .append("coverageYear", coverageYear)
            .append("grade", grade)
            .toString();
    }
}
