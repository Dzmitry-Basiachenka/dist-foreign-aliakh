package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for filtering FAS, FAS2 and NTS usages.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class FasNtsUsageFilterController extends CommonUsageFilterController implements IFasNtsUsageFilterController {

    private static final long serialVersionUID = 4364431450109560234L;

    @Override
    public List<Rightsholder> getRros() {
        return getRightsholderService().getRros(getSelectedProductFamily());
    }

    @Override
    public List<Integer> getFiscalYears() {
        return getUsageBatchService().getFiscalYears(getSelectedProductFamily());
    }

    @Override
    protected FasNtsUsageFilterWidget instantiateWidget() {
        return new FasNtsUsageFilterWidget(this);
    }
}
