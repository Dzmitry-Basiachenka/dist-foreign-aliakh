package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents rightsholder, payee and product family combination.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/18/20
 *
 * @author Stanislau Rudak
 */
public class RightsholderPayeeProductFamilyHolder {

    private Rightsholder rightsholder = new Rightsholder();
    private Rightsholder payee = new Rightsholder();
    private String productFamily;

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
    }

    public Rightsholder getPayee() {
        return payee;
    }

    public void setPayee(Rightsholder payee) {
        this.payee = payee;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        RightsholderPayeeProductFamilyHolder that = (RightsholderPayeeProductFamilyHolder) obj;
        return new EqualsBuilder()
            .append(rightsholder, that.rightsholder)
            .append(payee, that.payee)
            .append(productFamily, that.productFamily)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholder)
            .append(payee)
            .append(productFamily)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("rightsholder", rightsholder)
            .append("payee", payee)
            .append("productFamily", productFamily)
            .toString();
    }
}
