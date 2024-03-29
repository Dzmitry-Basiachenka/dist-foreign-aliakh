package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.repository.api.IUdmTypeOfUseRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IUdmTypeOfUseService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
@Service
public class UdmTypeOfUseService implements IUdmTypeOfUseService {

    @Autowired
    private IUdmTypeOfUseRepository udmTypeOfUseRepository;

    @Override
    public List<String> getAllUdmTous() {
        return udmTypeOfUseRepository.findAllUdmTous();
    }

    @Override
    public Map<String, String> getUdmTouToRmsTouMap() {
        return udmTypeOfUseRepository.findUdmTouToRmsTouMap();
    }
}
