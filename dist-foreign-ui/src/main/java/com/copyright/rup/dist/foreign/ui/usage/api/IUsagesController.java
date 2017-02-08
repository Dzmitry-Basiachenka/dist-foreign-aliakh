package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/**
 * Interface for controller for {@link IUsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
public interface IUsagesController extends IController<IUsagesWidget>, IBeanLoader<UsageDto>, IStreamSource {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUsagesController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Initializes {@link IUsagesFilterWidget}.
     *
     * @return initialized {@link IUsagesFilterWidget}
     */
    IUsagesFilterWidget initUsagesFilterWidget();

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);
}
