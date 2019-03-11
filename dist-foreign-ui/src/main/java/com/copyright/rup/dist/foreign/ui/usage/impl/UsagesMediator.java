package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageWorkflowStepEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesMediator;

import com.vaadin.ui.Button;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Set;

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
    private final Map<String, Set<UsageWorkflowStepEnum>> usageWorkflowSteps;

    /**
     * Constructor.
     *
     * @param usageWorkflowSteps map of usage workflow steps where key - Product Family,
     *                           value - set of {@link UsageWorkflowStepEnum}
     */
    public UsagesMediator(Map<String, Set<UsageWorkflowStepEnum>> usageWorkflowSteps) {
        this.usageWorkflowSteps = usageWorkflowSteps;
    }

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
        Set<UsageWorkflowStepEnum> usageSteps = usageWorkflowSteps.get(productFamily);
        if (CollectionUtils.isNotEmpty(usageSteps)) {
            loadUsageBatchButton.setVisible(ForeignSecurityUtils.hasLoadUsagePermission()
                && usageSteps.contains(UsageWorkflowStepEnum.LOAD_BATCH));
            loadFundPoolButton.setVisible(ForeignSecurityUtils.hasLoadFundPoolPermission()
                && usageSteps.contains(UsageWorkflowStepEnum.LOAD_FUNDPOOL));
            loadResearchedUsagesButton.setVisible(ForeignSecurityUtils.hasLoadResearchedUsagePermission()
                && usageSteps.contains(UsageWorkflowStepEnum.LOAD_RESEARCHED));
            sendForResearchButton.setVisible(ForeignSecurityUtils.hasSendForWorkResearchPermission()
                && usageSteps.contains(UsageWorkflowStepEnum.RESEARCH));
            assignClassificationButton.setVisible(ForeignSecurityUtils.hasAssignClassificationPermission()
                && usageSteps.contains(UsageWorkflowStepEnum.CLASSIFICATION));
            deleteUsageButton.setVisible(ForeignSecurityUtils.hasDeleteUsagePermission());
            addToScenarioButton.setVisible(ForeignSecurityUtils.hasCreateEditScenarioPermission());
        } else {
            loadUsageBatchButton.setVisible(false);
            loadFundPoolButton.setVisible(false);
            loadResearchedUsagesButton.setVisible(false);
            deleteUsageButton.setVisible(false);
            addToScenarioButton.setVisible(false);
            sendForResearchButton.setVisible(false);
        }
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
