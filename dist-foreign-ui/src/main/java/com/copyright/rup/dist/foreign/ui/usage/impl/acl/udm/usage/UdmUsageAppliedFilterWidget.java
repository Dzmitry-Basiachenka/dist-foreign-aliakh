package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmAppliedFilterPanel;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
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
        this.controller = controller;
        setWidth(265, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        setSizeFull();
        VaadinUtils.addComponentStyle(this, "udm-usages-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UdmUsageFilter}
     */
    public void refreshFilterPanel(UdmUsageFilter filter) {
        VerticalLayout layout = initFiltersPanelLayout();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUdmBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getPeriod, filter, "label.periods"), layout);
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
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getWrWrkInst, filter, "label.wr_wrk_inst"), layout);
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

    private void addLabel(Label label, VerticalLayout verticalLayout) {
        if (Objects.nonNull(label)) {
            label.setStyleName("v-label-white-space-normal");
            verticalLayout.addComponent(label);
        }
    }

    private VerticalLayout initFiltersPanelLayout() {
        VerticalLayout filtersPanelLayout = new VerticalLayout();
        filtersPanelLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(filtersPanelLayout);
        return filtersPanelLayout;
    }
}
