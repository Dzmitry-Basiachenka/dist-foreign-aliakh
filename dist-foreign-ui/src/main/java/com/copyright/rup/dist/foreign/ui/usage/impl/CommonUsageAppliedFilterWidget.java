package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.sal.SalUsageFilterController;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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
public class CommonUsageAppliedFilterWidget extends CommonAppliedFilterPanel implements IDateFormatter {

    private final ICommonUsageFilterController controller;
    private final String rightsholderLabel;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonUsageFilterController}
     */
    public CommonUsageAppliedFilterWidget(ICommonUsageFilterController controller) {
        super();
        this.controller = controller;
        VaadinUtils.addComponentStyle(this, "aclci-usage-filter-panel-widget");
        //TODO: avoid this condition
        if (controller instanceof SalUsageFilterController) {
            rightsholderLabel = "label.rightsholders";
        } else {
            rightsholderLabel = "label.rros";
        }
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UsageFilter}
     */
    public void refreshFilterPanel(UsageFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUsageBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(convertRroAccountNumbersToRroNames(filter.getRhAccountNumbers()),
                rightsholderLabel, String::valueOf), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UsageFilter::getPaymentDate, filter), filter,
                "label.payment_date_to"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getUsageStatus, filter, "label.status"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getFiscalYear, filter, "label.fiscal_year_to"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getUsagePeriod, filter, "label.usage_period"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getSalDetailType, filter, "label.detail_type"), layout);
        }
        setContent(layout);
    }

    private Set<String> convertBatchIdsToBatchNames(Set<String> batchesIds) {
        if (batchesIds == null || batchesIds.isEmpty()) {
            return Set.of();
        }
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

    private Function<UsageFilter, ?> getFunctionForDate(Function<UsageFilter, LocalDate> function, UsageFilter filter) {
        return Objects.nonNull(function.apply(filter))
            ? value -> toShortFormat(function.apply(filter))
            : function;
    }
}
