package com.copyright.rup.dist.foreign.integration.crm.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Result of reading rights distribution from the CRM endpoint "/getCCCRightsDistributionV2".
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 08/14/2019
 *
 * @author Aliaksandr Liakh
 */
public class CrmRightsDistributionResponse {

    private String cccEventId;
    private String omOrderDetailNumber;

    public String getCccEventId() {
        return cccEventId;
    }

    public void setCccEventId(String cccEventId) {
        this.cccEventId = cccEventId;
    }

    public String getOmOrderDetailNumber() {
        return omOrderDetailNumber;
    }

    public void setOmOrderDetailNumber(String omOrderDetailNumber) {
        this.omOrderDetailNumber = omOrderDetailNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        CrmRightsDistributionResponse that = (CrmRightsDistributionResponse) obj;
        return new EqualsBuilder()
            .append(cccEventId, that.cccEventId)
            .append(omOrderDetailNumber, that.omOrderDetailNumber)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(cccEventId)
            .append(omOrderDetailNumber)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("cccEventId", cccEventId)
            .append("omOrderDetailNumber", omOrderDetailNumber)
            .toString();
    }
}
