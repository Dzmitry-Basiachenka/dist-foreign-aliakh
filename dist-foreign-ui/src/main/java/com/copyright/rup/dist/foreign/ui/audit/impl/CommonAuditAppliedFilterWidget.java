package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.sal.SalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

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
public class CommonAuditAppliedFilterWidget extends CommonAppliedFilterPanel {

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
    public void refreshFilterPanel(AuditFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertRroAccountNumbersToRroNames(filter.getRhAccountNumbers()),
                "label.rightsholders", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(
                convertSalLicenseesAccountNumbersToNames(filter.getLicenseeAccountNumbers()), "label.licensees",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getStatuses(), "label.statuses", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getSalDetailType, filter, "label.detail_type"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getUsagePeriod, filter, "label.usage_period"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getCccEventId, filter, "label.event_id"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getDistributionName, filter, "label.distribution_name"),
                layout);
        }
        setContent(layout);
    }

    private Set<String> convertSalLicenseesAccountNumbersToNames(Set<Long> accountNumbers) {
        if (controller instanceof SalAuditFilterController) {
            return ((SalAuditFilterController)controller).getSalLicensees()
                .stream()
                .filter(salLicensee -> accountNumbers.contains(salLicensee.getAccountNumber()))
                .map(salLicensee -> salLicensee.getAccountNumber() + " - " + salLicensee.getName())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Set.of();
    }

    private Set<String> convertBatchIdsToBatchNames(Set<String> batchesIds) {
        return controller.getUsageBatches()
            .stream()
            .filter(usageBatch -> batchesIds.contains(usageBatch.getId()))
            .map(UsageBatch::getName)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<String> convertRroAccountNumbersToRroNames(Set<Long> accountNumbers) {
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
