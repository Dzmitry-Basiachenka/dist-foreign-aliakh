package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Widget for applied udm usage filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Anton Azarenka
 */
public class UdmUsageAppliedFilterWidget extends CommonUdmAppliedFilterPanel {

    private final IUdmUsageFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageFilterController}.
     */
    public UdmUsageAppliedFilterWidget(IUdmUsageFilterController controller) {
        super();
        this.controller = controller;
        VaadinUtils.addComponentStyle(this, "udm-usage-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UdmUsageFilter}
     */
    public void refreshFilterPanel(UdmUsageFilter filter) {
        VerticalLayout layout = initLayout();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUdmBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getPeriods(), "label.periods", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getUsageStatus, filter, "label.status"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getUdmUsageOrigin, filter, "label.usage_origin"),
                layout);
            addLabel(createLabelWithMultipleValues(filter.getAssignees(), "label.assignees", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getReportedPubTypes(), "label.reported_pub_types",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getPubFormats(), "label.publication_formats",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getDetailLicenseeClasses(), "label.detail_licensee_classes",
                DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(filter.getReportedTypeOfUses(), "label.types_of_use",
                String::valueOf), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getUsageDateFrom, filter), filter,
                "label.usage_date_from"), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getUsageDateTo, filter), filter,
                "label.usage_date_to"), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getSurveyStartDateFrom, filter),
                filter, "label.survey_start_date_from"), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getSurveyStartDateTo, filter),
                filter, "label.survey_start_date_to"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getChannel, filter, "label.channel"), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getCompanyId, filter, "label.company_id"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getCompanyName, filter, "label.company_name"), layout);
            addLabel(
                createLabelWithSingleValue(UdmUsageFilter::getSurveyCountry, filter, "label.survey_country"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getLanguage, filter, "label.language"), layout);
            addLabel(createLabelWithOperator(filter.getAnnualMultiplierExpression(), "label.annual_multiplier_from",
                "label.annual_multiplier_to"), layout);
            addLabel(createLabelWithOperator(filter.getAnnualizedCopiesExpression(), "label.annualized_copies_from",
                "label.annualized_copies_to"), layout);
            addLabel(createLabelWithOperator(filter.getStatisticalMultiplierExpression(),
                "label.statistical_multiplier_from", "label.statistical_multiplier_to"), layout);
            addLabel(createLabelWithOperator(filter.getQuantityExpression(), "label.quantity_from",
                "label.quantity_to"), layout);
        }
        setContent(layout);
    }

    private Set<String> convertBatchIdsToBatchNames(Set<String> batchesIds) {
        return controller.getUdmBatches()
            .stream()
            .filter(udmBatch -> batchesIds.contains(udmBatch.getId()))
            .map(UdmBatch::getName)
            .collect(Collectors.toSet());
    }

    private Function<UdmUsageFilter, ?> getFunctionForDate(Function<UdmUsageFilter, LocalDate> function,
                                                           UdmUsageFilter filter) {
        return Objects.nonNull(function.apply(filter))
            ? value -> DateUtils.format(function.apply(filter))
            : function;
    }
}
