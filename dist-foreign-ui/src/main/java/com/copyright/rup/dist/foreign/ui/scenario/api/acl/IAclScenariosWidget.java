package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for scenarios widget for ACL product family.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/20/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenariosWidget extends IWidget<IAclScenariosController>, IRefreshable, IMediatorProvider {

    /**
     * Selects specified ACL scenario in the grid.
     *
     * @param scenario {@link AclScenario} to select
     */
    void selectScenario(AclScenario scenario);

    /**
     * Gets selected ACL scenario in the grid.
     *
     * @return selected {@link AclScenario} or {@code null} if nothing is selected
     */
    AclScenario getSelectedScenario();

    /**
     * Refreshes metadata information for the selected ACL scenario.
     */
    void refreshSelectedScenario();
}
