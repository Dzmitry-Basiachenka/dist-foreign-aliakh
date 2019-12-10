package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasUsageWidget;
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

/**
 * Usage widget for FAS and FAS2 product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
public class FasUsageWidget extends CommonUsageWidget<IFasUsageWidget, IFasUsageController>
    implements IFasUsageWidget {

    private Button loadResearchedUsagesButton;
    private Button sendForResearchButton;
    private Button addToScenarioButton;
    private MenuBar usageBatchMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;

    @Override
    public IMediator initMediator() {
        FasUsageMediator mediator = new FasUsageMediator();
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setSendForResearchButton(sendForResearchButton);
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        return mediator;
    }

    @Override
    void addGridColumns() {
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
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount", "grossAmount", 110);
        addAmountColumn(UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", 135);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 115);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, 150);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, 145);
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 300);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    @Override
    HorizontalLayout initButtonsLayout() {
        loadResearchedUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.load_researched_usages"));
        loadResearchedUsagesButton.addClickListener(event ->
            Windows.showModalWindow(new ResearchedUsagesUploadWindow(getController())));
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked());
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(getController().getExportUsagesStreamSource().getSource());
        fileDownloader.extend(exportButton);
        sendForResearchButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_research"));
        SendForResearchFileDownloader sendForResearchDownloader = new SendForResearchFileDownloader(getController());
        sendForResearchDownloader.extend(sendForResearchButton);
        // Click listener and second isWorkNotFoundStatusApplied() call were added due to problem with
        // modal window appearance in chrome browser
        sendForResearchButton.addClickListener(event -> {
            if (!getController().isValidUsagesState(UsageStatusEnum.WORK_NOT_FOUND)) {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.WORK_NOT_FOUND,
                        "sent for research"));
            } else {
                NotificationWindow window = new NotificationWindow(ForeignUi.getMessage("message.send_for_research"));
                window.addCloseListener(closeEvent -> getController().clearFilter());
                Windows.showModalWindow(window);
            }
        });
        initUsageBatchMenuBar();
        VaadinUtils.setButtonsAutoDisabled(loadResearchedUsagesButton, addToScenarioButton);
        HorizontalLayout layout = new HorizontalLayout(usageBatchMenuBar, sendForResearchButton,
            loadResearchedUsagesButton, addToScenarioButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new UsageBatchUploadWindow(getController())));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewUsageBatchWindow(getController())));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df");
    }

    private void onAddToScenarioClicked() {
        String message = getScenarioValidationMessage();
        if (null != message) {
            Windows.showNotificationWindow(message);
        } else {
            showCreateScenarioWindow(new CreateScenarioWindow(getController()));
        }
    }

    private String getScenarioValidationMessage() {
        String message = null;
        if (0 == getController().getBeansCount()) {
            message = ForeignUi.getMessage("message.error.empty_usages");
        } else if (!getController().isValidUsagesState(UsageStatusEnum.ELIGIBLE)) {
            message = ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.ELIGIBLE,
                "added to scenario");
        } else {
            List<Long> accountNumbers = getController().getInvalidRightsholders();
            if (CollectionUtils.isNotEmpty(accountNumbers)) {
                message = ForeignUi.getMessage("message.error.add_to_scenario.invalid_rightsholders", "created",
                    accountNumbers);
            }
        }
        return message;
    }

    private static class SendForResearchFileDownloader extends OnDemandFileDownloader {

        private final IFasUsageController controller;

        /**
         * Controller.
         *
         * @param controller instance of {@link IFasUsageController}
         */
        SendForResearchFileDownloader(IFasUsageController controller) {
            super(controller.getSendForResearchUsagesStreamSource().getSource());
            this.controller = controller;
        }

        @Override
        public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) {
            return controller.isValidUsagesState(UsageStatusEnum.WORK_NOT_FOUND)
                && super.handleConnectorRequest(request, response, path);
        }
    }
}
