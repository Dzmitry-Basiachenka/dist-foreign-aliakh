package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents UDM value DTO.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueDto extends StoredEntity<String> {

    private Integer valuePeriod;
    private UdmValueStatusEnum status;
    private String assignee;
    private Long rhAccountNumber;
    private String rhName;
    private String wrWrkInst;
    private String systemTitle;
    private String systemStandardNumber;
    private String standardNumberType;
    private Integer lastValuePeriod;
    private String lastPubType;
    private String publicationType;
    private BigDecimal lastPriceInUsd;
    private Boolean lastPriceFlag;
    private String lastPriceSource;
    private String priceSource;
    private String lastPriceComment;
    private BigDecimal price;
    private String currency;
    private String priceType;
    private String priceAccessType;
    private Integer priceYear;
    private String priceComment;
    private BigDecimal priceInUsd;
    private Boolean priceFlag;
    private BigDecimal currencyExchangeRate;
    private LocalDate currencyExchangeRateDate;
    private BigDecimal lastContent;
    private Boolean lastContentFlag;
    private String lastContentSource;
    private String contentSource;
    private String lastContentComment;
    private BigDecimal content;
    private String contentComment;
    private Boolean contentFlag;
    private BigDecimal contentUnitPrice;
    private String comment;

    public Integer getValuePeriod() {
        return valuePeriod;
    }

    public void setValuePeriod(Integer valuePeriod) {
        this.valuePeriod = valuePeriod;
    }

    public UdmValueStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UdmValueStatusEnum status) {
        this.status = status;
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

    public String getSystemStandardNumber() {
        return systemStandardNumber;
    }

    public void setSystemStandardNumber(String systemStandardNumber) {
        this.systemStandardNumber = systemStandardNumber;
    }

    public String getStandardNumberType() {
        return standardNumberType;
    }

    public void setStandardNumberType(String standardNumberType) {
        this.standardNumberType = standardNumberType;
    }

    public Integer getLastValuePeriod() {
        return lastValuePeriod;
    }

    public void setLastValuePeriod(Integer lastValuePeriod) {
        this.lastValuePeriod = lastValuePeriod;
    }

    public String getLastPubType() {
        return lastPubType;
    }

    public void setLastPubType(String lastPubType) {
        this.lastPubType = lastPubType;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public BigDecimal getLastPriceInUsd() {
        return lastPriceInUsd;
    }

    public void setLastPriceInUsd(BigDecimal lastPriceInUsd) {
        this.lastPriceInUsd = lastPriceInUsd;
    }

    public Boolean isLastPriceFlag() {
        return lastPriceFlag;
    }

    public void setLastPriceFlag(Boolean lastPriceFlag) {
        this.lastPriceFlag = lastPriceFlag;
    }

    public String getLastPriceSource() {
        return lastPriceSource;
    }

    public void setLastPriceSource(String lastPriceSource) {
        this.lastPriceSource = lastPriceSource;
    }

    public String getPriceSource() {
        return priceSource;
    }

    public void setPriceSource(String priceSource) {
        this.priceSource = priceSource;
    }

    public String getLastPriceComment() {
        return lastPriceComment;
    }

    public void setLastPriceComment(String lastPriceComment) {
        this.lastPriceComment = lastPriceComment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPriceAccessType() {
        return priceAccessType;
    }

    public void setPriceAccessType(String priceAccessType) {
        this.priceAccessType = priceAccessType;
    }

    public Integer getPriceYear() {
        return priceYear;
    }

    public void setPriceYear(Integer priceYear) {
        this.priceYear = priceYear;
    }

    public String getPriceComment() {
        return priceComment;
    }

    public void setPriceComment(String priceComment) {
        this.priceComment = priceComment;
    }

    public BigDecimal getPriceInUsd() {
        return priceInUsd;
    }

    public void setPriceInUsd(BigDecimal priceInUsd) {
        this.priceInUsd = priceInUsd;
    }

    public Boolean isPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(Boolean priceFlag) {
        this.priceFlag = priceFlag;
    }

    public BigDecimal getCurrencyExchangeRate() {
        return currencyExchangeRate;
    }

    public void setCurrencyExchangeRate(BigDecimal currencyExchangeRate) {
        this.currencyExchangeRate = currencyExchangeRate;
    }

    public LocalDate getCurrencyExchangeRateDate() {
        return currencyExchangeRateDate;
    }

    public void setCurrencyExchangeRateDate(LocalDate currencyExchangeRateDate) {
        this.currencyExchangeRateDate = currencyExchangeRateDate;
    }

    public BigDecimal getLastContent() {
        return lastContent;
    }

    public void setLastContent(BigDecimal lastContent) {
        this.lastContent = lastContent;
    }

    public Boolean isLastContentFlag() {
        return lastContentFlag;
    }

    public void setLastContentFlag(Boolean lastContentFlag) {
        this.lastContentFlag = lastContentFlag;
    }

    public String getLastContentSource() {
        return lastContentSource;
    }

    public void setLastContentSource(String lastContentSource) {
        this.lastContentSource = lastContentSource;
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public String getLastContentComment() {
        return lastContentComment;
    }

    public void setLastContentComment(String lastContentComment) {
        this.lastContentComment = lastContentComment;
    }

    public BigDecimal getContent() {
        return content;
    }

    public void setContent(BigDecimal content) {
        this.content = content;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public Boolean isContentFlag() {
        return contentFlag;
    }

    public void setContentFlag(Boolean contentFlag) {
        this.contentFlag = contentFlag;
    }

    public BigDecimal getContentUnitPrice() {
        return contentUnitPrice;
    }

    public void setContentUnitPrice(BigDecimal contentUnitPrice) {
        this.contentUnitPrice = contentUnitPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmValueDto that = (UdmValueDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(valuePeriod, that.valuePeriod)
            .append(status, that.status)
            .append(assignee, that.assignee)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(systemStandardNumber, that.systemStandardNumber)
            .append(standardNumberType, that.standardNumberType)
            .append(lastValuePeriod, that.lastValuePeriod)
            .append(lastPubType, that.lastPubType)
            .append(publicationType, that.publicationType)
            .append(lastPriceInUsd, that.lastPriceInUsd)
            .append(lastPriceFlag, that.lastPriceFlag)
            .append(lastPriceSource, that.lastPriceSource)
            .append(priceSource, that.priceSource)
            .append(contentSource, that.contentSource)
            .append(lastPriceComment, that.lastPriceComment)
            .append(price, that.price)
            .append(currency, that.currency)
            .append(priceType, that.priceType)
            .append(priceAccessType, that.priceAccessType)
            .append(priceYear, that.priceYear)
            .append(priceComment, that.priceComment)
            .append(priceInUsd, that.priceInUsd)
            .append(priceFlag, that.priceFlag)
            .append(currencyExchangeRate, that.currencyExchangeRate)
            .append(currencyExchangeRateDate, that.currencyExchangeRateDate)
            .append(lastContent, that.lastContent)
            .append(lastContentFlag, that.lastContentFlag)
            .append(lastContentSource, that.lastContentSource)
            .append(lastContentComment, that.lastContentComment)
            .append(content, that.content)
            .append(contentComment, that.contentComment)
            .append(contentFlag, that.contentFlag)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(comment, that.comment)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(valuePeriod)
            .append(status)
            .append(assignee)
            .append(rhAccountNumber)
            .append(rhName)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(systemStandardNumber)
            .append(standardNumberType)
            .append(lastValuePeriod)
            .append(lastPubType)
            .append(publicationType)
            .append(lastPriceInUsd)
            .append(lastPriceFlag)
            .append(lastPriceSource)
            .append(priceSource)
            .append(lastPriceComment)
            .append(price)
            .append(currency)
            .append(priceType)
            .append(priceAccessType)
            .append(priceYear)
            .append(priceComment)
            .append(priceInUsd)
            .append(priceFlag)
            .append(currencyExchangeRate)
            .append(currencyExchangeRateDate)
            .append(lastContent)
            .append(lastContentFlag)
            .append(lastContentSource)
            .append(contentSource)
            .append(lastContentComment)
            .append(content)
            .append(contentComment)
            .append(contentFlag)
            .append(contentUnitPrice)
            .append(comment)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("valuePeriod", valuePeriod)
            .append("status", status)
            .append("assignee", assignee)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("systemStandardNumber", systemStandardNumber)
            .append("standardNumberType", standardNumberType)
            .append("lastValuePeriod", lastValuePeriod)
            .append("lastPubType", lastPubType)
            .append("publicationType", publicationType)
            .append("lastPriceInUsd", lastPriceInUsd)
            .append("lastPriceFlag", lastPriceFlag)
            .append("lastPriceSource", lastPriceSource)
            .append("priceSource", priceSource)
            .append("lastPriceComment", lastPriceComment)
            .append("price", price)
            .append("currency", currency)
            .append("priceType", priceType)
            .append("priceAccessType", priceAccessType)
            .append("priceYear", priceYear)
            .append("priceComment", priceComment)
            .append("priceInUsd", priceInUsd)
            .append("priceFlag", priceFlag)
            .append("currencyExchangeRate", currencyExchangeRate)
            .append("currencyExchangeRateDate", currencyExchangeRateDate)
            .append("lastContent", lastContent)
            .append("lastContentFlag", lastContentFlag)
            .append("lastContentSource", lastContentSource)
            .append("contentSource", contentSource)
            .append("lastContentComment", lastContentComment)
            .append("content", content)
            .append("contentComment", contentComment)
            .append("contentFlag", contentFlag)
            .append("contentUnitPrice", contentUnitPrice)
            .append("comment", comment)
            .toString();
    }
}
