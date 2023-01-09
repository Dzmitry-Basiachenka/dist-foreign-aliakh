package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Represents enum for SAL Grade Group.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/29/2020
 *
 * @author Ihar Suvorau
 */
public enum SalGradeGroupEnum {
    /**
     * 'Item Bank' grade group.
     */
    ITEM_BANK,
    /**
     * 'Grade K_5' grade group.
     */
    GRADEK_5,
    /**
     * 'Grade 6_8' grade group.
     */
    GRADE6_8,
    /**
     * 'Grade 9_12' grade group.
     */
    GRADE9_12;

    private static final Map<String, SalGradeGroupEnum> GRADE_GROUP_MAP =
        ImmutableMap.<String, SalGradeGroupEnum>builder()
            .put("K", GRADEK_5)
            .put("1", GRADEK_5)
            .put("2", GRADEK_5)
            .put("3", GRADEK_5)
            .put("4", GRADEK_5)
            .put("5", GRADEK_5)
            .put("6", GRADE6_8)
            .put("7", GRADE6_8)
            .put("8", GRADE6_8)
            .put("9", GRADE9_12)
            .put("10", GRADE9_12)
            .put("11", GRADE9_12)
            .put("12", GRADE9_12)
            .build();

    /**
     * Gets {@link SalGradeGroupEnum} by provided grade.
     *
     * @param grade grade
     * @return {@link SalGradeGroupEnum} instance
     */
    public static SalGradeGroupEnum getGroupByGrade(String grade) {
        return GRADE_GROUP_MAP.get(grade);
    }
}
