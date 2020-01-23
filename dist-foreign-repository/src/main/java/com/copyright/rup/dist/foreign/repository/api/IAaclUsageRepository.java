package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;

/**
 * Interface for AACL usage repository.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclUsageRepository {

    /**
     * Inserts AACL usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Deletes AACL {@link Usage} by given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Finds list of AACL {@link Usage}s by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds count of referenced usages in the df_usage_aacl table by ids.
     *
     * @param usageIds set of usage ids
     * @return the count of usages
     */
    int findReferencedAaclUsagesCountByIds(String... usageIds);
}
