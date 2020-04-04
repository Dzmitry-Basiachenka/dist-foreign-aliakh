package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;
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

    private Grid<FundPoolDetail> grid;

    /**
     * Constructor.
     *
     * @param fundPool   an {@link FundPool} to view
     * @param fundPoolDetails list of {@link FundPoolDetail}s
     */
    public ViewAaclFundPoolDetailsWindow(FundPool fundPool, List<FundPoolDetail> fundPoolDetails) {
        setWidth(600, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        initGrid(Objects.requireNonNull(fundPoolDetails));
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(Objects.requireNonNull(fundPool).getName());
        VaadinUtils.addComponentStyle(this, "view-aacl-fund-pool-details-window");
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        HorizontalLayout layout = new HorizontalLayout(closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-aacl-fund-pool-details-buttons");
        return layout;
    }

    private void initGrid(List<FundPoolDetail> fundPoolDetails) {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(fundPoolDetails);
        grid.setSizeFull();
        grid.addColumn(detail -> detail.getAggregateLicenseeClass().getId())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_id"))
            .setExpandRatio(1);
        grid.addColumn(detail -> detail.getAggregateLicenseeClass().getEnrollmentProfile())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_lc_enrollment"))
            .setExpandRatio(2);
        grid.addColumn(detail -> detail.getAggregateLicenseeClass().getDiscipline())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_lc_discipline"))
            .setExpandRatio(3);
        grid.addColumn(detail -> CurrencyUtils.format(detail.getGrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.gross_amount"))
            .setComparator((detail1, detail2) -> detail1.getGrossAmount().compareTo(detail2.getGrossAmount()))
            .setStyleGenerator(item -> "v-align-right")
            .setExpandRatio(2);
        VaadinUtils.addComponentStyle(grid, "view-aacl-fund-pool-details-grid");
    }
}
