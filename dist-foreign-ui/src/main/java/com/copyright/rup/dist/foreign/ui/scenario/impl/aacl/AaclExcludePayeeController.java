package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IAaclExcludePayeeController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclExcludePayeeController extends CommonController<IAaclExcludePayeeWidget>
    implements IAaclExcludePayeeController {

    private Scenario selectedScenario;

    @Autowired
    private IAaclExcludePayeeFilterController payeesFilterController;
    @Autowired
    private IAaclUsageService usageService;

    @Override
    public IAaclExcludePayeeFilterController getExcludePayeesFilterController() {
        return payeesFilterController;
    }

    @Override
    public void onFilterChanged() {
        getWidget().refresh();
    }

    @Override
    public List<PayeeTotalHolder> getPayeeTotalHolders() {
        return usageService.getPayeeTotalHoldersByFilter(selectedScenario,
            payeesFilterController.getWidget().getAppliedFilter());
    }

    @Override
    public void excludeDetails(Set<Long> payeeAccountNumbers, String reason) {
        //TODO {isuvorau} call service method
    }

    @Override
    public void setSelectedScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }

    @Override
    protected IAaclExcludePayeeWidget instantiateWidget() {
        return new AaclExcludePayeeWidget();
    }
}
