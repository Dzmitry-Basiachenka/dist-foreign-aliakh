package com.copyright.rup.dist.foreign.vui.usage.api.nts;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import com.vaadin.flow.data.provider.QuerySortOrder;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Controller for works classification.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/14/2019
 *
 * @author Ihar Suvorau
 */
public interface IWorkClassificationController {

    /**
     * Gets count of {@link WorkClassification}s by usage batches ids and search value.
     *
     * @param batchesIds  set of batches ids
     * @param searchValue search value
     * @return count of work {@link WorkClassification}s
     */
    int getClassificationCount(Set<String> batchesIds, String searchValue);

    /**
     * @return threshold value for work classifications size.
     */
    int getWorkClassificationThreshold();

    /**
     * Gets list of {@link WorkClassification}s by usage batches ids and search value.
     *
     * @param batchesIds  set of batches ids
     * @param searchValue search value
     * @param startIndex  start index
     * @param count       count
     * @param sortOrders  set of {@link QuerySortOrder}s
     * @return list of {@link WorkClassification}s
     */
    List<WorkClassification> getClassifications(Set<String> batchesIds, String searchValue, int startIndex, int count,
                                                List<QuerySortOrder> sortOrders);

    /**
     * Inserts new and updates existing works classifications.
     *
     * @param classifications   set of {@link WorkClassification}s to be updated
     * @param newClassification new classification
     */
    void updateClassifications(Set<WorkClassification> classifications, String newClassification);

    /**
     * Deletes all classifications by its Wr Wrk Insts.
     *
     * @param classifications set of {@link WorkClassification}s to be deleted
     */
    void deleteClassification(Set<WorkClassification> classifications);

    /**
     * Gets count of usages to be updated based on classification.
     *
     * @param classifications set of {@link WorkClassification}
     * @return count of usages
     */
    int getCountToUpdate(Set<WorkClassification> classifications);

    /**
     * Returns instance of {@link IStreamSource} with works classification.
     *
     * @param batchesIds          set of batches ids
     * @param searchValueSupplier search value supplier
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getExportWorkClassificationStreamSource(Set<String> batchesIds, Supplier<String> searchValueSupplier);
}
