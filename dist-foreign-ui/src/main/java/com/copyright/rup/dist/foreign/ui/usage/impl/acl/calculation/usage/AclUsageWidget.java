package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Implementation of {@link IAclUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageWidget extends HorizontalSplitPanel implements IAclUsageWidget {

    private static final String EMPTY_STYLE_NAME = "empty-acl-usages-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";

    private IAclUsageController controller;
    private Grid<AclUsageDto> aclUsagesGrid;
    private DataProvider<AclUsageDto, Void> dataProvider;
    private MenuBar aclUsageBatchMenuBar;
    private Button editButton;
    private MultiSelectionModelImpl<AclUsageDto> gridSelectionModel;

    @Override
    @SuppressWarnings("unchecked")
    public IAclUsageWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initAclUsageFilterWidget());
        setSecondComponent(initUsagesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void refresh() {
        aclUsagesGrid.deselectAll();
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IAclUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AclUsageMediator mediator = new AclUsageMediator();
        mediator.setAclUsageBatchMenuBar(aclUsageBatchMenuBar);
        mediator.setEditButton(editButton);
        return mediator;
    }

    private VerticalLayout initUsagesLayout() {
        initUsagesGrid();
        initAclUsageBatchMenuBar();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), aclUsagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(aclUsagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "acl-usages-layout");
        return layout;
    }

    private void initUsagesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    aclUsagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    aclUsagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                switchSelectAllCheckBoxVisibility(size);
                aclUsagesGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            }, AclUsageDto::getId);
        aclUsagesGrid = new Grid<>(dataProvider);
        addColumns();
        initSelectionMode();
        aclUsagesGrid.setSizeFull();
        VaadinUtils.addComponentStyle(aclUsagesGrid, "acl-usages-grid");
    }

    private void initAclUsageBatchMenuBar() {
        aclUsageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            aclUsageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            item -> Windows.showModalWindow(new CreateAclUsageBatchWindow(controller)));
        VaadinUtils.addComponentStyle(aclUsageBatchMenuBar, "acl-usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(aclUsageBatchMenuBar, "v-menubar-df");
    }

    private HorizontalLayout initButtonsLayout() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportAclUsagesStreamSource().getSource());
        fileDownloader.extend(exportButton);
        editButton = Buttons.createButton(ForeignUi.getMessage("button.edit"));
        editButton.setEnabled(false);
        editButton.addClickListener(event -> {
            Set<AclUsageDto> selectedUsages = aclUsagesGrid.getSelectedItems();
            if (selectedUsages.stream().allMatch(AclUsageDto::isEditable)) {
                Windows.showModalWindow(
                    new EditAclUsageWindow(controller, selectedUsages, saveEvent -> refresh()));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.usage_not_editable"));
            }
        });
        HorizontalLayout layout = new HorizontalLayout(aclUsageBatchMenuBar, editButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "acl-usage-buttons");
        return layout;
    }

    private void addColumns() {
        FooterRow footer = aclUsagesGrid.appendFooterRow();
        aclUsagesGrid.setFooterVisible(true);
        footer.getCell(addColumn(AclUsageDto::getId, "table.column.detail_id", "detailId", 250))
            .setText(String.format(FOOTER_LABEL, 0));
        footer.join(addColumn(AclUsageDto::getPeriod, "table.column.period", "period", 100),
            addColumn(AclUsageDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100),
            addColumn(AclUsageDto::getChannel, "table.column.channel", "channel", 100),
            addColumn(AclUsageDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130),
            addColumn(AclUsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(AclUsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 300),
            addColumn(u -> u.getDetailLicenseeClass().getId(), "table.column.det_lc_id", "detLcId", 100),
            addColumn(u -> u.getDetailLicenseeClass().getDescription(), "table.column.det_lc_name", "detLcName", 250),
            addColumn(AclUsageDto::getAggregateLicenseeClassId, "table.column.aggregate_licensee_class_id",
                "aggLcId", 100),
            addColumn(AclUsageDto::getAggregateLicenseeClassName, "table.column.aggregate_licensee_class_name",
                "aggLcName", 100),
            addColumn(AclUsageDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120),
            addColumn(usage -> Objects.nonNull(usage.getPublicationType()) ? usage.getPublicationType().getName()
                : StringUtils.EMPTY, "table.column.publication_type", "publicationType", 150),
            addAmountColumn(AclUsageDto::getContentUnitPrice, "table.column.content_unit_price", "contentUnitPrice",
                200),
            addColumn(AclUsageDto::getTypeOfUse, "table.column.tou", "typeOfUse", 120),
            addColumn(AclUsageDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies", 130),
            addColumn(AclUsageDto::getUpdateUser, "table.column.updated_by", "updateUser", 200),
            addColumn(value -> DateUtils.format(value.getUpdateDate()), "table.column.updated_date", "updateDate",
                110));
    }

    private Column<AclUsageDto, ?> addColumn(ValueProvider<AclUsageDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width) {
        return aclUsagesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<AclUsageDto, ?> addAmountColumn(Function<AclUsageDto, BigDecimal> function, String captionProperty,
                                                   String columnId, double width) {
        return aclUsagesGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        if (Objects.nonNull(gridSelectionModel) && ForeignSecurityUtils.hasSpecialistPermission()) {
            gridSelectionModel.setSelectAllCheckBoxVisibility(
                0 == beansCount || beansCount > controller.getRecordThreshold()
                    ? SelectAllCheckBoxVisibility.HIDDEN
                    : SelectAllCheckBoxVisibility.VISIBLE);
            gridSelectionModel.beforeClientResponse(false);
        }
    }

    private void initSelectionMode() {
        if (ForeignSecurityUtils.hasSpecialistPermission()) {
            gridSelectionModel =
                (MultiSelectionModelImpl<AclUsageDto>) aclUsagesGrid.setSelectionMode(SelectionMode.MULTI);
            aclUsagesGrid.addSelectionListener(
                event -> editButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        } else {
            aclUsagesGrid.setSelectionMode(SelectionMode.NONE);
        }
    }
}
