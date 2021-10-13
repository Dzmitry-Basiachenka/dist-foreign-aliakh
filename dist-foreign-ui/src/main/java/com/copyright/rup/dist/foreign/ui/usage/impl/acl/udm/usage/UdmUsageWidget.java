package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
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
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
public class UdmUsageWidget extends HorizontalSplitPanel implements IUdmUsageWidget {

    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";
    private static final int EXPECTED_SELECTED_SIZE = 1;
    private static final List<UsageStatusEnum> USAGE_STATUSES_EDIT_ALLOWED_FOR_RESEARCHER = Arrays.asList(
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.OPS_REVIEW);
    private final boolean hasResearcherPermission = ForeignSecurityUtils.hasResearcherPermission();
    private final boolean hasManagerPermission = ForeignSecurityUtils.hasManagerPermission();
    private final boolean hasSpecialistPermission = ForeignSecurityUtils.hasSpecialistPermission();
    private final Button editButton = Buttons.createButton(ForeignUi.getMessage("button.edit_usage"));
    private final Button multipleEditButton = Buttons.createButton(ForeignUi.getMessage("button.edit_multiple_usage"));
    private final Button publishButton = Buttons.createButton(ForeignUi.getMessage("button.publish_usage"));
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

    @Override
    @SuppressWarnings("unchecked")
    public IUdmUsageWidget init() {
        setSplitPosition(200, Unit.PIXELS);
        setFirstComponent(controller.initUsagesFilterWidget());
        setSecondComponent(initUsagesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public IMediator initMediator() {
        UdmUsageMediator mediator = new UdmUsageMediator();
        mediator.setBatchMenuBar(udmBatchMenuBar);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setEditButton(editButton);
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
        editButton.setEnabled(false);
        editButton.addClickListener(event -> {
            UdmUsageDto selectedUsage = udmUsagesGrid.getSelectedItems().iterator().next();
            initModalWindow(Collections.singleton(selectedUsage),
                () -> new UdmEditUsageWindow(controller, selectedUsage, saveEvent -> refresh()));
        });
        multipleEditButton.setEnabled(false);
        multipleEditButton.addClickListener(event -> {
            Set<UdmUsageDto> selectedUsages = udmUsagesGrid.getSelectedItems();
            initModalWindow(selectedUsages,
                hasResearcherPermission
                    ? () -> new UdmEditMultipleUsagesResearcherWindow(controller, selectedUsages,
                    saveEvent -> refresh())
                    : () -> new UdmEditMultipleUsagesWindow(controller, selectedUsages, saveEvent -> refresh()));
        });
        publishButton.addClickListener(event -> Windows.showModalWindow(new UdmUsageBaselinePublishWindow(controller)));
        VaadinUtils.setButtonsAutoDisabled(editButton, multipleEditButton);
        return new HorizontalLayout(udmBatchMenuBar, assignmentMenuBar, editButton, multipleEditButton, publishButton,
            exportButton);
    }

    private void initModalWindow(Set<UdmUsageDto> selectedUsages, Supplier<Window> createWindow) {
        if (hasResearcherPermission) {
            initEditResearcherWindow(selectedUsages, createWindow);
        } else {
            initEditWindow(selectedUsages, createWindow);
        }
    }

    private void initEditWindow(Set<UdmUsageDto> selectedUsages, Supplier<Window> createWindow) {
        if (isUsagesProcessingCompleted(selectedUsages)) {
            openEditWindow(selectedUsages, createWindow);
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.error.processing_usages"));
        }
    }

    private void initEditResearcherWindow(Set<UdmUsageDto> selectedUsages, Supplier<Window> createWindow) {
        if (isEditAllowedForResearcher(selectedUsages)) {
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

    private boolean isEditAllowedForResearcher(Set<UdmUsageDto> udmUsages) {
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
            selectedItem -> {
                Set<UdmUsageDto> udmUsages = udmUsagesGrid.getSelectedItems();
                int usagesCount = udmUsages.size();
                Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.assign", usagesCount),
                    () -> {
                        controller.assignUsages(udmUsages);
                        refresh();
                        Windows.showNotificationWindow(
                            ForeignUi.getMessage("message.notification.assignment_completed", usagesCount));
                    });
            });
        assignItem.setEnabled(false);
        unassignItem = item.addItem(ForeignUi.getMessage("menu.item.unassign"), null,
            selectedItem -> {
                boolean isUnassignmentAllowed = udmUsagesGrid.getSelectedItems()
                    .stream()
                    .allMatch(udmUsageDto -> userName.equals(udmUsageDto.getAssignee()));
                if (isUnassignmentAllowed) {
                    int usagesCount = udmUsagesGrid.getSelectedItems().size();
                    Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.unassign", usagesCount),
                        () -> {
                            controller.unassignUsages(getSelectedUsageIds());
                            refresh();
                            Windows.showNotificationWindow(
                                ForeignUi.getMessage("message.notification.unassignment_completed", usagesCount));
                        });
                } else {
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.error.unassign"));
                }
            });
        unassignItem.setEnabled(false);
        VaadinUtils.addComponentStyle(assignmentMenuBar, "v-menubar-df");
    }

    private void initUsagesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (isNotViewOnlyPermission()) {
                    switchSelectAllCheckBoxVisibility(size);
                }
                if (0 < size) {
                    udmUsagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmUsagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                udmUsagesGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            });
        udmUsagesGrid = new Grid<>(dataProvider);
        addColumns();
        udmUsagesGrid.setSizeFull();
        if (isNotViewOnlyPermission()) {
            gridSelectionModel =
                (MultiSelectionModelImpl<UdmUsageDto>) udmUsagesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            udmUsagesGrid.addSelectionListener(event -> {
                Set<UdmUsageDto> usageDtos = event.getAllSelectedItems();
                boolean isSelected = CollectionUtils.isNotEmpty(usageDtos);
                assignItem.setEnabled(isSelected);
                unassignItem.setEnabled(isSelected);
                editButton.setEnabled(EXPECTED_SELECTED_SIZE == usageDtos.size());
                multipleEditButton.setEnabled(isSelected);
            });
        } else {
            udmUsagesGrid.setSelectionMode(Grid.SelectionMode.NONE);
        }
        VaadinUtils.addComponentStyle(udmUsagesGrid, "udm-usages-grid");
    }

    private boolean isNotViewOnlyPermission() {
        return hasSpecialistPermission || hasManagerPermission || hasResearcherPermission;
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        //TODO {dbasiachenka} replace after implementation of controller logic
        int threshold = 10000;
        gridSelectionModel.setSelectAllCheckBoxVisibility(beansCount > threshold
            ? SelectAllCheckBoxVisibility.HIDDEN
            : SelectAllCheckBoxVisibility.VISIBLE);
        gridSelectionModel.beforeClientResponse(false);
    }

    private void addColumns() {
        FooterRow footer = udmUsagesGrid.appendFooterRow();
        udmUsagesGrid.setFooterVisible(true);
        Column<UdmUsageDto, ?> column = udmUsagesGrid.addComponentColumn(usage -> {
            String udmUsageId = usage.getId();
            Button button = Buttons.createButton(udmUsageId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> {
                controller.showUdmUsageHistory(udmUsageId, closeEvent -> restoreSelection(selectedUdmUsages));
                selectUsageForAudit(usage);
            });
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setId("detailId")
            .setSortProperty("detailId")
            .setWidth(200);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmUsageDto::getPeriod, "table.column.period", "period", 100, false),
            addColumn(UdmUsageDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130, false),
            addColumn(UdmUsageDto::getStatus, "table.column.usage_status", "status", 100, false),
            addColumn(UdmUsageDto::getAssignee, "table.column.assignee", "assignee", 100, false),
            addColumn(UdmUsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 150, false),
            addColumn(UdmUsageDto::getRhName, "table.column.rh_account_name", "rhName", 150, false),
            addColumn(UdmUsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100, false),
            addColumn(UdmUsageDto::getReportedTitle, "table.column.reported_title", "reportedTitle", 120, false),
            addColumn(UdmUsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 100, false),
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
            addColumn(u -> u.getDetailLicenseeClass().getDescription(), "table.column.det_lc_name", "detLcName", 100,
                false),
            addColumn(UdmUsageDto::getCompanyId, "table.column.company_id", "companyId", 100, hasResearcherPermission),
            addColumn(UdmUsageDto::getCompanyName, "table.column.company_name", "companyName", 120,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getSurveyRespondent, "table.column.survey_respondent", "surveyRespondent", 150,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getIpAddress, "table.column.ip_address", "ipAddress", 100,
                !(hasSpecialistPermission || hasManagerPermission)),
            addColumn(UdmUsageDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getChannel, "table.column.channel", "channel", 100, false),
            addColumn(u -> getStringFromLocalDate(u.getUsageDate()), "table.column.usage_date", "usageDate", 100,
                false),
            addColumn(u -> getStringFromLocalDate(u.getSurveyStartDate()), "table.column.survey_start_date",
                "surveyStartDate", 130, false),
            addColumn(u -> getStringFromLocalDate(u.getSurveyEndDate()), "table.column.survey_end_date",
                "surveyEndDate", 130, false),
            addColumn(UdmUsageDto::getAnnualMultiplier, "table.column.annual_multiplier", "annualMultiplier", 130,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getStatisticalMultiplier, "table.column.statistical_multiplier",
                "statisticalMultiplier", 150, hasResearcherPermission),
            addColumn(UdmUsageDto::getReportedTypeOfUse, "table.column.reported_tou", "reportedTypeOfUse", 120, false),
            addColumn(UdmUsageDto::getQuantity, "table.column.quantity", "quantity", 100, hasResearcherPermission),
            addColumn(UdmUsageDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies", 130,
                hasResearcherPermission),
            addColumn(u -> null != u.getIneligibleReason() ? u.getIneligibleReason().getReason() : StringUtils.EMPTY,
                "table.column.ineligible_reason", "ineligibleReason", 200, false),
            addColumn(u -> getStringFromDate(u.getCreateDate()), "table.column.load_date", "createDate", 100, false),
            addColumn(UdmUsageDto::getUpdateUser, "table.column.updated_by", "updateUser", 150, false),
            addColumn(u -> getStringFromDate(u.getUpdateDate()), "table.column.updated_date", "updateDate", 110,
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

    private String getStringFromLocalDate(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }

    private Set<String> getSelectedUsageIds() {
        return udmUsagesGrid.getSelectedItems()
            .stream()
            .map(BaseEntity::getId)
            .collect(Collectors.toSet());
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
     * Hides current usage selection and selects usage for which the history window was opened.
     *
     * @param usageToSeeAudit usage to select
     */
    private void selectUsageForAudit(UdmUsageDto usageToSeeAudit) {
        selectedUdmUsages = udmUsagesGrid.getSelectedItems();
        udmUsagesGrid.deselectAll();
        udmUsagesGrid.select(usageToSeeAudit);
    }

    /**
     * Restores previous usage selection. Removes selection of usage for which the history window was opened.
     *
     * @param usagesToSelect set of usages to select
     */
    private void restoreSelection(Set<UdmUsageDto> usagesToSelect) {
        selectedUdmUsages = null;
        udmUsagesGrid.deselectAll();
        usagesToSelect.forEach(udmUsageDto -> udmUsagesGrid.select(udmUsageDto));
    }
}
