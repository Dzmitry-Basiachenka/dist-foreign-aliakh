package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.micrometer.core.annotation.Timed;

/**
 * Implements {@link IPublicationTypeService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class PublicationTypeService implements IPublicationTypeService {

    @Autowired
    private IPublicationTypeRepository pubTypeRepository;

    @Override
    public boolean publicationTypeExist(String pubTypeName, String productFamily) {
        return pubTypeRepository.isExistForProductFamily(pubTypeName, productFamily);
    }

    @Override
    public List<PublicationType> getPublicationTypes(String productFamily) {
        return pubTypeRepository.findByProductFamily(productFamily);
    }

    @Override
    public List<AclPublicationType> getAclHistoricalPublicationTypes() {
        return pubTypeRepository.findAclHistoricalPublicationTypes();
    }

    @Override
    public void insertAclHistoricalPublicationType(AclPublicationType publicationType) {
        pubTypeRepository.insertAclHistoricalPublicationType(publicationType);
    }
}
