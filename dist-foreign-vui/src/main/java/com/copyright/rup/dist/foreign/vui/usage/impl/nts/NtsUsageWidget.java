package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
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
    private static final String CLASS_BUTTON_MENUBAR = "button-menubar";

    private final INtsUsageController controller;
    private MenuItem loadFundPoolMenuItem;
    private MenuBar additionalFundsMenuBar;
    private Button assignClassificationButton;
    private Button addToScenarioButton;

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
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setAdditionalFundsMenuBar(additionalFundsMenuBar);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, GridColumnEnum.ID);
        addColumn(UsageDto::getStatus, GridColumnEnum.STATUS);
        addColumn(UsageDto::getProductFamily, GridColumnEnum.PRODUCT_FAMILY);
        addColumn(UsageDto::getBatchName, GridColumnEnum.BATCH_NAME);
        addColumn(UsageDto::getRroAccountNumber, GridColumnEnum.RRO_ACCOUNT_NUMBER);
        addColumn(UsageDto::getRroName, GridColumnEnum.RRO_NAME);
        addColumn(UsageDto::getRhAccountNumber, GridColumnEnum.RH_ACCOUNT_NUMBER);
        addColumn(UsageDto::getRhName, GridColumnEnum.RH_NAME);
        addColumn(UsageDto::getWrWrkInst, GridColumnEnum.WR_WRK_INST);
        addColumn(UsageDto::getSystemTitle, GridColumnEnum.SYSTEM_TITLE);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), GridColumnEnum.FISCAL_YEAR);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PAYMENT_DATE);
        addColumn(UsageDto::getWorkTitle, GridColumnEnum.WORK_TITLE);
        addColumn(UsageDto::getArticle, GridColumnEnum.ARTICLE);
        addColumn(UsageDto::getPublisher, GridColumnEnum.PUBLISHER);
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PUB_DATE);
        addColumn(UsageDto::getNumberOfCopies, GridColumnEnum.NUMBER_OF_COPIES);
        addAmountColumn(UsageDto::getReportedValue, GridColumnEnum.REPORTED_VALUE);
        addAmountColumn(UsageDto::getGrossAmount, GridColumnEnum.GROSS_AMOUNT_IN_USD);
        addColumn(UsageDto::getMarket, GridColumnEnum.MARKET);
        addColumn(UsageDto::getMarketPeriodFrom, GridColumnEnum.MARKET_PERIOD_FROM);
        addColumn(UsageDto::getMarketPeriodTo, GridColumnEnum.MARKET_PERIOD_TO);
        addColumn(UsageDto::getAuthor, GridColumnEnum.AUTHOR);
        addColumn(UsageDto::getComment, GridColumnEnum.COMMENT);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        var buttonsLayout = new HorizontalLayout(initFundPoolMenuBar(), initAdditionalFundsMenuBar(),
            initAssignClassificationButton(), initAddToScenarioButton(), initExportDownloader());
        var toolbarLayout = new HorizontalLayout(buttonsLayout, getHideGridColumnsProvider().getMenuButton());
        toolbarLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
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

    private MenuBar initFundPoolMenuBar() {
        var fundPoolMenuBar = new MenuBar();
        var menuItem = fundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        loadFundPoolMenuItem = menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.load"),
            item -> Windows.showModalWindow(new FundPoolLoadWindow(controller)));
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(menuItem, CLASS_BUTTON_MENUBAR);
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, CLASS_BUTTON_MENUBAR);
        return fundPoolMenuBar;
    }

    private MenuBar initAdditionalFundsMenuBar() {
        additionalFundsMenuBar = new MenuBar();
        var menuItem =
            additionalFundsMenuBar.addItem(ForeignUi.getMessage("menu.caption.additional_funds"), null, null);
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.create"),
            item -> new AdditionalFundBatchesFilterWindow(controller).showFilterWindow());
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewAdditionalFundsWindow(controller)));
        VaadinUtils.addComponentStyle(menuItem, CLASS_BUTTON_MENUBAR);
        VaadinUtils.addComponentStyle(additionalFundsMenuBar, "additional-funds-menu-bar");
        VaadinUtils.addComponentStyle(additionalFundsMenuBar, CLASS_BUTTON_MENUBAR);
        return additionalFundsMenuBar;
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

    private Button initAssignClassificationButton() {
        assignClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.assign_classification"));
        assignClassificationButton.addClickListener(
            event -> new NtsUsageBatchSelectorWidget(controller).showFilterWindow());
        return assignClassificationButton;
    }

    private Button initAddToScenarioButton() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked(new CreateNtsScenarioWindow(controller)));
        return addToScenarioButton;
    }

    private OnDemandFileDownloader initExportDownloader() {
        var exportDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        exportDownloader.extend(Buttons.createButton(ForeignUi.getMessage("button.export")));
        return exportDownloader;
    }
}
