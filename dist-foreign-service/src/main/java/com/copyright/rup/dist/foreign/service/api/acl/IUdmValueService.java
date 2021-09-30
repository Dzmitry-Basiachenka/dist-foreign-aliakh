package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents interface of service for UDM value logic.
 * <p>
 * Copyright (C) 20121 copyright.com
 * <p>
 * Date: 09/15/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmValueService {

    /**
     * Gets map of currency codes to currency names.
     *
     * @return map of currency codes to currency names
     */
    Map<String, String> getCurrencyCodesToCurrencyNamesMap();

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
     * Populates value batch for provided period. Gets list of values from usages baseline for given period
     * and sends them to RMS for getting grants. Inserts granted values into df_udm_values table.
     *
     * @param period period of value batch to populate
     * @return count of populated values
     */
    int populateValueBatch(Integer period);
}
