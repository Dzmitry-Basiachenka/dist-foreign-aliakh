package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/**
 * Interface for audit widget controller.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
public interface IAuditController extends IController<IAuditWidget>, IBeanLoader<UsageDto> {

    /**
     * {@link #onFilterChanged()}.
     */
    Method ON_FILTER_CHANGED = ReflectTools.findMethod(IAuditController.class, "onFilterChanged");

    /**
     * @return filter controller.
     */
    IAuditFilterController getAuditFilterController();

    /**
     * Handles filter change.
     */
    void onFilterChanged();

    /**
     * Shows modal window with usage history.
     *
     * @param usageId  usage id
     * @param detailId detail id
     */
    void showUsageHistory(String usageId, String detailId);

    /**
     * @return instance of {@link IStreamSource} for export.
     */
    IStreamSource getExportUsagesStreamSource();
}
