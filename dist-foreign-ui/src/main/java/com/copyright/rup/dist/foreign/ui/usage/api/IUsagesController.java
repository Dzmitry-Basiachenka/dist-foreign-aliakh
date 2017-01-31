package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.ui.common.domain.UsageDto;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Interface for controller for {@link IUsagesWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
public interface IUsagesController extends IController<IUsagesWidget>, IBeanLoader<UsageDto> {

    /**
     * Initializes {@link IUsagesFilterWidget}.
     *
     * @return initialized {@link IUsagesFilterWidget}
     */
    IUsagesFilterWidget initUsagesFilterWidget();
}
