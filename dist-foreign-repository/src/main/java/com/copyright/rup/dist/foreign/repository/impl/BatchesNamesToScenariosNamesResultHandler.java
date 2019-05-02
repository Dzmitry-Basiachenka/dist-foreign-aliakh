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
 * Implementation of {@link ResultHandler} for getting map of batches names to scenarios names.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/30/2019
 *
 * @author Aliaksandr Liakh
 */
class BatchesNamesToScenariosNamesResultHandler implements ResultHandler {

    private final Map<String, String> batchesNamesToScenariosNames = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        BatchNameScenarioNamePair pair = (BatchNameScenarioNamePair) context.getResultObject();
        batchesNamesToScenariosNames.put(pair.getBatchName(), pair.getScenarioName());
    }

    /**
     * Gets map of batches names to scenarios names.
     *
     * @return a map of batches names to scenarios names
     */
    Map<String, String> getBatchesNamesToScenariosNames() {
        return batchesNamesToScenariosNames;
    }

    /**
     * Class to load batches names with scenarios names.
     */
    public static class BatchNameScenarioNamePair {

        private String batchName;
        private String scenarioName;

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public String getScenarioName() {
            return scenarioName;
        }

        public void setScenarioName(String scenarioName) {
            this.scenarioName = scenarioName;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            BatchNameScenarioNamePair that = (BatchNameScenarioNamePair) obj;
            return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.batchName, that.batchName)
                .append(this.scenarioName, that.scenarioName)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(batchName)
                .append(scenarioName)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("batchName", batchName)
                .append("scenarioName", scenarioName)
                .toString();
        }
    }
}
