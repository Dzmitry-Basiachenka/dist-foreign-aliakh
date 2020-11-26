package com.copyright.rup.dist.foreign.domain.report;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents SAL licensee.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 11/26/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalLicensee {

    private Long accountNumber;
    private String name;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

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
        SalLicensee that = (SalLicensee) obj;
        return new EqualsBuilder()
            .append(accountNumber, that.accountNumber)
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(accountNumber)
            .append(name)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("accountNumber", accountNumber)
            .append("name", name)
            .toString();
    }
}
