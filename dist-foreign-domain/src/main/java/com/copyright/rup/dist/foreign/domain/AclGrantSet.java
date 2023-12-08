package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Represents ACL grant set.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantSet extends StoredEntity<String> {

    private static final long serialVersionUID = -8512619663755375033L;

    private String name;
    private Integer grantPeriod;
    private Set<Integer> periods;
    private String licenseType;
    private Boolean editable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrantPeriod() {
        return grantPeriod;
    }

    public void setGrantPeriod(Integer grantPeriod) {
        this.grantPeriod = grantPeriod;
    }

    public Set<Integer> getPeriods() {
        return Set.copyOf(periods);
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = Set.copyOf(periods);
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclGrantSet that = (AclGrantSet) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(name, that.name)
            .append(grantPeriod, that.grantPeriod)
            .append(periods, that.periods)
            .append(licenseType, that.licenseType)
            .append(editable, that.editable)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(grantPeriod)
            .append(periods)
            .append(licenseType)
            .append(editable)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("grantPeriod", grantPeriod)
            .append("periods", periods)
            .append("licenseType", licenseType)
            .append("editable", editable)
            .toString();
    }
}
