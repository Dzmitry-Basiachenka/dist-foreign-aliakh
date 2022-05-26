package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Verifies {@link AclUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/26/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class AclUsageServiceIntegrationTest {

    @Autowired
    private IAclUsageService aclUsageService;

    @Test
    public void testGetRecordThreshold() {
        assertEquals(10000, aclUsageService.getRecordThreshold());
    }
}
