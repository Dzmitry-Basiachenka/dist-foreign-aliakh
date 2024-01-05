package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents UDM Completed Assignments by Employee report record.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/10/22
 *
 * @author Ihar Suvorau
 */
public class UdmCompletedAssignmentsReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = -5972072264730368517L;

    private String userName;
    private int usagesCount;
    private int valuesCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUsagesCount() {
        return usagesCount;
    }

    public void setUsagesCount(int usagesCount) {
        this.usagesCount = usagesCount;
    }

    public int getValuesCount() {
        return valuesCount;
    }

    public void setValuesCount(int valuesCount) {
        this.valuesCount = valuesCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UdmCompletedAssignmentsReportDto that = (UdmCompletedAssignmentsReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(userName, that.userName)
            .append(usagesCount, that.usagesCount)
            .append(valuesCount, that.valuesCount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(userName)
            .append(usagesCount)
            .append(valuesCount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("userName", userName)
            .append("usagesCount", usagesCount)
            .append("valuesCount", valuesCount)
            .toString();
    }
}
