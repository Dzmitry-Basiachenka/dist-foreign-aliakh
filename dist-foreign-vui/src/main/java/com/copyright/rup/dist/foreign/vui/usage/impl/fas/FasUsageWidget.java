package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
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

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
    private static final String WIDTH_300 = "300px";

    private final IFasUsageController controller;
    private MenuBar usageBatchMenuBar;
    private MenuItem loadUsageBatchMenuItem;
    private Button sendForResearchButton;
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
        FasUsageMediator mediator = new FasUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setSendForResearchButton(sendForResearchButton);
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setUpdateUsagesButton(updateUsagesButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", WIDTH_300);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", "180px");
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", "160px");
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", "200px");
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", "160px");
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", WIDTH_300);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", "150px");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", WIDTH_300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", "140px");
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", WIDTH_300);
        addColumn(usage -> Objects.nonNull(usage.getFasUsage())
                ? usage.getFasUsage().getReportedStandardNumber() : null,
            "table.column.reported_standard_number", "reportedStandardNumber", "260px");
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", "180px");
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", "225px");
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()),
            "table.column.fiscal_year", "fiscalYear", "130px");
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", "145px");
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", WIDTH_300);
        addColumn(UsageDto::getArticle, "table.column.article", "article", "135px");
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", "135px");
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", "110px");
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", "185px");
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", "170px");
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", "170px");
        addAmountColumn(UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", "170px");
        addColumn(UsageDto::getMarket, "table.column.market", "market", "120px");
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", "200px");
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", "185px");
        addColumn(UsageDto::getAuthor, "table.column.author", "author", WIDTH_300);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", "200px");
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        loadResearchedUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.load_researched_details"));
        loadResearchedUsagesButton.addClickListener(event ->
            Windows.showModalWindow(new ResearchedUsagesUploadWindow(controller)));
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked(new CreateScenarioWindow(controller)));
        var exportDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        exportDownloader.extend(Buttons.createButton(ForeignUi.getMessage("button.export")));
        sendForResearchButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_research"));
        var sendForResearchDownloader =
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
        initUsageBatchMenuBar();
        initUpdateUsagesButton();
        VaadinUtils.setButtonsAutoDisabled(loadResearchedUsagesButton, updateUsagesButton, addToScenarioButton);
        var buttonsLayout = new HorizontalLayout(usageBatchMenuBar, sendForResearchDownloader,
            loadResearchedUsagesButton, updateUsagesButton, addToScenarioButton, exportDownloader);
        var toolbarLayout = new HorizontalLayout(buttonsLayout, getHideGridColumnsProvider().getMenuButton());
        toolbarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbarLayout.setWidth(100, Unit.PERCENTAGE);
        VaadinUtils.addComponentStyle(toolbarLayout, "usages-toolbar");
        return toolbarLayout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        return null;
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.load"),
            item -> Windows.showModalWindow(new UsageBatchUploadWindow(controller)));
        menuItem.getSubMenu().addItem(ForeignUi.getMessage("menu.item.view"),
            item -> Windows.showModalWindow(new ViewUsageBatchWindow(controller)));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
    }

    private void initUpdateUsagesButton() {
        updateUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.update_usages"));
        updateUsagesButton.addClickListener(event -> {
            if (0 != controller.getBeansCount()) {
                Windows.showModalWindow(new FasUpdateUsageWindow(controller));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.update.empty_usages"));
            }
        });
    }
}
