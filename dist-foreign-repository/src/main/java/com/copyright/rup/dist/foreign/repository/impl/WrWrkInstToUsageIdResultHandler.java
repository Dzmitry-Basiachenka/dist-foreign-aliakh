package com.copyright.rup.dist.foreign.repository.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link ResultHandler} for getting map of Wr Wrk Insts to related usage ids.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/22/19
 *
 * @author Darya Baraukova
 */
class WrWrkInstToUsageIdResultHandler implements ResultHandler {

    private final Map<Long, Set<String>> wrWrkInstToUsageIdsMap = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        WrWrkInstUsageIdPair holder = (WrWrkInstUsageIdPair) context.getResultObject();
        Long wrWrkInst = holder.getWrWrkInst();
        if (wrWrkInstToUsageIdsMap.containsKey(wrWrkInst)) {
            Set<String> usageIds = new HashSet<>(wrWrkInstToUsageIdsMap.get(wrWrkInst));
            usageIds.add(holder.getUsageId());
            wrWrkInstToUsageIdsMap.put(wrWrkInst, usageIds);
        } else {
            wrWrkInstToUsageIdsMap.put(holder.getWrWrkInst(), Set.of(holder.getUsageId()));
        }
    }

    /**
     * @return map where key is Wr Wrk Inst, value is set of usage ids related to this work.
     */
    Map<Long, Set<String>> getWrWrkInstToUsageIdsMap() {
        return wrWrkInstToUsageIdsMap;
    }

    /**
     * Class to represent pair of Wr Wrk Inst and usage id.
     */
    public static class WrWrkInstUsageIdPair {

        private Long wrWrkInst;
        private String usageId;

        public Long getWrWrkInst() {
            return wrWrkInst;
        }

        public void setWrWrkInst(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }

        public String getUsageId() {
            return usageId;
        }

        public void setUsageId(String usageId) {
            this.usageId = usageId;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            WrWrkInstUsageIdPair that = (WrWrkInstUsageIdPair) obj;
            return new EqualsBuilder()
                .append(this.wrWrkInst, that.wrWrkInst)
                .append(this.usageId, that.usageId)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(wrWrkInst)
                .append(usageId)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("wrWrkInst", wrWrkInst)
                .append("usageId", usageId)
                .toString();
        }
    }
}
