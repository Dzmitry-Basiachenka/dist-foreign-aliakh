package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAclCommonReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
public abstract class AclCommonReportController extends CommonController<IAclCommonReportWidget>
    implements IAclCommonReportController {

    @Override
    public List<Integer> getPeriods() {
        //TODO implement service logic
        return Collections.emptyList();
    }

    @Override
    public List<AclScenario> getScenarios() {
        //TODO implement service logic
        return Collections.emptyList();
    }

    @Override
    public IAclCommonReportWidget instantiateWidget() {
        return new AclCommonReportWidget();
    }
}
