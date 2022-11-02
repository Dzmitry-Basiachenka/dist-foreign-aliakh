package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents detail about liability of ACL scenario.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/01/2022
 *
 * @author Anton Azarenka
 */
public class AclScenarioLiabilityDetail {

    private String liabilityDetailId;
    private String rightsholderId;
    private Long wrWrkInst;
    private String systemTitle;
    private String typeOfUse;
    private String licenseType;
    private String aggregateLicenseeClassName;
    private String productFamily;
    private BigDecimal netAmount;
    private BigDecimal serviceFeeAmount;
    private BigDecimal grossAmount;

    public String getLiabilityDetailId() {
        return liabilityDetailId;
    }

    public void setLiabilityDetailId(String liabilityDetailId) {
        this.liabilityDetailId = liabilityDetailId;
    }

    public String getRightsholderId() {
        return rightsholderId;
    }

    public void setRightsholderId(String rightsholderId) {
        this.rightsholderId = rightsholderId;
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

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getAggregateLicenseeClassName() {
        return aggregateLicenseeClassName;
    }

    public void setAggregateLicenseeClassName(String aggregateLicenseeClassName) {
        this.aggregateLicenseeClassName = aggregateLicenseeClassName;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenarioLiabilityDetail that = (AclScenarioLiabilityDetail) obj;
        return new EqualsBuilder()
            .append(liabilityDetailId, that.liabilityDetailId)
            .append(rightsholderId, that.rightsholderId)
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(typeOfUse, that.typeOfUse)
            .append(licenseType, that.licenseType)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(productFamily, that.productFamily)
            .append(netAmount, that.netAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(grossAmount, that.grossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(liabilityDetailId)
            .append(rightsholderId)
            .append(wrWrkInst)
            .append(systemTitle)
            .append(typeOfUse)
            .append(licenseType)
            .append(aggregateLicenseeClassName)
            .append(productFamily)
            .append(netAmount)
            .append(serviceFeeAmount)
            .append(grossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("liabilityDetailId", liabilityDetailId)
            .append("rightsholderId", rightsholderId)
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("typeOfUse", typeOfUse)
            .append("licenseType", licenseType)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("productFamily", productFamily)
            .append("netAmount", netAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("grossAmount", grossAmount)
            .toString();
    }
}
