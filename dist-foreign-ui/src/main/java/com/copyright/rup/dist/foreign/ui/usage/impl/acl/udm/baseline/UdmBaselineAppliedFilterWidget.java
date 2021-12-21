package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for applied udm baseline filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/19/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineAppliedFilterWidget extends CommonUdmAppliedFilterPanel {

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
        VerticalLayout layout = initLayout();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(filter.getPeriods(), "label.periods", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getUdmUsageOrigin, filter, "label.usage_origin"),
                layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getChannel, filter, "label.channel"), layout);
            addLabel(createLabelWithMultipleValues(filter.getDetailLicenseeClasses(), "label.detail_licensee_classes",
                DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(filter.getReportedTypeOfUses(), "label.types_of_use",
                String::valueOf), layout);
            addLabel(
                createLabelWithMultipleValues(filter.getAggregateLicenseeClasses(), "label.aggregate_licensee_classes",
                    AggregateLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getWrWrkInst, filter, "label.wr_wrk_inst"), layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getSystemTitle, filter, "label.system_title"),
                layout);
            addLabel(createLabelWithSingleValue(UdmBaselineFilter::getUsageDetailId, filter, "label.usage_detail_id"),
                layout);
            addLabel(
                createLabelWithSingleValue(UdmBaselineFilter::getSurveyCountry, filter, "label.survey_country"),
                layout);
            addLabel(createLabelWithOperator(filter.getAnnualizedCopiesExpression(), "label.annualized_copies_from",
                "label.annualized_copies_to"), layout);
        }
        setContent(layout);
    }
}
