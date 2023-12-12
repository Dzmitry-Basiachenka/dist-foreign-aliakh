package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

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
public class RightsholderDiscrepancy extends StoredEntity<String> {

    private static final long serialVersionUID = 3408324253266063408L;

    private Long wrWrkInst;
    private String workTitle;
    private String productFamily;
    private Rightsholder oldRightsholder = new Rightsholder();
    private Rightsholder newRightsholder = new Rightsholder();
    private RightsholderDiscrepancyStatusEnum status;

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

    public RightsholderDiscrepancyStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RightsholderDiscrepancyStatusEnum status) {
        this.status = status;
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
            .appendSuper(super.equals(object))
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.workTitle, that.workTitle)
            .append(this.productFamily, that.productFamily)
            .append(this.oldRightsholder, that.oldRightsholder)
            .append(this.newRightsholder, that.newRightsholder)
            .append(this.status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(wrWrkInst)
            .append(workTitle)
            .append(productFamily)
            .append(oldRightsholder)
            .append(newRightsholder)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("wrWrkInst", wrWrkInst)
            .append("workTitle", workTitle)
            .append("productFamily", productFamily)
            .append("oldRightsholder", oldRightsholder)
            .append("newRightsholder", newRightsholder)
            .append("status", status)
            .toString();
    }
}
