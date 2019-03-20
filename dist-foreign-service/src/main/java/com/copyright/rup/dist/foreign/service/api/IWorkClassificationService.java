package com.copyright.rup.dist.foreign.service.api;

/**
 * Interface of service for works classificaton.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
public interface IWorkClassificationService {

    /**
     * Get wrWrkInst classification.
     *
     * @param wrWrkInst wrWrkInst
     * @return wrWrkInst classification if work has classification, {@code null} otherwise
     */
    String getClassification(Long wrWrkInst);
}
