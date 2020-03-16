package com.copyright.rup.dist.foreign.ui.audit.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

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

    private static final Set<UsageStatusEnum> NTS_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.TO_BE_DISTRIBUTED,
            UsageStatusEnum.NTS_EXCLUDED, UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY,
            UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return NTS_STATUSES;
    }
}
