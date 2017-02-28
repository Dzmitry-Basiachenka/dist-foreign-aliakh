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
    private LocalDate paymentDate;
    private Integer fiscalYear;
    private BigDecimal grossAmount = BigDecimal.ZERO;

    /**
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return rro.
     */
    public Rightsholder getRro() {
        return rro;
    }

    /**
     * Sets rro.
     *
     * @param rro rro
     */
    public void setRro(Rightsholder rro) {
        this.rro = rro;
    }

    /**
     * @return payment date.
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets payment date.
     *
     * @param paymentDate payment date
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return fiscal year.
     */
    public Integer getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets fiscal year.
     *
     * @param fiscalYear fiscal year
     */
    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }
    /**
     * @return gross amount.
     */
    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets gross amount.
     *
     * @param grossAmount gross amount
     */
    public void setGrossAmount(BigDecimal grossAmount) {
        if (null != grossAmount) {
            this.grossAmount = grossAmount;
        }
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
            .append(this.paymentDate, that.paymentDate)
            .append(this.fiscalYear, that.fiscalYear)
            .append(this.grossAmount, that.grossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(rro)
            .append(paymentDate)
            .append(fiscalYear)
            .append(grossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("rro", rro)
            .append("paymentDate", paymentDate)
            .append("fiscalYear", fiscalYear)
            .append("grossAmount", grossAmount)
            .toString();
    }
}
