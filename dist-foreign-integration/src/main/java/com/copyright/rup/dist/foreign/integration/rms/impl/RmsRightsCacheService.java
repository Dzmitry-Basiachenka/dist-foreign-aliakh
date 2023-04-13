package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.prm.AbstractMultipleCacheService;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;
import com.copyright.rup.dist.foreign.integration.rms.impl.RmsRightsCacheService.RmsGrantKey;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IRmsRightsService} that uses cache to avoid redundant calls to RMS.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 10/04/2018
 *
 * @author Uladzislau Shalamitski
 */
public class RmsRightsCacheService extends AbstractMultipleCacheService<RmsGrantKey, Set<RmsGrant>>
    implements IRmsRightsService {

    private final IRmsRightsService rmsRightsService;
    private final boolean cacheEnabled;

    /**
     * Constructor.
     *
     * @param rmsRightsService instance of {@link IRmsRightsService}
     * @param timeToLive       time to keep cached grants in minutes
     * @param cacheEnabled     {@code true} if the cache is enabled, {@code false} if the cache is disabled
     */
    RmsRightsCacheService(IRmsRightsService rmsRightsService, int timeToLive, boolean cacheEnabled) {
        this.rmsRightsService = rmsRightsService;
        this.cacheEnabled = cacheEnabled;
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(timeToLive));
        logParams();
    }

    @Override
    public Set<RmsGrant> getGrants(List<Long> wrWrkInsts, LocalDate periodEndDate, Set<String> statuses,
                                   Set<String> typeOfUses, Set<String> licenseTypes) {
        if (cacheEnabled) {
            return getDataFromCache(wrWrkInsts.stream()
                .map(wrWrkInst -> new RmsGrantKey(wrWrkInst, periodEndDate, statuses, typeOfUses, licenseTypes))
                .collect(Collectors.toSet()))
                .values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        } else {
            return getGrantsWithoutCache(wrWrkInsts, periodEndDate, statuses, typeOfUses, licenseTypes);
        }
    }

    @Override
    public Map<RmsGrantKey, Set<RmsGrant>> loadData(Set<RmsGrantKey> grantKeys) {
        RmsGrantKey rmsGrantKey = grantKeys.stream().findAny().get();
        List<Long> wrWrkInsts = grantKeys.stream()
            .map(RmsGrantKey::getWrWrkInst)
            .collect(Collectors.toList());
        Set<RmsGrant> rmsGrants = getGrantsWithoutCache(wrWrkInsts, rmsGrantKey.getPeriodEndDate(),
            rmsGrantKey.getStatuses(), rmsGrantKey.getTypeOfUses(), rmsGrantKey.getLicenseTypes());
        return grantKeys.stream()
            .collect(Collectors.toMap(Function.identity(), grantKey -> rmsGrants.stream()
                .filter(rmsGrant -> rmsGrant.getWrWrkInst().equals(grantKey.getWrWrkInst()))
                .collect(Collectors.toSet())));
    }

    @Override
    protected Set<RmsGrant> loadData(RmsGrantKey key) {
        return null;
    }

    private void logParams() {
        getLogger().info("RMS rights cache service. ExpirationTime={}, CacheEnabled={}",
            this.getExpirationTime(), this.cacheEnabled);
    }

    private Set<RmsGrant> getGrantsWithoutCache(List<Long> wrWrkInsts, LocalDate periodEndDate, Set<String> statuses,
                                                Set<String> typeOfUses, Set<String> licenseTypes) {
        return rmsRightsService.getGrants(wrWrkInsts, periodEndDate, statuses, typeOfUses, licenseTypes);
    }

    /**
     * Key for RMS grant caching.
     */
    static class RmsGrantKey {

        private final Long wrWrkInst;
        private final LocalDate periodEndDate;
        private final Set<String> statuses;
        private final Set<String> typeOfUses;
        private final Set<String> licenseTypes;

        /**
         * Constructor.
         *
         * @param wrWrkInst     wrWrkInst
         * @param periodEndDate period end date
         * @param statuses      set of statuses
         * @param typeOfUses    set of type of uses
         * @param licenseTypes  set of license types
         */
        RmsGrantKey(Long wrWrkInst, LocalDate periodEndDate, Set<String> statuses, Set<String> typeOfUses,
                    Set<String> licenseTypes) {
            this.wrWrkInst = wrWrkInst;
            this.periodEndDate = periodEndDate;
            this.statuses = statuses;
            this.typeOfUses = typeOfUses;
            this.licenseTypes = licenseTypes;
        }

        Long getWrWrkInst() {
            return wrWrkInst;
        }

        LocalDate getPeriodEndDate() {
            return periodEndDate;
        }

        Set<String> getStatuses() {
            return statuses;
        }

        Set<String> getTypeOfUses() {
            return typeOfUses;
        }

        Set<String> getLicenseTypes() {
            return licenseTypes;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            RmsGrantKey that = (RmsGrantKey) obj;
            return new EqualsBuilder()
                .append(wrWrkInst, that.wrWrkInst)
                .append(periodEndDate, that.periodEndDate)
                .append(statuses, that.statuses)
                .append(typeOfUses, that.typeOfUses)
                .append(licenseTypes, that.licenseTypes)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(wrWrkInst)
                .append(periodEndDate)
                .append(statuses)
                .append(typeOfUses)
                .append(licenseTypes)
                .toHashCode();
        }
    }
}
