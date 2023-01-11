package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.collect.ImmutableMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Controller for exclude payees filter.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasExcludePayeeFilterController extends CommonController<IFasExcludePayeeFilterWidget>
    implements IFasExcludePayeeFilterController {

    private static final Map<String, Boolean> PARTICIPATING_STATUSES =
        ImmutableMap.of(ForeignUi.getMessage("label.participating"), Boolean.TRUE,
                        ForeignUi.getMessage("label.not_participating"), Boolean.FALSE);

    @Autowired
    private IScenarioService scenarioService;

    @Override
    public Map<String, Boolean> getParticipatingStatuses() {
        return PARTICIPATING_STATUSES;
    }

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenariosByProductFamiliesAndStatuses(FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET,
            EnumSet.of(ScenarioStatusEnum.IN_PROGRESS));
    }

    @Override
    protected IFasExcludePayeeFilterWidget instantiateWidget() {
        return new FasExcludePayeeFilterWidget();
    }
}
