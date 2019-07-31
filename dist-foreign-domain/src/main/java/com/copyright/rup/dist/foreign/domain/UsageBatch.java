package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents usage batch.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/13/17
 *
 * @author Aliaksei Pchelnikau
 */
public class UsageBatch extends StoredEntity<String> {

    private String name;
    private Rightsholder rro;
    private String productFamily;
    private LocalDate paymentDate;
    private Integer fiscalYear;
    private BigDecimal grossAmount;
    private FundPool fundPool;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rightsholder getRro() {
        return rro;
    }

    public void setRro(Rightsholder rro) {
        this.rro = rro;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets gross amount value.
     *
     * @param grossAmount gross amount
     */
    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public FundPool getFundPool() {
        return fundPool;
    }

    public void setFundPool(FundPool fundPool) {
        this.fundPool = fundPool;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageBatch that = (UsageBatch) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.name, that.name)
            .append(this.rro, that.rro)
            .append(this.productFamily, that.productFamily)
            .append(this.paymentDate, that.paymentDate)
            .append(this.fiscalYear, that.fiscalYear)
            .append(this.grossAmount, that.grossAmount)
            .append(this.fundPool, that.fundPool)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(rro)
            .append(productFamily)
            .append(paymentDate)
            .append(fiscalYear)
            .append(grossAmount)
            .append(fundPool)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("rro", rro)
            .append("productFamily", productFamily)
            .append("paymentDate", paymentDate)
            .append("fiscalYear", fiscalYear)
            .append("grossAmount", grossAmount)
            .append("fundPool", fundPool)
            .toString();
    }
}
