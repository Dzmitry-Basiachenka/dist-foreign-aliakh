package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.NotificationWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Main widget for usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/17
 *
 * @author Mikita Hladkikh
 */
class UsagesWidget extends HorizontalSplitPanel implements IUsagesWidget {

    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";

    private IUsagesController controller;
    private DataProvider<UsageDto, Void> dataProvider;
    private Grid<UsageDto> usagesGrid;
    private UsagesMediator mediator;
    private Button loadUsageBatchButton;
    private Button loadFundPoolButton;
    private Button loadResearchedUsagesButton;
    private Button deleteButton;
    private Button sendForResearchButton;
    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar withdrawnFundMenuBar;

    @Override
    public void refresh() {
        mediator.onProductFamilyChanged(getController().getSelectedProductFamily());
        dataProvider.refreshAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UsagesWidget init() {
        setFirstComponent(controller.initUsagesFilterWidget());
        setSecondComponent(initUsagesLayout());
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUsagesController controller) {
        this.controller = controller;
    }

    @Override
    public UsagesMediator initMediator() {
        mediator = new UsagesMediator();
        mediator.setLoadUsageBatchButton(loadUsageBatchButton);
        mediator.setLoadFundPoolButton(loadFundPoolButton);
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setDeleteUsageButton(deleteButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setSendForResearchButton(sendForResearchButton);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setWithdrawnFundMenuBar(withdrawnFundMenuBar);
        return mediator;
    }

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    /**
     * @return instance of {@link IUsagesController}.
     */
    IUsagesController getController() {
        return controller;
    }

    private VerticalLayout initUsagesLayout() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getSize();
                if (0 < size) {
                    usagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    usagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                return size;
            });
        usagesGrid = new Grid<>(dataProvider);
        addColumns();
        VaadinUtils.addComponentStyle(usagesGrid, "usages-grid");
        usagesGrid.setSelectionMode(SelectionMode.NONE);
        usagesGrid.setSizeFull();
        usagesGrid.getColumns().forEach(column -> column.setSortable(true));
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), usagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(usagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "usages-layout");
        return layout;
    }

    private void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), "table.column.fiscal_year",
            "fiscalYear", true, 105);
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", true, 125);
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, 135);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", true, 115);
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle", true, 300);
        addColumn(UsageDto::getArticle, "table.column.article", "article", true, 135);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, 135);
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", true, 90);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addColumn(usage -> CurrencyUtils.format(usage.getReportedValue(), null), "table.column.reported_value",
            "reportedValue", "v-align-right", 130);
        addColumn(usage -> CurrencyUtils.format(usage.getGrossAmount(), null), "table.column.gross_amount",
            "grossAmount", "v-align-right", 110);
        addColumn(usage -> CurrencyUtils.format(usage.getBatchGrossAmount(), null), "table.column.batch_gross_amount",
            "batchGrossAmount", "v-align-right", 135);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 115);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, 150);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, 145);
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 300);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, boolean isHidable,
                           double width) {
        usagesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, String style,
                           double width) {
        usagesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> style)
            .setWidth(width);
    }

    private HorizontalLayout initButtonsLayout() {
        loadUsageBatchButton = Buttons.createButton(ForeignUi.getMessage("button.load_usage_batch"));
        loadUsageBatchButton.addClickListener(event -> Windows.showModalWindow(new UsageBatchUploadWindow(controller)));
        loadFundPoolButton = Buttons.createButton(ForeignUi.getMessage("button.load_fund_pool"));
        loadFundPoolButton.addClickListener(event -> Windows.showModalWindow(new FundPoolLoadWindow(controller)));
        loadResearchedUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.load_researched_usages"));
        loadResearchedUsagesButton.addClickListener(event ->
            Windows.showModalWindow(new ResearchedUsagesUploadWindow(controller)));
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked());
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource());
        fileDownloader.extend(exportButton);
        sendForResearchButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_research"));
        SendForResearchFileDownloader sendForResearchDownloader = new SendForResearchFileDownloader(controller);
        sendForResearchDownloader.extend(sendForResearchButton);
        // Click listener and second isWorkNotFoundStatusApplied() call were added due to problem with
        // modal window appearance in chrome browser
        sendForResearchButton.addClickListener(event -> {
            if (!controller.isValidUsagesState(UsageStatusEnum.WORK_NOT_FOUND)) {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.WORK_NOT_FOUND,
                        "sent for research"));
            } else {
                NotificationWindow window = new NotificationWindow(ForeignUi.getMessage("message.send_for_research"));
                window.addCloseListener(closeEvent -> controller.clearFilter());
                Windows.showModalWindow(window);
            }
        });
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete_usage_batch"));
        deleteButton.addClickListener(event -> Windows.showModalWindow(new DeleteUsageBatchWindow(controller)));
        assignClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.assign_classification"));
        assignClassificationButton.addClickListener(
            event -> new NtsUsageBatchSelectorWidget(controller).showFilterWindow());
        initMenuBar();
        VaadinUtils.setButtonsAutoDisabled(loadUsageBatchButton, loadFundPoolButton, assignClassificationButton,
            loadResearchedUsagesButton, addToScenarioButton, deleteButton);
        HorizontalLayout layout =
            new HorizontalLayout(loadUsageBatchButton, loadFundPoolButton, withdrawnFundMenuBar,
                assignClassificationButton, sendForResearchButton, loadResearchedUsagesButton, addToScenarioButton,
                exportButton, deleteButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    private void initMenuBar() {
        withdrawnFundMenuBar = new MenuBar();
        MenuItem menuItem =
            withdrawnFundMenuBar.addItem(ForeignUi.getMessage("menu.caption.additional_funds"), null, null);
        menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            (item) -> {/*TODO {aliakh} create fund pool*/});
        menuItem.addItem(ForeignUi.getMessage("menu.item.delete"), null,
            (item) -> {/*TODO {isuvorau} delete fund pool*/});
        VaadinUtils.addComponentStyle(withdrawnFundMenuBar, "withdrawn-fund-menu-bar");
    }

    private void onAddToScenarioClicked() {
        if (0 < controller.getSize()) {
            if (controller.isValidUsagesState(UsageStatusEnum.ELIGIBLE)) {
                List<Long> accountNumbers = controller.getInvalidRightsholders();
                if (CollectionUtils.isNotEmpty(accountNumbers)) {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.add_to_scenario.invalid_rightsholders", "created",
                            accountNumbers));
                } else {
                    CreateScenarioWindow window = new CreateScenarioWindow(controller);
                    window.addListener(ScenarioCreateEvent.class, controller,
                        IUsagesController.ON_SCENARIO_CREATED);
                    Windows.showModalWindow(window);
                }
            } else {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.ELIGIBLE,
                        "added to scenario"));
            }
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.error.empty_usages"));
        }
    }

    private static class SendForResearchFileDownloader extends OnDemandFileDownloader {

        private final IUsagesController controller;

        /**
         * Controller.
         *
         * @param controller instance of {@link IUsagesController}
         */
        SendForResearchFileDownloader(IUsagesController controller) {
            super(controller.getSendForResearchUsagesStreamSource());
            this.controller = controller;
        }

        @Override
        public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) {
            return controller.isValidUsagesState(UsageStatusEnum.WORK_NOT_FOUND)
                && super.handleConnectorRequest(request, response, path);
        }
    }
}
