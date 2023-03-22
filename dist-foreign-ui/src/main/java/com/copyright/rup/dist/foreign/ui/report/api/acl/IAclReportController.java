package com.copyright.rup.dist.foreign.ui.report.api.acl;

import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Controller for ACL report menu.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/03/2022
 *
 * @author Ihar Suvorau
 */
public interface IAclReportController extends IController<IAclReportWidget> {

    /**
     * @return ACL Liabilities by Agg Lic Class report controller.
     */
    IAclCommonReportController getAclLiabilitiesByAggLicClassReportController();

    /**
     * @return ACL Liability Details report controller.
     */
    IAclCommonReportController getAclLiabilityDetailsReportController();

    /**
     * @return ACL Liabilities by Rightsholder report controller.
     */
    IAclCommonReportController getAclLiabilitiesByRhReportController();

    /**
     * @return tax notification report controller.
     */
    ITaxNotificationReportController getTaxNotificationReportController();

    /**
     * @return fund pool by Aggregate Licensee Class report controller.
     */
    IAclFundPoolByAggLcReportController getFundPoolByAggLcReportController();
}
