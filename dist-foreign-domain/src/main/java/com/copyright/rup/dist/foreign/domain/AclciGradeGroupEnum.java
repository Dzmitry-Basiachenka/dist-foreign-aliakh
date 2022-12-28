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
     * 'Grade HE' grade group.
     */
    GRADE_HE,

    /**
     * 'Grade K_2' grade group.
     */
    GRADEK_2,

    /**
     * 'Grade 3_5' grade group.
     */
    GRADE3_5,

    /**
     * 'Grade 6_8' grade group.
     */
    GRADE6_8,
    /**
     * 'Grade 9_12' grade group.
     */
    GRADE9_12;

    private static final Map<String, AclciGradeGroupEnum> GRADE_GROUP_MAP =
        ImmutableMap.<String, AclciGradeGroupEnum>builder()
            .put("K", GRADEK_2)
            .put("1", GRADEK_2)
            .put("2", GRADEK_2)
            .put("3", GRADE3_5)
            .put("4", GRADE3_5)
            .put("5", GRADE3_5)
            .put("6", GRADE6_8)
            .put("7", GRADE6_8)
            .put("8", GRADE6_8)
            .put("9", GRADE9_12)
            .put("10", GRADE9_12)
            .put("11", GRADE9_12)
            .put("12", GRADE9_12)
            .put("HE", GRADE_HE)
            .build();

    /**
     * Gets {@link AclciGradeGroupEnum} by provided grade.
     *
     * @param grade grade
     * @return {@link AclciGradeGroupEnum} instance
     */
    public static AclciGradeGroupEnum getGroupByGrade(String grade) {
        return GRADE_GROUP_MAP.get(grade);
    }
}
