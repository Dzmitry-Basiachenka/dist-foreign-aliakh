package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents specific data for ACL scenario detail.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/01/2022
 *
 * @author Anton Azarenka
 */
public class AclScenarioShareDetail {

    private Long rhAccountNumber;
    private String typeOfUse;
    private BigDecimal volumeWeight;
    private BigDecimal valueWeight;
    private BigDecimal valueShare;
    private BigDecimal volumeShare;
    private BigDecimal volumeWeightDenominator;
    private BigDecimal valueWeightDenominator;
    private BigDecimal netAmount = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeAmount = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal totalAmount = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    public BigDecimal getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(BigDecimal volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public BigDecimal getValueWeight() {
        return valueWeight;
    }

    public void setValueWeight(BigDecimal valueWeight) {
        this.valueWeight = valueWeight;
    }

    public BigDecimal getValueShare() {
        return valueShare;
    }

    public void setValueShare(BigDecimal valueShare) {
        this.valueShare = valueShare;
    }

    public BigDecimal getVolumeShare() {
        return volumeShare;
    }

    public void setVolumeShare(BigDecimal volumeShare) {
        this.volumeShare = volumeShare;
    }

    public BigDecimal getVolumeWeightDenominator() {
        return volumeWeightDenominator;
    }

    public void setVolumeWeightDenominator(BigDecimal volumeWeightDenominator) {
        this.volumeWeightDenominator = volumeWeightDenominator;
    }

    public BigDecimal getValueWeightDenominator() {
        return valueWeightDenominator;
    }

    public void setValueWeightDenominator(BigDecimal valueWeightDenominator) {
        this.valueWeightDenominator = valueWeightDenominator;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenarioShareDetail that = (AclScenarioShareDetail) obj;
        return new EqualsBuilder()
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(typeOfUse, that.typeOfUse)
            .append(volumeWeight, that.volumeWeight)
            .append(valueWeight, that.valueWeight)
            .append(valueShare, that.valueShare)
            .append(volumeShare, that.volumeShare)
            .append(volumeWeightDenominator, that.volumeWeightDenominator)
            .append(valueWeightDenominator, that.valueWeightDenominator)
            .append(netAmount, that.netAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(totalAmount, that.totalAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rhAccountNumber)
            .append(typeOfUse)
            .append(volumeWeight)
            .append(valueWeight)
            .append(valueShare)
            .append(volumeShare)
            .append(volumeWeightDenominator)
            .append(valueWeightDenominator)
            .append(netAmount)
            .append(serviceFeeAmount)
            .append(totalAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("rhAccountNumber", rhAccountNumber)
            .append("typeOfUse", typeOfUse)
            .append("volumeWeight", volumeWeight)
            .append("valueWeight", valueWeight)
            .append("valueShare", valueShare)
            .append("volumeShare", volumeShare)
            .append("volumeWeightDenominator", volumeWeightDenominator)
            .append("valueWeightDenominator", valueWeightDenominator)
            .append("netAmount", netAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("totalAmount", totalAmount)
            .toString();
    }
}
