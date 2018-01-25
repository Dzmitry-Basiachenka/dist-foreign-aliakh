package com.copyright.rup.dist.foreign.integration.rms.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents result of rights assignment process.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/24/18
 *
 * @author Darya Baraukova
 */
public class RightsAssignmentResult {

    private RightsAssignmentResultStatusEnum status;
    private String jobId;

    /**
     * Constructor.
     *
     * @param status {@link RightsAssignmentResultStatusEnum} instance
     */
    public RightsAssignmentResult(RightsAssignmentResultStatusEnum status) {
        this.status = status;
    }

    public void setStatus(RightsAssignmentResultStatusEnum status) {
        this.status = status;
    }

    public RightsAssignmentResultStatusEnum getStatus() {
        return status;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    /**
     * Checks whether rights assignment process was successful or not.
     *
     * @return {@code true} - if status is {@link RightsAssignmentResultStatusEnum#SUCCESS} and job id is populated,
     * {@code false} - otherwise
     */
    public boolean isSuccessful() {
        return RightsAssignmentResultStatusEnum.SUCCESS == status && StringUtils.isNotBlank(jobId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        final RightsAssignmentResult that = (RightsAssignmentResult) obj;
        return new EqualsBuilder()
            .append(this.status, that.status)
            .append(this.jobId, that.jobId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(status)
            .append(jobId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("status", status)
            .append("jobId", jobId)
            .toString();
    }

    /**
     * Enum containing result status of RMS Rights Assignment call.
     */
    public enum RightsAssignmentResultStatusEnum {
        /**
         * Rights assignment process was successful.
         */
        SUCCESS,

        /**
         * Rights assignment process was failed. Invalid response from RMS application.
         */
        RA_ERROR
    }
}
