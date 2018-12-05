package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Consumer to handle usages to check RH tax country.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.rhTaxConsumer")
public class RhTaxConsumer implements IConsumer<Usage> {

    @Autowired
    private IRhTaxService rhTaxService;

    @Override
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            rhTaxService.applyRhTaxCountry(usage);
        }
    }
}
