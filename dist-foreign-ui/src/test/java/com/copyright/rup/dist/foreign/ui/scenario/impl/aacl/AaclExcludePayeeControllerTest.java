package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclExcludePayeeController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/06/2020
 *
 * @author Ihar Suvorau
 */
public class AaclExcludePayeeControllerTest {

    private IAaclExcludePayeeController controller;
    private IAaclUsageService usageService;
    private IAaclExcludePayeeFilterController filterController;
    private IAaclExcludePayeeFilterWidget filterWidget;
    private Scenario scenario;

    @Before
    public void setUp() {
        usageService = createMock(IAaclUsageService.class);
        controller = new AaclExcludePayeeController();
        filterController = createMock(IAaclExcludePayeeFilterController.class);
        filterWidget = createMock(IAaclExcludePayeeFilterWidget.class);
        scenario = new Scenario();
        scenario.setId("68f9965b-b4f1-426f-be82-03f894deac73");
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, filterController);
    }

    @Test
    public void testGetPayeeTotalHolders() {
        IAaclExcludePayeeWidget widget = createMock(IAaclExcludePayeeWidget.class);
        Whitebox.setInternalState(controller, widget);
        List<PayeeTotalHolder> payeeTotalHolders = Collections.singletonList(new PayeeTotalHolder());
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(usageService.getPayeeTotalHoldersByFilter(scenario, filter)).andReturn(payeeTotalHolders).once();
        replay(usageService, widget, filterWidget, filterController);
        assertEquals(payeeTotalHolders, controller.getPayeeTotalHolders());
        verify(usageService, widget, filterWidget, filterController);
    }
}
