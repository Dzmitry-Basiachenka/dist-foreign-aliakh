package com.copyright.rup.dist.foreign.ui.mock;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.impl.RightsholderService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents {@link RightsholderService} with mock for {@link RightsholderService#updateAndGetRightsholders(Set)}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/31/18
 *
 * @author Ihar Suvorau
 */
public class RightsholderServiceMock extends RightsholderService {

    @Autowired
    public RightsholderServiceMock(IRightsholderRepository rightsholderRepository,
                                   IPrmRightsholderService prmRightsholderService) {
        super(rightsholderRepository, prmRightsholderService);
    }

    @Override
    public Map<Long, Rightsholder> updateAndGetRightsholders(Set<Long> accountNumbers) {
        Map<Long, Rightsholder> result = new HashMap<>();
        result.put(1000000001L, buildRightsholder(1000000001L, "Rothchild Consultants"));
        result.put(1000000002L, buildRightsholder(1000000002L, "Royal Society of Victoria"));
        return result;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        rightsholder.setId(RupPersistUtils.generateUuid());
        return rightsholder;
    }
}
