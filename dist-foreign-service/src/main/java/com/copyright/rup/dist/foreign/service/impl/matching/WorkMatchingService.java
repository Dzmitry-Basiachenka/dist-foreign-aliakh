package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.impl.matching.WorksMatchingJob.UsageGroupEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service encapsulates logic for matching {@link Usage}s to works against PI (Publi).
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/21/18
 *
 * @author Aliaksandr Radkevich
 */
@Service
public class WorkMatchingService implements IWorkMatchingService {

    private static final BigDecimal GROSS_AMOUNT_LIMIT = BigDecimal.valueOf(100L);
    private static final long UNIDENTIFIED_WR_WRK_INST = 123050824L;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IPiIntegrationService piIntegrationService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService auditService;

    @Override
    @Transactional
    public List<Usage> matchByIdno(List<Usage> usages) {
        List<Usage> result = new ArrayList<>();
        LOGGER.info("Search works by IDNOs. Started. IDNOsCount={}", usages.size());
        try {
            result = doMatchByIdno(usages);
            if (CollectionUtils.isNotEmpty(result)) {
                usageRepository.update(result);
                result.forEach(
                    usage -> auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                        String.format("Wr Wrk Inst %s was found by standard number %s", usage.getWrWrkInst(),
                            usage.getStandardNumber())));
            }
            updateUsagesStatusAndWriteAudit(usages, UsageGroupEnum.STANDARD_NUMBER);
        } catch (RupRuntimeException e) {
            LOGGER.warn("Search works by IDNOs. Failed. Reason=Unable to connect to RupEsApi.", e);
        }
        LOGGER.info("Search works by IDNOs. Finished. IDNOsCount={}, WorksFound={}", usages.size(), result.size());
        return result;
    }

    @Override
    @Transactional
    public List<Usage> matchByTitle(List<Usage> usages) {
        List<Usage> result = new ArrayList<>();
        LOGGER.info("Search works by Title. Started. TitlesCount={}", usages.size());
        try {
            result = doMatchByTitle(usages);
            if (CollectionUtils.isNotEmpty(result)) {
                usageRepository.update(result);
                result.forEach(
                    usage -> auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                        String.format("Wr Wrk Inst %s was found by title \"%s\"", usage.getWrWrkInst(),
                            usage.getWorkTitle())));
            }
            updateUsagesStatusAndWriteAudit(usages, UsageGroupEnum.TITLE);
        } catch (RupRuntimeException e) {
            LOGGER.warn("Search works by Title. Failed. Reason=Unable to connect to RupEsApi.", e);
        }
        LOGGER.info("Search works by Title. Finished. TitlesCount={}, WorksFound={}", usages.size(), result.size());
        return result;
    }

    @Override
    @Transactional
    public void updateStatusForUsagesWithNoStandardNumberAndTitle(List<Usage> usages) {
        updateUsagesStatusAndWriteAudit(usages, UsageGroupEnum.SINGLE_USAGE);
    }

    private List<Usage> doMatchByTitle(List<Usage> usages) {
        Set<String> titles = usages.stream()
            .map(Usage::getWorkTitle)
            .collect(Collectors.toSet());
        return CollectionUtils.isNotEmpty(titles)
            ? computeResult(usages, piIntegrationService.findWrWrkInstsByTitles(titles), Usage::getWorkTitle)
            : Collections.emptyList();
    }

    private List<Usage> doMatchByIdno(List<Usage> usages) {
        Map<String, String> idnoToTitleMap = new HashMap<>();
        usages.forEach(usage -> idnoToTitleMap.put(usage.getStandardNumber(), usage.getWorkTitle()));
        return MapUtils.isNotEmpty(idnoToTitleMap)
            ? computeResult(usages, piIntegrationService.findWrWrkInstsByIdnos(idnoToTitleMap),
            Usage::getStandardNumber)
            : Collections.emptyList();
    }

    private List<Usage> computeResult(List<Usage> usages, Map<String, Long> wrWrkInstsMap,
                                      Function<Usage, String> function) {
        List<Usage> result = new ArrayList<>(wrWrkInstsMap.size());
        if (MapUtils.isNotEmpty(wrWrkInstsMap)) {
            usages.forEach(usage -> {
                usage.setWrWrkInst(wrWrkInstsMap.get(function.apply(usage)));
                if (Objects.nonNull(usage.getWrWrkInst())) {
                    usage.setStatus(UsageStatusEnum.WORK_FOUND);
                    result.add(usage);
                }
            });
        }
        return result;
    }

    private void updateUsagesStatusAndWriteAudit(List<Usage> usages, UsageGroupEnum usageGroup) {
        usages.stream()
            .filter(usage -> UsageStatusEnum.NEW == usage.getStatus())
            .collect(Collectors.groupingBy(usageGroup.getMappingFunction()))
            .forEach((key, usagesGroup) -> {
                if (GROSS_AMOUNT_LIMIT.compareTo(sumUsagesGrossAmount(usagesGroup)) > 0) {
                    usagesGroup.forEach(usage -> {
                        usage.setStatus(UsageStatusEnum.ELIGIBLE);
                        usage.setProductFamily("NTS");
                        auditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
                            usageGroup.getNtsEligibleReason());
                    });
                } else {
                    usagesGroup.forEach(usage -> {
                        if (Objects.isNull(usage.getStandardNumber()) && Objects.isNull(usage.getWorkTitle())) {
                            usage.setWrWrkInst(UNIDENTIFIED_WR_WRK_INST);
                            usage.setStatus(UsageStatusEnum.WORK_FOUND);
                            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                                "Usage assigned unidentified work due to empty standard number and title");
                        } else {
                            usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
                            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
                                usageGroup.getWorkNotFoundReasonFunction().apply(usage));
                        }
                    });
                }
            });
        usageRepository.update(usages);
    }

    private BigDecimal sumUsagesGrossAmount(List<Usage> usages) {
        return usages.stream()
            .map(Usage::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
