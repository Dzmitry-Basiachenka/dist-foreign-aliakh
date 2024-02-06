package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IMediator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Usage widget for NTS product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
public class NtsUsageWidget extends CommonUsageWidget implements INtsUsageWidget {

    private static final long serialVersionUID = 7962483141394161599L;
    private static final String BATCH_NAMES_LIST_SEPARATOR = "<br><li>";

    private final INtsUsageController controller;
    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar additionalFundsMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuItem loadFundPoolMenuItem;

    /**
     * Constructor.
     *
     * @param controller {@link INtsUsageController} instance
     */
    NtsUsageWidget(INtsUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        var mediator = new NtsUsageMediator();
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setAdditionalFundsMenuBar(additionalFundsMenuBar);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        //TODO {aliakh} fix column width
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", "130px");
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", "115px");
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", "125px");
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", "145px");
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", "125px");
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", "135px");
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", "115px");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", "300px");
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", "110px");
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", "305px");
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", "140px");
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type",
            "standardNumberType", "155px");
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), "table.column.fiscal_year",
            "fiscalYear", "105px");
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", "115px");
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle", "305px");
        addColumn(UsageDto::getArticle, "table.column.article", "article", "135px");
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", "135px");
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", "90px");
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", "140px");
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", "130px");
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", "130px");
        addColumn(UsageDto::getMarket, "table.column.market", "market", "125px");
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", "150px");
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", "145px");
        addColumn(UsageDto::getAuthor, "table.column.author", "author", "300px");
        addColumn(UsageDto::getComment, "table.column.comment", "comment", "200px");
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        //TODO {aliakh} addToScenarioButton.addClickListener(onAddToScenarioClicked(new CreateNtsScenarioWindow()));
        var exportDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        exportDownloader.extend(Buttons.createButton(ForeignUi.getMessage("button.export")));
        assignClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.assign_classification"));
        //TODO {aliakh} assignClassificationButton.addClickListener(NtsUsageBatchSelectorWidget());
        initFundPoolMenuBar();
        initAdditionalFundsMenuBar();
        VaadinUtils.setButtonsAutoDisabled(assignClassificationButton, addToScenarioButton);
        var buttonsLayout = new HorizontalLayout(fundPoolMenuBar, additionalFundsMenuBar, assignClassificationButton,
            addToScenarioButton, exportDownloader);
        var toolbarLayout = new HorizontalLayout(buttonsLayout, getHideGridColumnsProvider().getMenuButton());
        toolbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbarLayout.setWidthFull();
        VaadinUtils.setPadding(toolbarLayout, 1, 3, 1, 3);
        return toolbarLayout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        String message;
        var batchesIds = getFilterWidget().getAppliedFilter().getUsageBatchesIds();
        if (CollectionUtils.isEmpty(batchesIds)) {
            message = ForeignUi.getMessage("message.error.empty_usage_batches");
        } else {
            var batchesNames = controller.getProcessingBatchesNames(batchesIds);
            if (CollectionUtils.isNotEmpty(batchesNames)) {
                message = ForeignUi.getMessage("message.error.processing_batches_names",
                    String.join(BATCH_NAMES_LIST_SEPARATOR, batchesNames));
            } else {
                var batchesNamesToScenarioNames = controller.getBatchesNamesToScenariosNames(batchesIds);
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
        MenuItem menuItem = fundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        loadFundPoolMenuItem = menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.load"),
            item -> Windows.showModalWindow(new FundPoolLoadWindow(controller)));
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "v-menubar-df");
    }

    private void initAdditionalFundsMenuBar() {
        additionalFundsMenuBar = new MenuBar();
        MenuItem menuItem =
            additionalFundsMenuBar.addItem(ForeignUi.getMessage("menu.caption.additional_funds"), null, null);
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.create"),
            item -> {}); //TODO {aliakh} initAdditionalFundBatchesFilterWindow
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewAdditionalFundsWindow(controller)));
        VaadinUtils.addComponentStyle(additionalFundsMenuBar, "additional-funds-menu-bar");
        VaadinUtils.addComponentStyle(additionalFundsMenuBar, "v-menubar-df");
    }

    private String getClassificationValidationMessage(Set<String> batchesIds) {
        String message = null;
        var batchesWithUnclassifiedWorks = controller.getBatchNamesWithUnclassifiedWorks(batchesIds);
        if (CollectionUtils.isNotEmpty(batchesWithUnclassifiedWorks)) {
            message = ForeignUi.getMessage("message.error.invalid_batch.unclassified_works",
                String.join(BATCH_NAMES_LIST_SEPARATOR, batchesWithUnclassifiedWorks));
        } else {
            var batchesWithoutRhsForStmOrNonStm = controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(
                getFilterWidget().getAppliedFilter().getUsageBatchesIds());
            var batchesWithoutRhsForStm = batchesWithoutRhsForStmOrNonStm.get(FdaConstants.STM_CLASSIFICATION);
            var batchesWithoutRhsForNonStm = batchesWithoutRhsForStmOrNonStm.get(FdaConstants.NON_STM_CLASSIFICATION);
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
