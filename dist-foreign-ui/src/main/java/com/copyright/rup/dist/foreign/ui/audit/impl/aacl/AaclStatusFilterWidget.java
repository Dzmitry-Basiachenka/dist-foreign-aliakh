package com.copyright.rup.dist.foreign.ui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

/**
 * Implementation of AACL for filter widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class AaclStatusFilterWidget extends CommonStatusFilterWidget {

    private static final Set<UsageStatusEnum> AACL_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.RH_FOUND, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.LOCKED,
            UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return AACL_STATUSES;
    }
}