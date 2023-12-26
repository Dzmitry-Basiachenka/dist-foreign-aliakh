package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageAppliedFilterWidget;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for applied ACLCI usage filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/14/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciUsageAppliedFilterWidget extends CommonUsageAppliedFilterWidget {

    private static final long serialVersionUID = 7982177046744894058L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclciUsageFilterController}.
     */
    public AclciUsageAppliedFilterWidget(IAclciUsageFilterController controller) {
        super(controller);
        VaadinUtils.addComponentStyle(this, "aclci-usage-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UsageFilter}
     */
    @Override
    public void refreshFilterPanel(UsageFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUsageBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UsageFilter::getUsageStatus, filter, "label.status"), layout);
            addLabel(createLabelWithMultipleValues(sortLicenseTypes(filter.getLicenseTypes()),
                "label.license_types", String::valueOf), layout);
        }
        setContent(layout);
    }

    private Set<String> sortLicenseTypes(Set<AclciLicenseTypeEnum> licenseTypes) {
        return licenseTypes
            .stream()
            .map(AclciLicenseTypeEnum::name)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
