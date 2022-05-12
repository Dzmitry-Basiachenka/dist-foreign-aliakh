package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link LdmtDetailsConsumer}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupPersistUtils.class)
public class LdmtDetailsConsumerTest {

    private LdmtDetailsConsumer ldmtDetailsConsumer;
    private IAclFundPoolService aclFundPoolService;

    @Before
    public void setUp() {
        ldmtDetailsConsumer = new LdmtDetailsConsumer();
        aclFundPoolService = createMock(IAclFundPoolService.class);
        Whitebox.setInternalState(ldmtDetailsConsumer, aclFundPoolService);
    }

    @Test
    public void testConsume() {
        mockStatic(RupPersistUtils.class);
        String id = "58289ebc-4377-4f28-85a7-ad2e0599c5d0";
        expect(RupPersistUtils.generateUuid()).andReturn(id).once();
        LdmtDetail ldmtDetail = buildLdmtDetail(id);
        List<LdmtDetail> ldmtDetails = Collections.singletonList(ldmtDetail);
        Capture<List<AclFundPoolDetail>> fundPoolDetailsCapture = newCapture();
        aclFundPoolService.insertAclFundPoolDetails(capture(fundPoolDetailsCapture));
        expectLastCall();
        replay(RupPersistUtils.class, aclFundPoolService);
        ldmtDetailsConsumer.consume(ldmtDetails);
        verifyAclFundPoolDetails(ldmtDetail, fundPoolDetailsCapture.getValue());
        verify(RupPersistUtils.class, aclFundPoolService);
    }

    private LdmtDetail buildLdmtDetail(String id) {
        LdmtDetail ldmtDetail = new LdmtDetail();
        ldmtDetail.setId(id);
        ldmtDetail.setDetailLicenseeClassId(1);
        ldmtDetail.setLicenseType("ACL");
        ldmtDetail.setTypeOfUse("PRINT");
        ldmtDetail.setGrossAmount(new BigDecimal("634420.48"));
        ldmtDetail.setNetAmount(new BigDecimal("450799.88"));
        return ldmtDetail;
    }

    private void verifyAclFundPoolDetails(LdmtDetail ldmtDetail, List<AclFundPoolDetail> fundPoolDetails) {
        assertEquals(1, fundPoolDetails.size());
        AclFundPoolDetail fundPoolDetail = fundPoolDetails.get(0);
        assertNotNull(fundPoolDetail);
        assertEquals(ldmtDetail.getId(), fundPoolDetail.getId());
        assertNull(fundPoolDetail.getFundPoolId());
        assertEquals(ldmtDetail.getDetailLicenseeClassId(), fundPoolDetail.getDetailLicenseeClass().getId());
        assertEquals(ldmtDetail.getLicenseType(), fundPoolDetail.getLicenseType());
        assertEquals(ldmtDetail.getTypeOfUse(), fundPoolDetail.getTypeOfUse());
        assertEquals(ldmtDetail.getGrossAmount(), fundPoolDetail.getGrossAmount());
        assertEquals(ldmtDetail.getNetAmount(), fundPoolDetail.getNetAmount());
        assertTrue(fundPoolDetail.isLdmtFlag());
    }
}
