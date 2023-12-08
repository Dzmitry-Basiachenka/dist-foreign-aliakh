package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/21
 *
 * @author Anton Azarenka
 */
public class UdmUsage extends StoredEntity<String> {

    private static final long serialVersionUID = 3608739840741410932L;

    private String originalDetailId;
    private String batchId;
    private UsageStatusEnum status;
    private String assignee;
    private Integer period;
    private LocalDate periodEndDate;
    private LocalDate usageDate;
    private Rightsholder rightsholder = new Rightsholder();
    private Long wrWrkInst;
    private String standardNumber;
    private String reportedStandardNumber;
    private String reportedTitle;
    private String systemTitle;
    private String article;
    private String reportedPubType;
    private String language;
    private String pubFormat;
    private String typeOfUse;
    private String reportedTypeOfUse;
    private Long quantity;
    private String surveyRespondent;
    private String surveyCountry;
    private String ipAddress;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private String actionReasonId;
    private String ineligibleReasonId;
    private Long companyId;
    private String companyName;
    private Integer detailLicenseeClassId;
    private String detailLicenseeClassName;
    private Integer annualMultiplier;
    private BigDecimal statisticalMultiplier;
    private BigDecimal annualizedCopies;
    private String comment;
    private String valueId;
    private boolean baselineFlag;

    public String getOriginalDetailId() {
        return originalDetailId;
    }

