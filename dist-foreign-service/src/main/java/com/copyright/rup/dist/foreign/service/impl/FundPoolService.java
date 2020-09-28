package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final Logger LOGGER = RupLogUtils.getLogger();

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
    public void deleteAaclFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete AACL fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPoolRepository.deleteDetailsByFundPoolId(fundPool.getId());
        fundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete AACL fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    public void deleteSalFundPool(FundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete SAL fund pool. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        fundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete SAL fund pool. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    public List<String> getNtsFundPoolNamesByUsageBatchId(String batchId) {
        return fundPoolRepository.findNamesByUsageBatchId(batchId);
    }

    @Override
    public List<FundPoolDetail> getDetailsByFundPoolId(String fundPoolId) {
        Map<Integer, FundPoolDetail> classIdToDetail =
            fundPoolRepository.findDetailsByFundPoolId(fundPoolId).stream()
                .collect(Collectors.toMap(detail -> detail.getAggregateLicenseeClass().getId(), detail -> detail));
        return licenseeClassService.getAggregateLicenseeClasses().stream()
            .map(alc -> classIdToDetail.getOrDefault(alc.getId(), buildZeroFundPoolDetail(alc)))
            .collect(Collectors.toList());
    }

    private FundPoolDetail buildZeroFundPoolDetail(AggregateLicenseeClass aggregateLicenseeClass) {
        FundPoolDetail detail = new FundPoolDetail();
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        return detail;
    }
}
