package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Widget for applied udm baseline filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/19/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = -4597508259261266305L;

    /**
     * Constructor.
     */
    public UdmBaselineAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "udm-baseline-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UdmBaselineFilter}
     */
    public void refreshFilterPanel(UdmBaselineFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getUdmUsageOrigin, filter, "label.usage_origin"),
                layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getChannel, filter, "label.channel"), layout);
            addLabel(createLabelWithMultipleValues(sortDetailLicenseeClasses(filter.getDetailLicenseeClasses()),
                "label.detail_licensee_classes", DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortAggregateLicenseeClasses(filter.getAggregateLicenseeClasses()),
                "label.aggregate_licensee_classes", AggregateLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getReportedTypeOfUses()),
                "label.reported_types_of_use", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getTypeOfUse, filter, "label.type_of_use"), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithOperator(filter.getSystemTitleExpression(), "label.system_title",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getUsageDetailIdExpression(), "label.usage_detail_id",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getSurveyCountryExpression(), "label.survey_country",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getAnnualizedCopiesExpression(), "label.annualized_copies_from",
                "label.annualized_copies_to"), layout);
        }
        setContent(layout);
    }
}
