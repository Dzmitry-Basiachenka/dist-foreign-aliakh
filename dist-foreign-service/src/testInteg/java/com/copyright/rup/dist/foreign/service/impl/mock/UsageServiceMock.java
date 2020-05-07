package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.UsageService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mock for {@link UsageService} that sorts output of {@link #getUsagesByIds(List)} method by Wr Wrk Inst.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/06/20
 *
 * @author Uladzislau Shalamitski
 */
public class UsageServiceMock extends UsageService {

    @Override
    public List<Usage> getUsagesByIds(List<String> usageIds) {
        return super.getUsagesByIds(usageIds).stream()
            .sorted(Comparator.comparing(Usage::getWrWrkInst))
            .collect(Collectors.toList());
    }
}
