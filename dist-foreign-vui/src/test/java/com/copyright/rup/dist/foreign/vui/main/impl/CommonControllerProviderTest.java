package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;

/**
 * Verifies {@link CommonControllerProvider}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/6/19
 *
 * @author Stanislau Rudak
 */
public class CommonControllerProviderTest {

    private ITestController firstControllerMock;
    private ITestController secondControllerMock;
    private IProductFamilyProvider productFamilyProviderMock;
    private TestCommonControllerProvider controllerProvider;

    @Before
    public void setUp() {
        firstControllerMock = createMock(ITestController.class);
        secondControllerMock = createMock(ITestController.class);
        productFamilyProviderMock = createMock(IProductFamilyProvider.class);
        controllerProvider = new TestCommonControllerProvider();
        Whitebox.setInternalState(controllerProvider, productFamilyProviderMock);
    }

    @Test
    public void testGetController() {
        expect(productFamilyProviderMock.getSelectedProductFamily()).andReturn("FAS").once();
        expect(productFamilyProviderMock.getSelectedProductFamily()).andReturn("FAS2").once();
        expect(productFamilyProviderMock.getSelectedProductFamily()).andReturn("NTS").once();
        replay(firstControllerMock, secondControllerMock, productFamilyProviderMock);
        assertSame(firstControllerMock, controllerProvider.getController().get());
        assertSame(firstControllerMock, controllerProvider.getController().get());
        assertSame(secondControllerMock, controllerProvider.getController().get());
        verify(firstControllerMock, secondControllerMock, productFamilyProviderMock);
    }

    private interface ITestController extends IController {
    }

    private class TestCommonControllerProvider extends CommonControllerProvider<ITestController> {

        @Override
        protected Map<String, ITestController> getProductFamilyToControllerMap() {
            return ImmutableMap.of(
                "FAS", firstControllerMock,
                "FAS2", firstControllerMock,
                "NTS", secondControllerMock
            );
        }
    }
}
