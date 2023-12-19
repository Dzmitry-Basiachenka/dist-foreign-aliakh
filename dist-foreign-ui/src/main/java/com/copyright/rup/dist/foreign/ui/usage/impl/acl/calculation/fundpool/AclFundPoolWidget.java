package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializableComparator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implementation of {@link IAclFundPoolWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolWidget extends HorizontalSplitPanel implements IAclFundPoolWidget, IMediatorProvider {

    private static final long serialVersionUID = -4290600546550074975L;

    private IAclFundPoolController controller;
    private MenuBar aclFundPoolMenuBar;
    private MenuBar.MenuItem createMenuItem;
    private Grid<AclFundPoolDetailDto> aclFundPoolDetailGrid;

    @Override
    @SuppressWarnings("unchecked")
    public IAclFundPoolWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initAclFundPoolFilterWidget());
        setSecondComponent(initFundPoolLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void refresh() {
        aclFundPoolDetailGrid.deselectAll();
        initDataProvider();
    }

    @Override
    public void setController(IAclFundPoolController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AclFundPoolMediator mediator = new AclFundPoolMediator();
        mediator.setCreateMenuItem(createMenuItem);
        return mediator;
    }

    private VerticalLayout initFundPoolLayout() {
        initFundPoolGrid();
        initFundPoolMenuBar();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), aclFundPoolDetailGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(aclFundPoolDetailGrid, 1);
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-layout");
        return layout;
    }

    private void initFundPoolGrid() {
        aclFundPoolDetailGrid = new Grid<>();
        initDataProvider();
        addColumns();
        aclFundPoolDetailGrid.setSelectionMode(Grid.SelectionMode.NONE);
        aclFundPoolDetailGrid.setSizeFull();
        VaadinUtils.addComponentStyle(aclFundPoolDetailGrid, "acl-fund-pool-grid");
    }

    private void initDataProvider() {
        ListDataProvider<AclFundPoolDetailDto> dataProvider = DataProvider.ofCollection(controller.getDtos());
        aclFundPoolDetailGrid.setDataProvider(dataProvider);
    }

    private void addColumns() {
        addColumn(AclFundPoolDetailDto::getFundPoolName, "table.column.fund_pool_name", "fundPoolName", 250)
            .setComparator((SerializableComparator<AclFundPoolDetailDto>) (detail1, detail2) ->
                detail1.getFundPoolName().compareToIgnoreCase(detail2.getFundPoolName()));
        addColumn(AclFundPoolDetailDto::getPeriod, "table.column.period", "period", 100);
        addColumn(AclFundPoolDetailDto::getLicenseType, "table.column.license_type", "licenseType", 100);
        addColumn(detail -> detail.isLdmtFlag() ? "LDMT" : "Manual", "table.column.source", "source", 100);
        addColumn(detail -> detail.getDetailLicenseeClass().getId(), "table.column.det_lc_id", "detailLicenseeClassId",
            150);
        addColumn(detail -> detail.getDetailLicenseeClass().getDescription(), "table.column.det_lc_name",
            "detailLicenseeClassName", 200);
        addColumn(detail -> detail.getAggregateLicenseeClass().getId(), "table.column.aggregate_licensee_class_id",
            "aggregateLicenseeClassId", 150);
        addColumn(detail -> detail.getAggregateLicenseeClass().getDescription(),
            "table.column.aggregate_licensee_class_name", "aggregateLicenseeClassName", 200);
        addColumn(AclFundPoolDetailDto::getTypeOfUse, "table.column.fund_pool_type", "typeOfUse", 150);
        addAmountColumn(AclFundPoolDetailDto::getGrossAmount, "table.column.gross_amount", "grossAmount");
        addAmountColumn(AclFundPoolDetailDto::getNetAmount, "table.column.net_amount", "netAmount");
    }

    private Column<AclFundPoolDetailDto, ?> addColumn(ValueProvider<AclFundPoolDetailDto, ?> valueProvider,
                                                      String captionProperty, String columnId, double width) {
        return aclFundPoolDetailGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private void addAmountColumn(Function<AclFundPoolDetailDto, BigDecimal> function, String captionProperty,
                                 String columnId) {
        aclFundPoolDetailGrid.addColumn(value -> CurrencyUtils.format(function.apply(value), null))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setComparator((SerializableComparator<AclFundPoolDetailDto>) (detail1, detail2) ->
                function.apply(detail1).compareTo(function.apply(detail2)))
            .setHidable(true)
            .setWidth(150);
    }

    private void initFundPoolMenuBar() {
        aclFundPoolMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            aclFundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        createMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            item -> Windows.showModalWindow(new CreateAclFundPoolWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewAclFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(aclFundPoolMenuBar, "acl-fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(aclFundPoolMenuBar, "v-menubar-df");
    }

    private HorizontalLayout initButtonsLayout() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportAclFundPoolDetailsStreamSource().getSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(aclFundPoolMenuBar, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-buttons");
        return layout;
    }
}
