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
 * Wraps list of {@link CrmRightsDistributionRequest}s for sending to the CRM service.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrmRightsDistributionRequestWrapper {

    @JsonProperty("list")
    private ListWrapper list;

    /**
     * Constructor for CRM rights distribution request wrapper.
     *
     * @param requests the requests for CRM
     */
    public CrmRightsDistributionRequestWrapper(Collection<CrmRightsDistributionRequest> requests) {
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
        CrmRightsDistributionRequestWrapper that = (CrmRightsDistributionRequestWrapper) obj;
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
     * Wraps collection of {@link CrmRightsDistributionRequest}s for sending to the CRM service.
     */
    static class ListWrapper {

        @JsonProperty("rightsDistribution")
        private Collection<CrmRightsDistributionRequest> requests;

        /**
         * Constructor.
         *
         * @param requests collection of {@link CrmRightsDistributionRequest} instances
         */
        public ListWrapper(Collection<CrmRightsDistributionRequest> requests) {
            this.requests = requests;
        }

        /**
         * @return collection of {@link CrmRightsDistributionRequest}.
         */
        public Collection<CrmRightsDistributionRequest> getRequests() {
            return requests;
        }

        /**
         * Sets collection of {@link CrmRightsDistributionRequest}.
         *
         * @param requests collection of {@link CrmRightsDistributionRequest}
         */
        public void setRequests(Collection<CrmRightsDistributionRequest> requests) {
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
