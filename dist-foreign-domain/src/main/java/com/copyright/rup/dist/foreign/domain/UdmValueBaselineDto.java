package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents UDM baseline value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/15/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmValueBaselineDto extends StoredEntity<String> {

    private static final long serialVersionUID = -7575333468753289266L;

    private Integer period;
    private Long wrWrkInst;
    private String systemTitle;
    private String publicationType;
    private BigDecimal price;
    private Boolean priceFlag;
    private BigDecimal content;
    private Boolean contentFlag;
    private BigDecimal contentUnitPrice;
    private Boolean contentUnitPriceFlag;
    private String comment;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(Boolean priceFlag) {
        this.priceFlag = priceFlag;
    }

    public BigDecimal getContent() {
        return content;
    }

    public void setContent(BigDecimal content) {
        this.content = content;
    }

    public Boolean getContentFlag() {
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

    public Boolean getContentUnitPriceFlag() {
        return contentUnitPriceFlag;
    }

    public void setContentUnitPriceFlag(Boolean contentUnitPriceFlag) {
        this.contentUnitPriceFlag = contentUnitPriceFlag;
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
        UdmValueBaselineDto that = (UdmValueBaselineDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(period, that.period)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(publicationType, that.publicationType)
            .append(price, that.price)
            .append(priceFlag, that.priceFlag)
            .append(content, that.content)
            .append(contentFlag, that.contentFlag)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(contentUnitPriceFlag, that.contentUnitPriceFlag)
            .append(comment, that.comment)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(period)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(publicationType)
            .append(price)
            .append(priceFlag)
            .append(content)
            .append(contentFlag)
            .append(contentUnitPrice)
            .append(contentUnitPriceFlag)
            .append(comment)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("period", period)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("publicationType", publicationType)
            .append("price", price)
            .append("priceFlag", priceFlag)
            .append("content", content)
            .append("contentFlag", contentFlag)
            .append("contentUnitPrice", contentUnitPrice)
            .append("contentUnitPriceFlag", contentUnitPriceFlag)
            .append("comment", comment)
            .toString();
    }
}
