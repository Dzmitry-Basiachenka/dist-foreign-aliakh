package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link LdmtDetailsPlainValidator}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetailsPlainValidatorTest {

    private final LdmtDetailsPlainValidator validator = new LdmtDetailsPlainValidator();

    @Test
    public void testValidateSuccess() {
        validator.validate(Collections.singletonList(buildLdmtDetail()));
    }

    @Test
    public void testValidateFailure() {
        try {
            validator.validate(Collections.singletonList(new LdmtDetail()));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [" +
                "Licensee Class Id should not be null or blank, " +
                "License Type should not be null or blank, " +
                "Type Of Use should not be null or blank, " +
                "Gross Amount should not be null, " +
                "Net Amount should not be null]", e.getMessage());
        }
    }

    private LdmtDetail buildLdmtDetail() {
        LdmtDetail ldmtDetail = new LdmtDetail();
        ldmtDetail.setDetailLicenseeClassId(1);
        ldmtDetail.setLicenseType("ACL");
        ldmtDetail.setTypeOfUse("PRINT");
        ldmtDetail.setGrossAmount(new BigDecimal("634420.48"));
        ldmtDetail.setNetAmount(new BigDecimal("450799.88"));
        return ldmtDetail;
    }
}
