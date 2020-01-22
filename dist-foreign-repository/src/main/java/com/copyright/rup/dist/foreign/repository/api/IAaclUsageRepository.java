package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

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

    /**
     * Finds list of AACL {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds AACL usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);
}
