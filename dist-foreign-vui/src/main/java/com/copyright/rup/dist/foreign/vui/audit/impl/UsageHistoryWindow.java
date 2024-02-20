package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.vui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;

import java.util.List;

/**
 * Modal window for displaying usage history.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/15/18
 *
 * @author Aliaksandr Radkevich
 */
public class UsageHistoryWindow extends CommonDialog implements IDateFormatter {

    private static final long serialVersionUID = 8394123336511924026L;

    private Grid<UsageAuditItem> grid;

    /**
     * Constructor.
     *
     * @param detailId        detail id
     * @param usageAuditItems audit items
     */
    public UsageHistoryWindow(String detailId, List<UsageAuditItem> usageAuditItems) {
        super.setHeaderTitle(ForeignUi.getMessage("window.usage_history", detailId));
        super.setWidth("1000px");
        super.setHeight("400px");
        super.add(initContent(usageAuditItems));
        super.setModalWindowProperties("usage-history-window", true);
    }

    private VerticalLayout initContent(List<UsageAuditItem> usageAuditItems) {
        var content = VaadinUtils.initSizeFullVerticalLayout(initGrid(usageAuditItems));
        content.setPadding(true);
        content.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        getFooter().add(Buttons.createCloseButton(this));
        return content;
    }

    private Grid<UsageAuditItem> initGrid(List<UsageAuditItem> usageAuditItems) {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(usageAuditItems);
        addColumn(item -> item.getActionType().name(), "label.action_type");
        addColumn(UsageAuditItem::getCreateUser, "label.action_user")
            .setComparator((item1, item2) -> item1.getCreateUser().compareToIgnoreCase(item2.getCreateUser()));
        addColumn(item -> toLongFormat(item.getCreateDate()), "label.action_date");
        addColumn(UsageAuditItem::getActionReason, "label.action_reason")
            .setComparator((item1, item2) -> item1.getActionReason().compareToIgnoreCase(item2.getActionReason()));
        VaadinUtils.setGridProperties(grid, "usage-history-grid");
        return grid;
    }

    private Column<UsageAuditItem> addColumn(ValueProvider<UsageAuditItem, ?> provider, String captionProperty) {
        return grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setAutoWidth(true)
            .setResizable(true);
    }
}
