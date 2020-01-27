package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
public class PublicationTypeService implements IPublicationTypeService {

    @Autowired
    private IPublicationTypeRepository pubTypeRepository;

    @Override
    public boolean isPublicationTypeExist(String pubTypeName) {
        return Objects.nonNull(pubTypeRepository.findIdByName(pubTypeName));
    }
}
