package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents ineligible reason of UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmIneligibleReason extends StoredEntity<String> {

    private String text;

    /**
     * Constructor.
     */
    public UdmIneligibleReason() {
    }

    /**
     * Constructor.
     *
     * @param id   ineligible reason id
     * @param text ineligible reason text
     */
    public UdmIneligibleReason(String id, String text) {
        this.setId(id);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmIneligibleReason that = (UdmIneligibleReason) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(text, that.text)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(text)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("text", text)
            .toString();
    }
}
