package com.copyright.rup.dist.foreign.service.impl.nts;

import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link INtsUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
@SuppressWarnings("all") // TODO {aliakh} add implementation and remove @SuppressWarnings
public class NtsUsageService implements INtsUsageService {

    @Autowired
    private INtsUsageRepository ntsUsageRepository;
}
