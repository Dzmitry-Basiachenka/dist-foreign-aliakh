package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonDrillDownByRightsholderWidget;

/**
 * Implementation of {@link INtsDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/9/2019
 *
 * @author Stanislau Rudak
 */
public class NtsDrillDownByRightsholderWidget extends CommonDrillDownByRightsholderWidget
    implements INtsDrillDownByRightsholderWidget {

    private static final long serialVersionUID = -6932982725647270658L;

    @Override
    protected void addColumns() {
        //TODO: {dbasiachenka} implement
    }

    @Override
    protected String[] getExcludedColumns() {
        //TODO: {dbasiachenka} implement
        return new String[0];
    }

    @Override
    protected String getSearchPrompt() {
        return ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget");
    }
}
