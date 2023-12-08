package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Domain object to represent LDMT detail received from Oracle.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetail extends StoredEntity<String> {

    private static final long serialVersionUID = 2064530055298811103L;

    private Integer detailLicenseeClassId;
    private String licenseType;
    private String typeOfUse;
    private BigDecimal grossAmount;
    private BigDecimal netAmount;

    public Integer getDetailLicenseeClassId() {
        return detailLicenseeClassId;
    }

    public void setDetailLicenseeClassId(Integer detailLicenseeClassId) {
        this.detailLicenseeClassId = detailLicenseeClassId;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        LdmtDetail that = (LdmtDetail) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(detailLicenseeClassId, that.detailLicenseeClassId)
            .append(licenseType, that.licenseType)
            .append(typeOfUse, that.typeOfUse)
            .append(grossAmount, that.grossAmount)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(detailLicenseeClassId)
            .append(licenseType)
            .append(typeOfUse)
            .append(grossAmount)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("detailLicenseeClassId", detailLicenseeClassId)
            .append("licenseType", licenseType)
            .append("typeOfUse", typeOfUse)
            .append("grossAmount", grossAmount)
            .append("netAmount", netAmount)
            .toString();
    }
}
