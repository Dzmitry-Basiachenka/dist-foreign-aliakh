package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.vui.common.IGridColumnAdder;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.Objects;

/**
 * Modal window to show AACL fund pool gross amounts.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/05/2020
 *
 * @author Stanislau Rudak
 */
public class ViewAaclFundPoolDetailsWindow extends CommonDialog implements IGridColumnAdder<FundPoolDetail> {

    private static final long serialVersionUID = -8710043628252847377L;

    /**
     * Constructor.
     *
     * @param fundPool        instance of {@link FundPool}
     * @param fundPoolDetails list of {@link FundPoolDetail}
     */
    public ViewAaclFundPoolDetailsWindow(FundPool fundPool, List<FundPoolDetail> fundPoolDetails) {
        super.setWidth("700px");
        super.setHeight("600px");
        super.setHeaderTitle(Objects.requireNonNull(fundPool).getName());
        super.add(initContent(fundPoolDetails));
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("view-aacl-fund-pool-details-window", true);
    }

    private VerticalLayout initContent(List<FundPoolDetail> fundPoolDetails) {
        return VaadinUtils.initSizeFullVerticalLayout(initGrid(Objects.requireNonNull(fundPoolDetails)));
    }

    private Grid<FundPoolDetail> initGrid(List<FundPoolDetail> fundPoolDetails) {
        var grid = new Grid<FundPoolDetail>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(fundPoolDetails);
        addColumn(grid, detail -> detail.getAggregateLicenseeClass().getId(),
            GridColumnEnum.AGGREGATE_LICENSEE_CLASS_ID);
        addColumn(grid, detail -> detail.getAggregateLicenseeClass().getEnrollmentProfile(),
            GridColumnEnum.AGGREGATE_LC_ENROLLMENT);
        addColumn(grid, detail -> detail.getAggregateLicenseeClass().getDiscipline(),
            GridColumnEnum.AGGREGATE_LC_DISCIPLINE);
        addAmountColumn(grid, detail -> CurrencyUtils.format(detail.getGrossAmount(), null),
            GridColumnEnum.GROSS_AMOUNT,
            (detail1, detail2) -> detail1.getGrossAmount().compareTo(detail2.getGrossAmount()));
        VaadinUtils.setGridProperties(grid, "view-aacl-fund-pool-details-grid");
        return grid;
    }

    private HorizontalLayout initButtonsLayout() {
        return new HorizontalLayout(Buttons.createCloseButton(this));
    }
}
