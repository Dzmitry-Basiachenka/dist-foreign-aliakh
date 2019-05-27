package com.copyright.rup.dist.foreign.repository.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.Map;

/**
 * Implementation of {@link ResultHandler} for getting map of batches names to Wr Wrk Insts related to this batch.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/22/19
 *
 * @author Darya Baraukova
 */
class BatchNameToUsageIdWrWrkInstResultHandler implements ResultHandler {

    private final Table<String, String, Long> batchNameToWrWrkInstsMap = HashBasedTable.create();

    @Override
    public void handleResult(ResultContext context) {
        BatchNameUsageIdWrWrkInstHolder holder = (BatchNameUsageIdWrWrkInstHolder) context.getResultObject();
        if (batchNameToWrWrkInstsMap.containsRow(holder.getBatchName())) {
            Map<String, Long> usageIdWrWrkInstMap = batchNameToWrWrkInstsMap.row(holder.getBatchName());
            usageIdWrWrkInstMap.put(holder.getUsageId(), holder.getWrWrkInst());
        } else {
            batchNameToWrWrkInstsMap.put(holder.getBatchName(), holder.getUsageId(), holder.getWrWrkInst());
        }
    }

    /**
     * @return map where key is batch name, value is set of Wr Wrk Insts related to this batch.
     */
    Table<String, String, Long> getBatchNameToWrWrkInstsMap() {
        return batchNameToWrWrkInstsMap;
    }

    /**
     * Class to represent holder of batch name, usage id and Wr Wrk Inst.
     */
    public static class BatchNameUsageIdWrWrkInstHolder {

        private String batchName;
        private String usageId;
        private Long wrWrkInst;

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public String getUsageId() {
            return usageId;
        }

        public void setUsageId(String usageId) {
            this.usageId = usageId;
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
            BatchNameUsageIdWrWrkInstHolder that = (BatchNameUsageIdWrWrkInstHolder) obj;
            return new EqualsBuilder()
                .append(this.batchName, that.batchName)
                .append(this.usageId, that.usageId)
                .append(this.wrWrkInst, that.wrWrkInst)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(batchName)
                .append(usageId)
                .append(wrWrkInst)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("batchName", batchName)
                .append("usageId", usageId)
                .append("wrWrkInst", wrWrkInst)
                .toString();
        }
    }

}
