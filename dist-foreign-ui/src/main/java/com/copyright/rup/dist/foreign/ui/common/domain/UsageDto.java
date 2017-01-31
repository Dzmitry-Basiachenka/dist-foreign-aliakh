package com.copyright.rup.dist.foreign.ui.common.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.Usage;
import com.copyright.rup.dist.foreign.UsageBatch;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents usage with usage batch.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/18/17
 *
 * @author Aliaksei Pchelnikau
 */
public class UsageDto extends StoredEntity<String> {

    private Long wrWrkInst;
    private String workTitle;
    private Rightsholder rightsholder;
    private BigDecimal grossAmount;
    private Rightsholder rro;
    private LocalDate paymentDate;
    private boolean eligible;

    /**
     * Constructor.
     *
     * @param batch batch
     * @param usage usage
     */
    public UsageDto(UsageBatch batch, Usage usage) {
        setId(checkNotNull(usage).getId());
        wrWrkInst = usage.getWrWrkInst();
        workTitle = usage.getWorkTitle();
        rightsholder = usage.getRightsholder();
        eligible = null == rightsholder;
        grossAmount = usage.getGrossAmount();
        rro = checkNotNull(batch).getRro();
        paymentDate = batch.getPaymentDate();
    }

    /**
     * @return work identifier.
     */
    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    /**
     * Sets work identifier.
     *
     * @param wrWrkInst work identifier
     */
    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    /**
     * @return work title.
     */
    public String getWorkTitle() {
        return workTitle;
    }

    /**
     * Sets work title.
     *
     * @param workTitle work title
     */
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    /**
     * @return rights holder.
     */
    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    /**
     * Sets rights holder.
     *
     * @param rightsholder rights holder
     */
    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
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
     * @return eligible.
     */
    public boolean isEligible() {
        return eligible;
    }

    /**
     * Sets eligible.
     *
     * @param eligible eligible
     */
    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageDto that = (UsageDto) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.rightsholder, that.rightsholder)
            .append(this.grossAmount, that.grossAmount)
            .append(this.rro, that.rro)
            .append(this.paymentDate, that.paymentDate)
            .append(this.eligible, that.eligible)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(wrWrkInst)
            .append(workTitle)
            .append(rightsholder)
            .append(grossAmount)
            .append(rro)
            .append(paymentDate)
            .append(eligible)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("rightsholder", rightsholder)
            .append("grossAmount", grossAmount)
            .append("rro", rro)
            .append("paymentDate", paymentDate)
            .append("eligible", eligible)
            .toString();
    }
}
