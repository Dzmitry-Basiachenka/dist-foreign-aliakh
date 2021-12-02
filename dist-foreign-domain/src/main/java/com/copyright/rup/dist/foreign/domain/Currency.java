package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents currency domain.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/30/21
 *
 * @author Anton Azarenka
 */
public class Currency {

    private String code;
    private String description;

    /**
     * Constructor.
     *
     * @param code        code
     * @param description description
     */
    public Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCodeAndDescription() {
        return String.format("%s - %s", getCode(), getDescription());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        Currency currency = (Currency) obj;
        return new EqualsBuilder()
            .append(code, currency.code)
            .append(description, currency.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(code)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("code", code)
            .append("description", description)
            .toString();
    }
}
