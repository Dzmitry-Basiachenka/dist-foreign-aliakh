package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link FasAuditController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/20/18
 *
 * @author Aliaksandr Radkevich
 */
public class FasAuditControllerTest {

    private FasAuditController controller;

    @Before
    public void setUp() {
        controller = new FasAuditController();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasAuditWidget.class));
    }
}
