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
public class NtsScenario {

    private BigDecimal minimumRhAmount = BigDecimal.ZERO;

    public BigDecimal getMinimumRhAmount() {
        return minimumRhAmount;
    }

    public void setMinimumRhAmount(BigDecimal minimumRhAmount) {
        this.minimumRhAmount = minimumRhAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        NtsScenario that = (NtsScenario) obj;
        return new EqualsBuilder()
            .append(this.minimumRhAmount, that.minimumRhAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(minimumRhAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("minimumRhAmount", minimumRhAmount)
            .toString();
    }
}
