package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Implementation of {@link IAclScenarioDetailsWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioDetailsWidget extends AclCommonScenarioDetailsWidget implements IAclScenarioDetailsWidget {

    private final IAclScenarioDetailsController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenarioDetailsController}
     */
    public AclScenarioDetailsWidget(IAclScenarioDetailsController controller) {
        this.controller = controller;
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.acl_scenario_details.search_widget");
    }

    @Override
    protected HorizontalLayout initButtons() {
        Button exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(controller.getExportAclScenarioDetailsStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        return new HorizontalLayout(exportDetailsButton, Buttons.createCloseButton(this));
    }

    @Override
    protected void addColumns() {
        addColumn(AclScenarioDetailDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(
            AclScenarioDetailDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", true, 130);
        addColumn(AclScenarioDetailDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(AclScenarioDetailDto::getUsageBatchName, "table.column.batch_name", "usageBatchName", true, 145);
        addColumn(AclScenarioDetailDto::getPeriodEndPeriod, "table.column.period_end_date", "periodEndDate", true, 125);
        addColumn(AclScenarioDetailDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(AclScenarioDetailDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 250);
        addColumn(AclScenarioDetailDto::getRhAccountNumberPrint, "table.column.print_rh_account_number",
            "rhAccountNumberPrint", true, 140);
        addColumn(AclScenarioDetailDto::getRhNamePrint, "table.column.print_rh_name", "rhNamePrint", true, 150);
        addColumn(AclScenarioDetailDto::getPayeeAccountNumberPrint, "table.column.print_payee_account_number",
            "payeeAccountNumberPrint", true, 160);
        addColumn(AclScenarioDetailDto::getPayeeNamePrint, "table.column.print_payee_name",
            "payeeNamePrint", true, 150);
        addColumn(AclScenarioDetailDto::getRhAccountNumberDigital, "table.column.digital_rh_account_number",
            "rhAccountNumberDigital", true, 140);
        addColumn(AclScenarioDetailDto::getRhNameDigital, "table.column.digital_rh_name", "rhNameDigital", true, 150);
        addColumn(AclScenarioDetailDto::getPayeeAccountNumberDigital, "table.column.digital_payee_account_number",
            "payeeAccountNumberDigital", true, 160);
        addColumn(AclScenarioDetailDto::getPayeeNameDigital, "table.column.digital_payee_name",
            "payeeNameDigital", true, 150);
        addColumn(AclScenarioDetailDto::getUsagePeriod, "table.column.usage_period", "usagePeriod", true, 100);
        addBigDecimalColumn(
            AclScenarioDetailDto::getUsageAgeWeight, "table.column.usage_age_weight", "usageAgeWeight", 130);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detailLicenseeClassId",
            true, 100);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassName, "table.column.det_lc_name",
            "detailLicenseeClassName", true, 200);
        addColumn(AclScenarioDetailDto::getAggregateLicenseeClassId, "table.column.aggregate_licensee_class_id",
            "aggregateLicenseeClassId", true, 100);
        addColumn(AclScenarioDetailDto::getAggregateLicenseeClassName, "table.column.aggregate_licensee_class_name",
            "aggregateLicenseeClassName", true, 200);
        addColumn(AclScenarioDetailDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", true, 120);
        addColumn(AclScenarioDetailDto::getReportedTypeOfUse, "table.column.reported_tou",
            "reportedTypeOfUse", true, 120);
        addColumn(AclScenarioDetailDto::getTypeOfUse, "table.column.tou", "typeOfUse", true, 75);
        addBigDecimalColumn(AclScenarioDetailDto::getNumberOfCopies, "table.column.acl_number_of_copies",
            "numberOfCopies", 125);
        addBigDecimalColumn(AclScenarioDetailDto::getWeightedCopies, "table.column.number_of_weighted_copies",
            "weightedCopies", 150);
        addColumn(detail -> detail.getPublicationType().getName(), "table.column.publication_type", "publicationType",
            true, 120);
        addBigDecimalColumn(detail -> detail.getPublicationType().getWeight(), "table.column.publication_type_weight",
            "pubTypeWeight", 120);
        addBigDecimalColumn(AclScenarioDetailDto::getPrice, "table.column.price", "price", 130);
        addBooleanColumn(AclScenarioDetailDto::isPriceFlag, "table.column.price_flag", "priceFlag", 110);
        addBigDecimalColumn(AclScenarioDetailDto::getContent, "table.column.content", "content", 100);
        addBooleanColumn(AclScenarioDetailDto::isContentFlag, "table.column.content_flag", "contentFlag", 110);
        addBigDecimalColumn(AclScenarioDetailDto::getContentUnitPrice, "table.column.content_unit_price",
            "contentUnitPrice", 150);
        addBooleanColumn(AclScenarioDetailDto::isContentUnitPriceFlag, "table.column.content_unit_price_flag",
            "contentUnitPriceFlag", 90);
        addBigDecimalColumn(AclScenarioDetailDto::getValueSharePrint, "table.column.print_value_share",
            "valueSharePrint", 140);
        addBigDecimalColumn(AclScenarioDetailDto::getVolumeSharePrint, "table.column.print_volume_share",
            "volumeSharePrint", 140);
        addBigDecimalColumn(AclScenarioDetailDto::getDetailSharePrint, "table.column.print_detail_share",
            "detailSharePrint", 140);
        addAmountColumn(AclScenarioDetailDto::getNetAmountPrint, "table.column.print_net_amount",
            "netAmountPrint", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getValueShareDigital, "table.column.digital_value_share",
            "valueShareDigital", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getVolumeShareDigital, "table.column.digital_volume_share",
            "volumeShareDigital", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getDetailShareDigital, "table.column.digital_detail_share",
            "detailShareDigital", 150);
        addAmountColumn(AclScenarioDetailDto::getNetAmountDigital, "table.column.digital_net_amount",
            "netAmountDigital", 150);
        addAmountColumn(AclScenarioDetailDto::getCombinedNetAmount, "table.column.combined_net_amount",
            "combinedNetAmount", 170);
    }
}
