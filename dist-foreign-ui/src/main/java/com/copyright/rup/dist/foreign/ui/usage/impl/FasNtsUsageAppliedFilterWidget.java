package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;

import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

/**
 * Widget for applied filters on Usage tab for FAS, FAS2 and NTS product families.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/13/2023
 *
 * @author Stepan Karakhanov
 */
public class FasNtsUsageAppliedFilterWidget extends CommonUsageAppliedFilterWidget implements IDateFormatter {

    private static final long serialVersionUID = -6615888316116581454L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonUsageFilterController}
     */
    public FasNtsUsageAppliedFilterWidget(ICommonUsageFilterController controller) {
        super(controller);
    }

    @Override
    public void refreshFilterPanel(UsageFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUsageBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(convertRroAccountNumbersToRroNames(filter.getRhAccountNumbers()),
                "label.rros", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UsageFilter::getPaymentDate, filter), filter,
                "label.payment_date_to"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getUsageStatus, filter, "label.status"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getFiscalYear, filter, "label.fiscal_year_to"), layout);
        }
        setContent(layout);
    }

    private Function<UsageFilter, ?> getFunctionForDate(Function<UsageFilter, LocalDate> function, UsageFilter filter) {
        return Objects.nonNull(function.apply(filter))
            ? value -> toShortFormat(function.apply(filter))
            : function;
    }
}
