package com.copyright.rup.dist.foreign.ui.mock;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Mock implementation of {@link IPrmIntegrationService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/06/2017
 *
 * @author Ihar Suvorau
 */
public class PrmIntegrationServiceMock implements IPrmIntegrationService {

    @Override
    public List<Rightsholder> getRightsholders(Set<Long> accountNumbers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Rightsholder getRightsholder(Long accountNumber) {
        return 2000017004L == accountNumber ? buildRightsholder(accountNumber) : null;
    }

    @Override
    public Table<String, String, Long> getRollUps(Collection<String> rightsholdersIds) {
        return HashBasedTable.create();
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RupPersistUtils.generateUuid());
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName("Access Copyright, The Canadian Copyright Agency");
        return rightsholder;
    }
}
