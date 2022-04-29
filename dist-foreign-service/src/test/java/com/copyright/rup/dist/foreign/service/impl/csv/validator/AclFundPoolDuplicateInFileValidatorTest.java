package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclFundPoolDuplicateInFileValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/29/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolDuplicateInFileValidatorTest {

    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private AclFundPoolDuplicateInFileValidator validator;

    @Before
    public void setUp() {
        validator = new AclFundPoolDuplicateInFileValidator();
    }

    @Test
    public void testIsValid() {
        assertTrue(validator.isValid(buildFundPoolDetail(1, PRINT_TOU)));
        assertTrue(validator.isValid(buildFundPoolDetail(1, DIGITAL_TOU)));
        assertTrue(validator.isValid(buildFundPoolDetail(2, PRINT_TOU)));
        assertFalse(validator.isValid(buildFundPoolDetail(1, PRINT_TOU)));
        assertFalse(validator.isValid(buildFundPoolDetail(2, PRINT_TOU)));
        assertTrue(validator.isValid(buildFundPoolDetail(2, DIGITAL_TOU)));
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Fund Pool with Detail Licensee Class Id and Type of Use already present in file",
            validator.getErrorMessage());
    }

    private AclFundPoolDetail buildFundPoolDetail(Integer detLicClassId, String typeOfUse) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(detLicClassId);
        AclFundPoolDetail fundPoolDetail = new AclFundPoolDetail();
        fundPoolDetail.setDetailLicenseeClass(detailLicenseeClass);
        fundPoolDetail.setTypeOfUse(typeOfUse);
        return fundPoolDetail;
    }
}
