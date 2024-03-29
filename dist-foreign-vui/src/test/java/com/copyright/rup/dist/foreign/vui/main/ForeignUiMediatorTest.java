package com.copyright.rup.dist.foreign.vui.main;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.main.impl.MainWidgetController;
import com.copyright.rup.dist.foreign.vui.main.impl.ProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link ForeignUiMediator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/14/2021
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ForeignUiMediatorTest {

    private static final String FDA_ACCESS_APPLICATION = "FDA_ACCESS_APPLICATION";
    private static final List<String> PRODUCT_FAMILIES = List.of(
        FdaConstants.AACL_PRODUCT_FAMILY,
        FdaConstants.FAS_PRODUCT_FAMILY,
        FdaConstants.CLA_FAS_PRODUCT_FAMILY,
        FdaConstants.NTS_PRODUCT_FAMILY
    );

    private Select<String> productFamilyComboBox;
    private IProductFamilyProvider productFamilyProvider;
    private IMainWidgetController controller;
    private ForeignUiMediator mediator;

    @Before
    public void setUp() {
        productFamilyComboBox = new Select<>();
        controller = createMock(MainWidgetController.class);
        productFamilyProvider = new ProductFamilyProvider();
        mediator = new ForeignUiMediator();
        mediator.setProductFamilySelect(productFamilyComboBox);
        mediator.setProductFamilyProvider(productFamilyProvider);
        mediator.setController(controller);
    }

    @Test
    public void testApplyPermissionsResearcher() {
        mockResearcherPermissions();
        controller.onProductFamilyChanged();
        expectLastCall().once();
        replay(SecurityUtils.class, controller);
        mediator.applyPermissions();
        Collection<String> products = ((ListDataProvider<String>) productFamilyComboBox.getDataProvider()).getItems();
        assertEquals(List.of(FdaConstants.FAS_PRODUCT_FAMILY), products);
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyComboBox.getValue());
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyProvider.getSelectedProductFamily());
        verify(SecurityUtils.class, controller);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        Collection<String> products = ((ListDataProvider<String>) productFamilyComboBox.getDataProvider()).getItems();
        assertEquals(PRODUCT_FAMILIES, new ArrayList<>(products));
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyComboBox.getValue());
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyProvider.getSelectedProductFamily());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        Collection<String> products = ((ListDataProvider<String>) productFamilyComboBox.getDataProvider()).getItems();
        assertEquals(PRODUCT_FAMILIES, new ArrayList<>(products));
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyComboBox.getValue());
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyProvider.getSelectedProductFamily());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        Collection<String> products = ((ListDataProvider<String>) productFamilyComboBox.getDataProvider()).getItems();
        assertEquals(PRODUCT_FAMILIES, new ArrayList<>(products));
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyComboBox.getValue());
        assertEquals(FdaConstants.FAS_PRODUCT_FAMILY, productFamilyProvider.getSelectedProductFamily());
        verify(SecurityUtils.class);
    }

    private void mockResearcherPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_RESEARCHER_PERMISSION")).andReturn(true).once();
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
    }

    private void mockManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
    }

    private void mockSpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
    }
}
