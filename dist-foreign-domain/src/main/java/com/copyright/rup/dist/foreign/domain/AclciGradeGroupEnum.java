package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Represents enum for ACLCI Grade Group.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/27/2022
 *
 * @author Anton Azarenka
 */
public enum AclciGradeGroupEnum {

    /**
     * 'Grade E' grade group.
     */
    GRADE_E("Elementary"),

    /**
     * 'Grade HE' grade group.
     */
    GRADE_HE("HE"),

    /**
     * 'Grade HS' grade group.
     */
    GRADE_HS("HS"),

    /**
     * 'Grade M' grade group.
     */
    GRADE_M("Middle");

    private static final Map<String, AclciGradeGroupEnum> GRADE_GROUP_MAP =
        ImmutableMap.<String, AclciGradeGroupEnum>builder()
            .put("E", GRADE_E)
            .put("K", GRADE_E)
            .put("1", GRADE_E)
            .put("2", GRADE_E)
            .put("3", GRADE_E)
            .put("4", GRADE_E)
            .put("5", GRADE_E)
            .put("6", GRADE_M)
            .put("7", GRADE_M)
            .put("8", GRADE_M)
            .put("9", GRADE_M)
            .put("M", GRADE_M)
            .put("10", GRADE_HS)
            .put("11", GRADE_HS)
            .put("12", GRADE_HS)
            .put("HE", GRADE_HE)
            .put("HS", GRADE_HS)
            .build();

    private final String label;

    /**
     * Constructor.
     *
     * @param value value
     */
    AclciGradeGroupEnum(String value) {
        this.label = value;
    }

    /**
     * Gets {@link AclciGradeGroupEnum} by provided grade.
     *
     * @param grade grade
     * @return {@link AclciGradeGroupEnum} instance
     */
    public static AclciGradeGroupEnum getGroupByGrade(String grade) {
        return GRADE_GROUP_MAP.get(grade);
    }

    @Override
    public String toString() {
        return label;
    }
}
