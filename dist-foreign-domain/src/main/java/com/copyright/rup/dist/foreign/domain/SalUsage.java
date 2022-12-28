package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents SAL specific usage details.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsage {

    private Long licenseeAccountNumber;
    private String licenseeName;
    private SalDetailTypeEnum detailType;
    private String grade;
    private SalGradeGroupEnum gradeGroup;
    private String assessmentName;
    private String assessmentType;
    private String reportedWorkPortionId;
    private String reportedArticle;
    private String reportedStandardNumber;
    private String reportedAuthor;
    private String reportedPublisher;
    private String reportedPublicationDate;
    private String reportedPageRange;
    private String reportedVolNumberSeries;
    private String reportedMediaType;
    private BigDecimal mediaTypeWeight;
    private String coverageYear;
    private LocalDate scoredAssessmentDate;
    private String questionIdentifier;
    private String states;
    private Integer numberOfViews;
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

    public SalDetailTypeEnum getDetailType() {
        return detailType;
    }

    public void setDetailType(SalDetailTypeEnum detailType) {
        this.detailType = detailType;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public SalGradeGroupEnum getGradeGroup() {
        return gradeGroup;
    }

    public void setGradeGroup(SalGradeGroupEnum gradeGroup) {
        this.gradeGroup = gradeGroup;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getReportedWorkPortionId() {
        return reportedWorkPortionId;
    }

    public void setReportedWorkPortionId(String reportedWorkPortionId) {
        this.reportedWorkPortionId = reportedWorkPortionId;
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

    public String getCoverageYear() {
        return coverageYear;
    }

    public void setCoverageYear(String coverageYear) {
        this.coverageYear = coverageYear;
    }

    public LocalDate getScoredAssessmentDate() {
        return scoredAssessmentDate;
    }

    public void setScoredAssessmentDate(LocalDate scoredAssessmentDate) {
        this.scoredAssessmentDate = scoredAssessmentDate;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public Integer getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(Integer numberOfViews) {
        this.numberOfViews = numberOfViews;
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
        SalUsage that = (SalUsage) obj;
        return new EqualsBuilder()
            .append(this.licenseeAccountNumber, that.licenseeAccountNumber)
            .append(this.licenseeName, that.licenseeName)
            .append(this.detailType, that.detailType)
            .append(this.grade, that.grade)
            .append(this.gradeGroup, that.gradeGroup)
            .append(this.assessmentName, that.assessmentName)
            .append(this.assessmentType, that.assessmentType)
            .append(this.reportedWorkPortionId, that.reportedWorkPortionId)
            .append(this.reportedArticle, that.reportedArticle)
            .append(this.reportedStandardNumber, that.reportedStandardNumber)
            .append(this.reportedAuthor, that.reportedAuthor)
            .append(this.reportedPublisher, that.reportedPublisher)
            .append(this.reportedPublicationDate, that.reportedPublicationDate)
            .append(this.reportedPageRange, that.reportedPageRange)
            .append(this.reportedVolNumberSeries, that.reportedVolNumberSeries)
            .append(this.reportedMediaType, that.reportedMediaType)
            .append(this.mediaTypeWeight, that.mediaTypeWeight)
            .append(this.coverageYear, that.coverageYear)
            .append(this.scoredAssessmentDate, that.scoredAssessmentDate)
            .append(this.questionIdentifier, that.questionIdentifier)
            .append(this.states, that.states)
            .append(this.numberOfViews, that.numberOfViews)
            .append(this.batchPeriodEndDate, that.batchPeriodEndDate)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(licenseeAccountNumber)
            .append(licenseeName)
            .append(detailType)
            .append(grade)
            .append(gradeGroup)
            .append(assessmentName)
            .append(assessmentType)
            .append(reportedWorkPortionId)
            .append(reportedArticle)
            .append(reportedStandardNumber)
            .append(reportedAuthor)
            .append(reportedPublisher)
            .append(reportedPublicationDate)
            .append(reportedPageRange)
            .append(reportedVolNumberSeries)
            .append(reportedMediaType)
            .append(mediaTypeWeight)
            .append(coverageYear)
            .append(scoredAssessmentDate)
            .append(questionIdentifier)
            .append(states)
            .append(numberOfViews)
            .append(batchPeriodEndDate)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("licenseeAccountNumber", licenseeAccountNumber)
            .append("licenseeName", licenseeName)
            .append("detailType", detailType)
            .append("grade", grade)
            .append("gradeGroup", gradeGroup)
            .append("assessmentName", assessmentName)
            .append("assessmentType", assessmentType)
            .append("reportedWorkPortionId", reportedWorkPortionId)
            .append("reportedArticle", reportedArticle)
            .append("reportedStandardNumber", reportedStandardNumber)
            .append("reportedAuthor", reportedAuthor)
            .append("reportedPublisher", reportedPublisher)
            .append("reportedPublicationDate", reportedPublicationDate)
            .append("reportedPageRange", reportedPageRange)
            .append("reportedVolNumberSeries", reportedVolNumberSeries)
            .append("reportedMediaType", reportedMediaType)
            .append("mediaTypeWeight", mediaTypeWeight)
            .append("coverageYear", coverageYear)
            .append("scoredAssessmentDate", scoredAssessmentDate)
            .append("questionIdentifier", questionIdentifier)
            .append("states", states)
            .append("numberOfViews", numberOfViews)
            .append("batchPeriodEndDate", batchPeriodEndDate)
            .toString();
    }
}
