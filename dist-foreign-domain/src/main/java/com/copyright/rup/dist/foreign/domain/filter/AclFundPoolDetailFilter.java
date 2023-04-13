package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for ACL fund pool details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolDetailFilter {

    private Set<String> fundPoolNames = new HashSet<>();
    private Set<Integer> periods = new HashSet<>();
    private Set<AggregateLicenseeClass> aggregateLicenseeClasses = new HashSet<>();
    private Set<DetailLicenseeClass> detailLicenseeClasses = new HashSet<>();
    private String licenseType;
    private String fundPoolType;

    /**
     * Default constructor.
     */
    public AclFundPoolDetailFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public AclFundPoolDetailFilter(AclFundPoolDetailFilter filter) {
        if (Objects.nonNull(filter)) {
            this.fundPoolNames = filter.getFundPoolNames();
            this.periods = filter.getPeriods();
            this.aggregateLicenseeClasses = filter.getAggregateLicenseeClasses();
            this.detailLicenseeClasses = filter.getDetailLicenseeClasses();
            this.licenseType = filter.getLicenseType();
            this.fundPoolType = filter.getFundPoolType();
        }
    }

    public Set<String> getFundPoolNames() {
        return fundPoolNames;
    }

    public void setFundPoolNames(Set<String> fundPoolNames) {
        this.fundPoolNames = fundPoolNames;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Set<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return aggregateLicenseeClasses;
    }

    public void setAggregateLicenseeClasses(Set<AggregateLicenseeClass> aggregateLicenseeClasses) {
        this.aggregateLicenseeClasses = aggregateLicenseeClasses;
    }

    public Set<DetailLicenseeClass> getDetailLicenseeClasses() {
        return detailLicenseeClasses;
    }

    public void setDetailLicenseeClasses(Set<DetailLicenseeClass> detailLicenseeClasses) {
        this.detailLicenseeClasses = detailLicenseeClasses;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getFundPoolType() {
        return fundPoolType;
    }

    public void setFundPoolType(String fundPoolType) {
        this.fundPoolType = fundPoolType;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(fundPoolNames)
            && CollectionUtils.isEmpty(periods)
            && CollectionUtils.isEmpty(aggregateLicenseeClasses)
            && CollectionUtils.isEmpty(detailLicenseeClasses)
            && StringUtils.isBlank(licenseType)
            && StringUtils.isBlank(fundPoolType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclFundPoolDetailFilter that = (AclFundPoolDetailFilter) obj;
        return new EqualsBuilder()
            .append(fundPoolNames, that.fundPoolNames)
            .append(periods, that.periods)
            .append(aggregateLicenseeClasses, that.aggregateLicenseeClasses)
            .append(detailLicenseeClasses, that.detailLicenseeClasses)
            .append(licenseType, that.licenseType)
            .append(fundPoolType, that.fundPoolType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(fundPoolNames)
            .append(periods)
            .append(aggregateLicenseeClasses)
            .append(detailLicenseeClasses)
            .append(licenseType)
            .append(fundPoolType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fundPoolNames", fundPoolNames)
            .append("periods", periods)
            .append("aggregateLicenseeClasses", aggregateLicenseeClasses)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .append("licenseType", licenseType)
            .append("fundPoolType", fundPoolType)
            .toString();
    }
}