    public void setOriginalDetailId(String originalDetailId) {
        this.originalDetailId = originalDetailId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public UsageStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UsageStatusEnum status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    public String getReportedStandardNumber() {
        return reportedStandardNumber;
    }

    public void setReportedStandardNumber(String reportedStandardNumber) {
        this.reportedStandardNumber = reportedStandardNumber;
    }

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getReportedTitle() {
        return reportedTitle;
    }

    public void setReportedTitle(String reportedTitle) {
        this.reportedTitle = reportedTitle;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getReportedPubType() {
        return reportedPubType;
    }

    public void setReportedPubType(String reportedPubType) {
        this.reportedPubType = reportedPubType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPubFormat() {
        return pubFormat;
    }

    public void setPubFormat(String pubFormat) {
        this.pubFormat = pubFormat;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public String getReportedTypeOfUse() {
        return reportedTypeOfUse;
    }

    public void setReportedTypeOfUse(String reportedTypeOfUse) {
        this.reportedTypeOfUse = reportedTypeOfUse;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSurveyRespondent() {
        return surveyRespondent;
    }

    public void setSurveyRespondent(String surveyRespondent) {
        this.surveyRespondent = surveyRespondent;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getSurveyCountry() {
        return surveyCountry;
    }

    public void setSurveyCountry(String surveyCountry) {
        this.surveyCountry = surveyCountry;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getSurveyStartDate() {
        return surveyStartDate;
    }

    public void setSurveyStartDate(LocalDate surveyStartDate) {
        this.surveyStartDate = surveyStartDate;
    }

    public LocalDate getSurveyEndDate() {
        return surveyEndDate;
    }

    public void setSurveyEndDate(LocalDate surveyEndDate) {
        this.surveyEndDate = surveyEndDate;
    }

    public String getActionReasonId() {
        return actionReasonId;
    }

    public void setActionReasonId(String actionReasonId) {
        this.actionReasonId = actionReasonId;
    }

    public String getIneligibleReasonId() {
        return ineligibleReasonId;
    }

    public void setIneligibleReasonId(String ineligibleReasonId) {
        this.ineligibleReasonId = ineligibleReasonId;
    }

    public Integer getDetailLicenseeClassId() {
        return detailLicenseeClassId;
    }

    public void setDetailLicenseeClassId(Integer detailLicenseeClassId) {
        this.detailLicenseeClassId = detailLicenseeClassId;
    }

    public String getDetailLicenseeClassName() {
        return detailLicenseeClassName;
    }

    public void setDetailLicenseeClassName(String detailLicenseeClassName) {
        this.detailLicenseeClassName = detailLicenseeClassName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getAnnualMultiplier() {
        return annualMultiplier;
    }

    public void setAnnualMultiplier(Integer annualMultiplier) {
        this.annualMultiplier = annualMultiplier;
    }

    public BigDecimal getStatisticalMultiplier() {
        return statisticalMultiplier;
    }

    public void setStatisticalMultiplier(BigDecimal statisticalMultiplier) {
        this.statisticalMultiplier = statisticalMultiplier;
    }

    public BigDecimal getAnnualizedCopies() {
        return annualizedCopies;
    }

    public void setAnnualizedCopies(BigDecimal annualizedCopies) {
        this.annualizedCopies = annualizedCopies;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public boolean isBaselineFlag() {
        return baselineFlag;
    }

    public void setBaselineFlag(boolean baselineFlag) {
        this.baselineFlag = baselineFlag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmUsage that = (UdmUsage) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(originalDetailId, that.originalDetailId)
            .append(batchId, that.batchId)
            .append(status, that.status)
            .append(assignee, that.assignee)
            .append(period, that.period)
            .append(periodEndDate, that.periodEndDate)
            .append(usageDate, that.usageDate)
            .append(standardNumber, that.standardNumber)
            .append(reportedStandardNumber, that.reportedStandardNumber)
            .append(rightsholder, that.rightsholder)
            .append(wrWrkInst, that.wrWrkInst)
            .append(reportedTitle, that.reportedTitle)
            .append(systemTitle, that.systemTitle)
            .append(article, that.article)
            .append(reportedPubType, that.reportedPubType)
            .append(language, that.language)
            .append(pubFormat, that.pubFormat)
            .append(typeOfUse, that.typeOfUse)
            .append(reportedTypeOfUse, that.reportedTypeOfUse)
            .append(quantity, that.quantity)
            .append(surveyRespondent, that.surveyRespondent)
            .append(companyId, that.companyId)
            .append(surveyCountry, that.surveyCountry)
            .append(ipAddress, that.ipAddress)
            .append(surveyStartDate, that.surveyStartDate)
            .append(surveyEndDate, that.surveyEndDate)
            .append(actionReasonId, that.actionReasonId)
            .append(ineligibleReasonId, that.ineligibleReasonId)
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(detailLicenseeClassName, that.detailLicenseeClassName)
            .append(companyName, that.companyName)
            .append(annualMultiplier, that.annualMultiplier)
            .append(statisticalMultiplier, that.statisticalMultiplier)
            .append(annualizedCopies, that.annualizedCopies)
            .append(comment, that.comment)
            .append(valueId, that.valueId)
            .append(baselineFlag, that.baselineFlag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(originalDetailId)
            .append(batchId)
            .append(status)
            .append(assignee)
            .append(period)
            .append(periodEndDate)
            .append(usageDate)
            .append(standardNumber)
            .append(reportedStandardNumber)
            .append(rightsholder)
            .append(wrWrkInst)
            .append(reportedTitle)
            .append(systemTitle)
            .append(article)
            .append(reportedPubType)
            .append(language)
            .append(pubFormat)
            .append(typeOfUse)
            .append(reportedTypeOfUse)
            .append(quantity)
            .append(surveyRespondent)
            .append(companyId)
            .append(surveyCountry)
            .append(ipAddress)
            .append(surveyStartDate)
            .append(surveyEndDate)
            .append(actionReasonId)
            .append(ineligibleReasonId)
            .append(detailLicenseeClassId)
            .append(detailLicenseeClassName)
            .append(companyName)
            .append(annualMultiplier)
            .append(statisticalMultiplier)
            .append(annualizedCopies)
            .append(comment)
            .append(valueId)
            .append(baselineFlag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("originalDetailId", originalDetailId)
            .append("batchId", batchId)
            .append("status", status)
            .append("assignee", assignee)
            .append("period", period)
            .append("periodEndDate", periodEndDate)
            .append("usageDate", usageDate)
            .append("standardNumber", standardNumber)
            .append("standardNumber", standardNumber)
            .append("reportedStandardNumber", reportedStandardNumber)
            .append("rightsholder", rightsholder)
            .append("wrWrkInst", wrWrkInst)
            .append("reportedTitle", reportedTitle)
            .append("systemTitle", systemTitle)
            .append("article", article)
            .append("reportedPubType", reportedPubType)
            .append("language", language)
            .append("pubFormat", pubFormat)
            .append("typeOfUse", typeOfUse)
            .append("reportedTypeOfUse", reportedTypeOfUse)
            .append("quantity", quantity)
            .append("surveyRespondent", surveyRespondent)
            .append("companyId", companyId)
            .append("surveyCountry", surveyCountry)
            .append("ipAddress", ipAddress)
            .append("surveyStartDate", surveyStartDate)
            .append("surveyEndDate", surveyEndDate)
            .append("actionReasonId", actionReasonId)
            .append("ineligibleReasonId", ineligibleReasonId)
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("detailLicenseeClassName", detailLicenseeClassName)
            .append("companyName", companyName)
            .append("annualMultiplier", annualMultiplier)
            .append("statisticalMultiplier", statisticalMultiplier)
            .append("annualizedCopies", annualizedCopies)
            .append("comment", comment)
            .append("valueId", valueId)
            .append("baselineFlag", baselineFlag)
            .toString();
    }
}
