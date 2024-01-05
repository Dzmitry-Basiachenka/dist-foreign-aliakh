package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditAppliedFilterWidget;

import com.vaadin.ui.VerticalLayout;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Widget for applied filters on Audit tab for SAL product family.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/14/2023
 *
 * @author Stepan Karakhanov
 */
public class SalAuditAppliedFilterWidget extends CommonAuditAppliedFilterWidget {

    private static final long serialVersionUID = 6829189486401132890L;
    private final ISalAuditFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalAuditFilterController}
     */
    public SalAuditAppliedFilterWidget(ISalAuditFilterController controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void refreshFilterPanel(AuditFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(convertRroAccountNumbersToRroNames(filter.getRhAccountNumbers()),
                "label.rightsholders", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(
                convertSalLicenseesAccountNumbersToNames(filter.getLicenseeAccountNumbers()), "label.licensees",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(convertBatchIdsToBatchNames(filter.getBatchesIds()),
                "label.batches", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getStatuses(), "label.statuses", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getSalDetailType, filter, "label.detail_type"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getUsagePeriod, filter, "label.usage_period"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getCccEventId, filter, "label.event_id"), layout);
            addLabel(createLabelWithSingleValue(AuditFilter::getDistributionName, filter, "label.distribution_name"),
                layout);
        }
        setContent(layout);
    }

    private Set<String> convertSalLicenseesAccountNumbersToNames(Set<Long> accountNumbers) {
        return controller.getSalLicensees()
            .stream()
            .filter(salLicensee -> accountNumbers.contains(salLicensee.getAccountNumber()))
            .map(salLicensee -> salLicensee.getAccountNumber() + " - " + salLicensee.getName())
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
