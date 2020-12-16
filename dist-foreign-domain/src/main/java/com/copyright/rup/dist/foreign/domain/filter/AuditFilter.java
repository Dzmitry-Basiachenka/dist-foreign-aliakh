package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
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
    private Set<Long> licenseeAccountNumbers = Sets.newHashSet();
    private Set<String> batchesIds = Sets.newHashSet();
    // TODO {dbaraukova} investigate options to remove product family field from filter as it is not a part of it
    private String productFamily;
    private Set<UsageStatusEnum> statuses = Sets.newHashSet();
    private String cccEventId;
    private String distributionName;
    private String searchValue;
    private Integer usagePeriod;
    private SalDetailTypeEnum salDetailType;

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
        setLicenseeAccountNumbers(filter.getLicenseeAccountNumbers());
        setCccEventId(filter.getCccEventId());
        setDistributionName(filter.getDistributionName());
        setSearchValue(filter.getSearchValue());
        setProductFamily(filter.getProductFamily());
        setUsagePeriod(filter.getUsagePeriod());
        setSalDetailType(filter.getSalDetailType());
    }

    public Integer getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(Integer usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    public Set<Long> getLicenseeAccountNumbers() {
        return licenseeAccountNumbers;
    }

    public void setLicenseeAccountNumbers(Set<Long> licenseeAccountNumbers) {
        this.licenseeAccountNumbers = licenseeAccountNumbers;
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

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
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

    public SalDetailTypeEnum getSalDetailType() {
        return salDetailType;
    }

    public void setSalDetailType(SalDetailTypeEnum salDetailType) {
        this.salDetailType = salDetailType;
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(rhAccountNumbers)
            && CollectionUtils.isEmpty(licenseeAccountNumbers)
            && CollectionUtils.isEmpty(batchesIds)
            && CollectionUtils.isEmpty(statuses)
            && StringUtils.isBlank(cccEventId)
            && StringUtils.isBlank(distributionName)
            && StringUtils.isBlank(searchValue)
            && Objects.isNull(usagePeriod)
            && Objects.isNull(salDetailType);
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
            .append(this.licenseeAccountNumbers, that.licenseeAccountNumbers)
            .append(this.batchesIds, that.batchesIds)
            .append(this.statuses, that.statuses)
            .append(this.productFamily, that.productFamily)
            .append(this.cccEventId, that.cccEventId)
            .append(this.distributionName, that.distributionName)
            .append(this.searchValue, that.searchValue)
            .append(this.usagePeriod, that.usagePeriod)
            .append(this.salDetailType, that.salDetailType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumbers)
            .append(licenseeAccountNumbers)
            .append(batchesIds)
            .append(statuses)
            .append(productFamily)
            .append(cccEventId)
            .append(distributionName)
            .append(searchValue)
            .append(usagePeriod)
            .append(salDetailType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("licenseeAccountNumbers", licenseeAccountNumbers)
            .append("batchesIds", batchesIds)
            .append("statuses", statuses)
            .append("productFamily", productFamily)
            .append("cccEventId", cccEventId)
            .append("distributionName", distributionName)
            .append("searchValue", searchValue)
            .append("usagePeriod", usagePeriod)
            .append("salDetailType", salDetailType)
            .toString();
    }
}
