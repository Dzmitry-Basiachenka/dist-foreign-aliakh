package com.copyright.rup.dist.foreign.vui.main;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.select.Select;

import java.util.List;

/**
 * Mediator for the {@link ForeignUi}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/11/21
 *
 * @author Anton Azarenka
 */
public class ForeignUiMediator implements IMediator {

    private static final List<String> PRODUCT_FAMILIES = List.of(
        FdaConstants.AACL_PRODUCT_FAMILY,
        FdaConstants.FAS_PRODUCT_FAMILY,
        FdaConstants.CLA_FAS_PRODUCT_FAMILY,
        FdaConstants.NTS_PRODUCT_FAMILY
    );

    private Select<String> productFamilySelect;
    private IProductFamilyProvider productFamilyProvider;
    private IMainWidgetController controller;

    @Override
    public void applyPermissions() {
        if (ForeignSecurityUtils.hasResearcherPermission()) {
            //TODO {vaadin23} should be change to ACL PF during ACL tab migration
            productFamilySelect.setItems(FdaConstants.FAS_PRODUCT_FAMILY);
            productFamilyProvider.setProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
            productFamilySelect.setValue(FdaConstants.FAS_PRODUCT_FAMILY);
            controller.onProductFamilyChanged();
        } else {
            productFamilySelect.setItems(PRODUCT_FAMILIES);
            productFamilySelect.setValue(FdaConstants.FAS_PRODUCT_FAMILY);
        }
    }

    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }

    public void setProductFamilySelect(Select<String> productFamilySelect) {
        this.productFamilySelect = productFamilySelect;
    }

    public void setProductFamilyProvider(IProductFamilyProvider productFamilyProvider) {
        this.productFamilyProvider = productFamilyProvider;
    }
}
