package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Contains information about rightsholder getting from FDA and RMS.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/24/18
 *
 * @author Ihar Suvorau
 */
public class RightsholderDiscrepancy {

    private Long wrWrkInst;
    private String workTitle;
    private Rightsholder oldRightsholder;
    private Rightsholder newRightsholder;
    private BigDecimal oldServiceFee;
    private BigDecimal newServiceFee;

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public Rightsholder getOldRightsholder() {
        return oldRightsholder;
    }

    public void setOldRightsholder(Rightsholder oldRightsholder) {
        this.oldRightsholder = oldRightsholder;
    }

    public Rightsholder getNewRightsholder() {
        return newRightsholder;
    }

    public void setNewRightsholder(Rightsholder newRightsholder) {
        this.newRightsholder = newRightsholder;
    }

    public BigDecimal getOldServiceFee() {
        return oldServiceFee;
    }

    public void setOldServiceFee(BigDecimal oldServiceFee) {
        this.oldServiceFee = oldServiceFee;
    }

    public BigDecimal getNewServiceFee() {
        return newServiceFee;
    }

    public void setNewServiceFee(BigDecimal newServiceFee) {
        this.newServiceFee = newServiceFee;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (null == object || this.getClass() != object.getClass()) {
            return false;
        }
        RightsholderDiscrepancy that = (RightsholderDiscrepancy) object;
        return new EqualsBuilder()
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.oldRightsholder, that.oldRightsholder)
            .append(this.newRightsholder, that.newRightsholder)
            .append(this.oldServiceFee, that.oldServiceFee)
            .append(this.newServiceFee, that.newServiceFee)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(wrWrkInst)
            .append(workTitle)
            .append(oldRightsholder)
            .append(newRightsholder)
            .append(oldServiceFee)
            .append(newServiceFee)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("oldRightsholder", oldRightsholder)
            .append("newRightsholder", newRightsholder)
            .append("oldServiceFee", oldServiceFee)
            .append("newServiceFee", newServiceFee)
            .toString();
    }
}
