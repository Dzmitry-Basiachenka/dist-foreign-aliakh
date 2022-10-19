package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;
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

import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * Implementation of {@link IAclGrantDetailWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailWidget extends HorizontalSplitPanel implements IAclGrantDetailWidget, IDateFormatter {

    private static final String EMPTY_STYLE_NAME = "empty-grants-grid";
    private static final String FOOTER_LABEL = "Grant Details Count: %s";

    private IAclGrantDetailController controller;
    private Grid<AclGrantDetailDto> aclGrantDetailsGrid;
    private DataProvider<AclGrantDetailDto, Void> dataProvider;
    private MenuBar grantSetMenuBar;
    private MenuBar.MenuItem createMenuItem;
    private Button editButton;
    private Button uploadButton;

    @Override
    @SuppressWarnings("unchecked")
    public IAclGrantDetailWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initAclGrantDetailFilterWidget());
        setSecondComponent(initGrantDetailsLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void refresh() {
        aclGrantDetailsGrid.deselectAll();
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IAclGrantDetailController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AclGrantDetailMediator mediator = new AclGrantDetailMediator();
        mediator.setCreateMenuItem(createMenuItem);
        mediator.setEditButton(editButton);
        mediator.setUploadButton(uploadButton);
        return mediator;
    }

    private VerticalLayout initGrantDetailsLayout() {
        initGrantDetailsGrid();
        initGrantSetMenuBar();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), aclGrantDetailsGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(aclGrantDetailsGrid, 1);
        VaadinUtils.addComponentStyle(layout, "acl-grant-details-layout");
        return layout;
    }

    private void initGrantDetailsGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    aclGrantDetailsGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    aclGrantDetailsGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                aclGrantDetailsGrid.getFooterRow(0).getCell("licenseType").setText(String.format(FOOTER_LABEL, size));
                return size;
            }, AclGrantDetailDto::getId);
        aclGrantDetailsGrid = new Grid<>(dataProvider);
        addColumns();
        aclGrantDetailsGrid.setSelectionMode(SelectionMode.MULTI);
        aclGrantDetailsGrid.setSizeFull();
        aclGrantDetailsGrid.addSelectionListener(
            event -> editButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        VaadinUtils.addComponentStyle(aclGrantDetailsGrid, "acl-grant-details-grid");
    }

    private void addColumns() {
        FooterRow footer = aclGrantDetailsGrid.appendFooterRow();
        aclGrantDetailsGrid.setFooterVisible(true);
        footer.getCell(
                addColumn(AclGrantDetailDto::getGrantSetName, "table.column.grant_set_name", "grantSetName", 200))
            .setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(AclGrantDetailDto::getGrantPeriod, "table.column.grant_period", "grantPeriod", 110),
            addColumn(AclGrantDetailDto::getLicenseType, "table.column.license_type", "licenseType", 130),
            addColumn(AclGrantDetailDto::getTypeOfUseStatus, "table.column.tou_status", "typeOfUseStatus", 150),
            addColumn(AclGrantDetailDto::getGrantStatus, "table.column.grant_status", "grantStatus", 120),
            addColumn(value -> BooleanUtils.toYNString(value.getEligible()), "table.column.eligible", "eligible", 100),
            addColumn(AclGrantDetailDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(AclGrantDetailDto::getSystemTitle, "table.column.system_title", "systemTitle", 300),
            addColumn(AclGrantDetailDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 150),
            addColumn(AclGrantDetailDto::getRhName, "table.column.rh_account_name", "rhName", 300),
            addColumn(AclGrantDetailDto::getTypeOfUse, "table.column.tou", "typeOfUse", 120),
            addColumn(value -> toShortFormat(value.getCreateDate()), "table.column.created_date", "createDate", 100),
            addColumn(value -> toShortFormat(value.getUpdateDate()), "table.column.updated_date", "updateDate", 100),
            addBooleanColumn(AclGrantDetailDto::getManualUploadFlag, "table.column.manual_upload_flag",
                "manualUploadFlag", 150));
    }

    private Column<AclGrantDetailDto, ?> addColumn(ValueProvider<AclGrantDetailDto, ?> valueProvider,
                                                   String captionProperty, String columnId, double width) {
        return aclGrantDetailsGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<AclGrantDetailDto, ?> addBooleanColumn(ValueProvider<AclGrantDetailDto, Boolean> valueProvider,
                                                          String captionProperty, String columnId, double width) {
        return aclGrantDetailsGrid.addColumn(value -> BooleanUtils.toYNString(valueProvider.apply(value)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private void initGrantSetMenuBar() {
        grantSetMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem = grantSetMenuBar.addItem(ForeignUi.getMessage("menu.caption.grant_set"), null, null);
        createMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            item -> Windows.showModalWindow(new CreateAclGrantSetWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewAclGrantSetWindow(controller)));
        VaadinUtils.addComponentStyle(grantSetMenuBar, "acl-grant-set-menu-bar");
        VaadinUtils.addComponentStyle(grantSetMenuBar, "v-menubar-df");
    }

    private HorizontalLayout initButtonsLayout() {
        editButton = Buttons.createButton(ForeignUi.getMessage("button.edit"));
        editButton.setEnabled(false);
        editButton.addClickListener(event -> {
            Set<AclGrantDetailDto> selectedGrants = aclGrantDetailsGrid.getSelectedItems();
            if (selectedGrants.stream().allMatch(AclGrantDetailDto::getEditable)) {
                Windows.showModalWindow(
                    new EditAclGrantDetailWindow(selectedGrants, controller, saveEvent -> refresh()));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.grant_not_editable"));
            }
        });
        VaadinUtils.addComponentStyle(editButton, "acl-edit-grant");
        uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> Windows.showModalWindow(new UploadGrantDetailWindow(controller)));
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportAclGrantDetailsStreamSource().getSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(grantSetMenuBar, uploadButton, editButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.setButtonsAutoDisabled(editButton, uploadButton);
        VaadinUtils.addComponentStyle(layout, "acl-grant-detail-buttons");
        return layout;
    }
}
