package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ACL ineligible rightsholder.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Anton Azarenka
 */
public class AclIneligibleRightsholder {

    private String organizationId;
    private Long rhAccountNumber;
    private String licenseType;
    private String typeOfUse;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclIneligibleRightsholder rightsholder = (AclIneligibleRightsholder) obj;
        return new EqualsBuilder()
            .append(organizationId, rightsholder.organizationId)
            .append(rhAccountNumber, rightsholder.rhAccountNumber)
            .append(licenseType, rightsholder.licenseType)
            .append(typeOfUse, rightsholder.typeOfUse)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(organizationId)
            .append(rhAccountNumber)
            .append(licenseType)
            .append(typeOfUse)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("organizationId", organizationId)
            .append("rhAccountNumber", rhAccountNumber)
            .append("licenseType", licenseType)
            .append("typeOfUse", typeOfUse)
            .toString();
    }
}
