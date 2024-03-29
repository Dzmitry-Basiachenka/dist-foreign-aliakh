package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageAppliedFilterWidget;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for applied filters on Usage tab for AACL product family.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/13/2023
 *
 * @author Stepan Karakhanov
 */
public class AaclUsageAppliedFilterWidget extends CommonUsageAppliedFilterWidget {

    private static final long serialVersionUID = 4284214902728462038L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonUsageFilterController}
     */
    public AaclUsageAppliedFilterWidget(ICommonUsageFilterController controller) {
        super(controller);
    }

    @Override
    public void refreshFilterPanel(UsageFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUsageBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getUsageStatus, filter, "label.status"), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getUsagePeriod, filter, "label.usage_period"), layout);
        }
        setContent(layout);
    }
}
