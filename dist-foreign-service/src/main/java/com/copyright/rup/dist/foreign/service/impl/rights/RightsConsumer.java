package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.dist.common.domain.DistributionConstant;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;
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
    private IChainProcessor<Usage> fasEligibilityProcessor;
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
        //TODO: remove product family specific logic once NTS chain will be implemented
        if (FdaConstants.NTS_PRODUCT_FAMILY.equals(usage.getProductFamily()) &&
            UsageStatusEnum.RH_FOUND == usage.getStatus()) {
            rhTaxproducer.send(usage);
        } else if (DistributionConstant.FOREIGN_PRODUCT_FAMILIES.contains(usage.getProductFamily()) &&
            UsageStatusEnum.RH_FOUND == usage.getStatus()) {
            //TODO: replace direct call of eligibility processor once rights processor will be implemented
            fasEligibilityProcessor.process(usage);
        }
    }
}
