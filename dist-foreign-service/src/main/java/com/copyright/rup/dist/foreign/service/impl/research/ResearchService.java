package com.copyright.rup.dist.foreign.service.impl.research;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.Set;

/**
 * Implementation of {@link IResearchService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/23/2018
 *
 * @author Nikita Levyankov
 */
@Service
public class ResearchService implements IResearchService {

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    public void sendForResearch(UsageFilter filter, OutputStream outputStream) {
        StopWatch stopWatch = new Slf4JStopWatch();
        Set<String> usageIds = usageRepository.writeUsagesForResearchAndFindIds(filter, outputStream);
        stopWatch.lap("usage.sendForResearch.writeUsagesForResearchAndFindIds");
        if (CollectionUtils.isNotEmpty(usageIds)) {
            usageRepository.updateStatus(usageIds, UsageStatusEnum.WORK_RESEARCH);
        }
        stopWatch.lap("usage.sendForResearch.updateStatus");
        usageIds.forEach(usageId -> usageAuditService.logAction(usageId, UsageActionTypeEnum.WORK_RESEARCH,
            "Usage detail was sent for research"));
        stopWatch.stop("usage.sendForResearch.logAction");
    }
}
