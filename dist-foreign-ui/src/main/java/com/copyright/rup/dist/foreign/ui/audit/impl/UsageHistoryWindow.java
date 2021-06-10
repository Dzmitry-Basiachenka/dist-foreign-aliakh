package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Modal window for displaying usage history.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/15/18
 *
 * @author Aliaksandr Radkevich
 */
public class UsageHistoryWindow extends Window {

    /**
     * Constructor.
     *
     * @param detailId        detail id
     * @param usageAuditItems audit items
     */
    public UsageHistoryWindow(String detailId, List<UsageAuditItem> usageAuditItems) {
        setCaption(ForeignUi.getMessage("window.usage_history", detailId));
        setContent(initContent(usageAuditItems));
        setWidth(700, Unit.PIXELS);
        setHeight(300, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-history-window");
    }

    private Grid initGrid(List<UsageAuditItem> usageAuditItems) {
        Grid<UsageAuditItem> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(usageAuditItems);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "usage-history-grid");
        grid.setSizeFull();
        grid.addColumn(item -> item.getActionType().name())
            .setCaption(ForeignUi.getMessage("label.action_type"));
        grid.addColumn(UsageAuditItem::getCreateUser)
            .setCaption(ForeignUi.getMessage("label.action_user"))
            .setComparator((item1, item2) -> item1.getCreateUser().compareToIgnoreCase(item2.getCreateUser()));
        grid.addColumn(item -> getStringFromDate(item.getCreateDate()))
            .setCaption(ForeignUi.getMessage("label.action_date"));
        grid.addColumn(UsageAuditItem::getActionReason)
            .setCaption(ForeignUi.getMessage("label.action_reason"))
            .setComparator((item1, item2) -> item1.getActionReason().compareToIgnoreCase(item2.getActionReason()))
            .setExpandRatio(1);
        return grid;
    }

    private VerticalLayout initContent(List<UsageAuditItem> usageAuditItems) {
        Grid grid = initGrid(usageAuditItems);
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout content = new VerticalLayout(grid, closeButton);
        content.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(grid, 1f);
        return content;
    }

    private String getStringFromDate(Date date) {
        return null != date ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault())
            .format(date) : StringUtils.EMPTY;
    }
}
