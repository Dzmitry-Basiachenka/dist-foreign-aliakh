package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class contains a pair of rightsholder and payee, corresponding to the rightsholder.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/1/17
 *
 * @author Aliaksandr Radkevich
 */
public class RightsholderPayeePair {

    private Rightsholder rightsholder;
    private Rightsholder payee;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        RightsholderPayeePair that = (RightsholderPayeePair) obj;
        return new EqualsBuilder()
            .append(this.rightsholder, that.rightsholder)
            .append(this.payee, that.payee)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholder)
            .append(payee)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rightsholder", rightsholder)
            .append("payee", payee)
            .toString();
    }
}
