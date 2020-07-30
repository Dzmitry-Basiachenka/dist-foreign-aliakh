package com.copyright.rup.dist.foreign.ui.usage.api.sal;

import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

/**
 * Interface for SAL usages controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Ihar Suvorau
 */
public interface ISalUsageController extends ICommonUsageController {

    /**
     * Gets licensee name by provided account number.
     *
     * @param licenseeAccountNumber licensee account number
     * @return licensee name
     */
    String getLicenseeName(Long licenseeAccountNumber);
}
