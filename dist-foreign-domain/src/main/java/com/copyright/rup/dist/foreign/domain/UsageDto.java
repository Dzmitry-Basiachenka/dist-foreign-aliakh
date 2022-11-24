package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Represents usage with usage batch.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/18/17
 *
 * @author Aliaksei Pchelnikau
 */
public class UsageDto extends StoredEntity<String> {

    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private String batchName;
    private Integer fiscalYear;
    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private Long wrWrkInst;
    private String workTitle;
    private String systemTitle;
    private String article;
    private Long rhAccountNumber;
    private String rhName;
    private String standardNumber;
    private String standardNumberType;
    private String publisher;
    private LocalDate publicationDate;
    private Integer numberOfCopies;
    private String market;
    private Integer marketPeriodFrom;
    private Integer marketPeriodTo;
    private String author;
    private Long payeeAccountNumber;
    private String payeeName;
    private BigDecimal grossAmount = DEFAULT_AMOUNT;
    private BigDecimal reportedValue = DEFAULT_AMOUNT;
    private BigDecimal batchGrossAmount;
    private BigDecimal netAmount = DEFAULT_AMOUNT;
    private BigDecimal serviceFee = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmount = DEFAULT_AMOUNT;
    private UsageStatusEnum status;
    private String productFamily;
    private String scenarioName;
    private String checkNumber;
    private OffsetDateTime checkDate;
    private String cccEventId;
    private String distributionName;
    private OffsetDateTime distributionDate;
    private LocalDate periodEndDate;
    private String comment;
    private AaclUsage aaclUsage;
    private SalUsage salUsage;
    private AclciUsage aclciUsage;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public BigDecimal getReportedValue() {
        return reportedValue;
    }

    public void setReportedValue(BigDecimal reportedValue) {
        this.reportedValue = reportedValue;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getBatchGrossAmount() {
        return batchGrossAmount;
    }

    public void setBatchGrossAmount(BigDecimal batchGrossAmount) {
        this.batchGrossAmount = batchGrossAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public UsageStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UsageStatusEnum status) {
        this.status = status;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public OffsetDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(OffsetDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public String getCccEventId() {
        return cccEventId;
    }

    public void setCccEventId(String cccEventId) {
        this.cccEventId = cccEventId;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public OffsetDateTime getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(OffsetDateTime distributionDate) {
        this.distributionDate = distributionDate;
    }

    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AaclUsage getAaclUsage() {
        return aaclUsage;
    }

    public void setAaclUsage(AaclUsage aaclUsage) {
        this.aaclUsage = aaclUsage;
    }

    public SalUsage getSalUsage() {
        return salUsage;
    }

    public void setSalUsage(SalUsage salUsage) {
        this.salUsage = salUsage;
    }

    public AclciUsage getAclciUsage() {
        return aclciUsage;
    }

    public void setAclciUsage(AclciUsage aclciUsage) {
        this.aclciUsage = aclciUsage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageDto that = (UsageDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.batchName, that.batchName)
            .append(this.fiscalYear, that.fiscalYear)
            .append(this.rroName, that.rroName)
            .append(this.rroAccountNumber, that.rroAccountNumber)
            .append(this.paymentDate, that.paymentDate)
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.systemTitle, that.systemTitle)
            .append(this.article, that.article)
            .append(this.rhAccountNumber, that.rhAccountNumber)
            .append(this.rhName, that.rhName)
            .append(this.standardNumber, that.standardNumber)
            .append(this.standardNumberType, that.standardNumberType)
            .append(this.publisher, that.publisher)
            .append(this.publicationDate, that.publicationDate)
            .append(this.numberOfCopies, that.numberOfCopies)
            .append(this.market, that.market)
            .append(this.marketPeriodFrom, that.marketPeriodFrom)
            .append(this.marketPeriodTo, that.marketPeriodTo)
            .append(this.author, that.author)
            .append(this.grossAmount, that.grossAmount)
            .append(this.reportedValue, that.reportedValue)
            .append(this.batchGrossAmount, that.batchGrossAmount)
            .append(this.status, that.status)
            .append(this.productFamily, that.productFamily)
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.payeeName, that.payeeName)
            .append(this.netAmount, that.netAmount)
            .append(this.serviceFee, that.serviceFee)
            .append(this.serviceFeeAmount, that.serviceFeeAmount)
            .append(this.scenarioName, that.scenarioName)
            .append(this.checkNumber, that.checkNumber)
            .append(this.checkDate, that.checkDate)
            .append(this.cccEventId, that.cccEventId)
            .append(this.distributionName, that.distributionName)
            .append(this.distributionDate, that.distributionDate)
            .append(this.periodEndDate, that.periodEndDate)
            .append(this.comment, that.comment)
            .append(this.aaclUsage, that.aaclUsage)
            .append(this.salUsage, that.salUsage)
            .append(this.aclciUsage, that.aclciUsage)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchName)
            .append(fiscalYear)
            .append(rroName)
            .append(rroAccountNumber)
            .append(paymentDate)
            .append(wrWrkInst)
            .append(workTitle)
            .append(systemTitle)
            .append(article)
            .append(rhAccountNumber)
            .append(rhName)
            .append(standardNumber)
            .append(standardNumberType)
            .append(publisher)
            .append(publicationDate)
            .append(numberOfCopies)
            .append(market)
            .append(marketPeriodFrom)
            .append(marketPeriodTo)
            .append(author)
            .append(grossAmount)
            .append(reportedValue)
            .append(batchGrossAmount)
            .append(status)
            .append(productFamily)
            .append(payeeAccountNumber)
            .append(payeeName)
            .append(netAmount)
            .append(serviceFee)
            .append(serviceFeeAmount)
            .append(scenarioName)
            .append(checkNumber)
            .append(checkDate)
            .append(cccEventId)
            .append(distributionName)
            .append(distributionDate)
            .append(periodEndDate)
            .append(comment)
            .append(aaclUsage)
            .append(salUsage)
            .append(aclciUsage)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("batchName", batchName)
            .append("fiscalYear", fiscalYear)
            .append("rroName", rroName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("paymentDate", paymentDate)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("systemTitle", systemTitle)
            .append("article", article)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("standardNumber", standardNumber)
            .append("standardNumberType", standardNumberType)
            .append("publisher", publisher)
            .append("publicationDate", publicationDate)
            .append("numberOfCopies", numberOfCopies)
            .append("market", market)
            .append("marketPeriodFrom", marketPeriodFrom)
            .append("marketPeriodTo", marketPeriodTo)
            .append("author", author)
            .append("grossAmount", grossAmount)
            .append("reportedValue", reportedValue)
            .append("batchGrossAmount", batchGrossAmount)
            .append("status", status)
            .append("productFamily", productFamily)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("payeeName", payeeName)
            .append("netAmount", netAmount)
            .append("serviceFee", serviceFee)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("scenarioName", scenarioName)
            .append("checkNumber", checkNumber)
            .append("checkDate", checkDate)
            .append("cccEventId", cccEventId)
            .append("distributionName", distributionName)
            .append("distributionDate", distributionDate)
            .append("periodEndDate", periodEndDate)
            .append("comment", comment)
            .append("aaclUsage", aaclUsage)
            .append("salUsage", salUsage)
            .append("aclciUsage", aclciUsage)
            .toString();
    }
}
