package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.NotificationWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.apache.commons.collections4.CollectionUtils;

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

    private static final long serialVersionUID = -7714913675577589759L;
    private static final String BATCH_NAMES_LIST_SEPARATOR = "<br><li>";
    private static final String CLASS_BUTTON_MENUBAR = "button-menubar";

    private final IAaclUsageController controller;
    private MenuBar usageBatchMenuBar;
    private MenuItem loadUsageBatchMenuItem;
    private MenuBar fundPoolMenuBar;
    private MenuItem loadFundPoolMenuItem;
    private Button sendForClassificationButton;
    private OnDemandFileDownloader sendForClassificationDownloader;
    private Button loadClassifiedUsagesButton;
    private Button addToScenarioButton;

    /**
     * Controller.
     *
     * @param controller instance of {@link IAaclUsageController}
     */
    AaclUsageWidget(IAaclUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        var mediator = new AaclUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setSendForClassificationButton(sendForClassificationButton);
        mediator.setSendForClassificationDownloader(sendForClassificationDownloader);
        mediator.setLoadClassifiedUsagesButton(loadClassifiedUsagesButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, GridColumnEnum.ID);
        addColumn(UsageDto::getStatus, GridColumnEnum.STATUS);
        addColumn(UsageDto::getProductFamily, GridColumnEnum.PRODUCT_FAMILY);
        addColumn(UsageDto::getBatchName, GridColumnEnum.BATCH_NAME);
        addColumn(usage -> CommonDateUtils.format(usage.getAaclUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), GridColumnEnum.BATCH_PERIOD_END_DATE);
        addColumn(UsageDto::getRhAccountNumber, GridColumnEnum.RH_ACCOUNT_NUMBER);
        addColumn(UsageDto::getRhName, GridColumnEnum.RH_NAME);
        addColumn(UsageDto::getWrWrkInst, GridColumnEnum.WR_WRK_INST);
        addColumn(UsageDto::getSystemTitle, GridColumnEnum.SYSTEM_TITLE);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getId(), GridColumnEnum.DET_LC_ID);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile(),
            GridColumnEnum.DET_LC_ENROLLMENT);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getDiscipline(),
            GridColumnEnum.DET_LC_DISCIPLINE);
        addColumn(usage -> usage.getAaclUsage().getPublicationType().getName(), GridColumnEnum.PUB_TYPE);
        addColumn(usage -> usage.getAaclUsage().getInstitution(), GridColumnEnum.INSTITUTION);
        addColumn(usage -> usage.getAaclUsage().getUsageAge().getPeriod(), GridColumnEnum.PERIOD);
        addColumn(usage -> usage.getAaclUsage().getUsageSource(), GridColumnEnum.USAGE_SOURCE);
        addColumn(UsageDto::getNumberOfCopies, GridColumnEnum.NUMBER_OF_COPIES);
        addColumn(usage -> usage.getAaclUsage().getNumberOfPages(), GridColumnEnum.NUMBER_OF_PAGES);
        addColumn(usage -> usage.getAaclUsage().getRightLimitation(), GridColumnEnum.RIGHT_LIMITATION);
        addColumn(UsageDto::getComment, GridColumnEnum.COMMENT);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        //TODO {aliakh} inline initialization
        initUsageBatchMenuBar();
        initFundPoolMenuBar();
        initAddToScenarioButton();
        var buttonsLayout = new HorizontalLayout(usageBatchMenuBar, fundPoolMenuBar,
            initSendForClassificationDownloade(), initLoadClassifiedUsagesButton(), addToScenarioButton,
            initExportDownloader());
        var toolbarLayout = new HorizontalLayout(buttonsLayout, getHideGridColumnsProvider().getMenuButton());
        toolbarLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbarLayout.setWidthFull();
        VaadinUtils.setPadding(toolbarLayout, 1, 3, 1, 3);
        return toolbarLayout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        String message = null;
        var batchesIds = getFilterWidget().getAppliedFilter().getUsageBatchesIds();
        if (CollectionUtils.isEmpty(batchesIds)) {
            message = ForeignUi.getMessage("message.error.empty_usage_batches");
        } else {
            var processingBatchesNames = controller.getProcessingBatchesNames(batchesIds);
            if (CollectionUtils.isNotEmpty(processingBatchesNames)) {
                message = ForeignUi.getMessage("message.error.processing_batches_names",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, processingBatchesNames));
            } else {
                var batchesNamesToScenarioNames = controller.getBatchesNamesToScenariosNames(batchesIds);
                if (batchesNamesToScenarioNames.isEmpty()) {
                    var ineligibleBatchNames = controller.getIneligibleBatchesNames(batchesIds);
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
        var menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.load"),
            item -> Windows.showModalWindow(new AaclUsageBatchUploadWindow(controller)));
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewAaclUsageBatchWindow(controller)));
        VaadinUtils.addComponentStyle(menuItem, CLASS_BUTTON_MENUBAR);
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, CLASS_BUTTON_MENUBAR);
    }

    private void initFundPoolMenuBar() {
        fundPoolMenuBar = new MenuBar();
        var menuItem =
            fundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        loadFundPoolMenuItem = menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.load"),
            item -> Windows.showModalWindow(new AaclFundPoolUploadWindow(controller)));
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewAaclFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(menuItem, CLASS_BUTTON_MENUBAR);
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, CLASS_BUTTON_MENUBAR);
    }

    private OnDemandFileDownloader initSendForClassificationDownloade() {
        sendForClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_classification"));
        sendForClassificationDownloader =
            new OnDemandFileDownloader(controller.getSendForClassificationUsagesStreamSource().getSource());
        sendForClassificationDownloader.extend(sendForClassificationButton);
        sendForClassificationButton.addClickListener(event -> {
            if (!controller.isValidForClassification()) {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.invalid_usages_for_classification"));
            } else {
                var window = new NotificationWindow(ForeignUi.getMessage("message.download_in_progress"));
                window.addOpenedChangeListener(closeEvent -> {
                    if (!closeEvent.isOpened()) {
                        controller.clearFilter();
                    }
                });
                Windows.showModalWindow(window);
            }
        });
        enableSendForClassificationDownloader();
        getFilterWidget().addFilterSaveAction(this::enableSendForClassificationDownloader);
        return sendForClassificationDownloader;
    }

    private void enableSendForClassificationDownloader() {
        var enabled = UsageStatusEnum.RH_FOUND.equals(getFilterWidget().getAppliedFilter().getUsageStatus());
        sendForClassificationDownloader.setEnabled(enabled);
        sendForClassificationButton.setEnabled(enabled);
        sendForClassificationDownloader.setTitle(
            ForeignUi.getMessage("message.error.invalid_usages_for_classification"));
    }

    private Button initLoadClassifiedUsagesButton() {
        loadClassifiedUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.load_classified_details"));
        loadClassifiedUsagesButton.addClickListener(event ->
            Windows.showModalWindow(new ClassifiedUsagesUploadWindow(controller)));
        return loadClassifiedUsagesButton;
    }

    private void initAddToScenarioButton() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        //TODO {aliakh} addToScenarioButton.addClickListener(new CreateAaclScenarioWindow(controller));
    }

    private OnDemandFileDownloader initExportDownloader() {
        var exportDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        exportDownloader.extend(Buttons.createButton(ForeignUi.getMessage("button.export")));
        return exportDownloader;
    }
}
