package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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

    @Autowired
    @Qualifier("df.integration.piIntegrationProxyService")
    private IPiIntegrationService piIntegrationService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    @Qualifier("df.service.rightsProducer")
    private IProducer<Usage> producer;

    @Override
    @Transactional
    public void matchByIdno(Usage usage) {
        doMatchByIdno(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageRepository.updateStatusAndWrWrkInstByStandardNumberAndTitle(Collections.singletonList(usage));
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by standard number %s", usage.getWrWrkInst(),
                    usage.getStandardNumber()));
            producer.send(usage);
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
            producer.send(usage);
        } else {
            List<Usage> usagesByTitle = usageRepository.findByTitleAndStatus(usage.getWorkTitle(), UsageStatusEnum.NEW);
            updateUsagesStatusAndWriteAudit(usagesByTitle, UsageGroupEnum.TITLE,
                (usagesToUpdate) -> usageRepository.updateStatusAndWrWrkInstByTitle(usagesToUpdate));
        }
    }

    @Override
    @Transactional
    public void updateStatusForUsageWithoutStandardNumberAndTitle(Usage usage) {
        updateUsagesStatusAndWriteAudit(Collections.singletonList(usage), UsageGroupEnum.SINGLE_USAGE,
            (usagesToUpdate) -> usageRepository.update(usagesToUpdate));
    }

    private void doMatchByTitle(Usage usage) {
        Long wrWrkInst = piIntegrationService.findWrWrkInstByTitle(usage.getWorkTitle());
        if (Objects.nonNull(wrWrkInst)) {
            usage.setWrWrkInst(wrWrkInst);
            usage.setSystemTitle(usage.getWorkTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
        }
    }

    private void doMatchByIdno(Usage usage) {
        Work work = piIntegrationService.findWorkByIdnoAndTitle(usage.getStandardNumber(), usage.getWorkTitle());
        if (Objects.nonNull(work) && Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
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
                    producer.send(usage);
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
