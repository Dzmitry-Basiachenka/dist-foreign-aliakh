package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Interface of repository for works classification.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
public interface IWorkClassificationRepository extends Serializable {

    /**
     * Inserts or updates work classification.
     *
     * @param workClassification {@link WorkClassification} instance
     */
    void insertOrUpdate(WorkClassification workClassification);

    /**
     * Deletes work classification based on Wr Wrk Inst.
     *
     * @param wrWrkInst Wr Wrk Inst
     */
    void deleteByWrWrkInst(Long wrWrkInst);

    /**
     * Finds Wr Wrk Inst classification.
     *
     * @param wrWrkInst Wr Wrk Inst
     * @return Wr Wrk Inst classification if work has classification, {@code null} otherwise
     */
    String findClassificationByWrWrkInst(Long wrWrkInst);

    /**
     * Finds list of {@link WorkClassification}s by usage batches ids and search value.
     *
     * @param batchesIds  list of batches ids
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link WorkClassification}s
     */
    List<WorkClassification> findByBatchIds(Set<String> batchesIds, String searchValue, Pageable pageable, Sort sort);

    /**
     * Finds count of {@link WorkClassification}s by usage batches ids and search value.
     *
     * @param batchesIds  list of batches ids
     * @param searchValue search value
     * @return count of work classifications
     */
    int findCountByBatchIds(Set<String> batchesIds, String searchValue);

    /**
     * Finds {@link WorkClassification}s by search value.
     *
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link WorkClassification}s
     */
    List<WorkClassification> findBySearch(String searchValue, Pageable pageable, Sort sort);

    /**
     * Finds count of {@link WorkClassification}s by search value.
     *
     * @param searchValue search value
     * @return count of work classifications
     */
    int findCountBySearch(String searchValue);
}
