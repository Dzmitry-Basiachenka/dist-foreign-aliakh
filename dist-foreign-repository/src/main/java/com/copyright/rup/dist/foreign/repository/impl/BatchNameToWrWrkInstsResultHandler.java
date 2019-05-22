package com.copyright.rup.dist.foreign.repository.impl;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link ResultHandler} for getting map of batches names to Wr Wrk Insts related to this batch.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/22/19
 *
 * @author Darya Baraukova
 */
class BatchNameToWrWrkInstsResultHandler implements ResultHandler {

    private final Map<String, Set<Long>> batchNameToWrWrkInstsMap = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        BatchNameWrWrkInstPair holder = (BatchNameWrWrkInstPair) context.getResultObject();
        if (batchNameToWrWrkInstsMap.containsKey(holder.getBatchName())) {
            Set<Long> wrWrkInsts = Sets.newHashSet(batchNameToWrWrkInstsMap.get(holder.getBatchName()));
            wrWrkInsts.add(holder.getWrWrkInst());
            batchNameToWrWrkInstsMap.put(holder.getBatchName(), wrWrkInsts);
        } else {
            batchNameToWrWrkInstsMap.put(holder.getBatchName(), Collections.singleton(holder.getWrWrkInst()));
        }
    }

    /**
     * @return map where key is batch name, value is set of Wr Wrk Insts related to this batch.
     */
    Map<String, Set<Long>> getBatchNameToWrWrkInstsMap() {
        return batchNameToWrWrkInstsMap;
    }

    /**
     * Class to represent pair of batch name to Wr Wrk Inst.
     */
    public static class BatchNameWrWrkInstPair {

        private String batchName;
        private Long wrWrkInst;

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public Long getWrWrkInst() {
            return wrWrkInst;
        }

        public void setWrWrkInst(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            BatchNameWrWrkInstPair that = (BatchNameWrWrkInstPair) obj;
            return new EqualsBuilder()
                .append(this.batchName, that.batchName)
                .append(this.wrWrkInst, that.wrWrkInst)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(batchName)
                .append(wrWrkInst)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("batchName", batchName)
                .append("wrWrkInst", wrWrkInst)
                .toString();
        }
    }

}
