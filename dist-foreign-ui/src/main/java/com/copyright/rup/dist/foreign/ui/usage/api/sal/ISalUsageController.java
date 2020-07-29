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
public interface ISalUsageController  extends ICommonUsageController {

    /**
     * Checks whether Item Bank with provided name already exists or not.
     *
     * @param name item bank name
     * @return {@code true} - if item bank exists, {@code false} - otherwise
     */
    boolean itemBankExists(String name);
}
