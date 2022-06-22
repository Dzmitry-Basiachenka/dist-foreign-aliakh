package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ACL scenario.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Anton Azarenka
 */
public class AclScenario extends StoredEntity<String> {

    private String usageBatchId;
    private String fundPoolId;
    private String grantSetId;
    private String name;
    private String description;
    private ScenarioStatusEnum status;
    private boolean editableFlag;
    private Integer periodEndDate;
    private String licenseType;

    public String getUsageBatchId() {
        return usageBatchId;
    }

    public void setUsageBatchId(String usageBatchId) {
        this.usageBatchId = usageBatchId;
    }

    public String getFundPoolId() {
        return fundPoolId;
    }

    public void setFundPoolId(String fundPoolId) {
        this.fundPoolId = fundPoolId;
    }

    public String getGrantSetId() {
        return grantSetId;
    }

    public void setGrantSetId(String grantSetId) {
        this.grantSetId = grantSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScenarioStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ScenarioStatusEnum status) {
        this.status = status;
    }

    public boolean isEditableFlag() {
        return editableFlag;
    }

    public void setEditableFlag(boolean editableFlag) {
        this.editableFlag = editableFlag;
    }

    public Integer getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(Integer periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenario that = (AclScenario) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(editableFlag, that.editableFlag)
            .append(usageBatchId, that.usageBatchId)
            .append(fundPoolId, that.fundPoolId)
            .append(grantSetId, that.grantSetId)
            .append(name, that.name)
            .append(description, that.description)
            .append(status, that.status)
            .append(periodEndDate, that.periodEndDate)
            .append(licenseType, that.licenseType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(usageBatchId)
            .append(fundPoolId)
            .append(grantSetId)
            .append(name)
            .append(description)
            .append(status)
            .append(editableFlag)
            .append(periodEndDate)
            .append(licenseType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("usageBatchId", usageBatchId)
            .append("fundPoolId", fundPoolId)
            .append("grantSetId", grantSetId)
            .append("name", name)
            .append("description", description)
            .append("status", status)
            .append("editableFlag", editableFlag)
            .append("periodEndDate", periodEndDate)
            .append("licenseType", licenseType)
            .toString();
    }
}
