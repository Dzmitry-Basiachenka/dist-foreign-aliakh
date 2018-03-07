package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.integration.pi.impl.PiIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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
        Set<String> titles = usages.stream()
            .map(Usage::getWorkTitle)
            .collect(Collectors.toSet());
        return CollectionUtils.isNotEmpty(titles)
            ? computeResult(usages, piIntegrationService.findWrWrkInstsByTitles(titles), Usage::getWorkTitle)
            : Collections.emptyList();
    }

    @Override
    public List<Usage> matchByIdno(List<Usage> usages) {
        Set<String> idnos = usages.stream()
            .map(Usage::getStandardNumber)
            .collect(Collectors.toSet());
        return CollectionUtils.isNotEmpty(idnos)
            ? computeResult(usages, piIntegrationService.findWrWrkInstsByIdnos(idnos),
            usage -> PiIntegrationService.normalizeIdno(usage.getStandardNumber()))
            : Collections.emptyList();
    }

    private List<Usage> computeResult(List<Usage> usages, Map<String, Long> wrWrkInstsMap,
                                      Function<Usage, String> function) {
        List<Usage> result = new ArrayList<>(wrWrkInstsMap.size());
        if (MapUtils.isNotEmpty(wrWrkInstsMap)) {
            usages.forEach(usage -> {
                usage.setWrWrkInst(wrWrkInstsMap.get(function.apply(usage)));
                if (Objects.nonNull(usage.getWrWrkInst())) {
                    usage.setStatus(UsageStatusEnum.WORK_FOUND);
                    result.add(usage);
                }
            });
        }
        return result;
    }
}
