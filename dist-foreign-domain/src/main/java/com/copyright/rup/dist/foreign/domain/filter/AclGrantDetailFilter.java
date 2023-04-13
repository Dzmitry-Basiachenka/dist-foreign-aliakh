package com.copyright.rup.dist.foreign.domain.filter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents a set of filtering criteria for ACL grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantDetailFilter {

    private Set<String> grantSetNames = new HashSet<>();
    private Set<String> licenseTypes = new HashSet<>();
    private Set<String> grantStatuses = new HashSet<>();
    private Set<String> typeOfUses = new HashSet<>();
    private Integer grantSetPeriod;
    private FilterExpression<Number> wrWrkInstExpression = new FilterExpression<>();
    private FilterExpression<Number> rhAccountNumberExpression = new FilterExpression<>();
    private FilterExpression<String> rhNameExpression = new FilterExpression<>();
    private FilterExpression<Boolean> eligibleExpression = new FilterExpression<>();
    private FilterExpression<Boolean> editableExpression = new FilterExpression<>();

    /**
     * Default constructor.
     */
    public AclGrantDetailFilter() {
    }

    /**
     * Constructs new filter based on existing.
     *
     * @param filter base filter
     */
    public AclGrantDetailFilter(AclGrantDetailFilter filter) {
        if (Objects.nonNull(filter)) {
            this.grantSetNames = filter.getGrantSetNames();
            this.licenseTypes = filter.getLicenseTypes();
            this.grantStatuses = filter.getGrantStatuses();
            this.typeOfUses = filter.getTypeOfUses();
            this.grantSetPeriod = filter.getGrantSetPeriod();
            this.wrWrkInstExpression = new FilterExpression<>(filter.getWrWrkInstExpression());
            this.rhAccountNumberExpression = new FilterExpression<>(filter.getRhAccountNumberExpression());
            this.rhNameExpression = new FilterExpression<>(filter.getRhNameExpression());
            this.eligibleExpression = new FilterExpression<>(filter.getEligibleExpression());
            this.editableExpression = new FilterExpression<>(filter.getEditableExpression());
        }
    }

    public Set<String> getGrantSetNames() {
        return grantSetNames;
    }

    public void setGrantSetNames(Set<String> grantSetNames) {
        this.grantSetNames = grantSetNames;
    }

    public Set<String> getLicenseTypes() {
        return licenseTypes;
    }

    public void setLicenseTypes(Set<String> licenseTypes) {
        this.licenseTypes = licenseTypes;
    }

    public Set<String> getGrantStatuses() {
        return grantStatuses;
    }

    public void setGrantStatuses(Set<String> grantStatuses) {
        this.grantStatuses = grantStatuses;
    }

    public Set<String> getTypeOfUses() {
        return typeOfUses;
    }

    public void setTypeOfUses(Set<String> typeOfUses) {
        this.typeOfUses = typeOfUses;
    }

    public Integer getGrantSetPeriod() {
        return grantSetPeriod;
    }

    public void setGrantSetPeriod(Integer grantSetPeriod) {
        this.grantSetPeriod = grantSetPeriod;
    }

    public FilterExpression<Number> getWrWrkInstExpression() {
        return wrWrkInstExpression;
    }

    public void setWrWrkInstExpression(FilterExpression<Number> wrWrkInstExpression) {
        this.wrWrkInstExpression = wrWrkInstExpression;
    }

    public FilterExpression<Number> getRhAccountNumberExpression() {
        return rhAccountNumberExpression;
    }

    public void setRhAccountNumberExpression(FilterExpression<Number> rhAccountNumberExpression) {
        this.rhAccountNumberExpression = rhAccountNumberExpression;
    }

    public FilterExpression<String> getRhNameExpression() {
        return rhNameExpression;
    }

    public void setRhNameExpression(FilterExpression<String> rhNameExpression) {
        this.rhNameExpression = rhNameExpression;
    }

    public FilterExpression<Boolean> getEligibleExpression() {
        return eligibleExpression;
    }

    public void setEligibleExpression(FilterExpression<Boolean> eligibleExpression) {
        this.eligibleExpression = eligibleExpression;
    }

    public FilterExpression<Boolean> getEditableExpression() {
        return editableExpression;
    }

    public void setEditableExpression(FilterExpression<Boolean> editableExpression) {
        this.editableExpression = editableExpression;
    }

    /**
     * @return {@code true} if filter does not contain any criteria, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(grantSetNames)
            && CollectionUtils.isEmpty(licenseTypes)
            && CollectionUtils.isEmpty(grantStatuses)
            && CollectionUtils.isEmpty(typeOfUses)
            && null == grantSetPeriod
            && wrWrkInstExpression.isEmpty()
            && rhAccountNumberExpression.isEmpty()
            && rhNameExpression.isEmpty()
            && eligibleExpression.isEmpty()
            && editableExpression.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclGrantDetailFilter that = (AclGrantDetailFilter) obj;
        return new EqualsBuilder()
            .append(grantSetNames, that.grantSetNames)
            .append(licenseTypes, that.licenseTypes)
            .append(grantStatuses, that.grantStatuses)
            .append(typeOfUses, that.typeOfUses)
            .append(grantSetPeriod, that.grantSetPeriod)
            .append(wrWrkInstExpression, that.wrWrkInstExpression)
            .append(rhAccountNumberExpression, that.rhAccountNumberExpression)
            .append(rhNameExpression, that.rhNameExpression)
            .append(eligibleExpression, that.eligibleExpression)
            .append(editableExpression, that.editableExpression)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(grantSetNames)
            .append(licenseTypes)
            .append(grantStatuses)
            .append(typeOfUses)
            .append(grantSetPeriod)
            .append(wrWrkInstExpression)
            .append(rhAccountNumberExpression)
            .append(rhNameExpression)
            .append(eligibleExpression)
            .append(editableExpression)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("grantSetNames", grantSetNames)
            .append("licenseTypes", licenseTypes)
            .append("grantStatuses", grantStatuses)
            .append("typeOfUses", typeOfUses)
            .append("grantSetPeriod", grantSetPeriod)
            .append("wrWrkInstExpression", wrWrkInstExpression)
            .append("rhAccountNumberExpression", rhAccountNumberExpression)
            .append("rhNameExpression", rhNameExpression)
            .append("eligibleExpression", eligibleExpression)
            .append("editableExpression", editableExpression)
            .toString();
    }
}
