package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.foreign.domain.Usage;
import org.springframework.stereotype.Component;

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

    @Override
    public void consume(Usage usage) {
        //TODO {ushalamitski} use service logic here
    }
}
