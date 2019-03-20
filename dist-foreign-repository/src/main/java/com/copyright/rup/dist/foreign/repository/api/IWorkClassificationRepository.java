package com.copyright.rup.dist.foreign.repository.api;

/**
 * Interface of repository for works classification.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
public interface IWorkClassificationRepository {

    /**
     * Finds wrWrkInst classification.
     *
     * @param wrWrkInst wrWrkInst
     * @return wrWrkInst classification if work has classification, {@code null} otherwise
     */
    String findClassificationByWrWrkInst(Long wrWrkInst);
}
