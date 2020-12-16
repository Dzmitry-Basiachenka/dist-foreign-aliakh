package com.copyright.rup.dist.foreign.ui.audit.api.sal;

import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;

import java.util.List;

/**
 * Interface for SAL audit filter controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalAuditFilterController extends ICommonAuditFilterController {

    /**
     * Gets list of usage periods.
     *
     * @return list of usage periods
     */
    List<Integer> getUsagePeriods();
}
