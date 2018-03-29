package com.copyright.rup.dist.foreign.integration.crm.api;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Represents request to CRM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/28/18
 *
 * @author Darya Baraukova
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrmRightsDistributionRequest {

    @JsonProperty(value = "cccEventId")
    private String cccEventId;

    @JsonProperty(value = "product")
    private String productFamily = "FAS";

    @JsonProperty(value = "rorAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rorAccountNumber;

    @JsonProperty(value = "payeeAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payeeAccountNumber;

    @JsonProperty(value = "royaltyAmount")
    private BigDecimal royaltyAmount;

    @JsonProperty(value = "currency")
    private String currency = "USD";

    @JsonProperty(value = "licenseCreateDate")
    @JsonFormat(pattern = "MM-dd-yyyy'T'hh:mm:ss a")
    private OffsetDateTime licenseCreateDate;

    @JsonProperty(value = "serviceFeeAmount")
    private BigDecimal serviceFeeAmount;

    @JsonProperty(value = "wrWrkInst")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long wrWrkInst;

    @JsonProperty(value = "publicationName")
    private String publicationName;

    @JsonProperty(value = "author")
    private String author;

    @JsonProperty(value = "chapterArticleTitle")
    private String chapterArticleTitle;

    @JsonProperty(value = "omOrderDetailNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long omOrderDetailNumber;

    @JsonProperty(value = "totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty(value = "periodEndDate")
    @JsonFormat(pattern = RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)
    private LocalDate periodEndDate;

    @JsonProperty(value = "publicationDate")
    @JsonFormat(pattern = RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)
    private LocalDate publicationDate;

    @JsonProperty(value = "numberOfCopies")
    private Integer numberOfCopies;

    @JsonProperty(value = "adjustmentFlag")
    private boolean adjustmentFlag;

    @JsonProperty(value = "passThroughFlag")
    private boolean passThroughFlag = true;

    @JsonProperty(value = "market")
    private String market;

    @JsonProperty(value = "marketPeriodFrom")
    private Integer marketPeriodFrom;

    @JsonProperty(value = "marketPeriodTo")
    private Integer marketPeriodTo;

    @JsonProperty(value = "licenseeAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long licenseeAccountNumber;

    @JsonProperty(value = "createUser")
    private String createUser = "FDA Distribution";

    @JsonProperty(value = "status")
    private String status = "D - Distributed";

    /**
     * Constructor for creating request to the CRM service.
     *
     * @param usage            {@link PaidUsage} instance
     * @param rroAccountNumber RRO account number
     */
    public CrmRightsDistributionRequest(PaidUsage usage, Long rroAccountNumber) {
        Objects.requireNonNull(usage);
        Objects.requireNonNull(rroAccountNumber);
        this.cccEventId = usage.getCccEventId();
        this.rorAccountNumber = usage.getRightsholder().getAccountNumber();
        this.payeeAccountNumber = usage.getPayee().getAccountNumber();
        this.royaltyAmount = usage.getNetAmount();
        this.licenseCreateDate =
            OffsetDateTime.ofInstant(usage.getCreateDate().toInstant(), ZoneId.systemDefault());
        this.serviceFeeAmount = usage.getServiceFeeAmount();
        this.wrWrkInst = usage.getWrWrkInst();
        this.publicationName = usage.getWorkTitle();
        this.author = usage.getAuthor();
        this.chapterArticleTitle = usage.getArticle();
        this.omOrderDetailNumber = usage.getDetailId();
        this.totalAmount = usage.getGrossAmount();
        this.periodEndDate = usage.getPeriodEndDate();
        this.publicationDate = usage.getPublicationDate();
        this.numberOfCopies = usage.getNumberOfCopies();
        this.market = usage.getMarket();
        this.marketPeriodFrom = usage.getMarketPeriodFrom();
        this.marketPeriodTo = usage.getMarketPeriodTo();
        this.licenseeAccountNumber = rroAccountNumber;
    }

    public String getCccEventId() {
        return cccEventId;
    }

    public void setCccEventId(String cccEventId) {
        this.cccEventId = cccEventId;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public Long getRorAccountNumber() {
        return rorAccountNumber;
    }

    public void setRorAccountNumber(Long rorAccountNumber) {
        this.rorAccountNumber = rorAccountNumber;
    }

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public BigDecimal getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(BigDecimal royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OffsetDateTime getLicenseCreateDate() {
        return licenseCreateDate;
    }

    public void setLicenseCreateDate(OffsetDateTime licenseCreateDate) {
        this.licenseCreateDate = licenseCreateDate;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterArticleTitle() {
        return chapterArticleTitle;
    }

    public void setChapterArticleTitle(String chapterArticleTitle) {
        this.chapterArticleTitle = chapterArticleTitle;
    }

    public Long getOmOrderDetailNumber() {
        return omOrderDetailNumber;
    }

    public void setOmOrderDetailNumber(Long omOrderDetailNumber) {
        this.omOrderDetailNumber = omOrderDetailNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public boolean isAdjustmentFlag() {
        return adjustmentFlag;
    }

    public void setAdjustmentFlag(boolean adjustmentFlag) {
        this.adjustmentFlag = adjustmentFlag;
    }

    public boolean isPassThroughFlag() {
        return passThroughFlag;
    }

    public void setPassThroughFlag(boolean passThroughFlag) {
        this.passThroughFlag = passThroughFlag;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Integer getMarketPeriodFrom() {
        return marketPeriodFrom;
    }

    public void setMarketPeriodFrom(Integer marketPeriodFrom) {
        this.marketPeriodFrom = marketPeriodFrom;
    }

    public Integer getMarketPeriodTo() {
        return marketPeriodTo;
    }

    public void setMarketPeriodTo(Integer marketPeriodTo) {
        this.marketPeriodTo = marketPeriodTo;
    }

    public Long getLicenseeAccountNumber() {
        return licenseeAccountNumber;
    }

    public void setLicenseeAccountNumber(Long licenseeAccountNumber) {
        this.licenseeAccountNumber = licenseeAccountNumber;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        CrmRightsDistributionRequest that = (CrmRightsDistributionRequest) obj;
        return new EqualsBuilder()
            .append(this.cccEventId, that.cccEventId)
            .append(this.productFamily, that.productFamily)
            .append(this.rorAccountNumber, that.rorAccountNumber)
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.royaltyAmount, that.royaltyAmount)
            .append(this.currency, that.currency)
            .append(this.licenseCreateDate, that.licenseCreateDate)
            .append(this.serviceFeeAmount, that.serviceFeeAmount)
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.publicationName, that.publicationName)
            .append(this.author, that.author)
            .append(this.chapterArticleTitle, that.chapterArticleTitle)
            .append(this.omOrderDetailNumber, that.omOrderDetailNumber)
            .append(this.totalAmount, that.totalAmount)
            .append(this.periodEndDate, that.periodEndDate)
            .append(this.publicationDate, that.publicationDate)
            .append(this.numberOfCopies, that.numberOfCopies)
            .append(this.passThroughFlag, that.passThroughFlag)
            .append(this.adjustmentFlag, that.adjustmentFlag)
            .append(this.market, that.market)
            .append(this.marketPeriodFrom, that.marketPeriodFrom)
            .append(this.marketPeriodTo, that.marketPeriodTo)
            .append(this.licenseeAccountNumber, that.licenseeAccountNumber)
            .append(this.createUser, that.createUser)
            .append(this.status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(cccEventId)
            .append(productFamily)
            .append(rorAccountNumber)
            .append(payeeAccountNumber)
            .append(royaltyAmount)
            .append(currency)
            .append(licenseCreateDate)
            .append(serviceFeeAmount)
            .append(wrWrkInst)
            .append(publicationName)
            .append(author)
            .append(chapterArticleTitle)
            .append(omOrderDetailNumber)
            .append(totalAmount)
            .append(periodEndDate)
            .append(publicationDate)
            .append(numberOfCopies)
            .append(passThroughFlag)
            .append(adjustmentFlag)
            .append(market)
            .append(marketPeriodFrom)
            .append(marketPeriodTo)
            .append(licenseeAccountNumber)
            .append(createUser)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("cccEventId", cccEventId)
            .append("productFamily", productFamily)
            .append("rorAccountNumber", rorAccountNumber)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("royaltyAmount", royaltyAmount)
            .append("currency", currency)
            .append("licenseCreateDate", licenseCreateDate)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("wrWrkInst", wrWrkInst)
            .append("publicationName", publicationName)
            .append("author", author)
            .append("chapterArticleTitle", chapterArticleTitle)
            .append("omOrderDetailNumber", omOrderDetailNumber)
            .append("totalAmount", totalAmount)
            .append("periodEndDate", periodEndDate)
            .append("publicationDate", publicationDate)
            .append("numberOfCopies", numberOfCopies)
            .append("passThroughFlag", passThroughFlag)
            .append("adjustmentFlag", adjustmentFlag)
            .append("market", market)
            .append("marketPeriodFrom", marketPeriodFrom)
            .append("marketPeriodTo", marketPeriodTo)
            .append("licenseeAccountNumber", licenseeAccountNumber)
            .append("createUser", createUser)
            .append("status", status)
            .toString();
    }
}
