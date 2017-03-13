package com.copyright.rup.dist.foreign.service.impl.util;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.context.DefaultRupContext;
import com.copyright.rup.common.context.RupContextHolder;
import com.copyright.rup.common.user.DefaultRupUser;
import com.copyright.rup.dist.common.test.TestUtils;

import org.junit.After;
import org.junit.Test;

/**
 * Verifies {@link RupContextUtils}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksei Pchelnikau
 */
public class RupContextUtilsTest {

    @After
    public void tierDown() {
        RupContextHolder.clearContext();
    }

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(RupContextUtils.class);
    }

    @Test
    public void testGetDefaultUserName() {
        assertEquals("SYSTEM", RupContextUtils.getUserName());
    }

    @Test
    public void testGetUserName() {
        String userName = "testUser";
        DefaultRupContext context = new DefaultRupContext();
        DefaultRupUser user = new DefaultRupUser();
        user.setUsername(userName);
        context.setActiveUser(user);
        RupContextHolder.setContext(context);
        assertEquals(userName, RupContextUtils.getUserName());
    }
}
