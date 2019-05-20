package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderOrganizationService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mock implementation of {@link IPrmRightsholderOrganizationService}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 01/18/2019
 *
 * @author Aliaksandr Liakh
 */
public class PrmRightsholderOrganizationServiceMock implements IPrmRightsholderOrganizationService {

    @Override
    public List<Rightsholder> getRightsholders(Collection<String> rhIds) {
        return rhIds.stream()
            .map(TestPrmRightsholdersHolder::getByRhId)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
