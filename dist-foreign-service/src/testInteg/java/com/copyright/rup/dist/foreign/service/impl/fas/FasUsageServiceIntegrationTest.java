package com.copyright.rup.dist.foreign.service.impl.fas;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Verifies {@link FasUsageService}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/13/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
public class FasUsageServiceIntegrationTest {

    @Autowired
    private IFasUsageService fasUsageService;

    @Test
    public void testGetRecordsThreshold() {
        assertEquals(10000, fasUsageService.getRecordsThreshold());
    }
}
