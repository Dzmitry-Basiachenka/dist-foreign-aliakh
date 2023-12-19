package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for applied ACL fund pool filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Mikita Maistrenka
 */
public class AclFundPoolAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = -927290636844095126L;

    /**
     * Constructor.
     */
    public AclFundPoolAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "acl-fund-pool-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link AclFundPoolDetailFilter}
     */
    public void refreshFilterPanel(AclFundPoolDetailFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getFundPoolNames()),
                "label.fund_pool.names", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortAggregateLicenseeClasses(filter.getAggregateLicenseeClasses()),
                "label.aggregate_licensee_classes", AggregateLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortDetailLicenseeClasses(filter.getDetailLicenseeClasses()),
                "label.detail_licensee_classes", DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithSingleValue(AclFundPoolDetailFilter::getLicenseType, filter, "label.license_type"),
                layout);
            addLabel(createLabelWithSingleValue(AclFundPoolDetailFilter::getFundPoolType, filter,
                    "label.fund_pool_type"), layout);
        }
        setContent(layout);
    }
}
