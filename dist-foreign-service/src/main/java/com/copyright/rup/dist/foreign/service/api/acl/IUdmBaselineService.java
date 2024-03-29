package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents interface of service for UDM baseline business logic.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineService {

    /**
     * Gets list of {@link UdmBaselineDto}s based on applied filter.
     *
     * @param filter   instance of {@link UdmBaselineFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmBaselineDto}s
     */
    List<UdmBaselineDto> getBaselineUsageDtos(UdmBaselineFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets UDM baseline usages count based on applied filter.
     *
     * @param filter instance of {@link UdmBaselineFilter}.
     * @return count of usages
     */
    int getBaselineUsagesCount(UdmBaselineFilter filter);

    /**
     * Gets all available baseline periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * @return threshold value for size of UDM records.
     */
    int getUdmRecordThreshold();

    /**
     * Gets map of wrWrkInsts to system titles.
     *
     * @param periods set of periods
     * @return map of wrWrkInsts to system titles
     */
    Map<Long, String> getWrWrkInstToSystemTitles(Set<Integer> periods);

    /**
     * Deletes selected usages from baseline by their ids.
     *
     * @param usageIds usage ids to delete
     * @param reason   reason
     * @param userName user name
     */
    void deleteFromBaseline(Set<String> usageIds, String reason, String userName);
}
