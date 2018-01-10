package com.copyright.rup.dist.foreign.integration.lm.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Wraps list of {@link LiabilityDetail}s for sending to the LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
public class LiabilityDetailsWrapper {

    @JsonProperty(value = "details")
    private final List<LiabilityDetail> liabilityDetails;

    /**
     * Constructor.
     *
     * @param liabilityDetails list of {@link LiabilityDetail}s
     */
    public LiabilityDetailsWrapper(List<LiabilityDetail> liabilityDetails) {
        this.liabilityDetails = liabilityDetails;
    }

    public List<LiabilityDetail> getLiabilityDetails() {
        return liabilityDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        LiabilityDetailsWrapper that = (LiabilityDetailsWrapper) obj;
        return new EqualsBuilder()
            .append(this.liabilityDetails, that.liabilityDetails)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(liabilityDetails)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("liabilityDetails", liabilityDetails)
            .toString();
    }
}
