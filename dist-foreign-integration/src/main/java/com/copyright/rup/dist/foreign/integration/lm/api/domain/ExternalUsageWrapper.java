package com.copyright.rup.dist.foreign.integration.lm.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Wraps list of {@link ExternalUsage} for sending to the LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/15/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageWrapper {

    @JsonProperty("details")
    private final List<ExternalUsage> externalUsages;

    /**
     * Constructor.
     *
     * @param externalUsages list of {@link ExternalUsage}s
     */
    public ExternalUsageWrapper(List<ExternalUsage> externalUsages) {
        this.externalUsages = externalUsages;
    }

    public List<ExternalUsage> getExternalUsages() {
        return externalUsages;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        ExternalUsageWrapper that = (ExternalUsageWrapper) obj;
        return new EqualsBuilder()
            .append(this.externalUsages, that.externalUsages)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(externalUsages)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("externalUsages", externalUsages)
            .toString();
    }
}
