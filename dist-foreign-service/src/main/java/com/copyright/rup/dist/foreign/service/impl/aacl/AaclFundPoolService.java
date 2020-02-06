package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclFundPoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
