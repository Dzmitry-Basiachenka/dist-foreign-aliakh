package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of service for UDM value logic.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/15/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmValueService {

    /**
     * Updates UDM value.
     *
     * @param udmValueDto {@link UdmValueDto} to update
     */
    void updateValue(UdmValueDto udmValueDto);

    /**
     * Gets list of all currencies.
     *
     * @return list of {@link Currency}
     */
    List<Currency> getAllCurrencies();

    /**
     * Gets all available periods for value.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of {@link UdmValueDto}s based on applied filter.
     *
     * @param filter   instance of {@link UdmValueFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmValueDto}s
     */
    List<UdmValueDto> getValueDtos(UdmValueFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets UDM values count based on applied filter.
     *
     * @param filter instance of {@link UdmValueFilter}.
     * @return count of values
     */
    int getValueCount(UdmValueFilter filter);

    /**
     * Assigns provided values to logged in user.
     *
     * @param valueIds set of value ids to assign to logged in user
     */
    void assignValues(Set<String> valueIds);

    /**
     * Un-assigns provided values.
     *
     * @param valueIds set of value ids to un-assign
     */
    void unassignValues(Set<String> valueIds);

    /**
     * Gets list of assignees from UDM values.
     *
     * @return list of assignees
     */
    List<String> getAssignees();

    /**
     * Gets list of last value periods from UDM values.
     *
     * @return list of last value periods
     */
    List<String> getLastValuePeriods();

    /**
     * Populates value batch for provided period. Gets list of values from usages baseline for given period
     * and sends them to RMS for getting grants. Inserts granted values into df_udm_values table.
     *
     * @param period period of value batch to populate
     * @return count of populated values
     */
    int populateValueBatch(Integer period);

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
     * @return threshold value for size of UDM records.
     */
    int getUdmRecordThreshold();

    /**
     * Publishes value batch for given period to baseline.
     *
     * @param period period of value batch to publish
     * @return count of newly published records
     */
    int publishToBaseline(Integer period);
}
