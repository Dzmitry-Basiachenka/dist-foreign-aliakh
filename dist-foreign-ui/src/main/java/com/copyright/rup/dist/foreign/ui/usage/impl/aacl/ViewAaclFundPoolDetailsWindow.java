package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.Objects;

/**
 * Modal window to show AACL fund pool gross amounts.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 2/5/20
 *
 * @author Stanislau Rudak
 */
public class ViewAaclFundPoolDetailsWindow extends Window {

    private Grid<AaclFundPoolDetail> grid;

    /**
     * Constructor.
     *
     * @param fundPool   an {@link AaclFundPool} to view
     * @param controller an {@link IAaclUsageController} instance
     */
    public ViewAaclFundPoolDetailsWindow(AaclFundPool fundPool, IAaclUsageController controller) {
        setWidth(600, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        initGrid(Objects.requireNonNull(fundPool), Objects.requireNonNull(controller));
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(fundPool.getName());
        VaadinUtils.addComponentStyle(this, "view-aacl-fund-pool-details-window");
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        HorizontalLayout layout = new HorizontalLayout(closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-aacl-fund-pool-details-buttons");
        return layout;
    }

    private void initGrid(AaclFundPool fundPool, IAaclUsageController controller) {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(controller.getFundPoolDetails(fundPool.getId()));
        grid.setSizeFull();
        grid.addColumn(detail -> detail.getAggregateLicenseeClass().getId())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_id"))
            .setComparator((detail1, detail2) -> detail1.getAggregateLicenseeClass().getId()
                .compareTo(detail2.getAggregateLicenseeClass().getId()))
            .setWidth(200);
        grid.addColumn(detail -> detail.getAggregateLicenseeClass().getName())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_name"))
            .setComparator((detail1, detail2) -> detail1.getAggregateLicenseeClass().getName()
                .compareTo(detail2.getAggregateLicenseeClass().getName()));
        grid.addColumn(AaclFundPoolDetail::getGrossAmount)
            .setCaption(ForeignUi.getMessage("table.column.gross_amount"))
            .setComparator((detail1, detail2) -> detail1.getGrossAmount().compareTo(detail2.getGrossAmount()))
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(110);
        VaadinUtils.addComponentStyle(grid, "view-aacl-fund-pool-details-grid");
    }
}
