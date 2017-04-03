package com.copyright.rup.dist.foreign.ui.component.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

import com.vaadin.data.validator.AbstractStringValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * Validator for Usage Batch uniqueness. Checks whether usage batch with provided name already exists or not.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/16/17
 *
 * @author Mikalai Bezmen
 */
public class UsageBatchNameUniqueValidator extends AbstractStringValidator {

    private static final String ERROR_MESSAGE = ForeignUi.getMessage("message.error.unique_name", "Usage Batch");
    private IUsagesController usagesController;

    /**
     * Constructs.
     *
     * @param usagesController instance of {@link IUsagesController}
     */
    public UsageBatchNameUniqueValidator(IUsagesController usagesController) {
        super(ERROR_MESSAGE);
        this.usagesController = usagesController;
    }

    @Override
    protected boolean isValidValue(String value) {
        return !usagesController.usageBatchExists(StringUtils.trimToEmpty(value));
    }
}
