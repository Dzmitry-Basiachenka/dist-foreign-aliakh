package com.copyright.rup.dist.foreign.integration.lm.impl.domain;

import com.copyright.rup.dist.foreign.domain.LiabilityDetail;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * Message to be sent to LM.
 * Contains source in header and list of liability detail belonging to the scenario.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class LiabilityDetailMessage {

    private final List<LiabilityDetail> liabilityDetails;
    private final Map<String, Object> headers;

    /**
     * Constructor.
     *
     * @param headers          map of headers
     * @param liabilityDetails list of {@link LiabilityDetail}s
     */
    public LiabilityDetailMessage(Map<String, Object> headers, List<LiabilityDetail> liabilityDetails) {
        this.headers = headers;
        this.liabilityDetails = liabilityDetails;
    }

    public List<LiabilityDetail> getLiabilityDetails() {
        return liabilityDetails;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        LiabilityDetailMessage that = (LiabilityDetailMessage) obj;
        return new EqualsBuilder()
            .append(this.headers, that.headers)
            .append(this.liabilityDetails, that.liabilityDetails)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(headers)
            .append(liabilityDetails)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("headers", headers)
            .append("liabilityDetails", liabilityDetails)
            .toString();
    }
}
