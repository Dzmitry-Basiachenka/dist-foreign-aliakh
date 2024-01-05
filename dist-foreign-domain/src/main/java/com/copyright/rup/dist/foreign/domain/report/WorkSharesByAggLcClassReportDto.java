package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents Work Shares by Aggregate Licensee Class Summary Report record.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/20
 *
 * @author Uladzislau Shalamitski
 */
public class WorkSharesByAggLcClassReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = -3946545660142989502L;

    private AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
    private Long wrWrkInst;
    private String workTitle;
    private Rightsholder rightsholder;
    private BigDecimal volumeShare;
    private BigDecimal valueShare;
    private BigDecimal totalShare;
    private BigDecimal netAmount;

    public AggregateLicenseeClass getAggregateLicenseeClass() {
        return aggregateLicenseeClass;
    }

    public void setAggregateLicenseeClass(AggregateLicenseeClass aggregateLicenseeClass) {
        this.aggregateLicenseeClass = aggregateLicenseeClass;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
    }

    public BigDecimal getVolumeShare() {
        return volumeShare;
    }

    public void setVolumeShare(BigDecimal volumeShare) {
        this.volumeShare = volumeShare;
    }

    public BigDecimal getValueShare() {
        return valueShare;
    }

    public void setValueShare(BigDecimal valueShare) {
        this.valueShare = valueShare;
    }

    public BigDecimal getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(BigDecimal totalShare) {
        this.totalShare = totalShare;
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
        WorkSharesByAggLcClassReportDto that =
            (WorkSharesByAggLcClassReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(aggregateLicenseeClass, that.aggregateLicenseeClass)
            .append(wrWrkInst, that.wrWrkInst)
            .append(workTitle, that.workTitle)
            .append(rightsholder, that.rightsholder)
            .append(volumeShare, that.volumeShare)
            .append(valueShare, that.valueShare)
            .append(totalShare, that.totalShare)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(aggregateLicenseeClass)
            .append(totalShare)
            .append(wrWrkInst)
            .append(workTitle)
            .append(rightsholder)
            .append(volumeShare)
            .append(valueShare)
            .append(totalShare)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("aggregateLicenseeClass", aggregateLicenseeClass)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("rightsholder", rightsholder)
            .append("volumeShare", volumeShare)
            .append("valueShare", valueShare)
            .append("totalShare", totalShare)
            .append("netAmount", netAmount)
            .toString();
    }
}
