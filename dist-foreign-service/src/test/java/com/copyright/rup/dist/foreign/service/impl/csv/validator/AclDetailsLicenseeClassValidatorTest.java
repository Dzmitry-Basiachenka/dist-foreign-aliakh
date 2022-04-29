package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclDetailsLicenseeClassValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/29/2022
 *
 * @author Anton Azarenka
 */
public class AclDetailsLicenseeClassValidatorTest {

    private static final Integer DET_LIC_CLASS_ID = 1;
    private AclDetailsLicenseeClassValidator validator;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        licenseeClassService = createMock(ILicenseeClassService.class);
        validator = new AclDetailsLicenseeClassValidator(licenseeClassService);
    }

    @Test
    public void testIsValid() {
        AclFundPoolDetail usage = buildFundPoolDetail(DET_LIC_CLASS_ID);
        expect(licenseeClassService.detailLicenseeClassExists(DET_LIC_CLASS_ID))
            .andReturn(true).once();
        replay(licenseeClassService);
        assertTrue(validator.isValid(usage));
        verify(licenseeClassService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Detail License Class Id doesn't exist in the system", validator.getErrorMessage());
    }

    private AclFundPoolDetail buildFundPoolDetail(Integer detLicClassId) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(detLicClassId);
        AclFundPoolDetail fundPoolDetail = new AclFundPoolDetail();
        fundPoolDetail.setDetailLicenseeClass(detailLicenseeClass);
        return fundPoolDetail;
    }
}
