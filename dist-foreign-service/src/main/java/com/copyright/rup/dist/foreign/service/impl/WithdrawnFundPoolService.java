package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.repository.api.IWithdrawnFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IWithdrawnFundPoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IWithdrawnFundPoolService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@Service
public class WithdrawnFundPoolService implements IWithdrawnFundPoolService {

    @Autowired
    private IWithdrawnFundPoolRepository withdrawnFundPoolRepository;

    @Override
    public List<WithdrawnFundPool> getAdditionalFunds() {
        return withdrawnFundPoolRepository.findAll();
    }

    void setWithdrawnFundPoolRepository(IWithdrawnFundPoolRepository withdrawnFundPoolRepository) {
        this.withdrawnFundPoolRepository = withdrawnFundPoolRepository;
    }
}
