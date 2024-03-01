package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonStatusFilterWidget;

import java.util.Collection;
import java.util.List;

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

    private static final long serialVersionUID = -7463534033650499304L;
    private static final List<UsageStatusEnum> AACL_STATUSES =
        List.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.RH_FOUND, UsageStatusEnum.ELIGIBLE,
            UsageStatusEnum.SCENARIO_EXCLUDED, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID,
            UsageStatusEnum.ARCHIVED);

    @Override
    public Collection<UsageStatusEnum> loadBeans() {
        return AACL_STATUSES;
    }
}
