package com.copyright.rup.dist.foreign.domain.common.util;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;

import org.junit.Test;

/**
 * Verifies {@link ForeignLogUtils}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
public class ForeignLogUtilsTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(ForeignLogUtils.class);
    }

    @Test
    public void testScenario() {
        Scenario scenario = new Scenario();
        scenario.setName("Scenario name");
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertEquals("ScenarioName='Scenario name', Status='SENT_TO_LM'",
            ForeignLogUtils.scenario(scenario).toString());
    }

    @Test
    public void testScenarioNullValue() {
        assertEquals("Scenario={NULL}", ForeignLogUtils.scenario(null).toString());
    }
}
