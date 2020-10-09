package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Usage widget for NTS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public class NtsUsageWidget extends CommonUsageWidget implements INtsUsageWidget {

    private static final String BATCH_NAMES_LIST_SEPARATOR = "<br><li>";

    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar additionalFundsMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private final INtsUsageController controller;

    /**
     * Controller.
     *
     * @param controller {@link INtsUsageController} instance
     */
    NtsUsageWidget(INtsUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        NtsUsageMediator mediator = new NtsUsageMediator();
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setAdditionalFundsMenuBar(additionalFundsMenuBar);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", true, 125);
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, 135);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true, 155);
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), "table.column.fiscal_year",
            "fiscalYear", true, 105);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", true, 115);
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle", true, 300);
        addColumn(UsageDto::getArticle, "table.column.article", "article", true, 135);
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, 135);
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", true, 90);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", 130);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 115);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, 150);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, 145);
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 300);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked(new CreateNtsScenarioWindow(controller)));
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        fileDownloader.extend(exportButton);
        assignClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.assign_classification"));
        assignClassificationButton.addClickListener(
            event -> new NtsUsageBatchSelectorWidget(controller).showFilterWindow());
        initFundPoolMenuBar();
        initAdditionalFundsMenuBar();
        VaadinUtils.setButtonsAutoDisabled(assignClassificationButton, addToScenarioButton);
        HorizontalLayout layout = new HorizontalLayout(fundPoolMenuBar, additionalFundsMenuBar,
            assignClassificationButton, addToScenarioButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        String message;
        Set<String> batchesIds = getFilterWidget().getAppliedFilter().getUsageBatchesIds();
        if (CollectionUtils.isEmpty(batchesIds)) {
            message = ForeignUi.getMessage("message.error.empty_usage_batches");
        } else {
            List<String> batchesNames = controller.getProcessingBatchesNames(batchesIds);
            if (CollectionUtils.isNotEmpty(batchesNames)) {
                message = ForeignUi.getMessage("message.error.processing_batches_names",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, batchesNames));
            } else {
                Map<String, String> batchesNamesToScenarioNames =
                    controller.getBatchesNamesToScenariosNames(batchesIds);
                if (batchesNamesToScenarioNames.isEmpty()) {
                    message = getClassificationValidationMessage(batchesIds);
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

    private void initFundPoolMenuBar() {
        fundPoolMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem = fundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        loadFundPoolMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new FundPoolLoadWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "v-menubar-df");
    }

    private void initAdditionalFundsMenuBar() {
        additionalFundsMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            additionalFundsMenuBar.addItem(ForeignUi.getMessage("menu.caption.additional_funds"), null, null);
        menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            item -> Windows.showModalWindow(initAdditionalFundBatchesFilterWindow()));
        menuItem.addItem(ForeignUi.getMessage("menu.item.delete"), null,
            item -> Windows.showModalWindow(new DeleteAdditionalFundsWindow(controller)));
        VaadinUtils.addComponentStyle(additionalFundsMenuBar, "additional-funds-menu-bar");
        VaadinUtils.addComponentStyle(additionalFundsMenuBar, "v-menubar-df");
    }

    private Window initAdditionalFundBatchesFilterWindow() {
        AdditionalFundBatchesFilterWidget widget = new AdditionalFundBatchesFilterWidget(
            controller::getUsageBatchesForAdditionalFunds);
        AdditionalFundBatchesFilterWindow window = new AdditionalFundBatchesFilterWindow(widget);
        window.updateSaveButtonClickListener(
            () -> {
                List<UsageBatch> selectedUsageBatches = widget.getSelectedUsageBatches();
                if (!selectedUsageBatches.isEmpty()) {
                    Windows.showModalWindow(
                        new AdditionalFundFilteredBatchesWindow(controller, selectedUsageBatches, window));
                } else {
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.usage.batches.empty"));
                }
            });
        window.addListener(FilterWindow.FilterSaveEvent.class, widget, FilterWindow.IFilterSaveListener.SAVE_HANDLER);
        return window;
    }

    private String getClassificationValidationMessage(Set<String> batchesIds) {
        String message = null;
        List<String> batchesWithUnclassifiedWorks = controller.getBatchNamesWithUnclassifiedWorks(batchesIds);
        if (CollectionUtils.isNotEmpty(batchesWithUnclassifiedWorks)) {
            message = ForeignUi.getMessage("message.error.invalid_batch.unclassified_works",
                String.join(BATCH_NAMES_LIST_SEPARATOR, batchesWithUnclassifiedWorks));
        } else {
            Map<String, List<String>> batchesWithoutRhsForStmOrNonStm =
                controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(
                    getFilterWidget().getAppliedFilter().getUsageBatchesIds());
            List<String> batchesWithoutRhsForStm =
                batchesWithoutRhsForStmOrNonStm.get(FdaConstants.STM_CLASSIFICATION);
            List<String> batchesWithoutRhsForNonStm =
                batchesWithoutRhsForStmOrNonStm.get(FdaConstants.NON_STM_CLASSIFICATION);
            if (CollectionUtils.isNotEmpty(batchesWithoutRhsForStm)) {
                message = ForeignUi.getMessage("message.error.invalid_batch.no_stm_rhs",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, batchesWithoutRhsForStm));
            }
            if (CollectionUtils.isNotEmpty(batchesWithoutRhsForNonStm)) {
                message = StringUtils.isBlank(message)
                    ? ForeignUi.getMessage("message.error.invalid_batch.no_non_stm_rhs",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, batchesWithoutRhsForNonStm))
                    : message.concat(ForeignUi.getMessage("message.error.invalid_batch.no_non_stm_rhs",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, batchesWithoutRhsForNonStm)));
            }
        }
        return message;
    }
}
