package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.service.impl.CommonRightsholderService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IRightsholderService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
@Service
public class RightsholderService extends CommonRightsholderService implements IRightsholderService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final IRightsholderRepository rightsholderRepository;

    /**
     * Constructor.
     *
     * @param rightsholderRepository an instance of {@link IRightsholderRepository}
     * @param prmRightsholderService an instance of {@link IPrmRightsholderService}
     */
    @Autowired
    public RightsholderService(IRightsholderRepository rightsholderRepository,
                               IPrmRightsholderService prmRightsholderService) {
        super(rightsholderRepository, prmRightsholderService);
        this.rightsholderRepository = rightsholderRepository;
    }

    @Override
    public List<Rightsholder> getRros() {
        return rightsholderRepository.findRros();
    }

    @Override
    public void updateRightsholder(Rightsholder rightsholder) {
        Objects.requireNonNull(rightsholder);
        LOGGER.info("Update rightsholder information. Started. RhAccount#={}", rightsholder.getAccountNumber());
        rightsholderRepository.deleteByAccountNumber(rightsholder.getAccountNumber());
        rightsholderRepository.insert(rightsholder);
        LOGGER.info("Update rightsholder information. Finished. RhAccount#={}", rightsholder.getAccountNumber());
    }

    @Override
    public List<Rightsholder> getFromUsages() {
        return rightsholderRepository.findFromUsages();
    }
}
