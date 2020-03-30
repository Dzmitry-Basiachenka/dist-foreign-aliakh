package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/31/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenariosControllerTest {

    private AaclScenariosController scenariosController;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        licenseeClassService = createMock(ILicenseeClassService.class);
        scenariosController = new AaclScenariosController();
        Whitebox.setInternalState(scenariosController, licenseeClassService);
    }

    @Test
    public void testGetDetailLicenseeClassesByScenarioId() {
        String scenarioId = "43e6b6e8-4c80-40ba-9836-7b27b2bbca5f";
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClassesByScenarioId(scenarioId))
            .andReturn(detailLicenseeClasses).once();
        replay(licenseeClassService);
        assertSame(detailLicenseeClasses, scenariosController.getDetailLicenseeClassesByScenarioId(scenarioId));
        verify(licenseeClassService);
    }
}
