package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclFundPoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private IAaclFundPoolRepository aaclFundPoolRepository;

    @Override
    public boolean aaclFundPoolExists(String name) {
        return aaclFundPoolRepository.aaclFundPoolExists(name);
    }

    @Override
    public List<AaclFundPool> getFundPools() {
        return aaclFundPoolRepository.findAll();
    }

    @Override
    public List<AaclFundPoolDetail> getDetailsByFundPoolId(String fundPoolId) {
        return aaclFundPoolRepository.findDetailsByFundPoolId(fundPoolId);
    }

    @Override
    @Transactional
    public void deleteFundPoolById(String fundPoolId) {
        aaclFundPoolRepository.deleteDetailsByFundPoolId(fundPoolId);
        aaclFundPoolRepository.deleteById(fundPoolId);
    }
}
