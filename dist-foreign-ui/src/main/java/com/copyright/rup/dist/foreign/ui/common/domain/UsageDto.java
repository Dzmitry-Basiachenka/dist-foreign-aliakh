package com.copyright.rup.dist.foreign.ui.common.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.util.UsageBatchUtils;

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

    private Integer detailId;
    private String batchName;
    private String fiscalYear;
    private Rightsholder rro;
    private LocalDate paymentDate;
    private Long wrWrkInst;
    private String workTitle;
    private String article;
    private Rightsholder rightsholder;
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
     * Constructor.
     *
     * @param batch batch
     * @param usage usage
     */
    public UsageDto(UsageBatch batch, Usage usage) {
        setId(checkNotNull(usage).getId());
        batchName = checkNotNull(batch).getName();
        fiscalYear = UsageBatchUtils.getFiscalYear(batch.getFiscalYear());
        rro = batch.getRro();
        paymentDate = batch.getPaymentDate();
        detailId = usage.getDetailId();
        wrWrkInst = usage.getWrWrkInst();
        workTitle = usage.getWorkTitle();
        article = usage.getArticle();
        rightsholder = usage.getRightsholder();
        standardNumber = usage.getStandardNumber();
        publisher = usage.getPublisher();
        publicationDate = usage.getPublicationDate();
        numberOfCopies = usage.getNumberOfCopies();
        market = usage.getMarket();
        marketPeriodFrom = usage.getMarketPeriodFrom();
        marketPeriodTo = usage.getMarketPeriodTo();
        author = usage.getAuthor();
        grossAmount = usage.getGrossAmount();
        originalAmount = usage.getOriginalAmount();
        status = usage.getStatus();
    }

    /**
     * @return detail id.
     */
    public Integer getDetailId() {
        return detailId;
    }

    /**
     * Sets detail id.
     *
     * @param detailId detail id
     */
    public void setDetailId(Integer detailId) {
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
    public String getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets fiscal year.
     *
     * @param fiscalYear fiscal year
     */
    public void setFiscalYear(String fiscalYear) {
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
     * @return rro.
     */
    public Rightsholder getRro() {
        return rro;
    }

    /**
     * Sets rro.
     *
     * @param rro rro
     */
    public void setRro(Rightsholder rro) {
        this.rro = rro;
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
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.rightsholder, that.rightsholder)
            .append(this.grossAmount, that.grossAmount)
            .append(this.rro, that.rro)
            .append(this.paymentDate, that.paymentDate)
            .append(this.status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(wrWrkInst)
            .append(workTitle)
            .append(rightsholder)
            .append(grossAmount)
            .append(rro)
            .append(paymentDate)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("rightsholder", rightsholder)
            .append("grossAmount", grossAmount)
            .append("rro", rro)
            .append("paymentDate", paymentDate)
            .append("status", status)
            .toString();
    }
}
