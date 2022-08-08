package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

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
    private String copiedFrom;
    private List<AclPublicationType> publicationTypes = new ArrayList<>();
    private List<UsageAge> usageAges = new ArrayList<>();
    private List<DetailLicenseeClass> detailLicenseeClasses = new ArrayList<>();

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

    public String getCopiedFrom() {
        return copiedFrom;
    }

    public void setCopiedFrom(String copiedFrom) {
        this.copiedFrom = copiedFrom;
    }

    public List<AclPublicationType> getPublicationTypes() {
        return publicationTypes;
    }

    public void setPublicationTypes(List<AclPublicationType> publicationTypes) {
        this.publicationTypes = publicationTypes;
    }

    public List<UsageAge> getUsageAges() {
        return usageAges;
    }

    public void setUsageAges(List<UsageAge> usageAges) {
        this.usageAges = usageAges;
    }

    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        return detailLicenseeClasses;
    }

    public void setDetailLicenseeClasses(List<DetailLicenseeClass> detailLicenseeClasses) {
        this.detailLicenseeClasses = detailLicenseeClasses;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclScenario scenario = (AclScenario) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(editableFlag, scenario.editableFlag)
            .append(usageBatchId, scenario.usageBatchId)
            .append(fundPoolId, scenario.fundPoolId)
            .append(grantSetId, scenario.grantSetId)
            .append(name, scenario.name)
            .append(description, scenario.description)
            .append(status, scenario.status)
            .append(periodEndDate, scenario.periodEndDate)
            .append(licenseType, scenario.licenseType)
            .append(copiedFrom, scenario.copiedFrom)
            .append(publicationTypes, scenario.publicationTypes)
            .append(usageAges, scenario.usageAges)
            .append(detailLicenseeClasses, scenario.detailLicenseeClasses)
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
            .append(copiedFrom)
            .append(publicationTypes)
            .append(usageAges)
            .append(detailLicenseeClasses)
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
            .append("copiedFrom", copiedFrom)
            .append("publicationTypes", publicationTypes)
            .append("usageAges", usageAges)
            .append("detailLicenseeClasses", detailLicenseeClasses)
            .toString();
    }
}
