package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.widget.api.IController;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Interface for UDM proxy value controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmProxyValueController extends IController<IUdmProxyValueWidget> {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUdmProxyValueController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Loads proxy values by filter.
     *
     * @return list of proxy values to be displayed on UI
     */
    List<UdmProxyValueDto> getProxyValues();

    /**
     * Initializes {@link IUdmProxyValueFilterWidget}.
     *
     * @return initialized {@link IUdmProxyValueFilterWidget}
     */
    IUdmProxyValueFilterWidget initProxyValueFilterWidget();
}
