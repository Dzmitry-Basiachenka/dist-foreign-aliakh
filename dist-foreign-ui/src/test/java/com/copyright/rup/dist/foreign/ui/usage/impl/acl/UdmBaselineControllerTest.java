package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Verifies {@link UdmBaselineController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineControllerTest {

    private final UdmBaselineController udmBaselineController = new UdmBaselineController();

    @Test
    public void testLoadBeans() {
        assertEquals(0, udmBaselineController.getBeansCount());
    }

    @Test
    public void testGetBeansCount() {
        assertEquals(new ArrayList<>(), udmBaselineController.loadBeans(0, 1, null));
    }
}
