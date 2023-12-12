package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents Classified usage detail.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/23/2020
 *
 * @author Anton Azarenka
 */
public class AaclClassifiedUsage extends StoredEntity<String> {

    private static final long serialVersionUID = -7719518357952373978L;

    private String detailId;
    private Long wrWrkInst;
    private String enrollmentProfile;
    private String discipline;
    private String publicationType;
    private String comment;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getEnrollmentProfile() {
        return enrollmentProfile;
    }

    public void setEnrollmentProfile(String enrollmentProfile) {
        this.enrollmentProfile = enrollmentProfile;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AaclClassifiedUsage that = (AaclClassifiedUsage) obj;
        return new EqualsBuilder()
            .append(detailId, that.detailId)
            .append(wrWrkInst, that.wrWrkInst)
            .append(enrollmentProfile, that.enrollmentProfile)
            .append(discipline, that.discipline)
            .append(publicationType, that.publicationType)
            .append(comment, that.comment)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(detailId)
            .append(wrWrkInst)
            .append(enrollmentProfile)
            .append(discipline)
            .append(publicationType)
            .append(comment)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("detailId", detailId)
            .append("wrWrkInst", wrWrkInst)
            .append("enrollmentProfile", enrollmentProfile)
            .append("discipline", discipline)
            .append("publicationType", publicationType)
            .append("comment", comment)
            .toString();
    }
}
