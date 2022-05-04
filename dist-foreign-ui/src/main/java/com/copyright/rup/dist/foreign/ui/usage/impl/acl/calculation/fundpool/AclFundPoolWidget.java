package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.util.Collections;
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

    private IAclFundPoolController controller;
    private MenuBar aclFundPoolMenuBar;
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
    public void setController(IAclFundPoolController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AclFundPoolMediator mediator = new AclFundPoolMediator();
        mediator.setFundPoolMenuBar(aclFundPoolMenuBar);
        return mediator;
    }

    private VerticalLayout initFundPoolLayout() {
        initUsagesGrid();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), aclFundPoolDetailGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(aclFundPoolDetailGrid, 1);
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-layout");
        return layout;
    }

    private void initUsagesGrid() {
        ListDataProvider<AclFundPoolDetailDto> dataProvider = new ListDataProvider<>(Collections.emptyList());
        aclFundPoolDetailGrid = new Grid<>(dataProvider);
        addColumns();
        aclFundPoolDetailGrid.setSelectionMode(Grid.SelectionMode.NONE);
        aclFundPoolDetailGrid.setSizeFull();
        VaadinUtils.addComponentStyle(aclFundPoolDetailGrid, "acl-usages-grid");
    }

    private void addColumns() {
        addColumn(AclFundPoolDetailDto::getFundPoolName, "table.column.fund_pool_name", "fundPoolName", 250);
        addColumn(AclFundPoolDetailDto::getPeriod, "table.column.period", "period", 100);
        addColumn(AclFundPoolDetailDto::getLicenseType, "table.column.license_type", "licenseType", 100);
        addColumn(AclFundPoolDetailDto::getSource, "table.column.source", "source", 100);
        addColumn(detail -> detail.getDetailLicenseeClass().getId(), "table.column.det_lc_id", "detailLicenseeClassId",
            150);
        addColumn(detail -> detail.getDetailLicenseeClass().getDescription(), "table.column.det_lc_name",
            "detailLicenseeClassName", 200);
        addColumn(detail -> detail.getAggregateLicenseeClass().getId(), "table.column.aggregate_licensee_class_id",
            "aggregateLicenseeClassId", 150);
        addColumn(detail -> detail.getAggregateLicenseeClass().getDescription(),
            "table.column.aggregate_licensee_class_name", "aggregateLicenseeClassName", 200);
        addColumn(AclFundPoolDetailDto::getTypeOfUse, "table.column.tou", "typeOfUse", 100);
        addAmountColumn(AclFundPoolDetailDto::getGrossAmount, "table.column.gross_amount", "grossAmount", 150);
        addAmountColumn(AclFundPoolDetailDto::getNetAmount, "table.column.net_amount", "netAmount", 150);
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

    private Column<AclFundPoolDetailDto, ?> addAmountColumn(Function<AclFundPoolDetailDto, BigDecimal> function,
                                                            String captionProperty, String columnId, double width) {
        return aclFundPoolDetailGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private HorizontalLayout initButtonsLayout() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        aclFundPoolMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            aclFundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        menuItem.addItem(ForeignUi.getMessage("menu.item.create"), null,
            item -> Windows.showModalWindow(new CreateAclFundPoolWindow(controller)));
        HorizontalLayout layout = new HorizontalLayout(aclFundPoolMenuBar, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(aclFundPoolMenuBar, "acl-fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(aclFundPoolMenuBar, "v-menubar-df");
        VaadinUtils.addComponentStyle(layout, "acl-fund-pool-buttons");
        return layout;
    }
}
