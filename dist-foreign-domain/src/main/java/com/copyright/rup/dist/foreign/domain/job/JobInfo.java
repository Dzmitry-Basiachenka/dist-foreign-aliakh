package com.copyright.rup.dist.foreign.domain.job;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.OffsetDateTime;

/**
 * Class that contains information about executed job.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/04/2019
 *
 * @author Uladzislau Shalamitski
 */
public class JobInfo {

    private String name;
    private OffsetDateTime executionDatetime;
    private JobStatusEnum status;
    private String result;

    /**
     * Constructor.
     */
    public JobInfo() {
    }

    /**
     * Constructor.
     *
     * @param status job status
     * @param result job result
     */
    public JobInfo(JobStatusEnum status, String result) {
        this.status = status;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getExecutionDatetime() {
        return executionDatetime;
    }

    public void setExecutionDatetime(OffsetDateTime executionDatetime) {
        this.executionDatetime = executionDatetime;
    }

    public JobStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JobStatusEnum status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        JobInfo jobInfo = (JobInfo) obj;
        return new EqualsBuilder()
            .append(name, jobInfo.name)
            .append(executionDatetime, jobInfo.executionDatetime)
            .append(status, jobInfo.status)
            .append(result, jobInfo.result)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(name)
            .append(executionDatetime)
            .append(status)
            .append(result)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("executionDatetime", executionDatetime)
            .append("status", status)
            .append("result", result)
            .toString();
    }
}
