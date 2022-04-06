package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

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
public class AclGrantDetailDto extends StoredEntity<String> {

    private String licenseType;
    private String typeOfUseStatus;
    private String grantStatus;
    private Boolean eligible;
    private Long wrWrkInst;
    private String systemTitle;
    private Long rhAccountNumber;
    private String rhName;
    private String typeOfUse;
    private Integer grantPeriod;
    private Boolean editable;
    private Boolean manualUploadFlag;

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTypeOfUseStatus() {
        return typeOfUseStatus;
    }

    public void setTypeOfUseStatus(String typeOfUseStatus) {
        this.typeOfUseStatus = typeOfUseStatus;
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
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

    public Boolean getManualUploadFlag() {
        return manualUploadFlag;
    }

    public void setManualUploadFlag(Boolean manualUploadFlag) {
        this.manualUploadFlag = manualUploadFlag;
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
            .append(typeOfUseStatus, that.typeOfUseStatus)
            .append(grantStatus, that.grantStatus)
            .append(eligible, that.eligible)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(typeOfUse, that.typeOfUse)
            .append(grantPeriod, that.grantPeriod)
            .append(editable, that.editable)
            .append(manualUploadFlag, that.manualUploadFlag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(licenseType)
            .append(typeOfUseStatus)
            .append(grantStatus)
            .append(eligible)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(rhAccountNumber)
            .append(rhName)
            .append(typeOfUse)
            .append(grantPeriod)
            .append(editable)
            .append(manualUploadFlag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("licenseType", licenseType)
            .append("typeOfUseStatus", typeOfUseStatus)
            .append("grantStatus", grantStatus)
            .append("eligible", eligible)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("typeOfUse", typeOfUse)
            .append("grantPeriod", grantPeriod)
            .append("editable", editable)
            .append("manualUploadFlag", manualUploadFlag)
            .toString();
    }
}
