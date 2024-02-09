package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonStatusFilterWidget;

import java.util.Collection;
import java.util.Set;

/**
 * Implementation of FAS for filter widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class FasStatusFilterWidget extends CommonStatusFilterWidget {

    private static final long serialVersionUID = 4546038763854322885L;
    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES =
        Set.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH,
            UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM,
            UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED, UsageStatusEnum.NTS_WITHDRAWN,
            UsageStatusEnum.TO_BE_DISTRIBUTED);

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return FAS_FAS2_STATUSES;
    }
}
