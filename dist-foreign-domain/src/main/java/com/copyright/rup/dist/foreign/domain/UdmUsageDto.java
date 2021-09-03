package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents UDM usage dto.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/2021
 *
 * @author Ihar Suvorau
 */
public class UdmUsageDto extends StoredEntity<String> {

    private Integer period;
    private LocalDate periodEndDate;
    private UdmUsageOriginEnum usageOrigin;
    private String originalDetailId;
    private UsageStatusEnum status;
    private String assignee;
    private Long rhAccountNumber;
    private String rhName;
    private Long wrWrkInst;
    private String reportedTitle;
    private String systemTitle;
    private String reportedStandardNumber;
    private String standardNumber;
    private String reportedPubType;
    private String pubFormat;
    private String article;
    private String language;
    private DetailLicenseeClass detailLicenseeClass;
    private Long companyId;
    private String companyName;
    private String surveyRespondent;
    private String ipAddress;
    private String surveyCountry;
    private UdmChannelEnum channel;
    private LocalDate usageDate;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private Integer annualMultiplier;
    private BigDecimal statisticalMultiplier;
    private String typeOfUse;
    private String reportedTypeOfUse;
    private Long quantity;
    private BigDecimal annualizedCopies;
    private UdmActionReason actionReason;
    private UdmIneligibleReason ineligibleReason;
    private String comment;
    private String researchUrl;
    private boolean baselineFlag;

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

    public UdmUsageOriginEnum getUsageOrigin() {
        return usageOrigin;
    }

    public void setUsageOrigin(UdmUsageOriginEnum usageOrigin) {
        this.usageOrigin = usageOrigin;
    }

    public UdmChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(UdmChannelEnum channel) {
        this.channel = channel;
    }

    public UsageStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UsageStatusEnum status) {
        this.status = status;
    }

    public String getOriginalDetailId() {
        return originalDetailId;
    }

    public void setOriginalDetailId(String originalDetailId) {
        this.originalDetailId = originalDetailId;
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

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
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

    public UdmActionReason getActionReason() {
        return actionReason;
    }

    public void setActionReason(UdmActionReason actionReason) {
        this.actionReason = actionReason;
    }

    public UdmIneligibleReason getIneligibleReason() {
        return ineligibleReason;
    }

    public void setIneligibleReason(UdmIneligibleReason ineligibleReason) {
        this.ineligibleReason = ineligibleReason;
    }

    public DetailLicenseeClass getDetailLicenseeClass() {
        return detailLicenseeClass;
    }

    public void setDetailLicenseeClass(DetailLicenseeClass detailLicenseeClass) {
        this.detailLicenseeClass = detailLicenseeClass;
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

    public String getResearchUrl() {
        return researchUrl;
    }

    public void setResearchUrl(String researchUrl) {
        this.researchUrl = researchUrl;
    }

    public boolean isBaselineFlag() {
        return baselineFlag;
    }

    public void setBaselineFlag(boolean baselineFlag) {
        this.baselineFlag = baselineFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmUsageDto udmUsage = (UdmUsageDto) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(period, udmUsage.period)
            .append(periodEndDate, udmUsage.periodEndDate)
            .append(usageOrigin, udmUsage.usageOrigin)
            .append(channel, udmUsage.channel)
            .append(status, udmUsage.status)
            .append(originalDetailId, udmUsage.originalDetailId)
            .append(usageDate, udmUsage.usageDate)
            .append(standardNumber, udmUsage.standardNumber)
            .append(reportedStandardNumber, udmUsage.reportedStandardNumber)
            .append(assignee, udmUsage.assignee)
            .append(rhAccountNumber, udmUsage.rhAccountNumber)
            .append(rhName, udmUsage.rhName)
            .append(wrWrkInst, udmUsage.wrWrkInst)
            .append(reportedTitle, udmUsage.reportedTitle)
            .append(systemTitle, udmUsage.systemTitle)
            .append(article, udmUsage.article)
            .append(reportedPubType, udmUsage.reportedPubType)
            .append(language, udmUsage.language)
            .append(pubFormat, udmUsage.pubFormat)
            .append(typeOfUse, udmUsage.typeOfUse)
            .append(reportedTypeOfUse, udmUsage.reportedTypeOfUse)
            .append(quantity, udmUsage.quantity)
            .append(surveyRespondent, udmUsage.surveyRespondent)
            .append(companyId, udmUsage.companyId)
            .append(surveyCountry, udmUsage.surveyCountry)
            .append(ipAddress, udmUsage.ipAddress)
            .append(surveyStartDate, udmUsage.surveyStartDate)
            .append(surveyEndDate, udmUsage.surveyEndDate)
            .append(actionReason, udmUsage.actionReason)
            .append(ineligibleReason, udmUsage.ineligibleReason)
            .append(detailLicenseeClass, udmUsage.detailLicenseeClass)
            .append(companyName, udmUsage.companyName)
            .append(annualMultiplier, udmUsage.annualMultiplier)
            .append(statisticalMultiplier, udmUsage.statisticalMultiplier)
            .append(annualizedCopies, udmUsage.annualizedCopies)
            .append(comment, udmUsage.comment)
            .append(researchUrl, udmUsage.researchUrl)
            .append(baselineFlag, udmUsage.baselineFlag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(periodEndDate)
            .append(usageOrigin)
            .append(channel)
            .append(status)
            .append(originalDetailId)
            .append(usageDate)
            .append(standardNumber)
            .append(reportedStandardNumber)
            .append(assignee)
            .append(rhAccountNumber)
            .append(rhName)
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
            .append(actionReason)
            .append(ineligibleReason)
            .append(detailLicenseeClass)
            .append(companyName)
            .append(annualMultiplier)
            .append(statisticalMultiplier)
            .append(annualizedCopies)
            .append(comment)
            .append(researchUrl)
            .append(baselineFlag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("periodEndDate", periodEndDate)
            .append("usageOrigin", usageOrigin)
            .append("channel", channel)
            .append("status", status)
            .append("originalDetailId", originalDetailId)
            .append("usageDate", usageDate)
            .append("standardNumber", standardNumber)
            .append("standardNumber", standardNumber)
            .append("reportedStandardNumber", reportedStandardNumber)
            .append("assignee", assignee)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
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
            .append("actionReason", actionReason)
            .append("ineligibleReason", ineligibleReason)
            .append("detailLicenseeClass", detailLicenseeClass)
            .append("companyName", companyName)
            .append("annualMultiplier", annualMultiplier)
            .append("statisticalMultiplier", statisticalMultiplier)
            .append("annualizedCopies", annualizedCopies)
            .append("comment", comment)
            .append("researchUrl", researchUrl)
            .append("baselineFlag", baselineFlag)
            .toString();
    }
}
