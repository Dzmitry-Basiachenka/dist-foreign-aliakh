package com.copyright.rup.dist.foreign.integration.lm.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * Message to be sent to LM.
 * Contains source in header and list of external usages belonging to the scenario.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/15/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageMessage {

    private final List<ExternalUsage> externalUsages;
    private final Map<String, Object> headers;

    /**
     * Constructor.
     *
     * @param headers        map of headers
     * @param externalUsages list of {@link ExternalUsage}
     */
    public ExternalUsageMessage(Map<String, Object> headers, List<ExternalUsage> externalUsages) {
        this.headers = headers;
        this.externalUsages = externalUsages;
    }

    public List<ExternalUsage> getExternalUsages() {
        return externalUsages;
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
        ExternalUsageMessage that = (ExternalUsageMessage) obj;
        return new EqualsBuilder()
            .append(this.headers, that.headers)
            .append(this.externalUsages, that.externalUsages)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(headers)
            .append(externalUsages)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("headers", headers)
            .append("externalUsages", externalUsages)
            .toString();
    }
}

