package com.copyright.rup.dist.foreign.ui.usage.api.sal;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;

import java.util.List;

/**
 * Interface for controller for SAL usages filtering.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public interface ISalUsageFilterController extends ICommonUsageFilterController {

    /**
     * @return list of Rightsholders to display on filter window.
     */
    List<Rightsholder> getRightsholders();
}
