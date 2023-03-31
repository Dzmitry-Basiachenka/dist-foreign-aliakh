package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Domain object to represent ACL fund pool detail.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/19/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolDetail extends StoredEntity<String> {

    private String fundPoolId;
    private DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
    private String licenseType;
    private String typeOfUse;
    private BigDecimal grossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal netAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private boolean ldmtFlag;

    public String getFundPoolId() {
        return fundPoolId;
    }

    public void setFundPoolId(String fundPoolId) {
        this.fundPoolId = fundPoolId;
    }

    public DetailLicenseeClass getDetailLicenseeClass() {
        return detailLicenseeClass;
    }

    public void setDetailLicenseeClass(DetailLicenseeClass detailLicenseeClass) {
        this.detailLicenseeClass = detailLicenseeClass;
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

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public boolean isLdmtFlag() {
        return ldmtFlag;
    }

    public void setLdmtFlag(boolean ldmtFlag) {
        this.ldmtFlag = ldmtFlag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclFundPoolDetail that = (AclFundPoolDetail) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(fundPoolId, that.fundPoolId)
            .append(detailLicenseeClass, that.detailLicenseeClass)
            .append(licenseType, that.licenseType)
            .append(typeOfUse, that.typeOfUse)
            .append(grossAmount, that.grossAmount)
            .append(netAmount, that.netAmount)
            .append(ldmtFlag, that.ldmtFlag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(fundPoolId)
            .append(detailLicenseeClass)
            .append(licenseType)
            .append(typeOfUse)
            .append(grossAmount)
            .append(netAmount)
            .append(ldmtFlag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("fundPoolId", fundPoolId)
            .append("detailLicenseeClass", detailLicenseeClass)
            .append("licenseType", licenseType)
            .append("typeOfUse", typeOfUse)
            .append("grossAmount", grossAmount)
            .append("netAmount", netAmount)
            .append("ldmtFlag", ldmtFlag)
            .toString();
    }
}
