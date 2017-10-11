package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Controller interface for {@link IDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public interface IDrillDownByRightsholderController
    extends IController<IDrillDownByRightsholderWidget>, IBeanLoader<UsageDto> {

    /**
     * Initializes and shows {@link IDrillDownByRightsholderWidget}.
     * Sets selected {@link Scenario} to the widget.
     *
     * @param accountNumber selected account number
     * @param rhName        rightsholder name
     * @param scenario      selected {@link Scenario}
     */
    void showWidget(Long accountNumber, String rhName, Scenario scenario);
}
