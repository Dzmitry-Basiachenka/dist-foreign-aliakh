package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclFundPoolService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAaclFundPoolService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AaclFundPoolService implements IAaclFundPoolService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IFundPoolRepository fundPoolRepository;
    @Autowired
    private IAaclFundPoolRepository aaclFundPoolRepository;
    @Autowired
    private ILicenseeClassService licenseeClassService;

    @Override
    public List<FundPool> getFundPools() {
        return aaclFundPoolRepository.findAll();
    }

    @Override
    public List<FundPoolDetail> getDetailsByFundPoolId(String fundPoolId) {
        Map<Integer, FundPoolDetail> classIdToDetail =
            aaclFundPoolRepository.findDetailsByFundPoolId(fundPoolId).stream()
                .collect(Collectors.toMap(detail -> detail.getAggregateLicenseeClass().getId(), detail -> detail));
        return licenseeClassService.getAggregateLicenseeClasses().stream()
            .map(alc -> classIdToDetail.getOrDefault(alc.getId(), buildZeroFundPoolDetail(alc)))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFundPoolById(String fundPoolId) {
        aaclFundPoolRepository.deleteDetailsByFundPoolId(fundPoolId);
        aaclFundPoolRepository.deleteById(fundPoolId);
    }

    @Override
    @Transactional
    public int insertFundPool(FundPool fundPool, Collection<FundPoolDetail> details) {
        LOGGER.info("Insert AACL fund pool. Started. FundPoolName={}", fundPool.getName());
        String userName = RupContextUtils.getUserName();
        fundPool.setId(RupPersistUtils.generateUuid());
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        fundPoolRepository.insert(fundPool);
        details.forEach(detail -> {
            detail.setId(RupPersistUtils.generateUuid());
            detail.setFundPoolId(fundPool.getId());
            detail.setCreateUser(userName);
            detail.setUpdateUser(userName);
            aaclFundPoolRepository.insertDetail(detail);
        });
        int count = details.size();
        LOGGER.info("Insert AACL fund pool. Finished. FundPoolName={}, UserName={}, UsagesCount={}",
            fundPool.getName(), userName, count);
        return count;
    }

    private FundPoolDetail buildZeroFundPoolDetail(AggregateLicenseeClass aggregateLicenseeClass) {
        FundPoolDetail detail = new FundPoolDetail();
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        return detail;
    }
}
