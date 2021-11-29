package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IUdmBaselineValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
@Service
public class UdmBaselineValueService implements IUdmBaselineValueService {

    @Autowired
    private IUdmBaselineValueRepository repository;

    @Override
    public List<Integer> getPeriods() {
        return repository.findPeriods();
    }
}
