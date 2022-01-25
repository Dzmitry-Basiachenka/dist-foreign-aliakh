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
 * Implementation of {@link ResultHandler} for getting map of wrWrkInsts to system titles.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
class WrWrkInstToSystemTitlesResultHandler implements ResultHandler {

    private final Map<Long, String> wrWrkInstToSystemTitles = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        WrWrkInstSystemTitlePair pair = (WrWrkInstSystemTitlePair) context.getResultObject();
        wrWrkInstToSystemTitles.put(pair.getWrWrkInst(), pair.getSystemTitle());
    }

    /**
     * @return map of wrWrkInsts to system titles.
     */
    Map<Long, String> getWrWrkInstToSystemTitles() {
        return wrWrkInstToSystemTitles;
    }

    /**
     * Class to load wrWrkInsts and system titles.
     */
    public static class WrWrkInstSystemTitlePair {

        private Long wrWrkInst;
        private String systemTitle;

        public Long getWrWrkInst() {
            return wrWrkInst;
        }

        public void setWrWrkInst(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }

        public String getSystemTitle() {
            return systemTitle;
        }

        public void setSystemTitle(String systemTitle) {
            this.systemTitle = systemTitle;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            WrWrkInstSystemTitlePair that = (WrWrkInstSystemTitlePair) obj;
            return new EqualsBuilder()
                .append(wrWrkInst, that.wrWrkInst)
                .append(systemTitle, that.systemTitle)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(wrWrkInst)
                .append(systemTitle)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("wrWrkInst", wrWrkInst)
                .append("systemTitle", systemTitle)
                .toString();
        }
    }
}
