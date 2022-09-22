package com.copyright.rup.dist.foreign.integration.oracle.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Represents model class to create request into Oracle AP.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/17/20
 *
 * @author Stanislau Rudak
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OracleRhTaxInformationRequest {

    @JsonProperty("payeeAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payeeAccountNumber;

    @JsonProperty("tboAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tboAccountNumber;

    /**
     * Constructor.
     *
     * @param payeeAccountNumber payee account number
     * @param tboAccountNumber   TBO account number
     */
    public OracleRhTaxInformationRequest(Long payeeAccountNumber, Long tboAccountNumber) {
        this.payeeAccountNumber = Objects.requireNonNull(payeeAccountNumber);
        this.tboAccountNumber = Objects.requireNonNull(tboAccountNumber);
    }

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public Long getTboAccountNumber() {
        return tboAccountNumber;
    }

    public void setTboAccountNumber(Long tboAccountNumber) {
        this.tboAccountNumber = tboAccountNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        OracleRhTaxInformationRequest that = (OracleRhTaxInformationRequest) obj;
        return new EqualsBuilder()
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.tboAccountNumber, that.tboAccountNumber)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(payeeAccountNumber)
            .append(tboAccountNumber)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("tboAccountNumber", tboAccountNumber)
            .toString();
    }
}
