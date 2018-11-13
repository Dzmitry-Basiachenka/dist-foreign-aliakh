package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final String UNIDENTIFIED_TITLE = "Unidentified";
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
        LOGGER.info("Search works by IDNOs. Started. IDNOsCount={}", usages.size());
        List<Usage> result = doMatchByIdno(usages);
        if (CollectionUtils.isNotEmpty(result)) {
            usageRepository.updateStatusAndWrWrkInstByStandardNumberAndTitle(result);
            result.forEach(usage -> usageRepository.findByStandardNumberTitleAndStatus(usage.getStandardNumber(),
                usage.getWorkTitle(), UsageStatusEnum.WORK_FOUND)
                .forEach(updatedUsage -> auditService.logAction(updatedUsage.getId(), UsageActionTypeEnum.WORK_FOUND,
                    String.format("Wr Wrk Inst %s was found by standard number %s", updatedUsage.getWrWrkInst(),
                        updatedUsage.getStandardNumber()))));
        }
        usages.stream()
            .filter(usage -> Objects.equals(UsageStatusEnum.NEW, usage.getStatus()))
            .forEach(usage -> {
                List<Usage> usagesByStandardNumber =
                    usageRepository.findByStandardNumberAndStatus(usage.getStandardNumber(), UsageStatusEnum.NEW);
                updateUsagesStatusAndWriteAudit(usagesByStandardNumber, UsageGroupEnum.STANDARD_NUMBER,
                    (usagesToUpdate) -> usageRepository.updateStatusAndWrWrkInstByStandardNumberAndTitle(
                        usagesToUpdate));
            });
        LOGGER.info("Search works by IDNOs. Finished. IDNOsCount={}, WorksFound={}", usages.size(), result.size());
        return result;
    }

    @Override
    @Transactional
    public void matchByIdno(Usage usage) {
        doMatchByIdno(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageRepository.updateStatusAndWrWrkInstByStandardNumberAndTitle(Collections.singletonList(usage));
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by standard number %s", usage.getWrWrkInst(),
                    usage.getStandardNumber()));
        } else {
            List<Usage> usagesByStandardNumber =
                usageRepository.findByStandardNumberAndStatus(usage.getStandardNumber(), UsageStatusEnum.NEW);
            updateUsagesStatusAndWriteAudit(usagesByStandardNumber, UsageGroupEnum.STANDARD_NUMBER,
                (usagesToUpdate) -> usageRepository.updateStatusAndWrWrkInstByStandardNumberAndTitle(usagesToUpdate));
        }
    }

    @Override
    @Transactional
    public void matchByTitle(Usage usage) {
        doMatchByTitle(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageRepository.updateStatusAndWrWrkInstByTitle(Collections.singletonList(usage));
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by title \"%s\"", usage.getWrWrkInst(), usage.getWorkTitle()));
        } else {
            List<Usage> usagesByTitle = usageRepository.findByTitleAndStatus(usage.getWorkTitle(), UsageStatusEnum.NEW);
            updateUsagesStatusAndWriteAudit(usagesByTitle, UsageGroupEnum.TITLE,
                (usagesToUpdate) -> usageRepository.updateStatusAndWrWrkInstByTitle(usagesToUpdate));
        }
    }

    @Override
    @Transactional
    public List<Usage> matchByTitle(List<Usage> usages) {
        LOGGER.info("Search works by Titles. Started. TitlesCount={}", usages.size());
        List<Usage> result = doMatchByTitle(usages);
        if (CollectionUtils.isNotEmpty(result)) {
            usageRepository.updateStatusAndWrWrkInstByTitle(result);
            result.stream()
                .map(Usage::getWorkTitle)
                .collect(Collectors.toSet())
                .forEach(title -> usageRepository.findByTitleAndStatus(title, UsageStatusEnum.WORK_FOUND)
                    .forEach(usage -> auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                        String.format("Wr Wrk Inst %s was found by title \"%s\"", usage.getWrWrkInst(),
                            usage.getWorkTitle()))));
        }
        usages.stream()
            .filter(usage -> Objects.equals(UsageStatusEnum.NEW, usage.getStatus()))
            .map(Usage::getWorkTitle)
            .collect(Collectors.toSet())
            .forEach(title -> {
                List<Usage> usagesByTitle = usageRepository.findByTitleAndStatus(title, UsageStatusEnum.NEW);
                updateUsagesStatusAndWriteAudit(usagesByTitle, UsageGroupEnum.TITLE,
                    (usagesToUpdate) -> usageRepository.updateStatusAndWrWrkInstByTitle(usagesToUpdate));
            });
        LOGGER.info("Search works by Titles. Finished. TitlesCount={}, WorksFound={}", usages.size(), result.size());
        return result;
    }

    @Override
    @Transactional
    public void updateStatusForUsagesWithNoStandardNumberAndTitle(List<Usage> usages) {
        usages.forEach(
            usage -> updateUsagesStatusAndWriteAudit(Collections.singletonList(usage), UsageGroupEnum.SINGLE_USAGE,
                (usagesToUpdate) -> usageRepository.updateStatusAndWrWrkInstByTitle(usagesToUpdate)));
        usageRepository.update(usages);
    }

    @Override
    @Transactional
    public void updateStatusForUsageWithoutStandardNumberAndTitle(Usage usage) {
        updateUsagesStatusAndWriteAudit(Collections.singletonList(usage), UsageGroupEnum.SINGLE_USAGE,
            (usagesToUpdate) -> usageRepository.update(usagesToUpdate));
    }

    private List<Usage> doMatchByTitle(List<Usage> usages) {
        Set<String> titles = usages.stream()
            .map(Usage::getWorkTitle)
            .collect(Collectors.toSet());
        return CollectionUtils.isNotEmpty(titles)
            ? computeResultByTitles(usages, piIntegrationService.findWrWrkInstsByTitles(titles))
            : Collections.emptyList();
    }

    private void doMatchByTitle(Usage usage) {
        Long wrWrkInst = piIntegrationService.findWrWrkInstByTitle(usage.getWorkTitle());
        if (Objects.nonNull(wrWrkInst)) {
            usage.setWrWrkInst(wrWrkInst);
            usage.setSystemTitle(usage.getWorkTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
        }
    }

    private List<Usage> doMatchByIdno(List<Usage> usages) {
        List<Usage> result = new ArrayList<>();
        usages.forEach(usage -> {
            Work work = piIntegrationService.findWorkByIdnoAndTitle(usage.getStandardNumber(), usage.getWorkTitle());
            if (Objects.nonNull(work)) {
                usage.setWrWrkInst(work.getWrWrkInst());
                usage.setSystemTitle(work.getMainTitle());
                addWorkFoundUsageToResult(result, usage);
            }
        });
        return result;
    }

    private void doMatchByIdno(Usage usage) {
        Work work = piIntegrationService.findWorkByIdnoAndTitle(usage.getStandardNumber(), usage.getWorkTitle());
        if (Objects.nonNull(work) && Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
        }
    }

    private List<Usage> computeResultByTitles(List<Usage> usages, Map<String, Long> titleToWrWrkInstMap) {
        List<Usage> result = new ArrayList<>(titleToWrWrkInstMap.size());
        if (MapUtils.isNotEmpty(titleToWrWrkInstMap)) {
            usages.forEach(usage -> {
                usage.setWrWrkInst(titleToWrWrkInstMap.get(usage.getWorkTitle()));
                usage.setSystemTitle(usage.getWorkTitle());
                addWorkFoundUsageToResult(result, usage);
            });
        }
        return result;
    }

    private void addWorkFoundUsageToResult(List<Usage> result, Usage usage) {
        if (Objects.nonNull(usage.getWrWrkInst())) {
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
            result.add(usage);
        }
    }

    private void updateUsagesStatusAndWriteAudit(List<Usage> usages, UsageGroupEnum usageGroup,
                                                 IUpdateFunction function) {
        if (GROSS_AMOUNT_LIMIT.compareTo(sumUsagesGrossAmount(usages)) > 0) {
            usages.forEach(usage -> {
                usage.setStatus(UsageStatusEnum.NTS_WITHDRAWN);
                usage.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
                auditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
                    usageGroup.getNtsEligibleReason());
            });
            usageRepository.updateToNtsEligible(usages);
        } else {
            usages.forEach(usage -> {
                if (Objects.isNull(usage.getStandardNumber()) && Objects.isNull(usage.getWorkTitle())) {
                    usage.setWrWrkInst(UNIDENTIFIED_WR_WRK_INST);
                    usage.setWorkTitle(UNIDENTIFIED_TITLE);
                    usage.setSystemTitle(UNIDENTIFIED_TITLE);
                    usage.setStatus(UsageStatusEnum.WORK_FOUND);
                    auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                        "Usage assigned unidentified work due to empty standard number and title");
                } else {
                    usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
                    auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
                        usageGroup.getWorkNotFoundReasonFunction().apply(usage));
                }
            });
            function.update(usages);
        }
    }

    private BigDecimal sumUsagesGrossAmount(List<Usage> usages) {
        return usages.stream()
            .map(Usage::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private enum UsageGroupEnum {

        /**
         * Enum constant to separate groups of {@link Usage}s with standard number.
         */
        STANDARD_NUMBER {
            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, " +
                    "is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Wr Wrk Inst was not found by standard number " + usage.getStandardNumber();
            }
        },
        /**
         * Enum constant to separate groups of {@link Usage}s with work title.
         */
        TITLE {
            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because sum of gross amounts, grouped by work title, " +
                    "is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Wr Wrk Inst was not found by title \"" + usage.getWorkTitle() + "\"";
            }
        },
        /**
         * Enum constant to separate groups of {@link Usage}s that don't have standard number and work title.
         */
        SINGLE_USAGE {
            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because gross amount is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Usage has no standard number and title";
            }
        };

        /**
         * @return reason for making {@link Usage}
         * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}.
         */
        abstract String getNtsEligibleReason();

        /**
         * @return {@link Function} for building reason for setting status
         * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_NOT_FOUND} for {@link Usage}.
         */
        abstract Function<Usage, String> getWorkNotFoundReasonFunction();
    }

    @FunctionalInterface
    private interface IUpdateFunction {
        void update(List<Usage> usages);
    }
}
