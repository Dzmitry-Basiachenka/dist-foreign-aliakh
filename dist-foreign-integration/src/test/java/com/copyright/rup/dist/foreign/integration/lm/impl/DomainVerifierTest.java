package com.copyright.rup.dist.foreign.integration.lm.impl;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.integration.lm.impl.domain.LiabilityDetailMessage;

import org.junit.Test;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Ihar Suvorau
 */
public class DomainVerifierTest {

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(LiabilityDetailMessage.class);
    }
}
