package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IAclReportController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/03/2022
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclReportController extends CommonController<IAclReportWidget> implements IAclReportController {

    @Autowired
    @Qualifier("df.aclLiabilitiesByAggLicClassReportController")
    private IAclCommonReportController liabilitiesByAggLicClassReportController;
    @Autowired
    @Qualifier("df.aclLiabilityDetailsReportController")
    private IAclCommonReportController liabilityDetailsReportController;

    @Override
    public IAclCommonReportController getAclLiabilitiesByAggLicClassReportController() {
        return liabilitiesByAggLicClassReportController;
    }

    @Override
    public IAclCommonReportController getAclLiabilityDetailsReportController() {
        return liabilityDetailsReportController;
    }

    @Override
    protected IAclReportWidget instantiateWidget() {
        return new AclReportWidget();
    }
}
