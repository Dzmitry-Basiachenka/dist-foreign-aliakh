package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Autowired
    @Qualifier("df.service.rhTaxProducer")
    private IProducer<Usage> rhTaxproducer;

    @Override
    public void consume(Usage usage) {
        if (Objects.nonNull(usage)) {
            rightsService.updateRight(usage);
        }
        if (FdaConstants.NTS_PRODUCT_FAMILY.equals(usage.getProductFamily()) &&
            Objects.nonNull(usage.getRightsholder().getAccountNumber())) {
            rhTaxproducer.send(usage);
        }
    }
}
