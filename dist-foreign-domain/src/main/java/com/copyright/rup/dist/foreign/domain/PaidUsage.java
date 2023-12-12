package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.OffsetDateTime;

/**
 * Represents paid usage domain object.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/20/18
 *
 * @author Darya Baraukova
 */
public class PaidUsage extends Usage {

    private static final long serialVersionUID = 5651810208744743067L;

    private Long rroAccountNumber;
    private String checkNumber;
    private OffsetDateTime checkDate;
    private String cccEventId;
    private String distributionName;
    private OffsetDateTime distributionDate;
    private OffsetDateTime periodEndDate;
    private String lmDetailId;
    private Boolean splitParentFlag;
    private boolean postDistributionFlag;

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public OffsetDateTime getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(OffsetDateTime checkDate) {
        this.checkDate = checkDate;
    }

    public String getCccEventId() {
        return cccEventId;
    }

    public void setCccEventId(String cccEventId) {
        this.cccEventId = cccEventId;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public OffsetDateTime getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(OffsetDateTime distributionDate) {
        this.distributionDate = distributionDate;
    }

    public OffsetDateTime getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(OffsetDateTime periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getLmDetailId() {
        return lmDetailId;
    }

    public void setLmDetailId(String lmDetailId) {
        this.lmDetailId = lmDetailId;
    }

    public Boolean getSplitParentFlag() {
        return splitParentFlag;
    }

    public void setSplitParentFlag(Boolean splitParentFlag) {
        this.splitParentFlag = splitParentFlag;
    }

    public boolean isPostDistributionFlag() {
        return postDistributionFlag;
    }

    public void setPostDistributionFlag(boolean postDistributionFlag) {
        this.postDistributionFlag = postDistributionFlag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        PaidUsage that = (PaidUsage) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.rroAccountNumber, that.rroAccountNumber)
            .append(this.checkNumber, that.checkNumber)
            .append(this.checkDate, that.checkDate)
            .append(this.cccEventId, that.cccEventId)
            .append(this.distributionName, that.distributionName)
            .append(this.distributionDate, that.distributionDate)
            .append(this.periodEndDate, that.periodEndDate)
            .append(this.lmDetailId, that.lmDetailId)
            .append(this.splitParentFlag, that.splitParentFlag)
            .append(this.postDistributionFlag, that.postDistributionFlag)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rroAccountNumber)
            .append(checkNumber)
            .append(checkDate)
            .append(cccEventId)
            .append(distributionName)
            .append(distributionDate)
            .append(periodEndDate)
            .append(lmDetailId)
            .append(splitParentFlag)
            .append(postDistributionFlag)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("rroAccountNumber", rroAccountNumber)
            .append("checkNumber", checkNumber)
            .append("checkDate", checkDate)
            .append("cccEventId", cccEventId)
            .append("distributionName", distributionName)
            .append("distributionDate", distributionDate)
            .append("periodEndDate", periodEndDate)
            .append("lmDetailId", lmDetailId)
            .append("splitParentFlag", splitParentFlag)
            .append("postDistributionFlag", postDistributionFlag)
            .toString();
    }
}
