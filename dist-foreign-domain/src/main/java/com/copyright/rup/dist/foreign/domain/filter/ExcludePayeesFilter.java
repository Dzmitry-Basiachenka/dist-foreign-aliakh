package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Filter fo exclude payees.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeesFilter {

    private Boolean payeeParticipating;
    private BigDecimal minimumThreshold;

    /**
     * Default constructor.
     */
    public ExcludePayeesFilter() {
        // default constructor
    }

    /**
     * Constructor.
     *
     * @param filter instance of {@link ExcludePayeesFilter}
     */
    public ExcludePayeesFilter(ExcludePayeesFilter filter) {
        this.payeeParticipating = filter.getPayeeParticipating();
        this.minimumThreshold = filter.getMinimumThreshold();
    }

    public Boolean getPayeeParticipating() {
        return payeeParticipating;
    }

    public void setPayeeParticipating(Boolean payeeParticipating) {
        this.payeeParticipating = payeeParticipating;
    }

    public BigDecimal getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(BigDecimal minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    public boolean isEmpty() {
        return Objects.isNull(payeeParticipating)
            && Objects.isNull(minimumThreshold);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        ExcludePayeesFilter that = (ExcludePayeesFilter) obj;
        return new EqualsBuilder()
            .append(payeeParticipating, that.payeeParticipating)
            .append(minimumThreshold, that.minimumThreshold)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(payeeParticipating)
            .append(minimumThreshold)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("payeeParticipating", payeeParticipating)
            .append("minimumThreshold", minimumThreshold)
            .toString();
    }
}
