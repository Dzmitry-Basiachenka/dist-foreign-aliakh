package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL rightsholder totals holder DTO.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclRightsholderTotalsHolderDto extends AclRightsholderTotalsHolder {

    private Long wrWrkInst;
    private String systemTitle;
    private BigDecimal totalGross = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal totalNet = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);

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

    public BigDecimal getTotalGross() {
        return totalGross;
    }

    public void setTotalGross(BigDecimal totalGross) {
        this.totalGross = totalGross;
    }

    public BigDecimal getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(BigDecimal totalNet) {
        this.totalNet = totalNet;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclRightsholderTotalsHolderDto that = (AclRightsholderTotalsHolderDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(wrWrkInst, that.wrWrkInst)
            .append(systemTitle, that.systemTitle)
            .append(totalGross, that.totalGross)
            .append(totalNet, that.totalNet)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(wrWrkInst)
            .append(systemTitle)
            .append(totalGross)
            .append(totalNet)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("wrWrkInst", wrWrkInst)
            .append("systemTitle", systemTitle)
            .append("totalGross", totalGross)
            .append("totalNet", totalNet)
            .toString();
    }
}
