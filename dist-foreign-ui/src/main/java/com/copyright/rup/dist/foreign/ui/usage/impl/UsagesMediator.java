package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesMediator;

import com.vaadin.ui.Button;

/**
 * Mediator for the usages widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/22/17
 *
 * @author Ihar Suvorau
 */
class UsagesMediator implements IUsagesMediator {

    private Button loadUsageBatchButton;
    private Button loadFundPoolButton;
    private Button loadResearchedUsagesButton;
    private Button deleteUsageButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;
    private Button assignClassificationButton;

    @Override
    public void applyPermissions() {
        loadUsageBatchButton.setVisible(ForeignSecurityUtils.hasLoadUsagePermission());
        loadFundPoolButton.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission());
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission());
        deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission());
        assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission());
    }

    @Override
    public void onProductFamilyChanged(String productFamily) {
        boolean isFasFas2ProductFamily = FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(productFamily);
        boolean isNtsProductFamily = FdaConstants.NTS_PRODUCT_FAMILY.equals(productFamily);
        loadUsageBatchButton.setVisible(ForeignSecurityUtils.hasLoadUsagePermission() && isFasFas2ProductFamily);
        loadFundPoolButton.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission() && isNtsProductFamily);
        loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission()
            && isFasFas2ProductFamily);
        sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission()
            && isFasFas2ProductFamily);
        assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission()
            && isNtsProductFamily);
        deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
        addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
    }

    void setLoadUsageBatchButton(Button loadUsageBatchButton) {
        this.loadUsageBatchButton = loadUsageBatchButton;
    }

    void setLoadFundPoolButton(Button loadFundPoolButton) {
        this.loadFundPoolButton = loadFundPoolButton;
    }

    void setLoadResearchedUsagesButton(Button loadResearchedUsagesButton) {
        this.loadResearchedUsagesButton = loadResearchedUsagesButton;
    }

    void setDeleteUsageButton(Button deleteUsageButton) {
        this.deleteUsageButton = deleteUsageButton;
    }

    void setAddToScenarioButton(Button addToScenarioButton) {
        this.addToScenarioButton = addToScenarioButton;
    }

    void setSendForResearchButton(Button sendForResearchButton) {
        this.sendForResearchButton = sendForResearchButton;
    }

    void setAssignClassificationButton(Button assignClassificationButton) {
        this.assignClassificationButton = assignClassificationButton;
    }
}
