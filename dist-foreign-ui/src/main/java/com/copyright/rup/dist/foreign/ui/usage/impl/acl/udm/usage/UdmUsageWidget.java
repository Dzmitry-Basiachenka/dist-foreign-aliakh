package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IUdmUsageWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/2021
 *
 * @author Ihar Suvorau
 */
public class UdmUsageWidget extends HorizontalSplitPanel implements IUdmUsageWidget, IDateFormatter {

    private static final long serialVersionUID = -5893066489854352835L;
    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";
    private static final Set<UsageStatusEnum> USAGE_STATUSES_EDIT_ALLOWED_FOR_RESEARCHER = EnumSet.of(
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.OPS_REVIEW);
    private static final String USAGES_PROCESSING_ERROR_MESSAGE = "message.error.processing_usages";
    private final boolean hasResearcherPermission = ForeignSecurityUtils.hasResearcherPermission();
    private final boolean hasManagerPermission = ForeignSecurityUtils.hasManagerPermission();
    private final boolean hasSpecialistPermission = ForeignSecurityUtils.hasSpecialistPermission();
    private final Button multipleEditButton = Buttons.createButton(ForeignUi.getMessage("button.edit_multiple_usage"));
    private final Button publishButton = Buttons.createButton(ForeignUi.getMessage("button.publish"));
    private final String userName = RupContextUtils.getUserName();
    private IUdmUsageController controller;
    private Grid<UdmUsageDto> udmUsagesGrid;
    private DataProvider<UdmUsageDto, Void> dataProvider;
    private MenuBar udmBatchMenuBar;
    private MenuBar assignmentMenuBar;
    private MenuBar.MenuItem assignItem;
    private MenuBar.MenuItem unassignItem;
    private SearchWidget searchWidget;
    private Set<UdmUsageDto> selectedUdmUsages;
    private MultiSelectionModelImpl<UdmUsageDto> gridSelectionModel;
    private boolean isAllSelected;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmUsageWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initUsagesFilterWidget());
        setSecondComponent(initUsagesLayout());
        setLocked(true);
        super.setSizeFull();
        return this;
    }

    @Override
    public IMediator initMediator() {
        UdmUsageMediator mediator = new UdmUsageMediator();
        mediator.setBatchMenuBar(udmBatchMenuBar);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setMultipleEditButton(multipleEditButton);
        mediator.setPublishButton(publishButton);
        return mediator;
    }

    @Override
    public void refresh() {
        udmUsagesGrid.deselectAll();
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IUdmUsageController controller) {
        this.controller = controller;
    }

    @Override
    public String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }

    @Override
    public void clearSearch() {
        searchWidget.clearSearchValue();
    }

    private VerticalLayout initUsagesLayout() {
        initUsagesGrid();
        VerticalLayout layout = new VerticalLayout(initToolbarLayout(), udmUsagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmUsagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-usages-layout");
        return layout;
    }

    private VerticalLayout initToolbarLayout() {
        initUsageBatchMenuBar();
        initAssignmentMenuBar();
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage(getSearchMessage()));
        searchWidget.setWidth(65, Unit.PERCENTAGE);
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout toolbar = new VerticalLayout(buttonsLayout, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_LEFT);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(true);
        VaadinUtils.addComponentStyle(toolbar, "udm-usages-toolbar");
        return toolbar;
    }

    private HorizontalLayout initButtonsLayout() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(getExportUdmUsagesStreamSourceForSpecificRole().getSource());
        fileDownloader.extend(exportButton);
        multipleEditButton.setEnabled(false);
        multipleEditButton.addClickListener(event -> {
            Set<UdmUsageDto> selectedUsages = udmUsagesGrid.getSelectedItems();
            initMultipleEditWindow(selectedUsages,
                hasResearcherPermission
                    ? () -> new UdmEditMultipleUsagesResearcherWindow(controller, selectedUsages,
                    saveEvent -> refresh())
                    : () -> new UdmEditMultipleUsagesWindow(controller, selectedUsages, saveEvent -> refresh()));
        });
        publishButton.addClickListener(event -> Windows.showModalWindow(new UdmUsageBaselinePublishWindow(controller,
            publishEvent -> refresh())));
        VaadinUtils.setButtonsAutoDisabled(multipleEditButton, publishButton);
        return new HorizontalLayout(udmBatchMenuBar, assignmentMenuBar, multipleEditButton, publishButton,
            exportButton);
    }

    private void initMultipleEditWindow(Set<UdmUsageDto> selectedUsages, Supplier<Window> createWindow) {
        if (hasResearcherPermission) {
            initEditResearcherWindow(selectedUsages, createWindow);
        } else {
            if (isUsagesProcessingCompleted(selectedUsages)) {
                Windows.showModalWindow(createWindow.get());
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage(USAGES_PROCESSING_ERROR_MESSAGE));
            }
        }
    }

    private void initEditResearcherWindow(Set<UdmUsageDto> selectedUsages, Supplier<Window> createWindow) {
        if (areUsageStatusesAllowedForResearcher(selectedUsages)) {
            if (areUsagesNonBaseline(selectedUsages)) {
                openEditWindow(selectedUsages, createWindow);
            } else {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.udm_usage_edit_forbidden_baseline"));
            }
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.error.udm_usage_edit_forbidden_for_researcher",
                    USAGE_STATUSES_EDIT_ALLOWED_FOR_RESEARCHER
                        .stream()
                        .map(UsageStatusEnum::name)
                        .collect(Collectors.joining(", "))));
        }
    }

    private void openEditWindow(Set<UdmUsageDto> selectedUsages, Supplier<Window> createWindow) {
        if (checkHasUsagesAssignee(selectedUsages)) {
            Windows.showModalWindow(createWindow.get());
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.error.udm_usage_edit_forbidden_unassigned"));
        }
    }

    private boolean checkHasUsagesAssignee(Set<UdmUsageDto> usages) {
        return usages.stream().allMatch(usage -> userName.equals(usage.getAssignee()));
    }

    private boolean areUsagesNonBaseline(Set<UdmUsageDto> udmUsages) {
        return udmUsages.stream().noneMatch(UdmUsageDto::isBaselineFlag);
    }

    private boolean isUsagesProcessingCompleted(Set<UdmUsageDto> udmUsages) {
        return udmUsages.stream().noneMatch(usageDto -> usageDto.getStatus().equals(UsageStatusEnum.NEW)
            || usageDto.getStatus().equals(UsageStatusEnum.WORK_FOUND));
    }

    private boolean areUsageStatusesAllowedForResearcher(Set<UdmUsageDto> udmUsages) {
        return udmUsages.stream()
            .allMatch(usageDto -> USAGE_STATUSES_EDIT_ALLOWED_FOR_RESEARCHER.contains(usageDto.getStatus()));
    }

    private String getSearchMessage() {
        return hasResearcherPermission ? "prompt.udm_search_researcher" : "prompt.udm_search";
    }

    private void initUsageBatchMenuBar() {
        udmBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            udmBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.udm_usage_batch"), null, null);
        menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new UdmBatchUploadWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewUdmBatchWindow(controller)));
        VaadinUtils.addComponentStyle(udmBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(udmBatchMenuBar, "v-menubar-df");
    }

    private void initAssignmentMenuBar() {
        assignmentMenuBar = new MenuBar();
        MenuBar.MenuItem item = assignmentMenuBar.addItem(ForeignUi.getMessage("menu.caption.assignment"), null, null);
        assignItem = item.addItem(ForeignUi.getMessage("menu.item.assign"), null,
            selectedItem -> showAssignmentConfirmDialog(udmUsages -> controller.assignUsages(udmUsages),
                "message.confirm.assign", "message.notification.assignment_completed"));
        assignItem.setEnabled(false);
        unassignItem = item.addItem(ForeignUi.getMessage("menu.item.unassign"), null,
            selectedItem -> {
                boolean isUnassignmentAllowed = udmUsagesGrid.getSelectedItems()
                    .stream()
                    .allMatch(udmUsageDto -> userName.equals(udmUsageDto.getAssignee()));
                if (isUnassignmentAllowed) {
                    showAssignmentConfirmDialog(udmUsages -> controller.unassignUsages(udmUsages),
                        "message.confirm.unassign", "message.notification.unassignment_completed");
                } else {
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.error.unassign"));
                }
            });
        unassignItem.setEnabled(false);
        VaadinUtils.addComponentStyle(assignmentMenuBar, "v-menubar-df");
    }

    private void showAssignmentConfirmDialog(Consumer<Set<UdmUsageDto>> actionConsumer, String confirmMessage,
                                             String completeMessage) {
        Set<UdmUsageDto> udmUsages = udmUsagesGrid.getSelectedItems();
        int usagesCount = udmUsages.size();
        Windows.showConfirmDialog(ForeignUi.getMessage(confirmMessage, usagesCount),
            () -> {
                actionConsumer.accept(udmUsages);
                refresh();
                Windows.showNotificationWindow(ForeignUi.getMessage(completeMessage, usagesCount));
            });
    }

    private void initUsagesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    udmUsagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmUsagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                switchSelectAllCheckBoxVisibility(size);
                udmUsagesGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            }, UdmUsageDto::getId);
        udmUsagesGrid = new Grid<>(dataProvider);
        addColumns();
        udmUsagesGrid.setSizeFull();
        initSelectionMode();
        initUsageWindowByDoubleClick();
        VaadinUtils.addComponentStyle(udmUsagesGrid, "udm-usages-grid");
    }

    private void addColumns() {
        FooterRow footer = udmUsagesGrid.appendFooterRow();
        udmUsagesGrid.setFooterVisible(true);
        Column<UdmUsageDto, ?> column = udmUsagesGrid.addComponentColumn(usage -> {
            String udmUsageId = usage.getId();
            Button button = Buttons.createButton(udmUsageId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> {
                controller.showUdmUsageHistory(udmUsageId, closeEvent ->
                    restoreSelection(selectedUdmUsages, isAllSelected));
                highlightSelectedUsage(usage);
            });
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setId("detailId")
            .setSortProperty("detailId")
            .setWidth(250);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmUsageDto::getPeriod, "table.column.period", "period", 100, false),
            addColumn(UdmUsageDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130, false),
            addColumn(UdmUsageDto::getStatus, "table.column.usage_status", "status", 145, false),
            addColumn(UdmUsageDto::getAssignee, "table.column.assignee", "assignee", 100, false),
            addColumn(UdmUsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 150, false),
            addColumn(UdmUsageDto::getRhName, "table.column.rh_account_name", "rhName", 300, false),
            addColumn(UdmUsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100, false),
            addColumn(UdmUsageDto::getReportedTitle, "table.column.reported_title", "reportedTitle", 250, false),
            addColumn(UdmUsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 300, false),
            addColumn(UdmUsageDto::getReportedStandardNumber, "table.column.reported_standard_number",
                "reportedStandardNumber", 190, false),
            addColumn(UdmUsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", 150, false),
            addColumn(UdmUsageDto::getReportedPubType, "table.column.reported_pub_type", "reportedPubType", 150, false),
            addColumn(UdmUsageDto::getPubFormat, "table.column.publication_format", "publicationFormat", 150, false),
            addColumn(UdmUsageDto::getArticle, "table.column.article", "article", 100, false),
            addColumn(UdmUsageDto::getLanguage, "table.column.language", "language", 100, false),
            addColumn(u -> null != u.getActionReason() ? u.getActionReason().getReason() : StringUtils.EMPTY,
                "table.column.action_reason", "actionReason", 200, false),
            addColumn(UdmUsageDto::getComment, "table.column.comment", "comment", 200, false),
            addColumn(UdmUsageDto::getResearchUrl, "table.column.research_url", "researchUrl", 200, false),
            addColumn(u -> u.getDetailLicenseeClass().getId(), "table.column.det_lc_id", "detLcId", 100, false),
            addColumn(u -> u.getDetailLicenseeClass().getDescription(), "table.column.det_lc_name", "detLcName", 250,
                false),
            addColumn(UdmUsageDto::getCompanyId, "table.column.company_id", "companyId", 250, hasResearcherPermission),
            addColumn(UdmUsageDto::getCompanyName, "table.column.company_name", "companyName", 250,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getSurveyRespondent, "table.column.survey_respondent", "surveyRespondent", 250,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getIpAddress, "table.column.ip_address", "ipAddress", 100,
                !(hasSpecialistPermission || hasManagerPermission)),
            addColumn(UdmUsageDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getChannel, "table.column.channel", "channel", 100, false),
            addColumn(u -> toShortFormat(u.getUsageDate()), "table.column.usage_date", "usageDate", 100,
                false),
            addColumn(u -> toShortFormat(u.getSurveyStartDate()), "table.column.survey_start_date",
                "surveyStartDate", 130, false),
            addColumn(u -> toShortFormat(u.getSurveyEndDate()), "table.column.survey_end_date",
                "surveyEndDate", 130, false),
            addColumn(UdmUsageDto::getAnnualMultiplier, "table.column.annual_multiplier", "annualMultiplier", 130,
                hasResearcherPermission),
            addBigDecimalColumn(UdmUsageDto::getStatisticalMultiplier, "table.column.statistical_multiplier",
                "statisticalMultiplier", 150, hasResearcherPermission),
            addColumn(UdmUsageDto::getReportedTypeOfUse, "table.column.reported_tou", "reportedTypeOfUse", 120, false),
            addColumn(UdmUsageDto::getTypeOfUse, "table.column.tou", "typeOfUse", 120, false),
            addColumn(UdmUsageDto::getQuantity, "table.column.quantity", "quantity", 100, hasResearcherPermission),
            addBigDecimalColumn(UdmUsageDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies",
                130, hasResearcherPermission),
            addColumn(u -> null != u.getIneligibleReason() ? u.getIneligibleReason().getReason() : StringUtils.EMPTY,
                "table.column.ineligible_reason", "ineligibleReason", 200, false),
            addColumn(u -> toShortFormat(u.getCreateDate()), "table.column.load_date", "createDate", 100, false),
            addColumn(UdmUsageDto::getUpdateUser, "table.column.updated_by", "updateUser", 200, false),
            addColumn(u -> toShortFormat(u.getUpdateDate()), "table.column.updated_date", "updateDate", 110,
                false));
    }

    private Column<UdmUsageDto, ?> addColumn(ValueProvider<UdmUsageDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width, boolean isHidden) {
        return udmUsagesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(!isHidden)
            .setHidden(isHidden)
            .setWidth(width);
    }

    private Column<UdmUsageDto, ?> addBigDecimalColumn(Function<UdmUsageDto, BigDecimal> function,
                                                       String captionProperty, String columnId, double width,
                                                       boolean isHidden) {
        return udmUsagesGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(!isHidden)
            .setHidden(isHidden)
            .setWidth(width);
    }

    private void initSelectionMode() {
        if (isNotViewOnlyPermission()) {
            gridSelectionModel =
                (MultiSelectionModelImpl<UdmUsageDto>) udmUsagesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            udmUsagesGrid.addSelectionListener(event -> {
                Set<UdmUsageDto> usageDtos = event.getAllSelectedItems();
                boolean isSelected = CollectionUtils.isNotEmpty(usageDtos);
                assignItem.setEnabled(isSelected);
                unassignItem.setEnabled(isSelected);
                multipleEditButton.setEnabled(isSelected);
            });
        } else {
            udmUsagesGrid.setSelectionMode(SelectionMode.SINGLE);
        }
    }

    private boolean isNotViewOnlyPermission() {
        return hasSpecialistPermission || hasManagerPermission || hasResearcherPermission;
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        if (isNotViewOnlyPermission()) {
            gridSelectionModel.setSelectAllCheckBoxVisibility(
                0 == beansCount || beansCount > controller.getUdmRecordThreshold()
                    ? SelectAllCheckBoxVisibility.HIDDEN
                    : SelectAllCheckBoxVisibility.VISIBLE);
            gridSelectionModel.beforeClientResponse(false);
        }
    }

    private void initUsageWindowByDoubleClick() {
        udmUsagesGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UdmUsageDto udmUsageDto = event.getItem();
                if (isNotViewOnlyPermission()) {
                    initUsageWindow(udmUsageDto);
                } else {
                    UdmViewUsageWindow viewWindow = new UdmViewUsageWindow(udmUsageDto);
                    viewWindow.addCloseListener(closeEvent -> udmUsagesGrid.deselect(udmUsageDto));
                    Windows.showModalWindow(viewWindow);
                }
                highlightSelectedUsage(udmUsageDto);
            }
        });
    }

    private void initUsageWindow(UdmUsageDto selectedUsage) {
        if (hasResearcherPermission) {
            initUsageWindowForResearcher(selectedUsage);
        } else {
            initSingleEditWindow(selectedUsage);
        }
    }

    private void initUsageWindowForResearcher(UdmUsageDto selectedUsage) {
        if (isEditAllowedForResearcher(Set.of(selectedUsage))) {
            showEditWindow(selectedUsage);
        } else {
            UdmViewUsageWindow viewWindow = new UdmViewUsageWindow(selectedUsage);
            viewWindow.addCloseListener(closeEvent -> restoreSelection(selectedUdmUsages, isAllSelected));
            Windows.showModalWindow(viewWindow);
        }
    }

    private void initSingleEditWindow(UdmUsageDto selectedUsage) {
        if (isUsagesProcessingCompleted(Set.of(selectedUsage))) {
            showEditWindow(selectedUsage);
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage(USAGES_PROCESSING_ERROR_MESSAGE));
        }
    }

    private void showEditWindow(UdmUsageDto selectedUsage) {
        UdmEditUsageWindow editWindow = new UdmEditUsageWindow(controller, selectedUsage, saveEvent -> refresh());
        editWindow.addCloseListener(closeEvent -> restoreSelection(selectedUdmUsages, isAllSelected));
        Windows.showModalWindow(editWindow);
    }

    private boolean isEditAllowedForResearcher(Set<UdmUsageDto> selectedUsages) {
        return areUsageStatusesAllowedForResearcher(selectedUsages)
            && areUsagesNonBaseline(selectedUsages)
            && checkHasUsagesAssignee(selectedUsages);
    }

    private IStreamSource getExportUdmUsagesStreamSourceForSpecificRole() {
        if (hasSpecialistPermission || hasManagerPermission) {
            return controller.getExportUdmUsagesStreamSourceSpecialistManagerRoles();
        } else if (hasResearcherPermission) {
            return controller.getExportUdmUsagesStreamSourceResearcherRole();
        } else {
            return controller.getExportUdmUsagesStreamSourceViewRole();
        }
    }

    /**
     * Hides current usage selection and selects usage for which view or history window was opened.
     *
     * @param usageToSeeAudit usage to select
     */
    private void highlightSelectedUsage(UdmUsageDto usageToSeeAudit) {
        selectedUdmUsages = udmUsagesGrid.getSelectedItems();
        isAllSelected = Objects.nonNull(gridSelectionModel) && gridSelectionModel.isAllSelected();
        udmUsagesGrid.deselectAll();
        udmUsagesGrid.select(usageToSeeAudit);
    }

    /**
     * Restores previous usage selection. Removes selection of usage for which view or history window was opened.
     *
     * @param usagesToSelect      set of usages to select
     * @param isAllUsagesSelected {@code true} if all usages are selected, {@code false} otherwise
     */
    private void restoreSelection(Set<UdmUsageDto> usagesToSelect, boolean isAllUsagesSelected) {
        selectedUdmUsages = null;
        isAllSelected = false;
        if (Objects.nonNull(gridSelectionModel) && isAllUsagesSelected) {
            gridSelectionModel.selectAll();
        } else {
            udmUsagesGrid.deselectAll();
            usagesToSelect.forEach(udmUsageDto -> udmUsagesGrid.select(udmUsageDto));
        }
    }
}
