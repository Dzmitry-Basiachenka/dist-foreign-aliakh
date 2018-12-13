package com.copyright.rup.dist.foreign.service.impl.matching;

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
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService auditService;

    @Override
    @Transactional
    public void matchByIdno(Usage usage) {
        Work work = doMatchByIdno(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageRepository.update(Collections.singletonList(usage));
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by standard number %s", usage.getWrWrkInst(),
                    usage.getStandardNumber()));
        } else {
            updateUsagesStatusAndWriteAudit(usage, work, UsageGroupEnum.STANDARD_NUMBER,
                usageRepository.getTotalAmountByStandardNumberAndBatchId(usage.getStandardNumber(),
                    usage.getBatchId()));
        }
    }

    @Override
    @Transactional
    public void matchByTitle(Usage usage) {
        Work work = doMatchByTitle(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageRepository.update(Collections.singletonList(usage));
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by title \"%s\"", usage.getWrWrkInst(), usage.getWorkTitle()));
        } else {
            updateUsagesStatusAndWriteAudit(usage, work, UsageGroupEnum.TITLE,
                usageRepository.getTotalAmountByTitleAndBatchId(usage.getWorkTitle(), usage.getBatchId()));
        }
    }

    @Override
    @Transactional
    public void updateStatusForUsageWithoutStandardNumberAndTitle(Usage usage) {
        updateUsagesStatusAndWriteAudit(usage, new Work(), UsageGroupEnum.SINGLE_USAGE, usage.getGrossAmount());
    }

    private Work doMatchByTitle(Usage usage) {
        Work work = piIntegrationService.findWorkByTitle(usage.getWorkTitle());
        if (Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            usage.setSystemTitle(usage.getWorkTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
        }
        return work;
    }

    private Work doMatchByIdno(Usage usage) {
        Work work = piIntegrationService.findWorkByIdnoAndTitle(usage.getStandardNumber(), usage.getWorkTitle());
        if (Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
        }
        return work;
    }

    private void updateUsagesStatusAndWriteAudit(Usage usage, Work work, UsageGroupEnum usageGroup,
                                                 BigDecimal totalGrossAmount) {
        if (GROSS_AMOUNT_LIMIT.compareTo(totalGrossAmount) > 0) {
            usage.setStatus(UsageStatusEnum.NTS_WITHDRAWN);
            usage.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
            auditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
                usageGroup.getNtsEligibleReason());
            usageRepository.updateToNtsWithdrawn(usage);
        } else {
            if (Objects.isNull(usage.getStandardNumber()) && Objects.isNull(usage.getWorkTitle())) {
                usage.setWrWrkInst(UNIDENTIFIED_WR_WRK_INST);
                usage.setWorkTitle(UNIDENTIFIED_TITLE);
                usage.setSystemTitle(UNIDENTIFIED_TITLE);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
                auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                    "Usage assigned unidentified work due to empty standard number and title");
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
                if (work.isMultipleMatches()) {
                    auditService.logAction(usage.getId(), UsageActionTypeEnum.MULTIPLE_RESULTS,
                        usageGroup.getMultipleMatchesReasonFunction().apply(usage));
                } else {
                    auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
                        usageGroup.getWorkNotFoundReasonFunction().apply(usage));
                }
            }
            usageRepository.update(Collections.singletonList(usage));
        }
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

            @Override
            Function<Usage, String> getMultipleMatchesReasonFunction() {
                return usage -> "Multiple results were found by standard number " + usage.getStandardNumber();
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

            @Override
            Function<Usage, String> getMultipleMatchesReasonFunction() {
                return usage -> "Multiple results were found by title \"" + usage.getWorkTitle() + "\"";
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

            @Override
            Function<Usage, String> getMultipleMatchesReasonFunction() {
                return usage -> "Usage has no standard number and title";
            }
        };

        /**
         * @return reason for making {@link Usage}
         * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NTS_WITHDRAWN}.
         */
        abstract String getNtsEligibleReason();

        /**
         * @return {@link Function} for building reason for setting status {@link UsageStatusEnum#WORK_NOT_FOUND}
         * with {@link UsageActionTypeEnum#WORK_NOT_FOUND} action type for {@link Usage}.
         */
        abstract Function<Usage, String> getWorkNotFoundReasonFunction();

        /**
         * @return {@link Function} for building reason for setting status {@link UsageStatusEnum#WORK_NOT_FOUND}
         * with {@link UsageActionTypeEnum#MULTIPLE_RESULTS} action type for {@link Usage}
         */
        abstract Function<Usage, String> getMultipleMatchesReasonFunction();
    }
}
