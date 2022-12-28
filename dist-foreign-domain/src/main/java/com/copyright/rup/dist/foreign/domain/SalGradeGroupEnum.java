package com.copyright.rup.dist.foreign.domain;

import com.google.common.collect.Range;

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

    private static final String GRADE_K = "K";
    private static final Range<Integer> GRADE_RANGE_1_5 = Range.closed(1, 5);
    private static final Range<Integer> GRADE_RANGE_6_8 = Range.closed(6, 8);
    private static final Range<Integer> GRADE_RANGE_9_12 = Range.closed(9, 12);

    /**
     * Gets {@link SalGradeGroupEnum} by provided grade.
     *
     * @param grade grade
     * @return {@link SalGradeGroupEnum} instance
     */
    public static SalGradeGroupEnum getGroupByGrade(String grade) {
        SalGradeGroupEnum result = null;
        if (GRADE_K.equals(grade)) {
            result = GRADEK_5;
        } else {
            Integer convertedGrade = Integer.parseInt(grade);
            if (GRADE_RANGE_1_5.contains(convertedGrade)) {
                result = GRADEK_5;
            } else if (GRADE_RANGE_6_8.contains(convertedGrade)) {
                result = GRADE6_8;
            } else if (GRADE_RANGE_9_12.contains(convertedGrade)) {
                result = GRADE9_12;
            }
        }
        return result;
    }
}
