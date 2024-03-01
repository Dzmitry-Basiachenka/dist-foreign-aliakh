package com.copyright.rup.dist.foreign.vui.audit.api.aacl;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;

import java.util.List;

/**
 * Interface for AACL audit filter controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public interface IAaclAuditFilterController extends ICommonAuditFilterController {

    /**
     * Gets list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> getUsagePeriods();
}
