package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IValidator;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Validator for verifying Rh id.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/10/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.usageRhIdValidator")
// TODO move to dist-common
class RhIdValidator implements IValidator<List<PaidUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRightsholderService rightsholderService;

    @Override
    public void validate(List<PaidUsage> paidUsages) {
        Set<String> rhIds = paidUsages.stream()
            .flatMap(usage -> Stream.of(usage.getRightsholder().getId(), usage.getPayee().getId()))
            .collect(Collectors.toSet());
        LOGGER.debug("Validate RH ids. Started. RhIds={}", rhIds);
        validateRhIds(rhIds);
        LOGGER.debug("Validate RH ids. Finished. RhIds={}", rhIds);
    }

    private void validateRhIds(Set<String> rhIds) {
        Set<String> nonExistingRhIds = Sets.difference(rhIds, getExistingRhIds(rhIds));
        if (CollectionUtils.isNotEmpty(nonExistingRhIds)) {
            throw new ValidationException(String.format(
                "The next list of rightsholder's ids haven't been found in PRM: %s",
                Joiner.on(", ").join(nonExistingRhIds)));
        }
    }

    private Set<String> getExistingRhIds(Set<String> rhIds) {
        return rightsholderService.updateAndGetRightsholdersByIds(rhIds).stream()
            .map(Rightsholder::getId)
            .collect(Collectors.toSet());
    }
}
