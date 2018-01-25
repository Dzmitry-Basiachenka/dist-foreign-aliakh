package com.copyright.rup.dist.foreign.integration.rms.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;
import java.util.Set;

/**
 * Represents rights assignment request.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/24/18
 *
 * @author Darya Baraukova
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RightsAssignmentRequest {

    @JsonProperty(value = "numWorks")
    private int numWorks;

    @JsonProperty(value = "works")
    private Set<Long> wrWrkInsts;

    /**
     * Constructor.
     *
     * @param wrWrkInsts set of Wr Wrk Insts
     */
    public RightsAssignmentRequest(Set<Long> wrWrkInsts) {
        this.wrWrkInsts = Objects.requireNonNull(wrWrkInsts);
        this.numWorks = wrWrkInsts.size();
    }

    public int getNumWorks() {
        return numWorks;
    }

    public void setNumWorks(int numWorks) {
        this.numWorks = numWorks;
    }

    public Set<Long> getWrWrkInsts() {
        return wrWrkInsts;
    }

    public void setWrWrkInsts(Set<Long> wrWrkInsts) {
        this.wrWrkInsts = wrWrkInsts;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        final RightsAssignmentRequest that = (RightsAssignmentRequest) obj;
        return new EqualsBuilder()
            .append(this.numWorks, that.numWorks)
            .append(this.wrWrkInsts, that.wrWrkInsts)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(numWorks)
            .append(wrWrkInsts)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("numWorks", numWorks)
            .append("wrWrkInsts", wrWrkInsts)
            .toString();
    }
}
