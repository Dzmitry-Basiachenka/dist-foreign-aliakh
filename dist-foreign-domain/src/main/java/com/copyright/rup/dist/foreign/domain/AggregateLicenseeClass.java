package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Domain object for aggregate licensee class.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 2/5/20
 *
 * @author Stanislau Rudak
 */
// TODO {srudak} check if we really can use Integer here
public class AggregateLicenseeClass extends StoredEntity<Integer> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AggregateLicenseeClass that = (AggregateLicenseeClass) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .toString();
    }
}
