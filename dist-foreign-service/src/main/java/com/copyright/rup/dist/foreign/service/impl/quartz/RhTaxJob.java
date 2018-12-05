package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.ImmutableSet;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Quartz job to send NTS usages in RH_FOUND status to RH Tax queue for further processing.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/04/18
 *
 * @author Pavel Liakh
 */
@DisallowConcurrentExecution
@Component
public class RhTaxJob extends QuartzJobBean {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    @Qualifier("df.service.rhTaxProducer")
    private IProducer<Usage> rhTaxProducer;

    /**
     * Finds NTS usages in RH_FOUND status and send to RH Tax queue.
     */
    @Override
    public void executeInternal(JobExecutionContext context) {
        UsageFilter ntsRhFoundUsagesFilter = new UsageFilter();
        ntsRhFoundUsagesFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        ntsRhFoundUsagesFilter.setProductFamilies(ImmutableSet.of(FdaConstants.NTS_PRODUCT_FAMILY));
        List<Usage> ntsRhFoundUsages = usageService.getUsagesByFilter(ntsRhFoundUsagesFilter);
        LOGGER.info("Send RH_FOUND usages to Rh Tax queue. Started. NtsRhFoundUsagesCount={}",
            LogUtils.size(ntsRhFoundUsages));
        ntsRhFoundUsages.forEach(rhTaxProducer::send);
        LOGGER.info("Send RH_FOUND usages to Rh Tax queue. Finished. NtsRhFoundUsagesCount={}",
            LogUtils.size(ntsRhFoundUsages));
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }

    void setRhTaxProducer(
        IProducer<Usage> rhTaxProducer) {
        this.rhTaxProducer = rhTaxProducer;
    }
}
