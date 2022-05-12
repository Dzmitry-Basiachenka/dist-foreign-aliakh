package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link LdmtDetailsBusinessValidator}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetailsBusinessValidatorTest {

    private static final int DETAIL_LICENSEE_CLASS_ID = 1;
    private static final String INVALID_LICENSE_TYPE = "ACLPRINT";
    private static final String INVALID_TYPE_OF_USE = "EMAIL_COPY";
    private static final BigDecimal INVALID_AMOUNT = new BigDecimal("12345678901.23");

    private LdmtDetailsBusinessValidator validator;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        validator = new LdmtDetailsBusinessValidator();
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(validator, licenseeClassService);
    }

    @Test
    public void testValidateSuccess() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(true).once();
        replay(licenseeClassService);
        validator.validate(Collections.singletonList(buildLdmtDetail()));
        verify(licenseeClassService);
    }

    @Test
    public void testValidateFailureDetailLicenseeClassId() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(false).once();
        replay(licenseeClassService);
        try {
            validator.validate(Collections.singletonList(buildLdmtDetail()));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [Detail Licensee Class is not valid: 1]", e.getMessage());
        }
        verify(licenseeClassService);
    }

    @Test
    public void testValidateFailureLicenseType() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(true).once();
        replay(licenseeClassService);
        try {
            LdmtDetail ldmtDetail = buildLdmtDetail();
            ldmtDetail.setLicenseType(INVALID_LICENSE_TYPE);
            validator.validate(Collections.singletonList(ldmtDetail));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [License Type is not valid: ACLPRINT]", e.getMessage());
        }
        verify(licenseeClassService);
    }

    @Test
    public void testValidateFailureTypeOfUse() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(true).once();
        replay(licenseeClassService);
        try {
            LdmtDetail ldmtDetail = buildLdmtDetail();
            ldmtDetail.setTypeOfUse(INVALID_TYPE_OF_USE);
            validator.validate(Collections.singletonList(ldmtDetail));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [Type of Use is not valid: EMAIL_COPY]", e.getMessage());
        }
        verify(licenseeClassService);
    }

    @Test
    public void testValidateFailureGrossAmount() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(true).once();
        replay(licenseeClassService);
        try {
            LdmtDetail ldmtDetail = buildLdmtDetail();
            ldmtDetail.setGrossAmount(INVALID_AMOUNT);
            validator.validate(Collections.singletonList(ldmtDetail));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [" +
                "The integer part of Gross Amount should be less or equal to 10 digits: 12345678901.23]",
                e.getMessage());
        }
        verify(licenseeClassService);
    }

    @Test
    public void testValidateFailureNetAmount() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(true).once();
        replay(licenseeClassService);
        try {
            LdmtDetail ldmtDetail = buildLdmtDetail();
            ldmtDetail.setNetAmount(INVALID_AMOUNT);
            validator.validate(Collections.singletonList(ldmtDetail));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [" +
                "The integer part of Net Amount should be less or equal to 10 digits: 12345678901.23, " +
                "Net Amount should be less than or equal to Gross Amount: 12345678901.23, 634420.48]", e.getMessage());
        }
        verify(licenseeClassService);
    }

    @Test
    public void testValidateFailureNetAmountGreaterThanGrossAmount() {
        expect(licenseeClassService.aclDetailLicenseeClassExists(DETAIL_LICENSEE_CLASS_ID)).andReturn(true).once();
        replay(licenseeClassService);
        try {
            LdmtDetail ldmtDetail = buildLdmtDetail();
            ldmtDetail.setNetAmount(ldmtDetail.getGrossAmount().add(BigDecimal.ONE));
            validator.validate(Collections.singletonList(ldmtDetail));
            fail();
        } catch (ValidationException e) {
            assertEquals("LDMT detail #0 is not valid: [" +
                "Net Amount should be less than or equal to Gross Amount: 634421.48, 634420.48]", e.getMessage());
        }
        verify(licenseeClassService);
    }

    private LdmtDetail buildLdmtDetail() {
        LdmtDetail ldmtDetail = new LdmtDetail();
        ldmtDetail.setDetailLicenseeClassId(DETAIL_LICENSEE_CLASS_ID);
        ldmtDetail.setLicenseType("ACL");
        ldmtDetail.setTypeOfUse("PRINT");
        ldmtDetail.setGrossAmount(new BigDecimal("634420.48"));
        ldmtDetail.setNetAmount(new BigDecimal("450799.88"));
        return ldmtDetail;
    }
}
