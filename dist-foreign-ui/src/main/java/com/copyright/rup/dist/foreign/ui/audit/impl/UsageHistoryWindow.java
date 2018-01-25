package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.DateColumnGenerator;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
class UsageHistoryWindow extends Window {

    /**
     * Constructor.
     *
     * @param detailId        detail id
     * @param usageAuditItems audit items
     */
    UsageHistoryWindow(String detailId, List<UsageAuditItem> usageAuditItems) {
        setCaption(ForeignUi.getMessage("window.usage_history", detailId));
        setContent(initContent(usageAuditItems));
        setWidth(700, Unit.PIXELS);
        setHeight(300, Unit.PIXELS);
    }

    private Table initTable(List<UsageAuditItem> usageAuditItems) {
        BeanContainer<String, UsageAuditItem> container = new BeanContainer<>(UsageAuditItem.class);
        container.setBeanIdResolver(UsageAuditItem::getId);
        container.addAll(usageAuditItems);
        Table table = new Table();
        table.setContainerDataSource(container);
        table.setSizeFull();
        table.setVisibleColumns("actionType", "createDate", "actionReason", "createUser");
        table.addGeneratedColumn("createDate", new DateColumnGenerator());
        table.setColumnHeaders(
            ForeignUi.getMessage("label.action_type"),
            ForeignUi.getMessage("label.action_date"),
            ForeignUi.getMessage("label.action_reason"),
            ForeignUi.getMessage("label.action_user"));
        return table;
    }

    private VerticalLayout initContent(List<UsageAuditItem> usageAuditItems) {
        Table table = initTable(usageAuditItems);
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout content = new VerticalLayout(table, closeButton);
        content.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(table, 1f);
        return content;
    }
}
