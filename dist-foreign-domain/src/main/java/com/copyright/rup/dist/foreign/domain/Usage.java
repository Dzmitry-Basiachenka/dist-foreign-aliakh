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
public class Usage extends StoredEntity<String> {

    private String batchId;
    private String scenarioId;
    private Long wrWrkInst;
    private String workTitle;
    private String systemTitle;
    private Rightsholder rightsholder = new Rightsholder();
    private Rightsholder payee = new Rightsholder();
    private String article;
    private String standardNumber;
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
    private String comment;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
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
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.systemTitle, that.systemTitle)
            .append(this.rightsholder, that.rightsholder)
            .append(this.article, that.article)
            .append(this.standardNumber, that.standardNumber)
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
            .append(this.comment, that.comment)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchId)
            .append(scenarioId)
            .append(wrWrkInst)
            .append(workTitle)
            .append(systemTitle)
            .append(rightsholder)
            .append(article)
            .append(standardNumber)
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
            .append(comment)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("batchId", batchId)
            .append("scenarioId", scenarioId)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("systemTitle", systemTitle)
            .append("rightsholder", rightsholder)
            .append("article", article)
            .append("standardNumber", standardNumber)
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
            .append("comment", comment)
            .toString();
    }
}
