package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.test.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/27/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class AaclDomainVerifierTest {

    private final Class<?> clazz;

    /**
     * Constructor.
     *
     * @param clazz class to verify
     */
    public AaclDomainVerifierTest(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = {
            {AaclUsage.class},
            {Scenario.AaclFields.class},
            {AaclClassifiedUsage.class},
        };
        return List.of(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(clazz);
    }
}
