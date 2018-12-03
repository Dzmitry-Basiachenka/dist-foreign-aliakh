package com.copyright.rup.dist.foreign.integration.oracle.impl.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents model class to create request for rightsholder tax information into Oracle AP.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OracleRhTaxInformationRequest {

    @JsonProperty(value = "payeeAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private final long payeeAccountNumber;

    @JsonProperty(value = "tboAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private final long tboAccountNumber;

    /**
     * Constructor.
     *
     * @param accountNumber rightsholder account number
     */
    public OracleRhTaxInformationRequest(long accountNumber) {
        this.payeeAccountNumber = accountNumber;
        this.tboAccountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("tboAccountNumber", tboAccountNumber)
            .toString();
    }

    protected long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    protected long getTboAccountNumber() {
        return tboAccountNumber;
    }
}
