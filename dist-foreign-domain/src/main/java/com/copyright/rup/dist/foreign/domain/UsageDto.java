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

    private Long detailId;
    private String batchName;
    private Integer fiscalYear;
    //TODO {ushalamitski} Should be replaced with Rightsholder domain
    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private Long wrWrkInst;
    private String workTitle;
    private String article;
    //TODO {ushalamitski} Should be replaced with Rightsholder domain
    private Long rhAccountNumber;
    private String rhName;
    private String standardNumber;
    private String publisher;
    private LocalDate publicationDate;
    private Integer numberOfCopies;
    private String market;
    private Integer marketPeriodFrom;
    private Integer marketPeriodTo;
    private String author;
    private Rightsholder payee = new Rightsholder();
    private BigDecimal grossAmount = DEFAULT_AMOUNT;
    private BigDecimal reportedValue = DEFAULT_AMOUNT;
    private BigDecimal batchGrossAmount = DEFAULT_AMOUNT;
    private BigDecimal netAmount = DEFAULT_AMOUNT;
    private BigDecimal serviceFee = DEFAULT_AMOUNT;
    private BigDecimal serviceFeeAmount = DEFAULT_AMOUNT;
    private UsageStatusEnum status;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

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

    public Rightsholder getPayee() {
        return payee;
    }

    public void setPayee(Rightsholder payee) {
        this.payee = payee;
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
            .append(this.detailId, that.detailId)
            .append(this.batchName, that.batchName)
            .append(this.fiscalYear, that.fiscalYear)
            .append(this.rroName, that.rroName)
            .append(this.rroAccountNumber, that.rroAccountNumber)
            .append(this.paymentDate, that.paymentDate)
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.article, that.article)
            .append(this.rhAccountNumber, that.rhAccountNumber)
            .append(this.rhName, that.rhName)
            .append(this.standardNumber, that.standardNumber)
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
            .append(this.payee, that.payee)
            .append(this.netAmount, that.netAmount)
            .append(this.serviceFee, that.serviceFee)
            .append(this.serviceFeeAmount, that.serviceFeeAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(detailId)
            .append(batchName)
            .append(fiscalYear)
            .append(rroName)
            .append(rroAccountNumber)
            .append(paymentDate)
            .append(wrWrkInst)
            .append(workTitle)
            .append(article)
            .append(rhAccountNumber)
            .append(rhName)
            .append(standardNumber)
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
            .append(payee)
            .append(netAmount)
            .append(serviceFee)
            .append(serviceFeeAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("detailId", detailId)
            .append("batchName", batchName)
            .append("fiscalYear", fiscalYear)
            .append("rroName", rroName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("paymentDate", paymentDate)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("article", article)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("standardNumber", standardNumber)
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
            .append("payee", payee)
            .append("netAmount", netAmount)
            .append("serviceFee", serviceFee)
            .append("serviceFeeAmount", serviceFeeAmount)
            .toString();
    }
}
