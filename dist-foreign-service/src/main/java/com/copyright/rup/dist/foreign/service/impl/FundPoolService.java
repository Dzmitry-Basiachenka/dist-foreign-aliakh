package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IFundPoolService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@Service
public class FundPoolService implements IFundPoolService {

    private static final int DEFAULT_SCALE = 2;
    private static final int SCALE_10 = 10;
    private static final BigDecimal ZERO = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.service_fee.sal}")
    private BigDecimal salServiceFee;

    @Autowired
    private IFundPoolRepository fundPoolRepository;
    @Autowired
    private INtsUsageService ntsUsageService;
    @Autowired
    private ILicenseeClassService licenseeClassService;

    @Override
    public FundPool getFundPoolById(String fundPoolId) {
        return fundPoolRepository.findById(fundPoolId);
    }

    @Override
    public List<FundPool> getFundPools(String productFamily) {
        return fundPoolRepository.findByProductFamily(productFamily);
    }

    @Override
    public boolean fundPoolExists(String productFamily, String name) {
        return fundPoolRepository.fundPoolExists(productFamily, name);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void createNtsFundPool(FundPool fundPool, Set<String> batchIds) {
        String userName = RupContextUtils.getUserName();
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        LOGGER.info(
            "Insert NTS fund pool. Started. FundPoolName={}, FundPoolAmount={}, BatchesCount={}, UserName={}",
            fundPool.getName(), fundPool.getTotalAmount(), LogUtils.size(batchIds), userName);
        fundPoolRepository.insert(fundPool);
        ntsUsageService.addWithdrawnUsagesToNtsFundPool(fundPool.getId(), batchIds, userName);
        LOGGER.info(
            "Insert NTS fund pool. Finished. FundPoolName={}, FundPoolAmount={}, BatchesCount={}, UserName={}",
            fundPool.getName(), fundPool.getTotalAmount(), LogUtils.size(batchIds), userName);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int createAaclFundPool(FundPool fundPool, List<FundPoolDetail> details) {
        String userName = RupContextUtils.getUserName();
        int count = details.size();
        LOGGER.info("Insert AACL fund pool. Started. FundPoolName={}, UserName={}, DetailsCount={}",
            fundPool.getName(), userName, count);
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setTotalAmount(details.stream()
            .map(FundPoolDetail::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        details.forEach(detail -> {
            detail.setId(RupPersistUtils.generateUuid());
            detail.setFundPoolId(fundPool.getId());
            detail.setCreateUser(userName);
            detail.setUpdateUser(userName);
            fundPoolRepository.insertDetail(detail);
        });
        LOGGER.info("Insert AACL fund pool. Finished. FundPoolName={}, UserName={}, DetailsCount={}",
            fundPool.getName(), userName, count);
        return count;
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void createSalFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert SAL fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setProductFamily(FdaConstants.SAL_PRODUCT_FAMILY);
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        LOGGER.info("Insert SAL fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void createAclciFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACLCI fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setProductFamily(FdaConstants.ACLCI_PRODUCT_FAMILY);
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        LOGGER.info("Insert ACLCI fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    public List<FundPool> getNtsNotAttachedToScenario() {
        return fundPoolRepository.findNtsNotAttachedToScenario();
    }

    @Override
    public List<FundPool> getAaclNotAttachedToScenario() {
        return fundPoolRepository.findAaclNotAttachedToScenario();
    }

    @Override
    public List<FundPool> getSalNotAttachedToScenario() {
        return fundPoolRepository.findSalNotAttachedToScenario();
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteNtsFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete NTS fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        ntsUsageService.deleteFromNtsFundPool(fundPool.getId());
        fundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete NTS fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(),
            userName);
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteAaclFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete AACL fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPoolRepository.deleteDetailsByFundPoolId(fundPool.getId());
        fundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete AACL fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteSalFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete SAL fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete SAL fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteAclciFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete ACLCI fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete ACLCI fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    public List<String> getNtsFundPoolNamesByUsageBatchId(String batchId) {
        return fundPoolRepository.findNamesByUsageBatchId(batchId);
    }

    @Override
    public List<FundPoolDetail> getDetailsByFundPoolId(String fundPoolId) {
        Map<Integer, FundPoolDetail> classIdToDetail =
            fundPoolRepository.findDetailsByFundPoolId(fundPoolId).stream()
                .collect(Collectors.toMap(detail -> detail.getAggregateLicenseeClass().getId(), Function.identity()));
        return licenseeClassService.getAggregateLicenseeClasses(FdaConstants.AACL_PRODUCT_FAMILY).stream()
            .map(alc -> classIdToDetail.getOrDefault(alc.getId(), buildZeroFundPoolDetail(alc)))
            .collect(Collectors.toList());
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public FundPool calculateSalFundPoolAmounts(FundPool fundPool) {
        var salFields = fundPool.getSalFields();
        BigDecimal splitPercent = salFields.getItemBankSplitPercent();
        salFields.setServiceFee(salServiceFee);
        if (0 == BigDecimal.ONE.compareTo(splitPercent)) {
            fundPool.setTotalAmount(salFields.getGrossAmount());
            salFields.setItemBankGrossAmount(salFields.getGrossAmount());
            salFields.setGradeKto5GrossAmount(ZERO);
            salFields.setGrade6to8GrossAmount(ZERO);
            salFields.setGrade9to12GrossAmount(ZERO);
        } else {
            BigDecimal multiplier = salFields.getGrossAmount().multiply(BigDecimal.ONE.subtract(splitPercent));
            var totalStudents = BigDecimal.valueOf(
                salFields.getGradeKto5NumberOfStudents()
                + (long) salFields.getGrade6to8NumberOfStudents()
                + (long) salFields.getGrade9to12NumberOfStudents());
            salFields.setGradeKto5GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, salFields.getGradeKto5NumberOfStudents()));
            salFields.setGrade6to8GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, salFields.getGrade6to8NumberOfStudents()));
            salFields.setGrade9to12GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, salFields.getGrade9to12NumberOfStudents()));
            salFields.setItemBankGrossAmount(salFields.getGrossAmount()
                .subtract(salFields.getGradeKto5GrossAmount())
                .subtract(salFields.getGrade6to8GrossAmount())
                .subtract(salFields.getGrade9to12GrossAmount()));
            fundPool.setTotalAmount(salFields.getItemBankGrossAmount()
                .add(salFields.getGradeKto5GrossAmount())
                .add(salFields.getGrade6to8GrossAmount())
                .add(salFields.getGrade9to12GrossAmount()));
        }
        return fundPool;
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public FundPool calculateAclciFundPoolAmounts(FundPool fundPool) {
        var aclciFields = fundPool.getAclciFields();
        BigDecimal splitPercent = aclciFields.getCurriculumDbSplitPercent();
        if (0 == BigDecimal.ONE.compareTo(splitPercent)) {
            fundPool.setTotalAmount(aclciFields.getGrossAmount());
            aclciFields.setCurriculumDbGrossAmount(aclciFields.getGrossAmount());
            aclciFields.setGradeKto2GrossAmount(ZERO);
            aclciFields.setGrade3to5GrossAmount(ZERO);
            aclciFields.setGrade6to8GrossAmount(ZERO);
            aclciFields.setGrade9to12GrossAmount(ZERO);
            aclciFields.setGradeHeGrossAmount(ZERO);
        } else {
            BigDecimal multiplier = aclciFields.getGrossAmount().multiply(BigDecimal.ONE.subtract(splitPercent));
            var totalStudents = BigDecimal.valueOf(
                aclciFields.getGradeKto2NumberOfStudents() +
                aclciFields.getGrade3to5NumberOfStudents() +
                aclciFields.getGrade6to8NumberOfStudents() +
                aclciFields.getGrade9to12NumberOfStudents() +
                aclciFields.getGradeHeNumberOfStudents());
            aclciFields.setGradeKto2GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, aclciFields.getGradeKto2NumberOfStudents()));
            aclciFields.setGrade3to5GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, aclciFields.getGrade3to5NumberOfStudents()));
            aclciFields.setGrade6to8GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, aclciFields.getGrade6to8NumberOfStudents()));
            aclciFields.setGrade9to12GrossAmount(
                calculateGradeAmount(multiplier, totalStudents, aclciFields.getGrade9to12NumberOfStudents()));
            aclciFields.setGradeHeGrossAmount(
                calculateGradeAmount(multiplier, totalStudents, aclciFields.getGradeHeNumberOfStudents()));
            aclciFields.setCurriculumDbGrossAmount(aclciFields.getGrossAmount()
                .subtract(aclciFields.getGradeKto2GrossAmount())
                .subtract(aclciFields.getGrade3to5GrossAmount())
                .subtract(aclciFields.getGrade6to8GrossAmount())
                .subtract(aclciFields.getGrade9to12GrossAmount())
                .subtract(aclciFields.getGradeHeGrossAmount()));
            fundPool.setTotalAmount(aclciFields.getCurriculumDbGrossAmount()
                .add(aclciFields.getGradeKto2GrossAmount())
                .add(aclciFields.getGrade3to5GrossAmount())
                .add(aclciFields.getGrade6to8GrossAmount())
                .add(aclciFields.getGrade9to12GrossAmount())
                .add(aclciFields.getGradeHeGrossAmount()));
        }
        return fundPool;
    }

    // Used for calculating grade gross amounts.
    // RoundingMode.DOWN is used while calculating to be consistent with current calculation in SC
    private static BigDecimal calculateGradeAmount(BigDecimal multiplier, BigDecimal totalStudentsCount,
                                                   long studentsCount) {
        BigDecimal amount;
        if (0 < studentsCount) {
            amount = multiplier.multiply(BigDecimal.valueOf(studentsCount))
                .divide(totalStudentsCount, SCALE_10, RoundingMode.DOWN)
                .setScale(DEFAULT_SCALE, RoundingMode.DOWN);
        } else {
            amount = ZERO;
        }
        return amount;
    }

    private FundPoolDetail buildZeroFundPoolDetail(AggregateLicenseeClass aggregateLicenseeClass) {
        var detail = new FundPoolDetail();
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        return detail;
    }
}
