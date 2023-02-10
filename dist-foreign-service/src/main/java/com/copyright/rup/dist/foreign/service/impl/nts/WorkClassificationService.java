package com.copyright.rup.dist.foreign.service.impl.nts;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.common.util.LogUtils.ILogWrapper;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IWorkClassificationService}.
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
    private static final Set<String> NON_BELLETRISTIC_CLASSIFICATIONS = ImmutableSet.of(FdaConstants.STM_CLASSIFICATION,
        FdaConstants.NON_STM_CLASSIFICATION);

    @Autowired
    private IWorkClassificationRepository workClassificationRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private INtsUsageRepository ntsUsageRepository;
    @Autowired
    @Qualifier("df.service.ntsNonBelletristicProcessor")
    private IChainProcessor<Usage> nonBelletristicProcessor;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int usagesBatchSize;
    @Value("$RUP{dist.foreign.grid.multi.select.record.threshold}")
    private int recordsThreshold;

    @Override
    @Transactional
    public void insertOrUpdateClassifications(Set<WorkClassification> classifications, String newClassification) {
        LOGGER.debug("Update classification. Started. WorksCount={}, Classification={}", LogUtils.size(classifications),
            newClassification);
        classifications.forEach(workClassification -> {
            if (null == workClassification.getId()) {
                workClassification.setId(RupPersistUtils.generateUuid());
            }
            workClassification.setClassification(newClassification);
            workClassification.setUpdateUser(RupContextUtils.getUserName());
            workClassification.setUpdateDate(Date.from(Instant.now().atZone(ZoneId.systemDefault()).toInstant()));
            workClassificationRepository.insertOrUpdate(workClassification);
        });
        updateClassifiedUsages();
        LOGGER.debug("Update classification. Finished. WorksCount={}, Classification={}",
            LogUtils.size(classifications), newClassification);
    }

    @Override
    @Transactional
    public void deleteClassifications(Set<WorkClassification> classifications) {
        String userName = RupContextUtils.getUserName();
        ILogWrapper classificationsCount = LogUtils.size(classifications);
        LOGGER.debug("Delete classification. Started. ClassificationsCount={}, UserName={}", classificationsCount,
            userName);
        List<Long> wrWrkInsts = new ArrayList<>();
        classifications.forEach(workClassification -> {
            Long wrWrkInst = workClassification.getWrWrkInst();
            workClassificationRepository.deleteByWrWrkInst(wrWrkInst);
            if (NON_BELLETRISTIC_CLASSIFICATIONS.contains(workClassification.getClassification())) {
                wrWrkInsts.add(wrWrkInst);
            }
        });
        if (!wrWrkInsts.isEmpty()) {
            ntsUsageRepository.updateUsagesStatusToUnclassified(wrWrkInsts, userName);
        }
        LOGGER.debug("Delete classification. Finished. ClassificationsCount={}, UserName={}", classificationsCount,
            userName);
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

    @Override
    public int getWorkClassificationThreshold() {
        return recordsThreshold;
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

    private void updateClassifiedUsages() {
        List<String> usageIdsToUpdate = ntsUsageRepository.findUsageIdsForClassificationUpdate();
        if (CollectionUtils.isNotEmpty(usageIdsToUpdate)) {
            LOGGER.debug("Update usages based on classification. Started. UsagesCount={}",
                LogUtils.size(usageIdsToUpdate));
            Iterables.partition(usageIdsToUpdate, usagesBatchSize)
                .forEach(partition -> nonBelletristicProcessor.process(usageRepository.findByIds(partition)));
            LOGGER.debug("Update usages based on classification. Finished. UsagesCount={}",
                LogUtils.size(usageIdsToUpdate));
        }
    }
}
