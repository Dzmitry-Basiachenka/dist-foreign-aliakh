package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AclCommonReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/06/2022
 *
 * @author Ihar Suvorau
 */
public class AclCommonReportControllerTest {

    private final AclCommonReportController controller = new AclCommonReportController() {
        @Override
        public IStreamSource getCsvStreamSource() {
            return null;
        }
    };

    private IAclScenarioService scenarioService;

    @Before
    public void setUp() {
        scenarioService = createMock(IAclScenarioService.class);
        Whitebox.setInternalState(controller, scenarioService);
    }

    @Test
    public void testGetScenarios() {
        List<AclScenario> scenarios = List.of(new AclScenario());
        expect(scenarioService.getScenariosByPeriod(202212)).andReturn(scenarios).once();
        replay(scenarioService);
        assertSame(scenarios, controller.getScenarios(202212));
        verify(scenarioService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202212, 202206);
        expect(scenarioService.getScenarioPeriods()).andReturn(periods).once();
        replay(scenarioService);
        assertSame(periods, controller.getPeriods());
        verify(scenarioService);
    }
}
