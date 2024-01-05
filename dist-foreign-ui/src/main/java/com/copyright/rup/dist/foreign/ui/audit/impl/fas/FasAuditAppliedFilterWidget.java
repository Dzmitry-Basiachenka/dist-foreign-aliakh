package com.copyright.rup.dist.foreign.ui.audit.impl.fas;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditAppliedFilterWidget;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for applied filters on Audit tab for FAS product family.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/14/2023
 *
 * @author Stepan Karakhanov
 */
public class FasAuditAppliedFilterWidget extends CommonAuditAppliedFilterWidget {

    private static final long serialVersionUID = 2575637179143286942L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonAuditFilterController}
     */
    public FasAuditAppliedFilterWidget(ICommonAuditFilterController controller) {
        super(controller);
    }

    @Override
    public void refreshFilterPanel(AuditFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertRroAccountNumbersToRroNames(filter.getRhAccountNumbers()),
                "label.rightsholders", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getStatuses(), "label.statuses", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getCccEventId, filter, "label.event_id"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getDistributionName, filter, "label.distribution_name"),
                layout);
        }
        setContent(layout);
    }
}
