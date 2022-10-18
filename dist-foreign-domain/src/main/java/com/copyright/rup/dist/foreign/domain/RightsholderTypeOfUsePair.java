package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class contains a pair of rightsholder account number and type of use.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/17/2022
 *
 * @author Anton Azarenka
 */
public class RightsholderTypeOfUsePair {

    private Rightsholder rightsholder;
    private String typeOfUse;

    public Rightsholder getRightsholder() {
        return rightsholder;
    }

    public void setRightsholder(Rightsholder rightsholder) {
        this.rightsholder = rightsholder;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        RightsholderTypeOfUsePair that = (RightsholderTypeOfUsePair) obj;
        return new EqualsBuilder()
            .append(rightsholder, that.rightsholder)
            .append(typeOfUse, that.typeOfUse)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rightsholder)
            .append(typeOfUse)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("rightsholder", rightsholder)
            .append("typeOfUse", typeOfUse)
            .toString();
    }
}
