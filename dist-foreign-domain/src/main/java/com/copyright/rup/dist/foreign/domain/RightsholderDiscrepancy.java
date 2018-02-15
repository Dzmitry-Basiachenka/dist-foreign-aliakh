package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    private String productFamily;
    private Rightsholder oldRightsholder;
    private Rightsholder newRightsholder;

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

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
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
            .append(this.productFamily, that.productFamily)
            .append(this.oldRightsholder, that.oldRightsholder)
            .append(this.newRightsholder, that.newRightsholder)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(wrWrkInst)
            .append(workTitle)
            .append(productFamily)
            .append(oldRightsholder)
            .append(newRightsholder)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("productFamily", productFamily)
            .append("oldRightsholder", oldRightsholder)
            .append("newRightsholder", newRightsholder)
            .toString();
    }
}
