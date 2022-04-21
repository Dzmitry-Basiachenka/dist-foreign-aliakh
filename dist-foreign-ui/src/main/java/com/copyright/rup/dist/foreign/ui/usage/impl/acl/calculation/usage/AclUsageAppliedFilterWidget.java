package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;

/**
 * Widget for applied ACL usage filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/21/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageAppliedFilterWidget extends CommonAclAppliedFilterPanel {

    /**
     * Constructor.
     */
    public AclUsageAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "acl-usage-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link AclUsageFilter}
     */
    public void refreshFilterPanel(AclUsageFilter filter) {
        VerticalLayout layout = initLayout();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithSingleValue(AclUsageFilter::getUsageBatchName, filter, "label.usage_batch_name"),
                layout);
            addLabel(createLabelWithSingleValue(AclUsageFilter::getUsageOrigin, filter, "label.usage_origin"),
                layout);
            addLabel(createLabelWithSingleValue(AclUsageFilter::getChannel, filter, "label.channel"), layout);
            addLabel(createLabelWithMultipleValues(filter.getPeriods(), "label.periods", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getDetailLicenseeClasses(), "label.detail_licensee_classes",
                DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(filter.getAggregateLicenseeClasses(),
                "label.aggregate_licensee_classes", AggregateLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(filter.getPubTypes(), "label.pub_types",
                PublicationType::getNameAndDescription), layout);
            addLabel(createLabelWithMultipleValues(filter.getTypeOfUses(), "label.types_of_use",
                String::valueOf), layout);
            addLabel(createLabelWithOperator(filter.getUsageDetailIdExpression(), "label.usage_detail_id",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithOperator(filter.getSystemTitleExpression(), "label.system_title",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getSurveyCountryExpression(), "label.survey_country",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getContentUnitPriceExpression(), "label.content_unit_price_from",
                "label.content_unit_price_to"), layout);
            addLabel(createLabelWithOperator(filter.getAnnualizedCopiesExpression(), "label.annualized_copies_from",
                "label.annualized_copies_to"), layout);
        }
        setContent(layout);
    }
}
