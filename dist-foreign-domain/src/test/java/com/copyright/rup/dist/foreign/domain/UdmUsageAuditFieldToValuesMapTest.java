package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link UdmUsageAuditFieldToValuesMap}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 12/06/2021
 *
 * @author Aliaksandr Liakh
 */
// TODO implement test
public class UdmUsageAuditFieldToValuesMapTest {

    private static final UsageStatusEnum STATUS_OLD = UsageStatusEnum.OPS_REVIEW;
    private static final UsageStatusEnum STATUS_NEW = UsageStatusEnum.ELIGIBLE;

    @Test
    public void testStatus() {
        UdmUsageDto usageDto = new UdmUsageDto();
        UdmUsageAuditFieldToValuesMap fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Detail Status", STATUS_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Detail Status' was edited. " +
                "Old Value is not specified. New Value is 'OPS_REVIEW'"),
            fieldToValuesMap.getEditAuditReasons());
        usageDto.setStatus(STATUS_OLD);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Detail Status", STATUS_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Detail Status' was edited. " +
                "Old Value is 'OPS_REVIEW'. New Value is 'ELIGIBLE'"),
            fieldToValuesMap.getEditAuditReasons());
        usageDto.setStatus(STATUS_NEW);
        fieldToValuesMap = new UdmUsageAuditFieldToValuesMap(usageDto);
        fieldToValuesMap.updateFieldValue("Detail Status", null);
        assertEquals(Collections.singletonList("The field 'Detail Status' was edited. " +
                "Old Value is 'ELIGIBLE'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }
}
