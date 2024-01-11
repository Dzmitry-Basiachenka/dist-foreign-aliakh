package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link FasScenariosController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class FasScenariosControllerTest {

    private FasScenariosController scenariosController;
    private IFasExcludePayeeController excludePayeesController;

    @Before
    public void setUp() {
        excludePayeesController = createMock(IFasExcludePayeeController.class);
        scenariosController = new FasScenariosController();
        Whitebox.setInternalState(scenariosController, excludePayeesController);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(scenariosController.instantiateWidget(), instanceOf(FasScenariosWidget.class));
    }

    @Test
    public void testOnExcludePayeesButtonClicked() {
        mockStatic(Windows.class);
        FasExcludePayeeWidget widget = new FasExcludePayeeWidget();
        expect(excludePayeesController.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replay(excludePayeesController, Windows.class);
        scenariosController.onExcludePayeesButtonClicked();
        verify(excludePayeesController, Windows.class);
    }
}
