package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Filter fo Audit.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditFilter {

    private Set<Long> rhAccountNumbers = Sets.newHashSet();
    private Set<String> batchesIds = Sets.newHashSet();
    private Set<String> productFamilies = Sets.newHashSet();
    private Set<UsageStatusEnum> statuses = Sets.newHashSet();
    private String cccEventId;
    private String distributionName;
    private String searchValue;

    /**
     * Default constructor.
     */
    public AuditFilter() {
        // default constructor
    }

    /**
     * Constructor.
     *
     * @param filter {@link AuditFilter}
     */
    public AuditFilter(AuditFilter filter) {
        setStatuses(filter.getStatuses());
        setBatchesIds(filter.getBatchesIds());
        setRhAccountNumbers(filter.getRhAccountNumbers());
        setCccEventId(filter.getCccEventId());
        setDistributionName(filter.getDistributionName());
        setSearchValue(filter.getSearchValue());
        setProductFamilies(filter.getProductFamilies());
    }

    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    public Set<String> getBatchesIds() {
        return batchesIds;
    }

    public void setBatchesIds(Set<String> batchesIds) {
        this.batchesIds = batchesIds;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Set<UsageStatusEnum> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<UsageStatusEnum> statuses) {
        this.statuses = statuses;
    }

    public Set<String> getProductFamilies() {
        return productFamilies;
    }

    public void setProductFamilies(Set<String> productFamilies) {
        this.productFamilies = productFamilies;
    }

    public String getCccEventId() {
        return cccEventId;
    }

    public void setCccEventId(String cccEventId) {
        this.cccEventId = cccEventId;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(rhAccountNumbers)
            && CollectionUtils.isEmpty(batchesIds)
            && CollectionUtils.isEmpty(statuses)
            && CollectionUtils.isEmpty(productFamilies)
            && StringUtils.isBlank(cccEventId)
            && StringUtils.isBlank(distributionName)
            && StringUtils.isBlank(searchValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        AuditFilter that = (AuditFilter) obj;
        return new EqualsBuilder()
            .append(this.rhAccountNumbers, that.rhAccountNumbers)
            .append(this.batchesIds, that.batchesIds)
            .append(this.statuses, that.statuses)
            .append(this.productFamilies, that.productFamilies)
            .append(this.cccEventId, that.cccEventId)
            .append(this.distributionName, that.distributionName)
            .append(this.searchValue, that.searchValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumbers)
            .append(batchesIds)
            .append(statuses)
            .append(productFamilies)
            .append(cccEventId)
            .append(distributionName)
            .append(searchValue)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("batchesIds", batchesIds)
            .append("statuses", statuses)
            .append("productFamilies", productFamilies)
            .append("cccEventId", cccEventId)
            .append("distributionName", distributionName)
            .append("searchValue", searchValue)
            .toString();
    }
}
