package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;

/**
 * Modal window for displaying UDM value history.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueHistoryWindow extends Window implements IDateFormatter {

    private static final long serialVersionUID = 1187827921121009110L;

    /**
     * Constructor.
     *
     * @param udmValueId {@link com.copyright.rup.dist.foreign.domain.UdmValue} id
     * @param auditItems audit items
     */
    public UdmValueHistoryWindow(String udmValueId, List<UdmValueAuditItem> auditItems) {
        super.setCaption(ForeignUi.getMessage("window.udm_value_history", udmValueId));
        super.setContent(initContent(auditItems));
        super.setWidth(700, Unit.PIXELS);
        super.setHeight(300, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-value-history-window");
    }

    private Grid<UdmValueAuditItem> initGrid(List<UdmValueAuditItem> auditItems) {
        Grid<UdmValueAuditItem> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(auditItems);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "udm-value-history-grid");
        grid.setSizeFull();
        grid.addColumn(item -> item.getActionType().name())
            .setCaption(ForeignUi.getMessage("label.action_type"));
        grid.addColumn(UdmValueAuditItem::getCreateUser)
            .setCaption(ForeignUi.getMessage("label.action_user"))
            .setComparator((item1, item2) -> item1.getCreateUser().compareToIgnoreCase(item2.getCreateUser()));
        grid.addColumn(item -> toLongFormat(item.getCreateDate()))
            .setCaption(ForeignUi.getMessage("label.action_date"));
        grid.addColumn(UdmValueAuditItem::getActionReason)
            .setCaption(ForeignUi.getMessage("label.action_reason"))
            .setComparator((item1, item2) -> item1.getActionReason().compareToIgnoreCase(item2.getActionReason()))
            .setExpandRatio(1);
        return grid;
    }

    private VerticalLayout initContent(List<UdmValueAuditItem> auditItems) {
        Grid<UdmValueAuditItem> grid = initGrid(auditItems);
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout content = new VerticalLayout(grid, closeButton);
        content.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(grid, 1f);
        return content;
    }
}
