package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;

/**
 * Widget for applied filters on Audit tab for AACL product family.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/14/2023
 *
 * @author Stepan Karakhanov
 */
public class AaclAuditAppliedFilterWidget extends CommonAuditAppliedFilterWidget {

    private static final long serialVersionUID = -4501260858745972136L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonAuditFilterController}
     */
    public AaclAuditAppliedFilterWidget(ICommonAuditFilterController controller) {
        super(controller);
    }

    @Override
    public void refreshFilterPanel(AuditFilter filter) {
        removeAll();
        var layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertRroAccountNumbersToRroNames(filter.getRhAccountNumbers()),
                "label.rightsholders", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getStatuses(), "label.statuses", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getUsagePeriod, filter, "label.usage_period"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getCccEventId, filter, "label.event_id"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getDistributionName, filter, "label.distribution_name"),
                layout);
        }
        add(layout);
    }
}
