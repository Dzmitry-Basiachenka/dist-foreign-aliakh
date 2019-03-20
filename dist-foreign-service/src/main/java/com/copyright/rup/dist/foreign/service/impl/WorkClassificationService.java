package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;
import com.copyright.rup.dist.foreign.service.api.IWorkClassificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IWorkClassificationRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
@Service
public class WorkClassificationService implements IWorkClassificationService {

    @Autowired
    private IWorkClassificationRepository workClassificationRepository;

    @Override
    public String getClassification(Long wrWrkInst) {
        return workClassificationRepository.findClassificationByWrWrkInst(wrWrkInst);
    }

    public void setWorkClassificationRepository(IWorkClassificationRepository workClassificationRepository) {
        this.workClassificationRepository = workClassificationRepository;
    }
}
