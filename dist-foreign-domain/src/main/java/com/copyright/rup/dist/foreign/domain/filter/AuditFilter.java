package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashSet;
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
public class AuditFilter implements Serializable {

    private static final long serialVersionUID = 7685302969208427378L;

    private Set<Long> rhAccountNumbers = new HashSet<>();
    private Set<Long> licenseeAccountNumbers = new HashSet<>();
    private Set<String> batchesIds = new HashSet<>();
    private Set<UsageStatusEnum> statuses = new HashSet<>();
    private String productFamily;
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
        if (Objects.nonNull(filter)) {
            this.statuses = filter.getStatuses();
            this.batchesIds = filter.getBatchesIds();
            this.rhAccountNumbers = filter.getRhAccountNumbers();
            this.licenseeAccountNumbers = filter.getLicenseeAccountNumbers();
            this.cccEventId = filter.getCccEventId();
            this.distributionName = filter.getDistributionName();
            this.searchValue = filter.getSearchValue();
            this.productFamily = filter.getProductFamily();
            this.usagePeriod = filter.getUsagePeriod();
            this.salDetailType = filter.getSalDetailType();
        }
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
