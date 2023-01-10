package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import org.junit.Test;

import java.util.List;

/**
 * Validates {@link AggregateLicenseeClassValidator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AggregateLicenseeClassValidatorTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";

    @Test
    public void testIsValidTrue() {
        ILicenseeClassService licenseeClassService = createMock(ILicenseeClassService.class);
        expect(licenseeClassService.getAggregateLicenseeClasses(AACL_PRODUCT_FAMILY))
            .andReturn(List.of(buildAggregateLicenseeClass(108))).once();
        replay(licenseeClassService);
        assertTrue(new AggregateLicenseeClassValidator(licenseeClassService, AACL_PRODUCT_FAMILY).isValid(
            buildFundPoolDetail(108)));
        verify(licenseeClassService);
    }

    @Test
    public void testIsValidFalse() {
        ILicenseeClassService licenseeClassService = createMock(ILicenseeClassService.class);
        expect(licenseeClassService.getAggregateLicenseeClasses(AACL_PRODUCT_FAMILY))
            .andReturn(List.of(buildAggregateLicenseeClass(108))).once();
        replay(licenseeClassService);
        assertFalse(new AggregateLicenseeClassValidator(licenseeClassService, AACL_PRODUCT_FAMILY).isValid(
            buildFundPoolDetail(666)));
        verify(licenseeClassService);
    }

    @Test
    public void testGetErrorMessage() {
        ILicenseeClassService licenseeClassService = createMock(ILicenseeClassService.class);
        expect(licenseeClassService.getAggregateLicenseeClasses(AACL_PRODUCT_FAMILY)).andReturn(List.of()).once();
        replay(licenseeClassService);
        assertEquals("Aggregate Licensee Class with such ID doesn't exist in the system",
            new AggregateLicenseeClassValidator(licenseeClassService, AACL_PRODUCT_FAMILY).getErrorMessage());
        verify(licenseeClassService);
    }

    private FundPoolDetail buildFundPoolDetail(Integer fundPoolId) {
        FundPoolDetail detail = new FundPoolDetail();
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        aggregateLicenseeClass.setId(fundPoolId);
        return detail;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id) {
        AggregateLicenseeClass alc = new AggregateLicenseeClass();
        alc.setId(id);
        alc.setEnrollmentProfile("EXGP");
        alc.setDiscipline("Life Sciences");
        return alc;
    }
}
