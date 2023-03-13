package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents usage.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/13/17
 *
 * @author Aliaksei Pchelnikau
 */
// TODO extract FAS specific fields to separate domain
public class Usage extends StoredEntity<String> {

    private String batchId;
    private String scenarioId;
    private String fundPoolId;
    private Long wrWrkInst;
    private String workTitle;
    private String systemTitle;
    private Rightsholder rightsholder = new Rightsholder();
    private Rightsholder payee = new Rightsholder();
    private String article;
    private String standardNumber;
    private String standardNumberType;
    private String publisher;
    private LocalDate publicationDate;
    private String market;
    private Integer marketPeriodFrom;
    private Integer marketPeriodTo;
    private String author;
    private Integer numberOfCopies;
    private BigDecimal reportedValue = BigDecimal.ZERO;
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal serviceFee;
    private BigDecimal serviceFeeAmount = BigDecimal.ZERO;
    private BigDecimal grossAmount = BigDecimal.ZERO;
    private UsageStatusEnum status;
    private String productFamily;
    private boolean rhParticipating;
    private boolean payeeParticipating;
    private String comment;
    private AaclUsage aaclUsage;
    private SalUsage salUsage;
    private AclciUsage aclciUsage;
    private FasUsage fasUsage;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getFundPoolId() {
        return fundPoolId;
    }

    public void setFundPoolId(String fundPoolId) {
        this.fundPoolId = fundPoolId;
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

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
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

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
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

    public Rightsholder getPayee() {
        return payee;
    }

    public void setPayee(Rightsholder payee) {
        this.payee = payee;
    }

    public boolean isRhParticipating() {
        return rhParticipating;
    }

    public void setRhParticipating(boolean rhParticipating) {
        this.rhParticipating = rhParticipating;
    }

    public boolean isPayeeParticipating() {
        return payeeParticipating;
    }

    public void setPayeeParticipating(boolean payeeParticipating) {
        this.payeeParticipating = payeeParticipating;
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

    public FasUsage getFasUsage() {
        return fasUsage;
    }

    public void setFasUsage(FasUsage fasUsage) {
        this.fasUsage = fasUsage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Usage that = (Usage) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.batchId, that.batchId)
            .append(this.scenarioId, that.scenarioId)
            .append(this.fundPoolId, that.fundPoolId)
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.systemTitle, that.systemTitle)
            .append(this.rightsholder, that.rightsholder)
            .append(this.article, that.article)
            .append(this.standardNumber, that.standardNumber)
            .append(this.standardNumberType, that.standardNumberType)
            .append(this.publisher, that.publisher)
            .append(this.publicationDate, that.publicationDate)
            .append(this.market, that.market)
            .append(this.marketPeriodFrom, that.marketPeriodFrom)
            .append(this.marketPeriodTo, that.marketPeriodTo)
            .append(this.author, that.author)
            .append(this.numberOfCopies, that.numberOfCopies)
            .append(this.reportedValue, that.reportedValue)
            .append(this.netAmount, that.netAmount)
            .append(this.serviceFee, that.serviceFee)
            .append(this.serviceFeeAmount, that.serviceFeeAmount)
            .append(this.grossAmount, that.grossAmount)
            .append(this.status, that.status)
            .append(this.productFamily, that.productFamily)
            .append(this.payee, that.payee)
            .append(this.rhParticipating, that.rhParticipating)
            .append(this.payeeParticipating, that.payeeParticipating)
            .append(this.comment, that.comment)
            .append(this.aaclUsage, that.aaclUsage)
            .append(this.salUsage, that.salUsage)
            .append(this.aclciUsage, that.aclciUsage)
            .append(this.fasUsage, that.fasUsage)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchId)
            .append(scenarioId)
            .append(fundPoolId)
            .append(wrWrkInst)
            .append(workTitle)
            .append(systemTitle)
            .append(rightsholder)
            .append(article)
            .append(standardNumber)
            .append(standardNumberType)
            .append(publisher)
            .append(publicationDate)
            .append(market)
            .append(marketPeriodFrom)
            .append(marketPeriodTo)
            .append(author)
            .append(numberOfCopies)
            .append(reportedValue)
            .append(netAmount)
            .append(serviceFee)
            .append(serviceFeeAmount)
            .append(grossAmount)
            .append(status)
            .append(productFamily)
            .append(payee)
            .append(rhParticipating)
            .append(payeeParticipating)
            .append(comment)
            .append(aaclUsage)
            .append(salUsage)
            .append(aclciUsage)
            .append(fasUsage)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("batchId", batchId)
            .append("scenarioId", scenarioId)
            .append("fundPoolId", fundPoolId)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("systemTitle", systemTitle)
            .append("rightsholder", rightsholder)
            .append("article", article)
            .append("standardNumber", standardNumber)
            .append("standardNumberType", standardNumberType)
            .append("publisher", publisher)
            .append("publicationDate", publicationDate)
            .append("market", market)
            .append("marketPeriodFrom", marketPeriodFrom)
            .append("marketPeriodTo", marketPeriodTo)
            .append("author", author)
            .append("numberOfCopies", numberOfCopies)
            .append("reportedValue", reportedValue)
            .append("netAmount", netAmount)
            .append("serviceFee", serviceFee)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("grossAmount", grossAmount)
            .append("status", status)
            .append("productFamily", productFamily)
            .append("payee", payee)
            .append("rhParticipating", rhParticipating)
            .append("payeeParticipating", payeeParticipating)
            .append("comment", comment)
            .append("aaclUsage", aaclUsage)
            .append("salUsage", salUsage)
            .append("aclciUsage", aclciUsage)
            .append("fasUsage", fasUsage)
            .toString();
    }
}
