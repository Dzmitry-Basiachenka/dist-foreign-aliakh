package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service encapsulates logic for matching {@link Usage}s to works against PI (Publi).
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/21/18
 *
 * @author Aliaksandr Radkevich
 */
@Service
public class WorkMatchingService implements IWorkMatchingService {

    @Autowired
    private IPiIntegrationService piIntegrationService;

    @Override
    public List<Usage> matchByTitle(List<Usage> usages) {
        Set<String> idnos = usages.stream()
            .filter(usage -> null == usage.getStandardNumber() && null != usage.getWorkTitle())
            .map(Usage::getWorkTitle)
            .collect(Collectors.toSet());
        Map<String, Long> wrWrkInstsByTitles = piIntegrationService.findWrWrkInstsByTitles(idnos);
        List<Usage> result = Lists.newArrayList();
        usages.forEach(usage -> {
            usage.setWrWrkInst(wrWrkInstsByTitles.get(usage.getStandardNumber()));
            if (null != usage.getWrWrkInst()) {
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
                result.add(usage);
            }
        });
        return result;
    }

    @Override
    public List<Usage> matchByIdno(List<Usage> usages) {
        Set<String> titles = usages.stream()
            .filter(usage -> null != usage.getStandardNumber())
            .map(Usage::getStandardNumber)
            .collect(Collectors.toSet());
        Map<String, Long> wrWrkInstsByIdno = piIntegrationService.findWrWrkInstsByIdno(titles);
        List<Usage> result = Lists.newArrayList();
        usages.forEach(usage -> {
            usage.setWrWrkInst(wrWrkInstsByIdno.get(usage.getStandardNumber()));
            if (null != usage.getWrWrkInst()) {
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
                result.add(usage);
            }
        });
        return result;
    }
}
