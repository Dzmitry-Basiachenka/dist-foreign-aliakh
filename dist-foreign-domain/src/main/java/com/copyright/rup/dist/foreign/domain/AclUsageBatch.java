package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Represents ACL usage batch.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageBatch extends StoredEntity<String> {

    private String name;
    private Integer distributionPeriod;
    private Set<Integer> periods;
    private Boolean editable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistributionPeriod() {
        return distributionPeriod;
    }

    public void setDistributionPeriod(Integer distributionPeriod) {
        this.distributionPeriod = distributionPeriod;
    }

    public Set<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Integer> periods) {
        this.periods = periods;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AclUsageBatch that = (AclUsageBatch) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(name, that.name)
            .append(distributionPeriod, that.distributionPeriod)
            .append(periods, that.periods)
            .append(editable, that.editable)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(distributionPeriod)
            .append(periods)
            .append(editable)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("distributionPeriod", distributionPeriod)
            .append("periods", periods)
            .append("editable", editable)
            .toString();
    }
}
