package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.IJobProcessor;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

/**
 * Abstract implementation of {@link IJobProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/16/2019
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
abstract class AbstractUsageJobProcessor extends AbstractChainProcessor<Usage> implements IJobProcessor {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private ISalUsageService salUsageService;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private Integer usagesBatchSize;

    private UsageStatusEnum usageStatus;
    private Map<String, Function<List<String>, List<Usage>>> productFamilyToUsageLoaderMap;

    @Override
    public JobInfo jobProcess(String productFamily) {
        checkProductFamilyIsSupported(productFamily);
        JobInfo jobInfo;
        List<String> usageIds = usageService.getUsageIdsByStatusAndProductFamily(usageStatus, productFamily);
        if (CollectionUtils.isNotEmpty(usageIds)) {
            LogUtils.ILogWrapper usagesCount = LogUtils.size(usageIds);
            LOGGER.info("Send {} usages for processing. Started. ProductFamily={}, UsagesCount={}", usageStatus,
                productFamily, usagesCount);
            Function<List<String>, List<Usage>> usageLoader = productFamilyToUsageLoaderMap.get(productFamily);
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> usageLoader.apply(partition)
                    .forEach(usage -> process(Collections.singletonList(usage))));
            String message = "ProductFamily=" + productFamily + ", UsagesCount=" + usagesCount;
            LOGGER.info("Send {} usages for processing. Finished. {}", usageStatus, message);
            jobInfo = new JobInfo(JobStatusEnum.FINISHED, message);
        } else {
            String message = "ProductFamily=" + productFamily + ", Reason=There are no usages";
            LOGGER.info("Send {} usages for processing. Skipped. {}", usageStatus, message);
            jobInfo = new JobInfo(JobStatusEnum.SKIPPED, message);
        }
        return jobInfo;
    }

    public void setUsageStatus(UsageStatusEnum usageStatus) {
        this.usageStatus = usageStatus;
    }

    void setUsagesBatchSize(Integer usagesBatchSize) {
        this.usagesBatchSize = usagesBatchSize;
    }

    /**
     * Post construct method.
     */
    @PostConstruct
    void init() {
        Function<List<String>, List<Usage>> fasNtsUsageLoader = ids -> usageService.getUsagesByIds(ids);
        productFamilyToUsageLoaderMap = ImmutableMap.of(
            FdaConstants.FAS_PRODUCT_FAMILY, fasNtsUsageLoader,
            FdaConstants.CLA_FAS_PRODUCT_FAMILY, fasNtsUsageLoader,
            FdaConstants.NTS_PRODUCT_FAMILY, fasNtsUsageLoader,
            FdaConstants.AACL_PRODUCT_FAMILY, ids -> aaclUsageService.getUsagesByIds(ids),
            FdaConstants.SAL_PRODUCT_FAMILY, ids -> salUsageService.getUsagesByIds(ids)
        );
    }

    private void checkProductFamilyIsSupported(String productFamily) {
        if (!productFamilyToUsageLoaderMap.containsKey(productFamily)) {
            throw new IllegalArgumentException(
                String.format("Product family is not registered. ProductFamily=%s, RegisteredProductFamilies=%s",
                    productFamily, productFamilyToUsageLoaderMap.keySet()));
        }
    }
}
