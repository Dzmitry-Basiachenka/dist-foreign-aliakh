package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents ACL Liability Details Report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/07/2022
 *
 * @author Anton Azarenka
 */
public class AclLiabilityDetailsReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = -8193675808782856329L;

    private long rhAccountNumber;
    private String rhName;
    private String title;
    private String wrWrkInst;
    private String scenarioName;
    private String licenseType;
    private String typeOfUse;
    private Integer aggregateLicenseeClassId;
    private String aggregateLicenseeClassName;
    private BigDecimal grossAmount;
    private BigDecimal netAmount;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(String wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
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

    public Integer getAggregateLicenseeClassId() {
        return aggregateLicenseeClassId;
    }

    public void setAggregateLicenseeClassId(Integer aggregateLicenseeClassId) {
        this.aggregateLicenseeClassId = aggregateLicenseeClassId;
    }

    public String getAggregateLicenseeClassName() {
        return aggregateLicenseeClassName;
    }

    public void setAggregateLicenseeClassName(String aggregateLicenseeClassName) {
        this.aggregateLicenseeClassName = aggregateLicenseeClassName;
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
        AclLiabilityDetailsReportDto that = (AclLiabilityDetailsReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rhAccountNumber, that.rhAccountNumber)
            .append(rhName, that.rhName)
            .append(title, that.title)
            .append(wrWrkInst, that.wrWrkInst)
            .append(scenarioName, that.scenarioName)
            .append(licenseType, that.licenseType)
            .append(typeOfUse, that.typeOfUse)
            .append(aggregateLicenseeClassId, that.aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName, that.aggregateLicenseeClassName)
            .append(grossAmount, that.grossAmount)
            .append(netAmount, that.netAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rhAccountNumber)
            .append(rhName)
            .append(title)
            .append(wrWrkInst)
            .append(scenarioName)
            .append(licenseType)
            .append(typeOfUse)
            .append(aggregateLicenseeClassId)
            .append(aggregateLicenseeClassName)
            .append(grossAmount)
            .append(netAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("rhAccountNumber", rhAccountNumber)
            .append("rhName", rhName)
            .append("title", title)
            .append("wrWrkInst", wrWrkInst)
            .append("scenarioName", scenarioName)
            .append("licenseType", licenseType)
            .append("typeOfUse", typeOfUse)
            .append("aggregateLicenseeClassId", aggregateLicenseeClassId)
            .append("aggregateLicenseeClassName", aggregateLicenseeClassName)
            .append("grossAmount", grossAmount)
            .append("netAmount", netAmount)
            .toString();
    }
}
