package com.copyright.rup.dist.foreign.integration.crm.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.Objects;

/**
 * Wraps list of {@link InsertRightsDistributionRequest}s for sending to the CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsertRightsDistributionRequestWrapper {

    @JsonProperty(value = "list")
    private ListWrapper list;

    /**
     * Constructor for CRM rights distribution request wrapper.
     *
     * @param requests the requests for CRM
     */
    public InsertRightsDistributionRequestWrapper(Collection<InsertRightsDistributionRequest> requests) {
        this.list = new ListWrapper(Objects.requireNonNull(requests));
    }

    /**
     * @return collection of CRM rights distribution requests.
     */
    public ListWrapper getList() {
        return list;
    }

    /**
     * Sets a collection of CRM rights distribution requests.
     *
     * @param list the collection of CRM rights distribution requests.
     */
    public void setList(ListWrapper list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        InsertRightsDistributionRequestWrapper that = (InsertRightsDistributionRequestWrapper) obj;
        return new EqualsBuilder()
            .append(this.list, that.list)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(list)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("list", list)
            .toString();
    }

    /**
     * Wraps collection of {@link InsertRightsDistributionRequest}s for sending to the CRM system.
     */
    static class ListWrapper {

        @JsonProperty(value = "rightsDistribution")
        private Collection<InsertRightsDistributionRequest> requests;

        /**
         * Constructor.
         *
         * @param requests collection of {@link InsertRightsDistributionRequest} instances
         */
        public ListWrapper(Collection<InsertRightsDistributionRequest> requests) {
            this.requests = requests;
        }

        /**
         * @return collection of {@link InsertRightsDistributionRequest}.
         */
        public Collection<InsertRightsDistributionRequest> getRequests() {
            return requests;
        }

        /**
         * Sets collection of {@link InsertRightsDistributionRequest}.
         *
         * @param requests collection of {@link InsertRightsDistributionRequest}
         */
        public void setRequests(Collection<InsertRightsDistributionRequest> requests) {
            this.requests = requests;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            ListWrapper that = (ListWrapper) obj;
            return new EqualsBuilder()
                .append(this.requests, that.requests)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(requests)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("requests", requests)
                .toString();
        }
    }
}
