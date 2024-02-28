package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageWidget;
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

import java.util.Objects;

/**
 * Usage widget for FAS and FAS2 product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
public class FasUsageWidget extends CommonUsageWidget implements IFasUsageWidget {

    private static final long serialVersionUID = -3584395313084707377L;

    private final IFasUsageController controller;
    private MenuItem loadUsageBatchMenuItem;
    private Button sendForResearchButton;
    private OnDemandFileDownloader sendForResearchDownloader;
    private Button loadResearchedUsagesButton;
    private Button updateUsagesButton;
    private Button addToScenarioButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFasUsageController}
     */
    FasUsageWidget(IFasUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        var mediator = new FasUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setSendForResearchButton(sendForResearchButton);
        mediator.setSendForResearchDownloader(sendForResearchDownloader);
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setUpdateUsagesButton(updateUsagesButton);
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
        addColumn(usage -> Objects.nonNull(usage.getFasUsage()) ?
            usage.getFasUsage().getReportedStandardNumber() : null, GridColumnEnum.REPORTED_STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), GridColumnEnum.FISCAL_YEAR);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PAYMENT_DATE);
        addColumn(UsageDto::getWorkTitle, GridColumnEnum.REPORTED_TITLE);
        addColumn(UsageDto::getArticle, GridColumnEnum.ARTICLE);
        addColumn(UsageDto::getPublisher, GridColumnEnum.PUBLISHER);
        addColumn(
            usage -> CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PUB_DATE);
        addColumn(UsageDto::getNumberOfCopies, GridColumnEnum.NUMBER_OF_COPIES);
        addAmountColumn(UsageDto::getReportedValue, GridColumnEnum.REPORTED_VALUE);
        addAmountColumn(UsageDto::getGrossAmount, GridColumnEnum.GROSS_AMOUNT_IN_USD);
        addAmountColumn(UsageDto::getBatchGrossAmount, GridColumnEnum.BATCH_GROSS_AMOUNT);
        addColumn(UsageDto::getMarket, GridColumnEnum.MARKET);
        addColumn(UsageDto::getMarketPeriodFrom, GridColumnEnum.MARKET_PERIOD_FROM);
        addColumn(UsageDto::getMarketPeriodTo, GridColumnEnum.MARKET_PERIOD_TO);
        addColumn(UsageDto::getAuthor, GridColumnEnum.AUTHOR);
        addColumn(UsageDto::getComment, GridColumnEnum.COMMENT);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        var buttonsLayout = new HorizontalLayout(initUsageBatchMenuBar(), initSendForResearchDownloader(),
            initLoadResearchedUsagesButton(), initUpdateUsagesButton(), initAddToScenarioButton(),
            initExportDownloader());
        var toolbarLayout = new HorizontalLayout(buttonsLayout, getHideGridColumnsProvider().getMenuButton());
        toolbarLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbarLayout.setWidthFull();
        VaadinUtils.setPadding(toolbarLayout, 1, 3, 1, 3);
        return toolbarLayout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        return null;
    }

    private MenuBar initUsageBatchMenuBar() {
        var usageBatchMenuBar = new MenuBar();
        var menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.load"),
            item -> Windows.showModalWindow(new UsageBatchUploadWindow(controller)));
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewUsageBatchWindow(controller)));
        VaadinUtils.addComponentStyle(menuItem, "button-menubar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "button-menubar");
        return usageBatchMenuBar;
    }

    private OnDemandFileDownloader initSendForResearchDownloader() {
        sendForResearchButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_research"));
        sendForResearchDownloader =
            new OnDemandFileDownloader(controller.getSendForResearchUsagesStreamSource().getSource());
        sendForResearchDownloader.extend(sendForResearchButton);
        sendForResearchButton.addClickListener(clickEvent -> {
            if (!controller.isValidFilteredUsageStatus(UsageStatusEnum.WORK_NOT_FOUND)) {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.WORK_NOT_FOUND,
                        "sent for research"));
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
        enableSendForResearchDownloader();
        getFilterWidget().addFilterSaveAction(this::enableSendForResearchDownloader);
        return sendForResearchDownloader;
    }

    private void enableSendForResearchDownloader() {
        var enabled = UsageStatusEnum.WORK_NOT_FOUND.equals(getFilterWidget().getAppliedFilter().getUsageStatus());
        sendForResearchDownloader.setEnabled(enabled);
        sendForResearchButton.setEnabled(enabled);
        sendForResearchDownloader.setTitle(ForeignUi.getMessage("message.error.invalid_usages_status",
            UsageStatusEnum.WORK_NOT_FOUND, "sent for research"));
    }

    private Button initLoadResearchedUsagesButton() {
        loadResearchedUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.load_researched_details"));
        loadResearchedUsagesButton.addClickListener(event ->
            Windows.showModalWindow(new ResearchedUsagesUploadWindow(controller)));
        return loadResearchedUsagesButton;
    }

    private Button initUpdateUsagesButton() {
        updateUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.update_usages"));
        updateUsagesButton.addClickListener(event -> {
            if (0 != controller.getBeansCount()) {
                Windows.showModalWindow(new FasUpdateUsageWindow(controller));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.update.empty_usages"));
            }
        });
        return updateUsagesButton;
    }

    private Button initAddToScenarioButton() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked(new CreateScenarioWindow(controller)));
        return addToScenarioButton;
    }

    private OnDemandFileDownloader initExportDownloader() {
        var exportDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        exportDownloader.extend(Buttons.createButton(ForeignUi.getMessage("button.export")));
        return exportDownloader;
    }
}
