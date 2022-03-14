package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Widget for applied ACL grant detail filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/11/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailAppliedFilterWidget extends CommonAclAppliedFilterPanel {

    /**
     * Constructor.
     */
    public AclGrantDetailAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "acl-grant-details-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link AclGrantDetailFilter}
     */
    public void refreshFilterPanel(AclGrantDetailFilter filter) {
        VerticalLayout layout = initLayout();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(filter.getGrantSetNames(), "label.grant_sets", String::valueOf),
                layout);
            addLabel(createLabelWithMultipleValues(filter.getLicenseTypes(), "label.license_types", String::valueOf),
                layout);
            addLabel(createLabelWithMultipleValues(filter.getGrantStatuses(), "label.grant_statuses", String::valueOf),
                layout);
            addLabel(createLabelWithMultipleValues(filter.getTypeOfUses(), "label.types_of_use", String::valueOf),
                layout);
            addLabel(createLabelWithSingleValue(AclGrantDetailFilter::getGrantSetPeriod, filter,
                "label.grant_set_period"), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithOperator(filter.getRhAccountNumberExpression(), "label.rh_account_number_from",
                "label.rh_account_number_to"), layout);
            addLabel(createLabelWithOperator(filter.getRhNameExpression(), "label.rh_name", StringUtils.EMPTY), layout);
            addLabel(createLabelWithSingleValue(aclGrantDetailFilter -> convertFilterOperatorToString(
                aclGrantDetailFilter.getEligibleExpression().getOperator()), filter, "label.eligible"), layout);
            addLabel(createLabelWithSingleValue(aclGrantDetailFilter -> convertFilterOperatorToString(
                aclGrantDetailFilter.getEditableExpression().getOperator()), filter, "label.editable"), layout);
        }
        setContent(layout);
    }
}
