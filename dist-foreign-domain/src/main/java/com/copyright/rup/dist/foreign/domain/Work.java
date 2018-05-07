package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents work is read from PI.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/05/2018
 *
 * @author Aliaksandr Liakh
 */
public class Work {

    private Long wrWrkInst;
    private String mainTitle;

    /**
     * Constructor.
     */
    public Work() {
    }

    /**
     * Constructor.
     *
     * @param wrWrkInst wr wrk inst
     * @param mainTitle main title
     */
    public Work(Long wrWrkInst, String mainTitle) {
        this.wrWrkInst = wrWrkInst;
        this.mainTitle = mainTitle;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Work that = (Work) o;
        return new EqualsBuilder()
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.mainTitle, that.mainTitle)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(wrWrkInst)
            .append(mainTitle)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("wrWrkInst", wrWrkInst)
            .append("mainTitle", mainTitle)
            .toString();
    }
}
