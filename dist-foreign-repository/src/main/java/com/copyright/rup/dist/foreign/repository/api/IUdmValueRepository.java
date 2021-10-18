package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for UDM values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
public interface IUdmValueRepository {

    /**
     * Inserts UDM Value into database.
     *
     * @param value {@link UdmValue} instance
     */
    void insert(UdmValue value);

    /**
     * Finds values count based on applied filter.
     *
     * @param filter instance of {@link UdmValueFilter}
     * @return the count of values
     */
    int findCountByFilter(UdmValueFilter filter);

    /**
     * Finds list of {@link UdmValueDto}s by UDM value filter.
     *
     * @param filter   instance of {@link UdmValueFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmValueDto}
     */
    List<UdmValueDto> findDtosByFilter(UdmValueFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds list of periods from UDM value.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Updates assignee for provided values. Assignee value can be nullable.
     *
     * @param valueIds   value ids to update
     * @param assignee   assignee or null
     * @param updateUser user who updates assignee
     */
    void updateAssignee(Set<String> valueIds, String assignee, String updateUser);

    /**
     * Finds list of assignees from UDM values.
     *
     * @return list of assignees
     */
    List<String> findAssignees();

    /**
     * Finds list of last value periods from UDM values.
     *
     * @return list of last value periods
     */
    List<String> findLastValuePeriods();

    /**
     * Checks whether a value batch for given period is allowed for publishing.
     * In order to publish the entire batch, all work values must have: Pub Type, Content Unit Price.
     * And all work values must NOT be in the following statuses: NEW, RSCHD_IN_THE_PREV_PERIOD
     *
     * @param period period of value batch to check
     * @return {@code true} if value batch for given period is allowed for publishing, otherwise {@code false}
     */
    boolean isAllowedForPublishing(Integer period);

    /**
     * Publishes value batch for given period to baseline.
     *
     * @param period   period of value batch to publish
     * @param userName name of the user who publishes to baseline
     * @return count of published values
     */
    int publishToBaseline(Integer period, String userName);
}
