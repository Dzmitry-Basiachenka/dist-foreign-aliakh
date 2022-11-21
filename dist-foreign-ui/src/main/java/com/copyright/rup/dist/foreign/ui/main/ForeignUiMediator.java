package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.api.IMainWidgetController;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.google.common.collect.ImmutableSet;
import com.vaadin.ui.ComboBox;

import java.util.Set;

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

    private static final Set<String> PRODUCT_FAMILIES = ImmutableSet.of(
        FdaConstants.FAS_PRODUCT_FAMILY, FdaConstants.CLA_FAS_PRODUCT_FAMILY, FdaConstants.NTS_PRODUCT_FAMILY,
        FdaConstants.AACL_PRODUCT_FAMILY, FdaConstants.SAL_PRODUCT_FAMILY, FdaConstants.ACL_PRODUCT_FAMILY,
        FdaConstants.ACLCI_PRODUCT_FAMILY);

    private ComboBox<String> productFamilyComboBox;
    private IReportController reportController;
    private IProductFamilyProvider productFamilyProvider;
    private IMainWidgetController controller;

    @Override
    public void applyPermissions() {
        if (ForeignSecurityUtils.hasResearcherPermission()) {
            productFamilyComboBox.setItems(FdaConstants.ACL_PRODUCT_FAMILY);
            productFamilyProvider.setProductFamily(FdaConstants.ACL_PRODUCT_FAMILY);
            productFamilyComboBox.setSelectedItem(FdaConstants.ACL_PRODUCT_FAMILY);
            controller.onProductFamilyChanged();
            reportController.onProductFamilyChanged();
        } else {
            productFamilyComboBox.setItems(PRODUCT_FAMILIES);
            productFamilyComboBox.setSelectedItem(FdaConstants.FAS_PRODUCT_FAMILY);
        }
    }

    public void setController(IMainWidgetController controller) {
        this.controller = controller;
    }

    public void setProductFamilyComboBox(ComboBox<String> productFamilyComboBox) {
        this.productFamilyComboBox = productFamilyComboBox;
    }

    public void setReportController(IReportController reportController) {
        this.reportController = reportController;
    }

    public void setProductFamilyProvider(IProductFamilyProvider productFamilyProvider) {
        this.productFamilyProvider = productFamilyProvider;
    }
}
