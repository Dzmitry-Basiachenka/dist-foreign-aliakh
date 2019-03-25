package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;
import com.copyright.rup.dist.foreign.service.api.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.Iterables;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IWorkClassificationRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
@Service
public class WorkClassificationService implements IWorkClassificationService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWorkClassificationRepository workClassificationRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    @Qualifier("df.service.ntsNonBelletristicProcessor")
    private IChainProcessor<Usage> nonBelletristicProcessor;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int usagesBatchSize;

    @Override
    @Transactional
    public void insertOrUpdateClassifications(Set<WorkClassification> classifications, String newClassification) {
        LOGGER.info("Update classification. Started. WorksCount={}, Classification={}", LogUtils.size(classifications),
            newClassification);
        classifications.forEach(workClassification -> {
            if (null == workClassification.getId()) {
                workClassification.setId(RupPersistUtils.generateUuid());
            }
            workClassification.setClassification(newClassification);
            workClassificationRepository.insertOrUpdate(workClassification);
        });
        updateUnclassifiedUsages();
        LOGGER.info("Update classification. Finished. WorksCount={}, Classification={}", LogUtils.size(classifications),
            newClassification);
    }

    @Override
    @Transactional
    public void deleteClassifications(Set<WorkClassification> classifications) {
        LOGGER.info("Delete classification. Started. WorksCount={}", LogUtils.size(classifications));
        classifications.forEach(workClassification ->
            workClassificationRepository.deleteByWrWrkInst(workClassification.getWrWrkInst()));
        LOGGER.info("Delete classification. Finished. WorksCount={}", LogUtils.size(classifications));
    }

    @Override
    public String getClassification(Long wrWrkInst) {
        return workClassificationRepository.findClassificationByWrWrkInst(wrWrkInst);
    }

    @Override
    public List<WorkClassification> getClassifications(Set<String> batchesIds, String searchValue, Pageable pageable,
                                                       Sort sort) {
        return CollectionUtils.isNotEmpty(batchesIds)
            ? workClassificationRepository.findByBatchIds(batchesIds, searchValue, pageable, sort)
            : workClassificationRepository.findBySearch(searchValue, pageable, sort);
    }

    @Override
    public int getClassificationCount(Set<String> batchesIds, String searchValue) {
        return CollectionUtils.isNotEmpty(batchesIds)
            ? workClassificationRepository.findCountByBatchIds(batchesIds, searchValue)
            : workClassificationRepository.findCountBySearch(searchValue);
    }

    void setWorkClassificationRepository(IWorkClassificationRepository workClassificationRepository) {
        this.workClassificationRepository = workClassificationRepository;
    }

    void setUsageRepository(IUsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    void setNonBelletristicProcessor(IChainProcessor<Usage> nonBelletristicProcessor) {
        this.nonBelletristicProcessor = nonBelletristicProcessor;
    }

    void setUsagesBatchSize(int usagesBatchSize) {
        this.usagesBatchSize = usagesBatchSize;
    }

    private void updateUnclassifiedUsages() {
        List<String> unclassifiedUsageIds = usageRepository.findUnclassifiedUsageIds();
        LOGGER.debug("Update unclassified usages. Started. UnclassifiedUsagesCount={}",
            LogUtils.size(unclassifiedUsageIds));
        Iterables.partition(unclassifiedUsageIds, usagesBatchSize)
            .forEach(partition -> usageRepository.findByIds(partition).forEach(nonBelletristicProcessor::process));
        LOGGER.debug("Update unclassified usages. Finished. UnclassifiedUsagesCount={}",
            LogUtils.size(unclassifiedUsageIds));
    }
}
