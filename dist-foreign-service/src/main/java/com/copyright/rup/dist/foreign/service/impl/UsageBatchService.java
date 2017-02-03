package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IUsageBatchService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 */
@Service("df.service.usageBatchService")
public class UsageBatchService implements IUsageBatchService {

    @Autowired
    private IUsageBatchRepository usageBatchRepository;

    @Override
    public List<Integer> getFiscalYears() {
        return usageBatchRepository.findFiscalYears();
    }
}
