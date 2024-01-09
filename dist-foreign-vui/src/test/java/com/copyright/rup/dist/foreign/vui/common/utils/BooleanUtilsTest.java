package com.copyright.rup.dist.foreign.vui.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import org.junit.Test;

/**
 * Verifies {@link BooleanUtils}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/14/2021
 *
 * @author Aliaksandr Liakh
 */
public class BooleanUtilsTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(BooleanUtils.class);
    }

    @Test
    public void testToYNString() {
        assertNull(BooleanUtils.toYNString(null));
        assertEquals("Y", BooleanUtils.toYNString(true));
        assertEquals("N", BooleanUtils.toYNString(false));
    }
}
