package com.copyright.rup.dist.foreign.service.impl.fas;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IFasUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class FasUsageService implements IFasUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IFasUsageRepository fasUsageRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? usageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? usageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    @Transactional
    public void deleteFromScenarioByPayees(String scenarioId, Set<Long> accountNumbers, String reason) {
        Set<String> usageIds = fasUsageRepository
            .deleteFromScenarioByPayees(scenarioId, accountNumbers, RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }

    @Override
    @Transactional
    public void redesignateToNtsWithdrawnByPayees(String scenarioId, Set<Long> accountNumbers, String reason) {
        Set<String> usageIds = fasUsageRepository
            .redesignateToNtsWithdrawnByPayees(scenarioId, accountNumbers, RupContextUtils.getUserName());
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, reason);
    }

    @Override
    public void loadResearchedUsages(List<ResearchedUsage> researchedUsages) {
        LogUtils.ILogWrapper researchedUsagesCount = LogUtils.size(researchedUsages);
        LOGGER.info("Load researched usages. Started. ResearchedUsagesCount={}", researchedUsagesCount);
        populateTitlesStandardNumberAndType(researchedUsages);
        markAsWorkFound(researchedUsages);
        List<String> usageIds = researchedUsages.stream()
            .map(ResearchedUsage::getUsageId)
            .collect(Collectors.toList());
        chainExecutor.execute(usageRepository.findByIds(usageIds), ChainProcessorTypeEnum.RIGHTS);
        LOGGER.info("Load researched usages. Finished. ResearchedUsagesCount={}", researchedUsagesCount);
    }

    @Override
    @Transactional
    public void markAsWorkFound(List<ResearchedUsage> researchedUsages) {
        fasUsageRepository.updateResearchedUsages(researchedUsages);
        researchedUsages.forEach(
            researchedUsage -> usageAuditService.logAction(researchedUsage.getUsageId(),
                UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was added based on research", researchedUsage.getWrWrkInst()))
        );
    }

    @Override
    @Transactional
    public List<String> moveToArchive(Scenario scenario) {
        LOGGER.info("Move details to archive. Started. {}", ForeignLogUtils.scenario(scenario));
        List<String> usageIds =
            usageArchiveRepository.copyToArchiveByScenarioId(scenario.getId(), RupContextUtils.getUserName());
        usageRepository.deleteByScenarioId(scenario.getId());
        LOGGER.info("Move details to archive. Finished. {}, UsagesCount={}", ForeignLogUtils.scenario(scenario),
            LogUtils.size(usageIds));
        return usageIds;
    }

    private void populateTitlesStandardNumberAndType(Collection<ResearchedUsage> researchedUsages) {
        researchedUsages.forEach(researchedUsage -> {
            Work work = piIntegrationService.findWorkByWrWrkInst(researchedUsage.getWrWrkInst());
            researchedUsage.setStandardNumberType(work.getMainIdnoType());
            researchedUsage.setStandardNumber(work.getMainIdno());
            researchedUsage.setSystemTitle(work.getMainTitle());
        });
    }
}
