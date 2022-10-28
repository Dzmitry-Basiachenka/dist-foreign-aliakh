package com.copyright.rup.dist.foreign.domain.filter;

import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for ACL scenarios.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/28/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenarioFilter {

    private Set<Integer> periods = new HashSet<>();
    private Set<String> licenseTypes = new HashSet<>();
    private Boolean editable;
    private ScenarioStatusEnum status;

    /**
     * Default constructor.
     */
    public AclScenarioFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public AclScenarioFilter(AclScenarioFilter filter) {
        if (Objects.nonNull(filter)) {
            setPeriods(filter.getPeriods());
            setLicenseTypes(filter.getLicenseTypes());
            setEditable(filter.getEditable());
            setStatus(filter.getStatus());
        }
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Set<String> getLicenseTypes() {
        return licenseTypes;
    }

    public void setLicenseTypes(Set<String> licenseTypes) {
        this.licenseTypes = licenseTypes;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public ScenarioStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ScenarioStatusEnum status) {
        this.status = status;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(periods)
            && CollectionUtils.isEmpty(licenseTypes)
            && Objects.isNull(editable)
            && Objects.isNull(status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenarioFilter that = (AclScenarioFilter) obj;
        return new EqualsBuilder()
            .append(periods, that.periods)
            .append(licenseTypes, that.licenseTypes)
            .append(editable, that.editable)
            .append(status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(periods)
            .append(licenseTypes)
            .append(editable)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("periods", periods)
            .append("licenseTypes", licenseTypes)
            .append("editable", editable)
            .append("status", status)
            .toString();
    }
}
