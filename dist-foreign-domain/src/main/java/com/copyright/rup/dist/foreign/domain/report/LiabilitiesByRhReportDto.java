package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents Liabilities by Rightsholder report record.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/15/20
 *
 * @author Uladzislau Shalamitski
 */
public class LiabilitiesByRhReportDto extends StoredEntity<String> {

    private long rhAccountNumber;
    private String rhName;
    private BigDecimal grossAmount;
    private BigDecimal serviceFeeAmount;
    private BigDecimal netAmount;
    private BigDecimal itemBankNetAmount;
    private BigDecimal usageDetailNetAmount;
    private int countOfPassages;

    public long getRhAccountNumber() {
        return rhAccountNumber;
    }

    public void setRhAccountNumber(long rhAccountNumber) {
        this.rhAccountNumber = rhAccountNumber;
    }

    public String getRhName() {
        return rhName;
    }

    public void setRhName(String rhName) {
        this.rhName = rhName;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getItemBankNetAmount() {
        return itemBankNetAmount;
    }

    public void setItemBankNetAmount(BigDecimal itemBankNetAmount) {
        this.itemBankNetAmount = itemBankNetAmount;
    }

    public BigDecimal getUsageDetailNetAmount() {
        return usageDetailNetAmount;
    }

    public void setUsageDetailNetAmount(BigDecimal usageDetailNetAmount) {
        this.usageDetailNetAmount = usageDetailNetAmount;
    }

    public int getCountOfPassages() {
        return countOfPassages;
    }

    public void setCountOfPassages(int countOfPassages) {
        this.countOfPassages = countOfPassages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        LiabilitiesByRhReportDto dto = (LiabilitiesByRhReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rhAccountNumber, dto.rhAccountNumber)
            .append(countOfPassages, dto.countOfPassages)
            .append(rhName, dto.rhName)
            .append(grossAmount, dto.grossAmount)
            .append(serviceFeeAmount, dto.serviceFeeAmount)
            .append(netAmount, dto.netAmount)
            .append(itemBankNetAmount, dto.itemBankNetAmount)
            .append(usageDetailNetAmount, dto.usageDetailNetAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rhAccountNumber)
            .append(rhName)
            .append(grossAmount)
            .append(serviceFeeAmount)
            .append(netAmount)
            .append(itemBankNetAmount)
            .append(usageDetailNetAmount)
            .append(countOfPassages)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("grossAmount", grossAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("netAmount", netAmount)
            .append("itemBankNetAmount", itemBankNetAmount)
            .append("usageDetailNetAmount", usageDetailNetAmount)
            .append("countOfPassages", countOfPassages)
            .toString();
    }
}
