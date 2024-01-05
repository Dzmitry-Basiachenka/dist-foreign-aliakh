package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FdaConstants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents summary market report record.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/18
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportDto extends StoredEntity<String> {

    private static final long serialVersionUID = -740745273841610218L;

    private String batchName;
    private String rroName;
    private Long rroAccountNumber;
    private LocalDate paymentDate;
    private String market;
    private BigDecimal marketTotalAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal totalPaymentAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getRroName() {
        return rroName;
    }

    public void setRroName(String rroName) {
        this.rroName = rroName;
    }

    public Long getRroAccountNumber() {
        return rroAccountNumber;
    }

    public void setRroAccountNumber(Long rroAccountNumber) {
        this.rroAccountNumber = rroAccountNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public BigDecimal getMarketTotalAmount() {
        return marketTotalAmount;
    }

    public void setMarketTotalAmount(BigDecimal marketTotalAmount) {
        this.marketTotalAmount = marketTotalAmount;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SummaryMarketReportDto that = (SummaryMarketReportDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(rroName, that.rroName)
            .append(rroAccountNumber, that.rroAccountNumber)
            .append(batchName, that.batchName)
            .append(paymentDate, that.paymentDate)
            .append(market, that.market)
            .append(marketTotalAmount, that.marketTotalAmount)
            .append(totalPaymentAmount, that.totalPaymentAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(rroName)
            .append(rroAccountNumber)
            .append(batchName)
            .append(paymentDate)
            .append(market)
            .append(marketTotalAmount)
            .append(totalPaymentAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("rroName", rroName)
            .append("rroAccountNumber", rroAccountNumber)
            .append("batchName", batchName)
            .append("paymentDate", paymentDate)
            .append("market", market)
            .append("marketTotalAmount", marketTotalAmount)
            .append("totalPaymentAmount", totalPaymentAmount)
            .toString();
    }
}
