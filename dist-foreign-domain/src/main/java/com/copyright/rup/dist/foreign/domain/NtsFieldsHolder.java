package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Represents fields specific for NTS scenario.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/15/2019
 *
 * @author Aliaksandr Liakh
 */
public class NtsFieldsHolder {

    private BigDecimal rhMinimumAmount = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);

    public BigDecimal getRhMinimumAmount() {
        return rhMinimumAmount;
    }

    public void setRhMinimumAmount(BigDecimal rhMinimumAmount) {
        this.rhMinimumAmount = rhMinimumAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        NtsFieldsHolder that = (NtsFieldsHolder) obj;
        return new EqualsBuilder()
            .append(this.rhMinimumAmount, that.rhMinimumAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhMinimumAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhMinimumAmount", rhMinimumAmount)
            .toString();
    }
}
