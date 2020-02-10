package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclFundPoolService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolServiceTest {

    private static final String FUND_POOL_ID = "5a40ff60-31d2-4bab-9871-60cff88b7889";
    private static final String FUND_POOL_NAME = "fund pool name";

    private final AaclFundPoolService aaclFundPoolService = new AaclFundPoolService();

    private IAaclFundPoolRepository aaclFundPoolRepository;

    @Before
    public void setUp() {
        aaclFundPoolRepository = createStrictMock(IAaclFundPoolRepository.class);
        Whitebox.setInternalState(aaclFundPoolService, aaclFundPoolRepository);
    }

    @Test
    public void testAaclFundPoolExists() {
        expect(aaclFundPoolRepository.aaclFundPoolExists(FUND_POOL_NAME)).andReturn(true).once();
        replay(aaclFundPoolRepository);
        assertTrue(aaclFundPoolService.aaclFundPoolExists(FUND_POOL_NAME));
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testGetFundPools() {
        List<AaclFundPool> fundPools = Collections.singletonList(new AaclFundPool());
        expect(aaclFundPoolRepository.findAll()).andReturn(fundPools).once();
        replay(aaclFundPoolRepository);
        assertEquals(fundPools, aaclFundPoolService.getFundPools());
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testGetDetailsByFundPoolId() {
        List<AaclFundPoolDetail> details = Collections.singletonList(new AaclFundPoolDetail());
        expect(aaclFundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID)).andReturn(details).once();
        replay(aaclFundPoolRepository);
        assertEquals(details, aaclFundPoolService.getDetailsByFundPoolId(FUND_POOL_ID));
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testDeleteFundPoolById() {
        aaclFundPoolRepository.deleteDetailsByFundPoolId(FUND_POOL_ID);
        expectLastCall().once();
        aaclFundPoolRepository.deleteById(FUND_POOL_ID);
        expectLastCall().once();
        replay(aaclFundPoolRepository);
        aaclFundPoolService.deleteFundPoolById(FUND_POOL_ID);
        verify(aaclFundPoolRepository);
    }
}
