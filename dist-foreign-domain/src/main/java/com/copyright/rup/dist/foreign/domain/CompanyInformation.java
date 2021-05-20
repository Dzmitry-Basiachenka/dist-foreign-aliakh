package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents company information domain.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/21
 *
 * @author Darya Baraukova
 */
public class CompanyInformation extends StoredEntity<Long> {

    private String name;
    private Integer detailedLicenseeClassId;

    /**
     * @return company name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets company name.
     *
     * @param name company name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return detailed licensee class id.
     */
    public Integer getDetailedLicenseeClassId() {
        return detailedLicenseeClassId;
    }

    /**
     * Sets detailed licensee class id.
     *
     * @param detailedLicenseeClassId detailed licensee class id to set
     */
    public void setDetailedLicenseeClassId(Integer detailedLicenseeClassId) {
        this.detailedLicenseeClassId = detailedLicenseeClassId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        final CompanyInformation that = (CompanyInformation) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(that))
            .append(this.name, that.name)
            .append(this.detailedLicenseeClassId, that.detailedLicenseeClassId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(detailedLicenseeClassId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("detailedLicenseeClassId", detailedLicenseeClassId)
            .toString();
    }
}
