package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for applied filters on Audit tab for all product families.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/07/2023
 *
 * @author Stepan Karakhanov
 */
public abstract class CommonAuditAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = 7438808219835864740L;

    private final ICommonAuditFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonAuditFilterController}
     */
    public CommonAuditAppliedFilterWidget(ICommonAuditFilterController controller) {
        super();
        this.controller = controller;
        VaadinUtils.addComponentStyle(this, "aclci-usage-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link AuditFilter}
     */
    public abstract void refreshFilterPanel(AuditFilter filter);

    /**
     * Convert batches ids to batches names.
     *
     * @param batchesIds set of batches id
     * @return set of batches names
     */
    protected Set<String> convertBatchIdsToBatchNames(Set<String> batchesIds) {
        return controller.getUsageBatches()
            .stream()
            .filter(usageBatch -> batchesIds.contains(usageBatch.getId()))
            .map(UsageBatch::getName)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Convert RROs account numbers to names.
     *
     * @param accountNumbers set of RROs account numbers
     * @return set of RROs names
     */
    protected Set<String> convertRroAccountNumbersToRroNames(Set<Long> accountNumbers) {
        if (accountNumbers == null || accountNumbers.isEmpty()) {
            return Set.of();
        }
        return controller.getRightsholdersByAccountNumbers(accountNumbers)
            .stream()
            .filter(rightsholder -> accountNumbers.contains(rightsholder.getAccountNumber()))
            .sorted(Comparator.comparing(Rightsholder::getAccountNumber))
            .map(rightsholder -> rightsholder.getAccountNumber() + " - " + rightsholder.getName())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
