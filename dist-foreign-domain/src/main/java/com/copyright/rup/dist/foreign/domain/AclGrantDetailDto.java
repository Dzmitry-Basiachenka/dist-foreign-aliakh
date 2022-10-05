package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ACL grant detail dto.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailDto extends AclGrantDetail {

    private String licenseType;
    private String rhName;
    private Integer grantPeriod;
    private Boolean editable;
    private String grantSetName;

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public Integer getGrantPeriod() {
        return grantPeriod;
    }

    public void setGrantPeriod(Integer grantPeriod) {
        this.grantPeriod = grantPeriod;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getGrantSetName() {
        return grantSetName;
    }

    public void setGrantSetName(String grantSetName) {
        this.grantSetName = grantSetName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclGrantDetailDto that = (AclGrantDetailDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(licenseType, that.licenseType)
            .append(rhName, that.rhName)
            .append(grantPeriod, that.grantPeriod)
            .append(editable, that.editable)
            .append(grantSetName, that.grantSetName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(licenseType)
            .append(rhName)
            .append(grantPeriod)
            .append(editable)
            .append(grantSetName)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("licenseType", licenseType)
            .append("rhName", rhName)
            .append("grantPeriod", grantPeriod)
            .append("editable", editable)
            .append("grantSetName", grantSetName)
            .toString();
    }
}
