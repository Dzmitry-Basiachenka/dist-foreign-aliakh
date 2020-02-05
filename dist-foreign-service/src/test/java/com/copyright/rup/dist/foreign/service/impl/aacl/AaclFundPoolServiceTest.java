package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private static final String FUND_POOL_NAME = "fund pool name";

    private final AaclFundPoolService aaclFundPoolService = new AaclFundPoolService();

    private IAaclFundPoolRepository aaclFundPoolRepository;

    @Before
    public void setUp() {
        aaclFundPoolRepository = createMock(IAaclFundPoolRepository.class);
        Whitebox.setInternalState(aaclFundPoolService, aaclFundPoolRepository);
    }

    @Test
    public void testAaclFundPoolExists() {
        expect(aaclFundPoolRepository.aaclFundPoolExists(FUND_POOL_NAME)).andReturn(true).once();
        replay(aaclFundPoolRepository);
        assertTrue(aaclFundPoolRepository.aaclFundPoolExists(FUND_POOL_NAME));
        verify(aaclFundPoolRepository);
    }
}
