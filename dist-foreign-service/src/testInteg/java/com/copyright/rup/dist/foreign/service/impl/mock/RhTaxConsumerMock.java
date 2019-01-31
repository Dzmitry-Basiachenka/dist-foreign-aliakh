package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.tax.RhTaxConsumer;

import java.util.concurrent.CountDownLatch;

/**
 * Proxy for {@link RhTaxConsumer} for synchronizing {@link RhTaxConsumer#consume(Usage)} method
 * with the tests main thread.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/30/2019
 *
 * @author Aliaksandr Liakh
 */
public class RhTaxConsumerMock extends RhTaxConsumer {

    private CountDownLatch latch;

    @Override
    public void consume(Usage usage) {
        super.consume(usage);
        if (null != latch) {
            latch.countDown();
        }
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
