package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for {@link UsageCsvProcessor}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/22/17
 *
 * @author Aliaksei Pchelnikau
 */
@Component
public class UsageCsvProcessorFactory {

    @Autowired
    private ObjectFactory<UsageCsvProcessor> prototypeFactory;

    /**
     * @return instance of {@link UsageCsvProcessor}.
     */
    public UsageCsvProcessor getProcessor() {
        return prototypeFactory.getObject();
    }
}
