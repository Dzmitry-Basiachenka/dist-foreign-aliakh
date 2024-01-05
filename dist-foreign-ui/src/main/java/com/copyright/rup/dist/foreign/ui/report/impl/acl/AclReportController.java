package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportController;
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

    private static final long serialVersionUID = 6491233177330520783L;

    @Autowired
    @Qualifier("df.aclLiabilitiesByAggLicClassReportController")
    private IAclCommonReportController liabilitiesByAggLicClassReportController;
    @Autowired
    @Qualifier("df.aclLiabilityDetailsReportController")
    private IAclCommonReportController liabilityDetailsReportController;
    @Autowired
    @Qualifier("df.aclLiabilitiesByRhReportController")
    private IAclCommonReportController liabilitiesByRhReportController;
    @Autowired
    private ITaxNotificationReportController taxNotificationReportController;
    @Autowired
    private IAclFundPoolByAggLcReportController fundPoolByAggLcReportController;
    @Autowired
    @Qualifier("df.comparisonByAggLcClassAndTitleReportController")
    private IAclCommonReportController comparisonByAggLcClassAndTitleReportController;

    @Override
    public IAclCommonReportController getAclLiabilitiesByAggLicClassReportController() {
        return liabilitiesByAggLicClassReportController;
    }

    @Override
    public IAclCommonReportController getAclLiabilityDetailsReportController() {
        return liabilityDetailsReportController;
    }

    @Override
    public IAclCommonReportController getAclLiabilitiesByRhReportController() {
        return liabilitiesByRhReportController;
    }

    @Override
    public ITaxNotificationReportController getTaxNotificationReportController() {
        return taxNotificationReportController;
    }

    @Override
    public IAclFundPoolByAggLcReportController getFundPoolByAggLcReportController() {
        return fundPoolByAggLcReportController;
    }

    @Override
    public IAclCommonReportController getComparisonByAggLcClassAndTitleReportController() {
        return comparisonByAggLcClassAndTitleReportController;
    }

    @Override
    protected IAclReportWidget instantiateWidget() {
        return new AclReportWidget();
    }
}
