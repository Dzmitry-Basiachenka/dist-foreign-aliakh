package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    private String originId;
    private LocalDate periodEndDate;
    private LocalDate usageDate;
    private String standardNumber;
    private Long wrWrkInst;
    private String reportedTitle;
    private String article;
    private String pubType;
    private String language;
    private String pubFormat;
    private String typeOfUse;
    private Integer quantity;
    private String surveyRespondent;
    private Long companyId;
    private String countryName;
    private String ipAddress;
    private LocalDate surveyStartDate;
    private LocalDate surveyEndDate;
    private String ineligibleReason;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getPubType() {
        return pubType;
    }

    public void setPubType(String pubType) {
        this.pubType = pubType;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getIneligibleReason() {
        return ineligibleReason;
    }

    public void setIneligibleReason(String ineligibleReason) {
        this.ineligibleReason = ineligibleReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        UdmUsage udmUsage = (UdmUsage) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(originId, udmUsage.originId)
            .append(periodEndDate, udmUsage.periodEndDate)
            .append(usageDate, udmUsage.usageDate)
            .append(standardNumber, udmUsage.standardNumber)
            .append(wrWrkInst, udmUsage.wrWrkInst)
            .append(reportedTitle, udmUsage.reportedTitle)
            .append(article, udmUsage.article)
            .append(pubType, udmUsage.pubType)
            .append(language, udmUsage.language)
            .append(pubFormat, udmUsage.pubFormat)
            .append(typeOfUse, udmUsage.typeOfUse)
            .append(quantity, udmUsage.quantity)
            .append(surveyRespondent, udmUsage.surveyRespondent)
            .append(companyId, udmUsage.companyId)
            .append(countryName, udmUsage.countryName)
            .append(ipAddress, udmUsage.ipAddress)
            .append(surveyStartDate, udmUsage.surveyStartDate)
            .append(surveyEndDate, udmUsage.surveyEndDate)
            .append(ineligibleReason, udmUsage.ineligibleReason)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(originId)
            .append(periodEndDate)
            .append(usageDate)
            .append(standardNumber)
            .append(wrWrkInst)
            .append(reportedTitle)
            .append(article)
            .append(pubType)
            .append(language)
            .append(pubFormat)
            .append(typeOfUse)
            .append(quantity)
            .append(surveyRespondent)
            .append(companyId)
            .append(countryName)
            .append(ipAddress)
            .append(surveyStartDate)
            .append(surveyEndDate)
            .append(ineligibleReason)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("originId", originId)
            .append("periodEndDate", periodEndDate)
            .append("usageDate", usageDate)
            .append("standardNumber", standardNumber)
            .append("wrWrkInst", wrWrkInst)
            .append("reportedTitle", reportedTitle)
            .append("article", article)
            .append("pubType", pubType)
            .append("language", language)
            .append("pubFormat", pubFormat)
            .append("typeOfUse", typeOfUse)
            .append("quantity", quantity)
            .append("surveyRespondent", surveyRespondent)
            .append("companyId", companyId)
            .append("countryName", countryName)
            .append("ipAddress", ipAddress)
            .append("surveyStartDate", surveyStartDate)
            .append("surveyEndDate", surveyEndDate)
            .append("ineligibleReason", ineligibleReason)
            .toString();
    }
}
