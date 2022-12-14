package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents ACLCI specific usage details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsage {

    private Long licenseeAccountNumber;
    private String licenseeName;
    private String coveragePeriod;
    private AclciLicenseTypeEnum licenseType;
    private String reportedMediaType;
    private BigDecimal mediaTypeWeight;
    private String reportedArticle;
    private String reportedStandardNumber;
    private String reportedAuthor;
    private String reportedPublisher;
    private String reportedPublicationDate;
    private String reportedPageRange;
    private String reportedVolNumberSeries;
    private String reportedGrade;
    private String gradeGroup;
    private LocalDate batchPeriodEndDate;

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

    public String getCoveragePeriod() {
        return coveragePeriod;
    }

    public void setCoveragePeriod(String coveragePeriod) {
        this.coveragePeriod = coveragePeriod;
    }

    public AclciLicenseTypeEnum getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(AclciLicenseTypeEnum licenseType) {
        this.licenseType = licenseType;
    }

    public String getReportedMediaType() {
        return reportedMediaType;
    }

    public void setReportedMediaType(String reportedMediaType) {
        this.reportedMediaType = reportedMediaType;
    }

    public BigDecimal getMediaTypeWeight() {
        return mediaTypeWeight;
    }

    public void setMediaTypeWeight(BigDecimal mediaTypeWeight) {
        this.mediaTypeWeight = mediaTypeWeight;
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

    public String getReportedAuthor() {
        return reportedAuthor;
    }

    public void setReportedAuthor(String reportedAuthor) {
        this.reportedAuthor = reportedAuthor;
    }

    public String getReportedPublisher() {
        return reportedPublisher;
    }

    public void setReportedPublisher(String reportedPublisher) {
        this.reportedPublisher = reportedPublisher;
    }

    public String getReportedPublicationDate() {
        return reportedPublicationDate;
    }

    public void setReportedPublicationDate(String reportedPublicationDate) {
        this.reportedPublicationDate = reportedPublicationDate;
    }

    public String getReportedPageRange() {
        return reportedPageRange;
    }

    public void setReportedPageRange(String reportedPageRange) {
        this.reportedPageRange = reportedPageRange;
    }

    public String getReportedVolNumberSeries() {
        return reportedVolNumberSeries;
    }

    public void setReportedVolNumberSeries(String reportedVolNumberSeries) {
        this.reportedVolNumberSeries = reportedVolNumberSeries;
    }

    public String getReportedGrade() {
        return reportedGrade;
    }

    public void setReportedGrade(String reportedGrade) {
        this.reportedGrade = reportedGrade;
    }

    public String getGradeGroup() {
        return gradeGroup;
    }

    public void setGradeGroup(String gradeGroup) {
        this.gradeGroup = gradeGroup;
    }

    public LocalDate getBatchPeriodEndDate() {
        return batchPeriodEndDate;
    }

    public void setBatchPeriodEndDate(LocalDate batchPeriodEndDate) {
        this.batchPeriodEndDate = batchPeriodEndDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclciUsage that = (AclciUsage) obj;
        return new EqualsBuilder()
            .append(licenseeAccountNumber, that.licenseeAccountNumber)
            .append(licenseeName, that.licenseeName)
            .append(coveragePeriod, that.coveragePeriod)
            .append(licenseType, that.licenseType)
            .append(reportedMediaType, that.reportedMediaType)
            .append(mediaTypeWeight, that.mediaTypeWeight)
            .append(reportedArticle, that.reportedArticle)
            .append(reportedStandardNumber, that.reportedStandardNumber)
            .append(reportedAuthor, that.reportedAuthor)
            .append(reportedPublisher, that.reportedPublisher)
            .append(reportedPublicationDate, that.reportedPublicationDate)
            .append(reportedPageRange, that.reportedPageRange)
            .append(reportedVolNumberSeries, that.reportedVolNumberSeries)
            .append(reportedGrade, that.reportedGrade)
            .append(gradeGroup, that.gradeGroup)
            .append(batchPeriodEndDate, that.batchPeriodEndDate)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(licenseeAccountNumber)
            .append(licenseeName)
            .append(coveragePeriod)
            .append(licenseType)
            .append(reportedMediaType)
            .append(mediaTypeWeight)
            .append(reportedArticle)
            .append(reportedStandardNumber)
            .append(reportedAuthor)
            .append(reportedPublisher)
            .append(reportedPublicationDate)
            .append(reportedPageRange)
            .append(reportedVolNumberSeries)
            .append(reportedGrade)
            .append(gradeGroup)
            .append(batchPeriodEndDate)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("licenseeAccountNumber", licenseeAccountNumber)
            .append("licenseeName", licenseeName)
            .append("coveragePeriod", coveragePeriod)
            .append("licenseType", licenseType)
            .append("reportedMediaType", reportedMediaType)
            .append("mediaTypeWeight", mediaTypeWeight)
            .append("reportedArticle", reportedArticle)
            .append("reportedStandardNumber", reportedStandardNumber)
            .append("reportedAuthor", reportedAuthor)
            .append("reportedPublisher", reportedPublisher)
            .append("reportedPublicationDate", reportedPublicationDate)
            .append("reportedPageRange", reportedPageRange)
            .append("reportedVolNumberSeries", reportedVolNumberSeries)
            .append("reportedGrade", reportedGrade)
            .append("gradeGroup", gradeGroup)
            .append("batchPeriodEndDate", batchPeriodEndDate)
            .toString();
    }
}
