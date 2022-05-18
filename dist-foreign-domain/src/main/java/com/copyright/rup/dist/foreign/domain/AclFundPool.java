package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Domain object to represent ACL fund pool.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/19/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPool extends StoredEntity<String> {

    private String name;
    private Integer period;
    private String licenseType;
    private boolean manualUploadFlag;
    private BigDecimal totalAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    private BigDecimal netAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public boolean isManualUploadFlag() {
        return manualUploadFlag;
    }

    public void setManualUploadFlag(boolean manualUploadFlag) {
        this.manualUploadFlag = manualUploadFlag;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
        AclFundPool fundPool = (AclFundPool) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(manualUploadFlag, fundPool.manualUploadFlag)
            .append(name, fundPool.name)
            .append(period, fundPool.period)
            .append(licenseType, fundPool.licenseType)
            .append(totalAmount, fundPool.totalAmount)
            .append(netAmount, fundPool.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(period)
            .append(licenseType)
            .append(manualUploadFlag)
            .append(totalAmount)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("period", period)
            .append("licenseType", licenseType)
            .append("manualUploadFlag", manualUploadFlag)
            .append("totalAmount", totalAmount)
            .append("netAmount", netAmount)
            .toString();
    }
}
