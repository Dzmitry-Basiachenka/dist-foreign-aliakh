package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;

import java.util.Collection;
import java.util.Set;

/**
 * Implementation of SAL status filter widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalStatusFilterWidget extends CommonStatusFilterWidget {

    private static final long serialVersionUID = -5754794249784330724L;
    private static final Set<UsageStatusEnum> SAL_STATUSES =
        Set.of(UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.LOCKED, UsageStatusEnum.PAID,
            UsageStatusEnum.RH_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.WORK_FOUND,
            UsageStatusEnum.ARCHIVED, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.NEW, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.WORK_NOT_GRANTED);

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return SAL_STATUSES;
    }
}
