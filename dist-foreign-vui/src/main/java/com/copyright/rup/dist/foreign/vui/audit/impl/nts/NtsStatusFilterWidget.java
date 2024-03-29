package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonStatusFilterWidget;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of NTS for filter widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class NtsStatusFilterWidget extends CommonStatusFilterWidget {

    private static final long serialVersionUID = 5405397414330951434L;

    private static final List<UsageStatusEnum> NTS_STATUSES =
        List.of(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.US_TAX_COUNTRY,
            UsageStatusEnum.NON_STM_RH, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE,
            UsageStatusEnum.SCENARIO_EXCLUDED, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID,
            UsageStatusEnum.ARCHIVED);

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return NTS_STATUSES;
    }
}
