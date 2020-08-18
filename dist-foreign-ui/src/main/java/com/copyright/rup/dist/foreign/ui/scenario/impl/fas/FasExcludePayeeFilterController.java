package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
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
public class FasExcludePayeeFilterController extends CommonController<IExcludePayeeFilterWidget>
    implements IExcludePayeeFilterController {

    private static final Map<String, Boolean> PARTICIPATING_STATUSES = new LinkedHashMap<>();

    @Autowired
    private IScenarioService scenarioService;

    static {
        PARTICIPATING_STATUSES.put(ForeignUi.getMessage("label.participating"), Boolean.TRUE);
        PARTICIPATING_STATUSES.put(ForeignUi.getMessage("label.not_participating"), Boolean.FALSE);
    }

    @Override
    public Map<String, Boolean> getParticipatingStatuses() {
        return Collections.unmodifiableMap(PARTICIPATING_STATUSES);
    }

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenariosByProductFamiliesAndStatuses(FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET,
            EnumSet.of(ScenarioStatusEnum.IN_PROGRESS));
    }

    @Override
    protected IExcludePayeeFilterWidget instantiateWidget() {
        return new FasExcludePayeeFilterWidget();
    }
}