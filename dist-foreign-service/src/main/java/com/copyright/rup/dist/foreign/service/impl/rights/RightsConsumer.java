package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Consumer to handle usages for getting Rights.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 */
@Component("df.service.rightsConsumer")
public class RightsConsumer implements IConsumer<Usage> {

    @Autowired
    private IRightsService rightsService;

    @Override
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            rightsService.updateRight(usage);
        }
    }
}
