package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for UDM usages.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmUsageRepository {

    /**
     * Inserts UDM usage.
     *
     * @param udmUsage instance of {@link UdmUsage}
     */
    void insert(UdmUsage udmUsage);

    /**
     * Checks whether UDM usage with provided original detail id exists.
     *
     * @param originalDetailId original detail id
     * @return {@code true} if UDM usage with provided original detail id exists, otherwise {@code false}
     */
    boolean isOriginalDetailIdExist(String originalDetailId);

    /**
     * Finds list of {@link UdmUsageDto}s by UDM usage filter.
     *
     * @param filter   instance of {@link UdmUsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmUsageDto}
     */
    List<UdmUsageDto> findDtosByFilter(UdmUsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds UDM usages count based on applied filter.
     *
     * @param filter instance of {@link UdmUsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UdmUsageFilter filter);

    /**
     * Finds {@link UdmUsage}s by their ids.
     *
     * @param udmUsagesIds list of ids of the {@link UdmUsage}
     * @return list of {@link UdmUsage} with the given id or {@code null} if none exists
     */
    List<UdmUsage> findByIds(List<String> udmUsagesIds);

    /**
     * Updates given {@link UdmUsage} in case of its version is the same as in database.
     *
     * @param udmUsage {@link UdmUsage} to update
     * @return id of updated record, otherwise {@code null}
     */
    String updateProcessedUsage(UdmUsage udmUsage);

    /**
     * Finds list of {@link UdmUsage} ids by specified {@link UsageStatusEnum}.
     *
     * @param status {@link UsageStatusEnum} instance
     * @return the list of found {@link UdmUsage} ids
     */
    List<String> findIdsByStatus(UsageStatusEnum status);

    /**
     * @return list of assignees from UDM usages.
     */
    List<String> findAssignees();

    /**
     * @return list of publication types from UDM usages.
     */
    List<String> findPublicationTypes();

    /**
     * @return list of publication formats from UDM usages.
     */
    List<String> findPublicationFormats();

    /**
     * Updates status of {@link UdmUsage}s by their ids.
     *
     * @param udmUsageIds set of ids of the {@link UdmUsage}
     * @param status      instance of {@link UsageStatusEnum}
     */
    void updateStatusByIds(Set<String> udmUsageIds, UsageStatusEnum status);

    /**
     * Finds list of {@link UdmActionReason}.
     *
     * @return list of {@link UdmActionReason}
     */
    List<UdmActionReason> findActionReasons();

    /**
     * Finds list of {@link UdmIneligibleReason}.
     *
     * @return list of {@link UdmIneligibleReason}
     */
    List<UdmIneligibleReason> findIneligibleReasons();

    /**
     * Finds {@link UdmIneligibleReason} by id.
     *
     * @param id ineligible reason id
     * @return instance of {@link UdmIneligibleReason}
     */
    UdmIneligibleReason findIneligibleReasonById(String id);

    /**
     * Deletes all {@link UdmUsage}s from the batch with given id.
     *
     * @param udmBatchId {@link com.copyright.rup.dist.foreign.domain.UdmBatch} id
     */
    void deleteByBatchId(String udmBatchId);

    /**
     * Updates assignee for provided usages. Value to assign can be nullable.
     *
     * @param udmUsageIds usage ids to update
     * @param assignee    assignee or null
     * @param updateUser  user who updates assignee
     */
    void updateAssignee(Set<String> udmUsageIds, String assignee, String updateUser);
}
