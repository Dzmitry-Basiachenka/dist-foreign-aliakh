package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ACL grant detail.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantDetail extends StoredEntity<String> {

    private static final long serialVersionUID = 5601976659993664487L;

    private String grantSetId;
    private String grantStatus;
    private String typeOfUse;
    private String typeOfUseStatus;
    private Long wrWrkInst;
    private String systemTitle;
    private Long rhAccountNumber;
    private Boolean eligible;
    private Boolean manualUploadFlag;
    private Long payeeAccountNumber;

    public String getGrantSetId() {
        return grantSetId;
    }

    public void setGrantSetId(String grantSetId) {
        this.grantSetId = grantSetId;
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public String getTypeOfUseStatus() {
        return typeOfUseStatus;
    }

    public void setTypeOfUseStatus(String typeOfUseStatus) {
        this.typeOfUseStatus = typeOfUseStatus;
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

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public Boolean getManualUploadFlag() {
        return manualUploadFlag;
    }

    public void setManualUploadFlag(Boolean manualUploadFlag) {
        this.manualUploadFlag = manualUploadFlag;
    }

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclGrantDetail that = (AclGrantDetail) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(grantSetId, that.grantSetId)
            .append(grantStatus, that.grantStatus)
            .append(typeOfUse, that.typeOfUse)
            .append(typeOfUseStatus, that.typeOfUseStatus)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(eligible, that.eligible)
            .append(manualUploadFlag, that.manualUploadFlag)
            .append(payeeAccountNumber, that.payeeAccountNumber)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(grantSetId)
            .append(grantStatus)
            .append(typeOfUse)
            .append(typeOfUseStatus)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(rhAccountNumber)
            .append(eligible)
            .append(manualUploadFlag)
            .append(payeeAccountNumber)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("grantSetId", grantSetId)
            .append("grantStatus", grantStatus)
            .append("typeOfUse", typeOfUse)
            .append("typeOfUseStatus", typeOfUseStatus)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("rhAccountNumber", rhAccountNumber)
            .append("eligible", eligible)
            .append("manualUploadFlag", manualUploadFlag)
            .append("payeeAccountNumber", payeeAccountNumber)
            .toString();
    }
}
