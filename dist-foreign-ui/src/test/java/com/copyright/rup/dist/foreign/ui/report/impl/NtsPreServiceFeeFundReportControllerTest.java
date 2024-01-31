package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Before;
import org.junit.Test;

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

    private NtsPreServiceFeeFundReportController controller;

    @Before
    public void setUp() {
        controller = new NtsPreServiceFeeFundReportController();
    }

    @Test
    public void testGetCsvStreamSource() {
        //TODO: {dbasiachenka} implement
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsPreServiceFeeFundReportWidget.class));
    }
}
