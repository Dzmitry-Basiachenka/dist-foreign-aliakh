package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsageFilter {

    private Set<Long> rhAccountNumbers = Sets.newHashSet();
    private Set<String> usageBatchesIds = Sets.newHashSet();
    private String productFamily;
    private UsageStatusEnum usageStatus;
    private LocalDate paymentDate;
    private Integer fiscalYear;
    private Integer usagePeriod;
    private SalDetailTypeEnum salDetailType;
    private Set<AclciLicenseTypeEnum> licenseTypes = new HashSet<>();

    /**
     * Default constructor.
     */
    public UsageFilter() {
        // Default constructor
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public UsageFilter(UsageFilter filter) {
        if (null != filter) {
            setRhAccountNumbers(filter.getRhAccountNumbers());
            setUsageBatchesIds(filter.getUsageBatchesIds());
            setUsageStatus(filter.getUsageStatus());
            setPaymentDate(filter.getPaymentDate());
            setFiscalYear(filter.getFiscalYear());
            setProductFamily(filter.getProductFamily());
            setUsagePeriod(filter.getUsagePeriod());
            setSalDetailType(filter.getSalDetailType());
            setLicenseTypes(filter.getLicenseTypes());
        }
    }

    /**
     * Constructs new filter based on previously stored one.
     *
     * @param usageFilter instance of {@link ScenarioUsageFilter}
     */
    public UsageFilter(ScenarioUsageFilter usageFilter) {
        //TODO: set setSalDetailType once it is added to ScenarioUsageFilter
        Objects.requireNonNull(usageFilter);
        setRhAccountNumbers(usageFilter.getRhAccountNumbers());
        setUsageBatchesIds(usageFilter.getUsageBatchesIds());
        setUsageStatus(usageFilter.getUsageStatus());
        setPaymentDate(usageFilter.getPaymentDate());
        setFiscalYear(usageFilter.getFiscalYear());
        setProductFamily(usageFilter.getProductFamily());
    }

    public Set<Long> getRhAccountNumbers() {
        return rhAccountNumbers;
    }

    public void setRhAccountNumbers(Set<Long> rhAccountNumbers) {
        this.rhAccountNumbers = rhAccountNumbers;
    }

    public Set<String> getUsageBatchesIds() {
        return usageBatchesIds;
    }

    public void setUsageBatchesIds(Set<String> usageBatchesIds) {
        this.usageBatchesIds = usageBatchesIds;
    }

    public UsageStatusEnum getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public Integer getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(Integer usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    public SalDetailTypeEnum getSalDetailType() {
        return salDetailType;
    }

    public void setSalDetailType(SalDetailTypeEnum salDetailType) {
        this.salDetailType = salDetailType;
    }

    public Set<AclciLicenseTypeEnum> getLicenseTypes() {
        return licenseTypes;
    }

    public void setLicenseTypes(Set<AclciLicenseTypeEnum> licenseTypes) {
        this.licenseTypes = licenseTypes;
    }

    /**
     * @return {@code true} if filter does not contain any criteria except Product Family, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(rhAccountNumbers)
            && CollectionUtils.isEmpty(usageBatchesIds)
            && null == paymentDate
            && null == fiscalYear
            && null == usageStatus
            && null == usagePeriod
            && null == salDetailType
            && CollectionUtils.isEmpty(licenseTypes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageFilter that = (UsageFilter) obj;
        return new EqualsBuilder()
            .append(this.rhAccountNumbers, that.rhAccountNumbers)
            .append(this.usageBatchesIds, that.usageBatchesIds)
            .append(this.usageStatus, that.usageStatus)
            .append(this.productFamily, that.productFamily)
            .append(this.paymentDate, that.paymentDate)
            .append(this.fiscalYear, that.fiscalYear)
            .append(this.usagePeriod, that.usagePeriod)
            .append(this.salDetailType, that.salDetailType)
            .append(this.licenseTypes, that.licenseTypes)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumbers)
            .append(usageBatchesIds)
            .append(usageStatus)
            .append(productFamily)
            .append(paymentDate)
            .append(fiscalYear)
            .append(usagePeriod)
            .append(salDetailType)
            .append(licenseTypes)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("rhAccountNumbers", rhAccountNumbers)
            .append("usageBatchesIds", usageBatchesIds)
            .append("usageStatus", usageStatus)
            .append("productFamily", productFamily)
            .append("paymentDate", paymentDate)
            .append("fiscalYear", fiscalYear)
            .append("usagePeriod", usagePeriod)
            .append("salDetailType", salDetailType)
            .append("licenseTypes", licenseTypes)
            .toString();
    }
}
