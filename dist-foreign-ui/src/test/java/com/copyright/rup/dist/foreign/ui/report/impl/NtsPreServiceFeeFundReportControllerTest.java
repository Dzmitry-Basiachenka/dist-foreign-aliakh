package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link NtsPreServiceFeeFundReportController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportControllerTest {

    private IFundPoolService fundPoolService;
    private NtsPreServiceFeeFundReportController controller;

    @Before
    public void setUp() {
        fundPoolService = createMock(IFundPoolService.class);
        controller = new NtsPreServiceFeeFundReportController();
        Whitebox.setInternalState(controller, fundPoolService);
    }

    @Test
    public void testGetCsvStreamSource() {
        //TODO: {dbasiachenka} implement
    }

    @Test
    public void testGetPreServiceSeeFunds() {
        List<FundPool> preServiceFeeFunds = List.of(new FundPool());
        expect(fundPoolService.getFundPools("NTS")).andReturn(preServiceFeeFunds).once();
        replay(fundPoolService);
        assertSame(preServiceFeeFunds, controller.getPreServiceFeeFunds());
        verify(fundPoolService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsPreServiceFeeFundReportWidget.class));
    }
}
