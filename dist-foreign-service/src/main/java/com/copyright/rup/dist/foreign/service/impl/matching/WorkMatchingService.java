package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private static final String MATCHING_BY_IDNO_FINISHED_LOG = "Consume FAS usages for matching processing. " +
        "Finished. UsageId={}, StandardNumber={}, WorkTitle={}, MatchBy=IDNO, WrWrkInst={}, UsageStatus={}";
    private static final String MATCHING_BY_TITLE_FINISHED_LOG = "Consume FAS usages for matching processing. " +
        "Finished. UsageId={}, WorkTitle={}, MatchBy=Title, WrWrkInst={}, UsageStatus={}";
    private static final String NOT_MATCHED_FINISHED_LOG = "Consume FAS usages for matching processing. " +
        "Finished. UsageId={}, ProductFamily={}, WorkTitle={}, WrWrkInst={}, UsageStatus={}";
    private static final String UDM_MATCHING_BY_WR_WRK_INST_FINISHED_LOG =
        "Consume UDM usages for matching processing. " +
            "Finished. UsageId={}, WrWrkInst={}, MatchBy=WrWrkInst, UsageStatus={}";
    private static final String UDM_MATCHING_BY_IDNO_FINISHED_LOG = "Consume UDM usages for matching processing. " +
        "Finished. UsageId={}, ReportedStandardNumber={}, ReportedTitle={}, MatchBy=IDNO, WrWrkInst={}, UsageStatus={}";
    private static final String UDM_MATCHING_BY_TITLE_FINISHED_LOG = "Consume UDM usages for matching processing. " +
        "Finished. UsageId={}, ReportedTitle={}, MatchBy=Title, WrWrkInst={}, UsageStatus={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    private IUdmUsageAuditService udmAuditService;

    @Override
    @Transactional
    public void matchingFasUsages(List<Usage> usages) {
        usages.forEach(usage -> {
            if (StringUtils.isNoneEmpty(usage.getStandardNumber())) {
                matchByStandardNumber(usage);
                LOGGER.trace(MATCHING_BY_IDNO_FINISHED_LOG, usage.getId(), usage.getStandardNumber(),
                    usage.getWorkTitle(), usage.getWrWrkInst(), usage.getStatus());
            } else if (StringUtils.isNoneEmpty(usage.getWorkTitle())) {
                matchByTitle(usage);
                LOGGER.trace(MATCHING_BY_TITLE_FINISHED_LOG, usage.getId(),
                    usage.getWorkTitle(), usage.getWrWrkInst(), usage.getStatus());
            } else {
                updateUsagesStatusAndWriteAudit(usage, new Work(), UsageGroupEnum.SINGLE_USAGE, usage.getGrossAmount());
                LOGGER.trace(NOT_MATCHED_FINISHED_LOG, usage.getId(), usage.getProductFamily(),
                    usage.getWorkTitle(), usage.getWrWrkInst(), usage.getStatus());
            }
        });
    }

    @Override
    @Transactional
    public void matchingAaclUsages(List<Usage> usages) {
        usages.forEach(usage -> {
            matchByWrWrkInst(usage);
            LOGGER.trace(
                "Consume AACL usages for matching processing. Processed. UsageId={}, WrWrkInst={}, UsageStatus={}",
                usage.getId(), usage.getWrWrkInst(), usage.getStatus());
        });
    }

    @Override
    @Transactional
    public void matchingSalUsages(List<Usage> usages) {
        usages.forEach(usage -> {
            matchByWrWrkInst(usage);
            LOGGER.trace(
                "Consume SAL usages for matching processing. Processed. UsageId={}, WrWrkInst={}, UsageStatus={}",
                usage.getId(), usage.getWrWrkInst(), usage.getStatus());
        });
    }

    @Override
    @Transactional
    public void matchingUdmUsages(List<UdmUsage> usages) {
        usages.forEach(usage -> {
            if (Objects.nonNull(usage.getWrWrkInst())) {
                updateUdmUsageWorkInfo(usage, piIntegrationService.findWorkByWrWrkInst(usage.getWrWrkInst()),
                    UdmUsageGroupEnum.WR_WRK_INST);
                LOGGER.trace(UDM_MATCHING_BY_WR_WRK_INST_FINISHED_LOG, usage.getId(), usage.getWrWrkInst(),
                    usage.getStatus());
            } else if (StringUtils.isNoneEmpty(usage.getReportedStandardNumber())) {
                updateUdmUsageWorkInfo(usage,
                    piIntegrationService.findWorkByStandardNumber(usage.getReportedStandardNumber()),
                    UdmUsageGroupEnum.STANDARD_NUMBER);
                LOGGER.trace(UDM_MATCHING_BY_IDNO_FINISHED_LOG, usage.getId(), usage.getReportedStandardNumber(),
                    usage.getReportedTitle(), usage.getWrWrkInst(), usage.getStatus());
            } else if (StringUtils.isNoneEmpty(usage.getReportedTitle())) {
                updateUdmUsageWorkInfo(usage, piIntegrationService.findWorkByTitle(usage.getReportedTitle()),
                    UdmUsageGroupEnum.TITLE);
                LOGGER.trace(UDM_MATCHING_BY_TITLE_FINISHED_LOG, usage.getId(),
                    usage.getReportedTitle(), usage.getWrWrkInst(), usage.getStatus());
            }
        });
    }

    private void matchByStandardNumber(Usage usage) {
        Work work = doMatchByStandardNumber(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageService.updateProcessedUsage(usage);
            String searchParameter = work.isHostIdnoFlag() ? "host IDNO" : "standard number";
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found by %s %s", usage.getWrWrkInst(), searchParameter,
                    usage.getStandardNumber()));
        } else {
            updateUsagesStatusAndWriteAudit(usage, work, UsageGroupEnum.STANDARD_NUMBER,
                usageRepository.getTotalAmountByStandardNumberAndBatchId(usage.getStandardNumber(),
                    usage.getBatchId()));
        }
    }

    private void matchByTitle(Usage usage) {
        Work work = doMatchByTitle(usage);
        if (UsageStatusEnum.WORK_FOUND == usage.getStatus()) {
            usageService.updateProcessedUsage(usage);
            if (work.isHostIdnoFlag()) {
                auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                    String.format("Wr Wrk Inst %s was found by host IDNO %s", usage.getWrWrkInst(),
                        usage.getStandardNumber()));
            } else {
                auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                    String.format("Wr Wrk Inst %s was found by title \"%s\"", usage.getWrWrkInst(),
                        usage.getWorkTitle()));
            }
        } else {
            updateUsagesStatusAndWriteAudit(usage, work, UsageGroupEnum.TITLE,
                usageRepository.getTotalAmountByTitleAndBatchId(usage.getWorkTitle(), usage.getBatchId()));
        }
    }

    private void matchByWrWrkInst(Usage usage) {
        Work work = piIntegrationService.findWorkByWrWrkInst(usage.getWrWrkInst());
        if (Objects.nonNull(work.getWrWrkInst())) {
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
            usage.setStandardNumber(work.getMainIdno());
            usage.setStandardNumberType(StringUtils.upperCase(work.getMainIdnoType()));
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                String.format("Wr Wrk Inst %s was found in PI", usage.getWrWrkInst()));
        } else {
            usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
                String.format("Wr Wrk Inst %s was not found in PI", usage.getWrWrkInst()));
        }
        usageService.updateProcessedUsage(usage);
    }

    private Work doMatchByTitle(Usage usage) {
        Work work = piIntegrationService.findWorkByTitle(usage.getWorkTitle());
        if (Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
            usage.setStandardNumberType(StringUtils.upperCase(work.getMainIdnoType()));
            usage.setStandardNumber(work.getMainIdno());
        }
        return work;
    }

    private Work doMatchByStandardNumber(Usage usage) {
        Work work = piIntegrationService.findWorkByStandardNumber(usage.getStandardNumber());
        if (Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            if (Objects.isNull(usage.getWorkTitle())) {
                usage.setWorkTitle(work.getMainTitle());
            }
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
            usage.setStandardNumber(work.getMainIdno());
            usage.setStandardNumberType(StringUtils.upperCase(work.getMainIdnoType()));
        }
        return work;
    }

    private void updateUdmUsageWorkInfo(UdmUsage usage, Work work, UdmUsageGroupEnum usageGroup) {
        if (Objects.nonNull(work.getWrWrkInst())) {
            usage.setWrWrkInst(work.getWrWrkInst());
            usage.setSystemTitle(work.getMainTitle());
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
            usage.setStandardNumber(work.getMainIdno());
            if (work.isHostIdnoFlag()) {
                udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                    String.format("Wr Wrk Inst %s was found by host IDNO %s",
                        usage.getWrWrkInst(), usage.getStandardNumber()));
            } else {
                udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
                    usageGroup.getWorkFoundReasonFunction().apply(usage));
            }
        } else {
            usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            usage.setSystemTitle(null);
            usage.setStandardNumber(null);
            usage.getRightsholder().setAccountNumber(null);
            if (work.isMultipleMatches()) {
                udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.MULTIPLE_RESULTS,
                    usageGroup.getMultipleMatchesReasonFunction().apply(usage));
            } else {
                udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
                    usageGroup.getWorkNotFoundReasonFunction().apply(usage));
            }
        }
        udmUsageService.updateProcessedUsage(usage);
    }

    private void updateUsagesStatusAndWriteAudit(Usage usage, Work work, UsageGroupEnum usageGroup,
                                                 BigDecimal totalGrossAmount) {
        if (GROSS_AMOUNT_LIMIT.compareTo(totalGrossAmount) > 0) {
            usage.setStatus(UsageStatusEnum.NTS_WITHDRAWN);
            usageService.updateProcessedUsage(usage);
            auditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
                usageGroup.getNtsEligibleReason());
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
            usageService.updateProcessedUsage(usage);
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

    private enum UdmUsageGroupEnum {

        /**
         * Enum constant to separate groups of {@link UdmUsage}s with standard number.
         */
        STANDARD_NUMBER {
            @Override
            Function<UdmUsage, String> getWorkFoundReasonFunction() {
                return usage -> String.format("Wr Wrk Inst %s was found by standard number %s",
                    usage.getWrWrkInst(), usage.getReportedStandardNumber());
            }

            @Override
            Function<UdmUsage, String> getWorkNotFoundReasonFunction() {
                return usage -> String.format("Wr Wrk Inst was not found by standard number %s",
                    usage.getReportedStandardNumber());
            }

            @Override
            Function<UdmUsage, String> getMultipleMatchesReasonFunction() {
                return usage -> String.format("Multiple results were found by standard number %s",
                    usage.getReportedStandardNumber());
            }
        },
        /**
         * Enum constant to separate groups of {@link UdmUsage}s with work title.
         */
        TITLE {
            @Override
            Function<UdmUsage, String> getWorkFoundReasonFunction() {
                return usage -> String.format("Wr Wrk Inst %s was found by title \"%s\"",
                    usage.getWrWrkInst(), usage.getReportedTitle());
            }

            @Override
            Function<UdmUsage, String> getWorkNotFoundReasonFunction() {
                return usage -> String.format("Wr Wrk Inst was not found by title \"%s\"",
                    usage.getReportedTitle());
            }

            @Override
            Function<UdmUsage, String> getMultipleMatchesReasonFunction() {
                return usage -> String.format("Multiple results were found by title \"%s\"",
                    usage.getReportedTitle());
            }
        },
        /**
         * Enum constant to separate groups of {@link UdmUsage}s with work wrWrkInst.
         */
        WR_WRK_INST {
            @Override
            Function<UdmUsage, String> getWorkFoundReasonFunction() {
                return usage -> String.format("Wr Wrk Inst %s was found in PI", usage.getWrWrkInst());
            }

            @Override
            Function<UdmUsage, String> getWorkNotFoundReasonFunction() {
                return usage -> String.format("Wr Wrk Inst %s was not found in PI", usage.getWrWrkInst());
            }

            @Override
            Function<UdmUsage, String> getMultipleMatchesReasonFunction() {
                throw new RupRuntimeException("PI should never have multiple matches by Wr Wrk Inst");
            }
        };

        /**
         * @return reason for making {@link UdmUsage}
         * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_FOUND}.
         */
        abstract Function<UdmUsage, String> getWorkFoundReasonFunction();

        /**
         * @return {@link Function} for building reason for setting status {@link UsageStatusEnum#WORK_NOT_FOUND}
         * with {@link UsageActionTypeEnum#WORK_NOT_FOUND} action type for {@link UdmUsage}.
         */
        abstract Function<UdmUsage, String> getWorkNotFoundReasonFunction();

        /**
         * @return {@link Function} for building reason for setting status {@link UsageStatusEnum#WORK_NOT_FOUND}
         * with {@link UsageActionTypeEnum#MULTIPLE_RESULTS} action type for {@link UdmUsage}
         */
        abstract Function<UdmUsage, String> getMultipleMatchesReasonFunction();
    }
}
