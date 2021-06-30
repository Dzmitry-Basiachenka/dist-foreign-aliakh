package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import java.util.List;

/**
 * Represents interface of service for UDM usages business logic.
 * <p>
 * Copyright (C) 20121 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmUsageService {

    /**
     * Inserts UDM usages.
     *
     * @param udmBatch  {@link UdmBatch} instance
     * @param udmUsages list of {@link UdmUsage}s
     */
    void insertUdmUsages(UdmBatch udmBatch, List<UdmUsage> udmUsages);

    /**
     * Checks whether UDM usage with provided original detail id exists.
     *
     * @param originalDetailId original detail id
     * @return {@code true} if UDM usage with provided original detail id exists, otherwise {@code false}
     */
    boolean isOriginalDetailIdExist(String originalDetailId);

    /**
     * Gets list of {@link UdmUsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UdmUsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmUsageDto}s
     */
    List<UdmUsageDto> getUsageDtos(UdmUsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets UDM usages count based on applied filter.
     *
     * @param filter instance of {@link UdmUsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UdmUsageFilter filter);

    /**
     * Sends list of UDM usages on queue for PI matching process.
     *
     * @param udmUsages list of {@link UdmUsage} to be sent
     */
    void sendForMatching(List<UdmUsage> udmUsages);

    /**
     * Updates {@link UdmUsage} and verifies that version of {@link UdmUsage} is the same as in database.
     * Throws an {@link com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException} in case of
     * there are {@link UdmUsage} version discrepancies.
     *
     * @param usage {@link UdmUsage} to update
     */
    void updateProcessedUsage(UdmUsage usage);

    /**
     * Gets list of {@link UdmUsage} ids by specified {@link UsageStatusEnum}.
     *
     * @param status {@link UsageStatusEnum} instance
     * @return the list of found {@link UdmUsage} ids
     */
    List<String> getUdmUsageIdsByStatus(UsageStatusEnum status);

    /**
     * Gets list of {@link UdmUsage}s by specified {@link UdmUsage} ids.
     *
     * @param udmUsageIds list of {@link UdmUsage} ids
     * @return list of {@link UdmUsage}s
     */
    List<UdmUsage> getUdmUsagesByIds(List<String> udmUsageIds);

    /**
     * @return list of assignees from UDM usages.
     */
    List<String> getAssignees();

    /**
     * @return list of publication types from UDM usages.
     */
    List<String> getPublicationTypes();

    /**
     * @return list of publication formats from UDM usages.
     */
    List<String> getPublicationFormats();

    /**
     * Gets list of {@link UdmActionReason}.
     *
     * @return list of {@link UdmActionReason}
     */
    List<UdmActionReason> getActionReasons();

    /**
     * Gets list of {@link UdmIneligibleReason}.
     *
     * @return list of {@link UdmIneligibleReason}
     */
    List<UdmIneligibleReason> getIneligibleReasons();
}
