package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.aacl.AaclUsageService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mock for {@link AaclUsageService} that preserves input order of usages in {@link #getUsagesByIds(List)} method.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 3/4/20
 *
 * @author Stanislau Rudak
 */
public class AaclUsageServiceMock extends AaclUsageService {

    @Override
    public List<Usage> getUsagesByIds(List<String> usageIds) {
        Map<String, Usage> idsToUsages = super.getUsagesByIds(usageIds).stream()
            .collect(Collectors.toMap(Usage::getId, usage -> usage));
        return usageIds.stream()
            .map(id -> Objects.requireNonNull(idsToUsages.get(id)))
            .collect(Collectors.toList());
    }
}
