package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents UDM proxy value DTO.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmProxyValueDto {

    private String pubTypeName;
    private Integer period;
    private BigDecimal contentUnitPrice;
    private Integer contentUnitPriceCount;

    public String getPubTypeName() {
        return pubTypeName;
    }

    public void setPubTypeName(String pubTypeName) {
        this.pubTypeName = pubTypeName;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getContentUnitPrice() {
        return contentUnitPrice;
    }

    public void setContentUnitPrice(BigDecimal contentUnitPrice) {
        this.contentUnitPrice = contentUnitPrice;
    }

    public Integer getContentUnitPriceCount() {
        return contentUnitPriceCount;
    }

    public void setContentUnitPriceCount(Integer contentUnitPriceCount) {
        this.contentUnitPriceCount = contentUnitPriceCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmProxyValueDto that = (UdmProxyValueDto) obj;
        return new EqualsBuilder()
            .append(pubTypeName, that.pubTypeName)
            .append(period, that.period)
            .append(contentUnitPrice, that.contentUnitPrice)
            .append(contentUnitPriceCount, that.contentUnitPriceCount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(pubTypeName)
            .append(period)
            .append(contentUnitPrice)
            .append(contentUnitPriceCount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("pubTypeName", pubTypeName)
            .append("period", period)
            .append("contentUnitPrice", contentUnitPrice)
            .append("contentUnitPriceCount", contentUnitPriceCount)
            .toString();
    }
}
