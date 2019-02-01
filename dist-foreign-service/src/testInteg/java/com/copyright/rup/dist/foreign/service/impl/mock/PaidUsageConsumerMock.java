package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.service.impl.usage.paid.PaidUsageConsumer;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Proxy for {@link PaidUsageConsumer} for synchronizing {@link PaidUsageConsumer#consume(List)} method
 * with the tests main thread.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/22/18
 *
 * @author Darya Baraukova
 */
public class PaidUsageConsumerMock extends PaidUsageConsumer {

    private CountDownLatch latch;

    @Override
    public void consume(List<PaidUsage> usages) {
        super.consume(usages);
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
