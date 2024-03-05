package com.copyright.rup.dist.foreign.service.api.nts;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Interface of service for works classificaton.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
public interface IWorkClassificationService extends Serializable {

    /**
     * Inserts or updates work classification and updates
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#UNCLASSIFIED} usages.
     *
     * @param workClassifications set of {@link WorkClassification} instance
     * @param newClassification   new classification
     */
    void insertOrUpdateClassifications(Set<WorkClassification> workClassifications, String newClassification);

    /**
     * Deletes work classifications based on theirs Wr Wrk Inst.
     *
     * @param workClassifications set of {@link WorkClassification} instance
     */
    void deleteClassifications(Set<WorkClassification> workClassifications);

    /**
     * Gets Wr Wrk Inst classification.
     *
     * @param wrWrkInst Wr Wrk Inst
     * @return wrWrkInst classification if work has classification, {@code null} otherwise
     */
    String getClassification(Long wrWrkInst);

    /**
     * Gets list of {@link WorkClassification}s by usage batches ids and search value.
     *
     * @param batchesIds  list of batches ids
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link WorkClassification}s
     */
    List<WorkClassification> getClassifications(Set<String> batchesIds, String searchValue, Pageable pageable,
                                                Sort sort);

    /**
     * Finds count of {@link WorkClassification}s by usage batches ids and search value.
     *
     * @param batchesIds  list of batches ids
     * @param searchValue search value
     * @return count of work classifications
     */
    int getClassificationCount(Set<String> batchesIds, String searchValue);

    /**
     * @return threshold value for work classifications size.
     */
    int getWorkClassificationThreshold();
}
