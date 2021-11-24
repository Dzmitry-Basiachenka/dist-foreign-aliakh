package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link UdmBaselineValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueControllerTest {

    private final UdmBaselineValueController udmBaselineValueController = new UdmBaselineValueController();
    private IUdmBaselineValueFilterController filterController;
    private IUdmBaselineValueFilterWidget filterWidget;

    @Before
    public void setUp() {
        filterController = createMock(IUdmBaselineValueFilterController.class);
        filterWidget = createMock(IUdmBaselineValueFilterWidget.class);
        Whitebox.setInternalState(udmBaselineValueController, filterController);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(udmBaselineValueController.instantiateWidget());
    }

    @Test
    public void testInitBaselineFilterWidget() {
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(filterController);
        udmBaselineValueController.initBaselineValuesFilterWidget();
        verify(filterController);
    }
}
