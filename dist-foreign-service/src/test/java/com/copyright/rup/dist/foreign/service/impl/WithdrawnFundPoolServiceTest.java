package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.repository.api.IWithdrawnFundPoolRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link WithdrawnFundPoolService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
public class WithdrawnFundPoolServiceTest {

    private WithdrawnFundPoolService withdrawnFundPoolService;
    private IWithdrawnFundPoolRepository withdrawnFundPoolRepository;

    @Before
    public void setUp() {
        withdrawnFundPoolService = new WithdrawnFundPoolService();
        withdrawnFundPoolRepository = createMock(IWithdrawnFundPoolRepository.class);
        withdrawnFundPoolService.setWithdrawnFundPoolRepository(withdrawnFundPoolRepository);
    }

    @Test
    public void testFindAll() {
        List<WithdrawnFundPool> funds = Collections.singletonList(buildWithdrawnFundPool());
        expect(withdrawnFundPoolRepository.findAll()).andReturn(funds).once();
        replay(withdrawnFundPoolRepository);
        assertEquals(funds, withdrawnFundPoolService.getAdditionalFunds());
        verify(withdrawnFundPoolRepository);
    }

    private WithdrawnFundPool buildWithdrawnFundPool() {
        WithdrawnFundPool withdrawnFundPool = new WithdrawnFundPool();
        withdrawnFundPool.setName("Additional Fund");
        return withdrawnFundPool;
    }
}
