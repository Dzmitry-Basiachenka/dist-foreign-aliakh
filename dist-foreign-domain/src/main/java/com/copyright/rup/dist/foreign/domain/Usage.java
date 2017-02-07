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

    private Long detailId;
    private String batchId;
    private Long wrWrkInst;
    private String workTitle;
    private Rightsholder rightsholder;
    private String article;
    private String standardNumber;
    private String publisher;
    private LocalDate publicationDate;
    private String market;
    private Integer marketPeriodFrom;
    private Integer marketPeriodTo;
    private String author;
    private Integer numberOfCopies;
    private BigDecimal originalAmount = BigDecimal.ZERO;
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal serviceFee = BigDecimal.ZERO;
    private BigDecimal serviceFeeAmount = BigDecimal.ZERO;
    private BigDecimal grossAmount = BigDecimal.ZERO;
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
     * @return batch id.
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * Sets batch id.
     *
     * @param batchId batch id
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
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
     * @return rights holder.
     */
    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    /**
     * Sets rights holder.
     *
     * @param rightsholder rights holder
     */
    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
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
        if (null != originalAmount) {
            this.originalAmount = originalAmount;
        }
    }

    /**
     * @return net amount.
     */
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    /**
     * Sets net amount.
     *
     * @param netAmount net amount
     */
    public void setNetAmount(BigDecimal netAmount) {
        if (null != netAmount) {
            this.netAmount = netAmount;
        }
    }

    /**
     * @return service fee.
     */
    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    /**
     * Sets service fee.
     *
     * @param serviceFee service fee
     */
    public void setServiceFee(BigDecimal serviceFee) {
        if (null != serviceFee) {
            this.serviceFee = serviceFee;
        }
    }

    /**
     * @return service fee amount.
     */
    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    /**
     * Sets service fee amount.
     *
     * @param serviceFeeAmount service fee amount
     */
    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        if (null != serviceFeeAmount) {
            this.serviceFeeAmount = serviceFeeAmount;
        }
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
        if (null != grossAmount) {
            this.grossAmount = grossAmount;
        }
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
            .append(this.detailId, that.detailId)
            .append(this.batchId, that.batchId)
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
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
            .append(this.originalAmount, that.originalAmount)
            .append(this.netAmount, that.netAmount)
            .append(this.serviceFee, that.serviceFee)
            .append(this.serviceFeeAmount, that.serviceFeeAmount)
            .append(this.grossAmount, that.grossAmount)
            .append(this.status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(detailId)
            .append(batchId)
            .append(wrWrkInst)
            .append(workTitle)
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
            .append(originalAmount)
            .append(netAmount)
            .append(serviceFee)
            .append(serviceFeeAmount)
            .append(grossAmount)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("detailId", detailId)
            .append("batchId", batchId)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
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
            .append("originalAmount", originalAmount)
            .append("netAmount", netAmount)
            .append("serviceFee", serviceFee)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("grossAmount", grossAmount)
            .append("status", status)
            .toString();
    }
}
