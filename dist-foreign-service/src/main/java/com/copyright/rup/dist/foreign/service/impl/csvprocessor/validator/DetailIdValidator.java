package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The validator to check whether detail id doesn't exist in the database.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/22/17
 *
 * @author Aliaksei Pchelnikau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DetailIdValidator implements IValidator<Usage> {

    private static final String ERROR_MESSAGE = "Detail ID: Detail with such ID already exists";
    @Autowired
    private IUsageService usageService;

    @Override
    public boolean isValid(Usage value) {
        //TODO {apchelnikau} stub for PMD, will be implemented in the TK-80798
        usageService.getClass();
        return true;
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
