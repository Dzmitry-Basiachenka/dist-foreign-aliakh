package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FdaConstants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents NTS pre-service fee fund report record.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/01/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = 7570799718926833051L;

    private String batchName;
    private LocalDate paymentDate;
    private Integer numberOfDetails;
    private BigDecimal grossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getNumberOfDetails() {
        return numberOfDetails;
    }

    public void setNumberOfDetails(Integer numberOfDetails) {
        this.numberOfDetails = numberOfDetails;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        NtsPreServiceFeeFundReportDto that = (NtsPreServiceFeeFundReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(batchName, that.batchName)
            .append(paymentDate, that.paymentDate)
            .append(numberOfDetails, that.numberOfDetails)
            .append(grossAmount, that.grossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(batchName)
            .append(paymentDate)
            .append(numberOfDetails)
            .append(grossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("batchName", batchName)
            .append("paymentDate", paymentDate)
            .append("numberOfDetails", numberOfDetails)
            .append("grossAmount", grossAmount)
            .toString();
    }
}
