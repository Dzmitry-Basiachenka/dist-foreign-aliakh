package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link AclCommonReportController} to generate ACL Liability Details report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/06/2022
 *
 * @author Anton Azarenka
 */
@Component("df.aclLiabilityDetailsReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclLiabilityDetailsReportController extends AclCommonReportController {

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("liability_details_report_", os -> {
        });
    }
}
