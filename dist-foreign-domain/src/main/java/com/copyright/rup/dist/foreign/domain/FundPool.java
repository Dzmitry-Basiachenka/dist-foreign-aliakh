package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Domain object to represent Fund Pool.
 *
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/07/20
 *
 * @author Darya Baraukova
 */
public class FundPool extends StoredEntity<String> {

    private String productFamily;
    private String name;
    private String comment;
    private BigDecimal totalAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        FundPool that = (FundPool) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(productFamily, that.productFamily)
            .append(name, that.name)
            .append(comment, that.comment)
            .append(totalAmount, that.totalAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(productFamily)
            .append(name)
            .append(comment)
            .append(totalAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("productFamily", productFamily)
            .append("name", name)
            .append("comment", comment)
            .append("totalAmount", totalAmount)
            .toString();
    }
}
