package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Class contains a pair of payee account number and set of aggregate licensee classes.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/09/2020
 *
 * @author Ihar Suvorau
 */
public class PayeeAccountAggregateLicenseeClassesPair {

    private Long payeeAccountNumber;
    private Set<AggregateLicenseeClass> aggregateLicenseeClasses;

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public Set<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return aggregateLicenseeClasses;
    }

    public void setAggregateLicenseeClasses(Set<AggregateLicenseeClass> aggregateLicenseeClasses) {
        this.aggregateLicenseeClasses = aggregateLicenseeClasses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        PayeeAccountAggregateLicenseeClassesPair that = (PayeeAccountAggregateLicenseeClassesPair) obj;
        return new EqualsBuilder()
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.aggregateLicenseeClasses, that.aggregateLicenseeClasses)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(payeeAccountNumber)
            .append(aggregateLicenseeClasses)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("aggregateLicenseeClasses", aggregateLicenseeClasses)
            .toString();
    }
}
