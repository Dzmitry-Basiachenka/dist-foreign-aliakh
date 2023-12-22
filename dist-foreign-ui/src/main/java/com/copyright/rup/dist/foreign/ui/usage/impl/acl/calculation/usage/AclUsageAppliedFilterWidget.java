package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
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
public class AclUsageAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = -3313668266292683499L;

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
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithSingleValue(AclUsageFilter::getUsageBatchName, filter, "label.usage_batch_name"),
                layout);
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortDetailLicenseeClasses(filter.getDetailLicenseeClasses()),
                "label.detail_licensee_classes", DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortAggregateLicenseeClasses(filter.getAggregateLicenseeClasses()),
                "label.aggregate_licensee_classes", AggregateLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortPublicationTypes(filter.getPubTypes()), "label.pub_types",
                PublicationType::getNameAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getReportedTypeOfUses()),
                "label.reported_types_of_use", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(AclUsageFilter::getUsageOrigin, filter, "label.usage_origin"),
                layout);
            addLabel(createLabelWithSingleValue(AclUsageFilter::getChannel, filter, "label.channel"), layout);
            addLabel(createLabelWithSingleValue(AclUsageFilter::getTypeOfUse, filter, "label.type_of_use"), layout);
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
            addLabel(createLabelWithSingleValue(aclUsageFilter -> convertFilterOperatorToString(
                aclUsageFilter.getContentUnitPriceFlagExpression().getOperator()), filter,
                "label.content_unit_price_flag"), layout);
            addLabel(createLabelWithSingleValue(aclUsageFilter -> convertFilterOperatorToString(
                aclUsageFilter.getWorkDeletedFlagExpression().getOperator()), filter, "label.mdwms_deleted"), layout);
            addLabel(createLabelWithOperator(filter.getAnnualizedCopiesExpression(), "label.annualized_copies_from",
                "label.annualized_copies_to"), layout);
        }
        setContent(layout);
    }
}
