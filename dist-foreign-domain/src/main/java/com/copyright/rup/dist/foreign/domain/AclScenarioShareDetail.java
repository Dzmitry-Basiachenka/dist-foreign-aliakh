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

    private Long wrWrkInst;
    private Long rhAccountNumber;
    private Long payeeAccountNumber;
    private String typeOfUse;
    private BigDecimal volumeWeight;
    private BigDecimal valueWeight;
    private BigDecimal valueShare;
    private BigDecimal volumeShare;
    private BigDecimal detailShare;
    private BigDecimal netAmount;
    private BigDecimal serviceFeeAmount;
    private BigDecimal grossAmount;

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public Long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(Long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
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

    public BigDecimal getDetailShare() {
        return detailShare;
    }

    public void setDetailShare(BigDecimal detailShare) {
        this.detailShare = detailShare;
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
        AclScenarioShareDetail that = (AclScenarioShareDetail) obj;
        return new EqualsBuilder()
            .append(wrWrkInst, that.wrWrkInst)
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(payeeAccountNumber, that.payeeAccountNumber)
            .append(typeOfUse, that.typeOfUse)
            .append(volumeWeight, that.volumeWeight)
            .append(valueWeight, that.valueWeight)
            .append(valueShare, that.valueShare)
            .append(volumeShare, that.volumeShare)
            .append(detailShare, that.detailShare)
            .append(netAmount, that.netAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(grossAmount, that.grossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(wrWrkInst)
            .append(rhAccountNumber)
            .append(payeeAccountNumber)
            .append(typeOfUse)
            .append(volumeWeight)
            .append(valueWeight)
            .append(valueShare)
            .append(volumeShare)
            .append(detailShare)
            .append(netAmount)
            .append(serviceFeeAmount)
            .append(grossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("wrWrkInst", wrWrkInst)
            .append("rhAccountNumber", rhAccountNumber)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("typeOfUse", typeOfUse)
            .append("volumeWeight", volumeWeight)
            .append("valueWeight", valueWeight)
            .append("valueShare", valueShare)
            .append("volumeShare", volumeShare)
            .append("detailShare", detailShare)
            .append("netAmount", netAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("grossAmount", grossAmount)
            .toString();
    }
}
