package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.service.api.IPaidUsageService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Consumer to handle list of {@link PaidUsage}s from LM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/21/18
 *
 * @author Darya Baraukova
 */
@Component("df.service.paidUsageConsumer")
public class PaidUsageConsumer implements IConsumer<List<PaidUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private ISalUsageService salUsageService;

    @Override
    @Profiled(tag = "PaidUsageConsumer.consume")
    public void consume(List<PaidUsage> usages) {
        LOGGER.info("Consume paid information from LM. Started. UsagesCount={}", LogUtils.size(usages));
        if (CollectionUtils.isNotEmpty(usages)) {
            Map<String, List<PaidUsage>> productFamilyToPaidUsagesMap = usages.stream()
                .collect(Collectors.groupingBy(PaidUsage::getProductFamily));
            productFamilyToPaidUsagesMap.forEach((productFamily, paidUsages) -> {
                IPaidUsageService paidUsageService = getProductFamilyToServiceMap().get(productFamily);
                paidUsageService.updatePaidInfo(paidUsages);
            });
            LOGGER.info("Consume paid information from LM. Finished. UsagesCount={}", LogUtils.size(usages));
        } else {
            LOGGER.warn("Consume paid information from LM. Failed. Reason='Usages list is empty'");
        }
    }

    protected Map<String, IPaidUsageService> getProductFamilyToServiceMap() {
        return ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, usageService,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, usageService,
            FdaConstants.NTS_PRODUCT_FAMILY, usageService,
            FdaConstants.AACL_PRODUCT_FAMILY, aaclUsageService,
            FdaConstants.SAL_PRODUCT_FAMILY, salUsageService
        );
    }
}
