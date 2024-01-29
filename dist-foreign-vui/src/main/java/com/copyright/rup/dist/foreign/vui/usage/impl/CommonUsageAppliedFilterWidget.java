package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.vui.common.CommonAppliedFilterPanel;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for applied filters on Usage tab for all product families.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/01/2023
 *
 * @author Stepan Karakhanov
 */
public abstract class CommonUsageAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = 6989782716499601246L;

    private final ICommonUsageFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonUsageFilterController}
     */
    public CommonUsageAppliedFilterWidget(ICommonUsageFilterController controller) {
        super();
        this.controller = controller;
        VaadinUtils.addComponentStyle(this, "usage-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UsageFilter}
     */
    public abstract void refreshFilterPanel(UsageFilter filter);

    /**
     * Convert batches ids to batches names.
     *
     * @param batchesIds set of batches id
     * @return set of batches names
     */
    protected Set<String> convertBatchIdsToBatchNames(Set<String> batchesIds) {
        if (CollectionUtils.isEmpty(batchesIds)) {
            return Set.of();
        }
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
        if (CollectionUtils.isEmpty(accountNumbers)) {
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
