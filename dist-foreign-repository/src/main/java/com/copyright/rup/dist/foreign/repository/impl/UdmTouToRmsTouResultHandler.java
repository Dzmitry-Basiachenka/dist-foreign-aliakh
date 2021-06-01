package com.copyright.rup.dist.foreign.repository.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ResultHandler} for getting map of UDM type of uses to RMS type of uses.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
class UdmTouToRmsTouResultHandler implements ResultHandler {

    private final Map<String, String> udmTousToRmsTous = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        UdmTouRmsTouPair pair = (UdmTouRmsTouPair) context.getResultObject();
        udmTousToRmsTous.put(pair.getUdmTypeOfUse(), pair.getRmsTypeOfUse());
    }

    /**
     * @return a map of UDM type of uses to RMS type of uses.
     */
    Map<String, String> getUdmTousToRmsTous() {
        return udmTousToRmsTous;
    }

    /**
     * Class to load UDM type of uses with RMS type of uses.
     */
    public static class UdmTouRmsTouPair {

        private String udmTypeOfUse;
        private String rmsTypeOfUse;

        public String getUdmTypeOfUse() {
            return udmTypeOfUse;
        }

        public void setUdmTypeOfUse(String udmTypeOfUse) {
            this.udmTypeOfUse = udmTypeOfUse;
        }

        public String getRmsTypeOfUse() {
            return rmsTypeOfUse;
        }

        public void setRmsTypeOfUse(String rmsTypeOfUse) {
            this.rmsTypeOfUse = rmsTypeOfUse;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (null == o || getClass() != o.getClass()) {
                return false;
            }
            UdmTouRmsTouPair that = (UdmTouRmsTouPair) o;
            return new EqualsBuilder()
                .append(udmTypeOfUse, that.udmTypeOfUse)
                .append(rmsTypeOfUse, that.rmsTypeOfUse)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(udmTypeOfUse)
                .append(rmsTypeOfUse)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("udmTypeOfUse", udmTypeOfUse)
                .append("rmsTypeOfUse", rmsTypeOfUse)
                .toString();
        }
    }
}
