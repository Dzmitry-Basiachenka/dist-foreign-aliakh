package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.repository.api.IUdmPriceTypeRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmPriceTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmPriceTypeService}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmPriceTypeService implements IUdmPriceTypeService {

    @Autowired
    private IUdmPriceTypeRepository udmPriceTypeRepository;

    @Override
    public List<String> getAllPriceTypes() {
        return udmPriceTypeRepository.findAllPriceTypes();
    }

    @Override
    public List<String> getAllPriceAccessTypes() {
        return udmPriceTypeRepository.findAllPriceAccessTypes();
    }
}
