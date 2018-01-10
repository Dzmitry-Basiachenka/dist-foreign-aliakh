package com.copyright.rup.dist.foreign.integration;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.LiabilityDetail;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.LiabilityDetailsWrapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class DomainVerifierTest {

    private Class classToVerify;

    /**
     * Constructs new test for given class.
     *
     * @param classToVerify class to verify
     */
    public DomainVerifierTest(Class classToVerify) {
        this.classToVerify = classToVerify;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = new Object[][]{
            {LiabilityDetail.class},
            {LiabilityDetailsWrapper.class}
        };
        return Arrays.asList(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(classToVerify);
    }
}
