package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link AclScenarioService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioServiceTest {

    private final IAclScenarioService aclScenarioService = new AclScenarioService();

    @Test
    public void testGetScenarios() {
        //TODO {dbasiachenka} implement
        assertEquals(Collections.emptyList(), aclScenarioService.getScenarios());
    }
}
