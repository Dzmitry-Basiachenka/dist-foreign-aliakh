package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.NotificationWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Usage widget for AACL product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
public class AaclUsageWidget extends CommonUsageWidget implements IAaclUsageWidget {

    private static final String BATCH_NAMES_LIST_SEPARATOR = "<br><li>";

    private MenuBar usageBatchMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private Button sendForClassificationButton;
    private Button loadClassifiedUsagesButton;
    private Button addToScenarioButton;
    private Button exportButton;
    private final IAaclUsageController controller;

    /**
     * Controller.
     *
     * @param controller {@link IAaclUsageController} instance
     */
    AaclUsageWidget(IAaclUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AaclUsageMediator mediator = new AaclUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setSendForClassificationButton(sendForClassificationButton);
        mediator.setLoadClassifiedUsagesButton(loadClassifiedUsagesButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usage -> CommonDateUtils.format(usage.getAaclUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "periodEndDate", true, 115);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true,
            155);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getId(), "table.column.det_lc_id",
            "detailLicenseeClassId", true, 80);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile(),
            "table.column.det_lc_enrollment", "detailLicenseeEnrollment", true, 140);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getDiscipline(),
            "table.column.det_lc_discipline", "detailLicenseeDiscipline", true, 140);
        addColumn(usage -> usage.getAaclUsage().getPublicationType().getName(), "table.column.publication_type",
            "publicationType", true, 140);
        addColumn(usage -> usage.getAaclUsage().getInstitution(), "table.column.institution", "institution",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getUsageAge().getPeriod(), "table.column.usage_period", "usagePeriod",
            true, 100);
        addColumn(usage -> usage.getAaclUsage().getUsageSource(), "table.column.usage_source", "usageSource",
            true, 140);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addColumn(usage -> usage.getAaclUsage().getNumberOfPages(), "table.column.number_of_pages",
            "numberOfPages", true, 140);
        addColumn(usage -> usage.getAaclUsage().getRightLimitation(), "table.column.right_limitation",
            "rightLimitation", true, 120);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        initUsageBatchMenuBar();
        initFundPoolMenuBar();
        initSendForClassificationButton();
        initLoadClassifiedUsagesButton();
        initAddToScenarioButton();
        initExportButton();
        VaadinUtils.setButtonsAutoDisabled(loadClassifiedUsagesButton, addToScenarioButton);
        HorizontalLayout layout = new HorizontalLayout(usageBatchMenuBar, fundPoolMenuBar, sendForClassificationButton,
            loadClassifiedUsagesButton, addToScenarioButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        String message = null;
        Set<String> batchesIds = getFilterWidget().getAppliedFilter().getUsageBatchesIds();
        if (CollectionUtils.isEmpty(batchesIds)) {
            message = ForeignUi.getMessage("message.error.empty_usage_batches");
        } else {
            List<String> processingBatchesNames = controller.getProcessingBatchesNames(batchesIds);
            if (CollectionUtils.isNotEmpty(processingBatchesNames)) {
                message = ForeignUi.getMessage("message.error.processing_batches_names",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, processingBatchesNames));
            } else {
                Map<String, String> batchesNamesToScenarioNames =
                    controller.getBatchesNamesToScenariosNames(batchesIds);
                if (batchesNamesToScenarioNames.isEmpty()) {
                    List<String> ineligibleBatchNames = controller.getIneligibleBatchesNames(batchesIds);
                    if (CollectionUtils.isNotEmpty(ineligibleBatchNames)) {
                        message = ForeignUi.getMessage("message.error.batches_with_non_eligible_usages",
                            String.join(BATCH_NAMES_LIST_SEPARATOR, ineligibleBatchNames));
                    }
                } else {
                    message = ForeignUi.getMessage("message.error.batches_already_associated_with_scenarios",
                        batchesNamesToScenarioNames.entrySet().stream()
                            .map(entry -> entry.getKey() + " : " + entry.getValue())
                            .collect(Collectors.joining(BATCH_NAMES_LIST_SEPARATOR)));
                }
            }
        }
        return message;
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new AaclUsageBatchUploadWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewAaclUsageBatchWindow(controller)));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df");
    }

    private void initFundPoolMenuBar() {
        fundPoolMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            fundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        loadFundPoolMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new AaclFundPoolUploadWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewAaclFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "v-menubar-df");
    }

    private void initSendForClassificationButton() {
        sendForClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_classification"));
        SendForClassificationFileDownloader sendForClassificationDownloader =
            new SendForClassificationFileDownloader(controller);
        sendForClassificationDownloader.extend(sendForClassificationButton);
        // Click listener and second isValidFilteredUsageStatus() call were added due to problem with
        // modal window appearance in chrome browser
        sendForClassificationButton.addClickListener(event -> {
            if (!controller.isValidForClassification()) {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.invalid_usages_for_classification"));
            } else {
                NotificationWindow window =
                    new NotificationWindow(ForeignUi.getMessage("message.download_in_progress"));
                window.addCloseListener(closeEvent -> controller.clearFilter());
                Windows.showModalWindow(window);
            }
        });
    }

    private void initLoadClassifiedUsagesButton() {
        loadClassifiedUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.load_classified_details"));
        loadClassifiedUsagesButton.addClickListener(event ->
            Windows.showModalWindow(new ClassifiedUsagesUploadWindow(controller)));
    }

    private void initAddToScenarioButton() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked(new CreateAaclScenarioWindow(controller)));
    }

    private void initExportButton() {
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        fileDownloader.extend(exportButton);
    }

    private static class SendForClassificationFileDownloader extends OnDemandFileDownloader {

        private final IAaclUsageController controller;

        /**
         * Controller.
         *
         * @param controller instance of {@link IAaclUsageController}
         */
        SendForClassificationFileDownloader(IAaclUsageController controller) {
            super(controller.getSendForClassificationUsagesStreamSource().getSource());
            this.controller = controller;
        }

        @Override
        public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) {
            return controller.isValidForClassification() && super.handleConnectorRequest(request, response, path);
        }
    }
}
