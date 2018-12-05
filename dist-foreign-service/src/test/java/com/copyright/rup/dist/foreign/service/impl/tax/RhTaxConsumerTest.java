package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link RhTaxConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxConsumerTest {

    private IConsumer<Usage> rhTaxConsumer;
    private IRhTaxService rhTaxService;

    @Before
    public void setUp() {
        rhTaxService = createMock(IRhTaxService.class);
        rhTaxConsumer = new RhTaxConsumer();
        Whitebox.setInternalState(rhTaxConsumer, rhTaxService);
    }

    @Test
    public void testConsume() {
        Usage usage = new Usage();
        rhTaxService.applyRhTaxCountry(usage);
        expectLastCall().once();
        replay(rhTaxService);
        rhTaxConsumer.consume(usage);
        verify(rhTaxService);
    }

    @Test
    public void testConsumeNull() {
        replay(rhTaxService);
        rhTaxConsumer.consume(null);
        verify(rhTaxService);
    }
}
