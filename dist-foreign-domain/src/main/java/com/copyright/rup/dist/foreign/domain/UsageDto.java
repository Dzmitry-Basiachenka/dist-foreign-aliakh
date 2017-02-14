package com.copyright.rup.dist.foreign.domain;

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

    private Long detailId;
    private String batchName;
    private Integer fiscalYear;
    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private Long wrWrkInst;
    private String workTitle;
    private String article;
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
    private BigDecimal grossAmount;
    private BigDecimal originalAmount;
    private UsageStatusEnum status;

    /**
     * @return detail id.
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * Sets detail id.
     *
     * @param detailId detail id
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * @return batch name.
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * Sets batch name.
     *
     * @param batchName batch name
     */
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    /**
     * @return fiscal year.
     */
    public Integer getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets fiscal year.
     *
     * @param fiscalYear fiscal year
     */
    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    /**
     * @return work identifier.
     */
    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    /**
     * Sets work identifier.
     *
     * @param wrWrkInst work identifier
     */
    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    /**
     * @return work title.
     */
    public String getWorkTitle() {
        return workTitle;
    }

    /**
     * Sets work title.
     *
     * @param workTitle work title
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    /**
     * @return article.
     */
    public String getArticle() {
        return article;
    }

    /**
     * Sets article.
     *
     * @param article article
     */
    public void setArticle(String article) {
        this.article = article;
    }

    /**
     * @return standard number.
     */
    public String getStandardNumber() {
        return standardNumber;
    }

    /**
     * Sets standard number.
     *
     * @param standardNumber standard number
     */
    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    /**
     * @return publisher.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets publication date.
     *
     * @param publisher publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return publication date.
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets publication date.
     *
     * @param publicationDate publication date
     */
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * @return market.
     */
    public String getMarket() {
        return market;
    }

    /**
     * Sets market.
     *
     * @param market market
     */
    public void setMarket(String market) {
        this.market = market;
    }

    /**
     * @return market period from.
     */
    public Integer getMarketPeriodFrom() {
        return marketPeriodFrom;
    }

    /**
     * Sets market period from.
     *
     * @param marketPeriodFrom market period from
     */
    public void setMarketPeriodFrom(Integer marketPeriodFrom) {
        this.marketPeriodFrom = marketPeriodFrom;
    }

    /**
     * @return market period to.
     */
    public Integer getMarketPeriodTo() {
        return marketPeriodTo;
    }

    /**
     * Sets market period to.
     *
     * @param marketPeriodTo market period to
     */
    public void setMarketPeriodTo(Integer marketPeriodTo) {
        this.marketPeriodTo = marketPeriodTo;
    }

    /**
     * @return author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return number of copies.
     */
    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     * Sets number of copies.
     *
     * @param numberOfCopies number of copies
     */
    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    /**
     * @return original amount.
     */
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    /**
     * Sets original amount.
     *
     * @param originalAmount original amount
     */
    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    /**
     * @return gross amount.
     */
    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets gross amount.
     *
     * @param grossAmount gross amount
     */
    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    /**
     * @return payment date.
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets payment date.
     *
     * @param paymentDate payment date
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return status.
     */
    public UsageStatusEnum getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status status
     */
    public void setStatus(UsageStatusEnum status) {
        this.status = status;
    }

    /**
     * @return rro name.
     */
    public String getRroName() {
        return rroName;
    }

    /**
     * Sets rro name.
     *
     * @param rroName rro name to set
     */
    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    /**
     * @return rro account number.
     */
    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    /**
     * Sets rro account number.
     *
     * @param rroAccountNumber rro account number to set
     */
    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    /**
     * @return rightsholder name.
     */
    public String getRhName() {
        return rhName;
    }

    /**
     * Sets rightsholder name.
     *
     * @param rhName rightsholder name to set
     */
    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    /**
     * @return rightsholder account number.
     */
    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    /**
     * Sets rightsholder account number.
     *
     * @param rhAccountNumber rightsholder account number
     */
    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
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
            .append(this.originalAmount, that.originalAmount)
            .append(this.status, that.status)
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
            .append(originalAmount)
            .append(status)
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
            .append("originalAmount", originalAmount)
            .append("status", status)
            .toString();
    }
}
