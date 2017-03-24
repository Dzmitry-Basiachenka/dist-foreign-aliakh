package com.copyright.rup.dist.foreign.ui.component.validator;

import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validates uniqueness of the name of Scenario while creating.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/16/17
 *
 * @author Mikalai Bezmen
 */
public class ScenarioNameUniqueValidator extends AbstractStringValidator {

    private static final String ERROR_MESSAGE = ForeignUi.getMessage("message.validation.unique_name", "Scenario");
    private IScenarioService scenarioService;

    /**
     * Constructor.
     *
     * @param scenarioService {@link IScenarioService}
     */
    public ScenarioNameUniqueValidator(IScenarioService scenarioService) {
        super(ERROR_MESSAGE);
        this.scenarioService = scenarioService;
    }

    @Override
    protected boolean isValidValue(String value) {
        return !scenarioService.scenarioExists(StringUtils.trimToEmpty(value));
    }
}
