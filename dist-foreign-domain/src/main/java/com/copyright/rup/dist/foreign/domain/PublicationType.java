package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Represents publication type domain object.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public class PublicationType extends StoredEntity<String> {

    private static final long serialVersionUID = -7100991895181385565L;

    private String name;
    private String description;
    private String productFamily;
    private BigDecimal weight;

    /**
     * Constructor.
     */
    public PublicationType() {
    }

    /**
     * Constructor.
     *
     * @param pubType instance of {@link PublicationType}
     */
    public PublicationType(PublicationType pubType) {
        setId(pubType.getId());
        this.name = pubType.getName();
        this.description = pubType.getDescription();
        this.productFamily = pubType.getProductFamily();
        this.weight = pubType.getWeight();
    }

    public String getNameAndDescription() {
        return String.format("%s - %s", getName(), getDescription());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }

        PublicationType that = (PublicationType) obj;

        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(name, that.name)
            .append(description, that.description)
            .append(productFamily, that.productFamily)
            .append(weight, that.weight)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(description)
            .append(productFamily)
            .append(weight)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("description", description)
            .append("productFamily", productFamily)
            .append("weight", weight)
            .toString();
    }
}
