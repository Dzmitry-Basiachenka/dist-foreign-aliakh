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
    private String mainIdno;
    private String mainIdnoType;
    private boolean multipleMatches;

    /**
     * Constructor.
     */
    public Work() {
    }

    /**
     * Constructor.
     *
     * @param wrWrkInst    wr wrk inst
     * @param mainTitle    main title
     * @param mainIdno     main idno
     * @param mainIdnoType main idno type
     */
    public Work(Long wrWrkInst, String mainTitle, String mainIdno, String mainIdnoType) {
        this.wrWrkInst = wrWrkInst;
        this.mainTitle = mainTitle;
        this.mainIdno = mainIdno;
        this.mainIdnoType = mainIdnoType;
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

    public String getMainIdno() {
        return mainIdno;
    }

    public void setMainIdno(String mainIdno) {
        this.mainIdno = mainIdno;
    }

    public boolean isMultipleMatches() {
        return multipleMatches;
    }

    public void setMultipleMatches(boolean multipleMatches) {
        this.multipleMatches = multipleMatches;
    }

    public String getMainIdnoType() {
        return mainIdnoType;
    }

    public void setMainIdnoType(String mainIdnoType) {
        this.mainIdnoType = mainIdnoType;
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
            .append(this.mainIdno, that.mainIdno)
            .append(this.multipleMatches, that.multipleMatches)
            .append(this.mainIdnoType, that.mainIdnoType)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(wrWrkInst)
            .append(mainTitle)
            .append(mainIdno)
            .append(multipleMatches)
            .append(mainIdnoType)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("wrWrkInst", wrWrkInst)
            .append("mainTitle", mainTitle)
            .append("mainIdno", mainIdno)
            .append("multipleMatches", multipleMatches)
            .append("mainIdnoType", mainIdnoType)
            .toString();
    }
}
