package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Widget for applied UDM usage filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Anton Azarenka
 */
public class UdmUsageAppliedFilterWidget extends CommonAppliedFilterPanel implements IDateFormatter {

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
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getUdmBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getUsageStatus, filter, "label.status"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getUdmUsageOrigin, filter, "label.usage_origin"),
                layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getAssignees()),
                "label.assignees", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortDetailLicenseeClasses(filter.getDetailLicenseeClasses()),
                "label.detail_licensee_classes", DetailLicenseeClass::getIdAndDescription), layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getReportedPubTypes()),
                "label.reported_pub_types", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getReportedTypeOfUses()),
                "label.reported_types_of_use", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getPubFormats()),
                "label.publication_formats", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getUsageDateFrom, filter), filter,
                "label.usage_date_from"), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getUsageDateTo, filter), filter,
                "label.usage_date_to"), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getSurveyStartDateFrom, filter),
                filter, "label.survey_start_date_from"), layout);
            addLabel(createLabelWithSingleValue(getFunctionForDate(UdmUsageFilter::getSurveyStartDateTo, filter),
                filter, "label.survey_start_date_to"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getChannel, filter, "label.channel"), layout);
            addLabel(createLabelWithSingleValue(UdmUsageFilter::getTypeOfUse, filter, "label.type_of_use"), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithOperator(filter.getReportedTitleExpression(), "label.reported_title",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getSystemTitleExpression(), "label.system_title",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getUsageDetailIdExpression(), "label.usage_detail_id",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getCompanyIdExpression(), "label.company_id_from",
                "label.company_id_to"), layout);
            addLabel(createLabelWithOperator(filter.getCompanyNameExpression(), "label.company_name",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getSurveyRespondentExpression(), "label.survey_respondent",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getSurveyCountryExpression(), "label.survey_country",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getLanguageExpression(), "label.language",
                StringUtils.EMPTY), layout);
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
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Function<UdmUsageFilter, ?> getFunctionForDate(Function<UdmUsageFilter, LocalDate> function,
                                                           UdmUsageFilter filter) {
        return Objects.nonNull(function.apply(filter))
            ? value -> toShortFormat(function.apply(filter))
            : function;
    }
}
