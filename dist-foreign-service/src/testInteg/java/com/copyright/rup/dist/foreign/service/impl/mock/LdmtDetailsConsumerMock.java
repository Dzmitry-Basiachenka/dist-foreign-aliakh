package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.LdmtDetailsConsumer;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Mock for {@link LdmtDetailsConsumer}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetailsConsumerMock extends LdmtDetailsConsumer {

    private CountDownLatch latch;

    @Override
    public void consume(List<LdmtDetail> ldmtDetails) {
        super.consume(ldmtDetails);
        if (null != latch) {
            latch.countDown();
        }
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
