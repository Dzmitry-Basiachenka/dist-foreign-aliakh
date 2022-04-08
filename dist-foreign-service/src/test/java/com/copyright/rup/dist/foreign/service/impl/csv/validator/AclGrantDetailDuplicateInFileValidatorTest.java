package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclGrantDetailDuplicateInFileValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/07/2022
 *
 * @author Ihar Suvorau
 */
public class AclGrantDetailDuplicateInFileValidatorTest {

    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private AclGrantDetailDuplicateInFileValidator validator;

    @Before
    public void setUp() {
        validator = new AclGrantDetailDuplicateInFileValidator();
    }

    @Test
    public void testIsValid() {
        assertTrue(validator.isValid(buildGrantDetail(1L, PRINT_TOU)));
        assertTrue(validator.isValid(buildGrantDetail(1L, DIGITAL_TOU)));
        assertTrue(validator.isValid(buildGrantDetail(2L, PRINT_TOU)));
        assertFalse(validator.isValid(buildGrantDetail(1L, PRINT_TOU)));
        assertFalse(validator.isValid(buildGrantDetail(2L, PRINT_TOU)));
        assertTrue(validator.isValid(buildGrantDetail(2L, DIGITAL_TOU)));
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Grant with Wr Wrk Inst and Type of Use already present in file", validator.getErrorMessage());
    }

    private AclGrantDetailDto buildGrantDetail(Long wrWrkInst, String typeOfUse) {
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setWrWrkInst(wrWrkInst);
        grantDetailDto.setTypeOfUse(typeOfUse);
        return grantDetailDto;
    }
}
