package com.copyright.rup.dist.foreign.service.impl;

import static java.util.Objects.requireNonNull;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IRmsGrantsService;

import com.google.common.collect.Maps;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IRmsGrantsService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
@Service
public class RmsGrantsService implements IRmsGrantsService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IRmsIntegrationService rmsIntegrationService;

    @Override
    @Profiled(tag = "service.RmsGrantsService.getAccountNumbersByWrWrkInsts")
    public Map<Long, Long> getAccountNumbersByWrWrkInsts(List<Long> wrWrkInsts) {
        Set<RmsGrant> allRmsGrants = rmsIntegrationService.getAllRmsGrants(wrWrkInsts);
        LOGGER.info("Get account numbers from RMS. Started. WrWrkInstsCount={}, RmsGrantsCount={}", wrWrkInsts.size(),
            allRmsGrants.size());
        Map<Long, List<RmsGrant>> wrWrkInstToRmsGrants = allRmsGrants
            .stream()
            .collect(Collectors.groupingBy(RmsGrant::getWrWrkInst));
        Map<Long, Long> wrWrkInstToAccountNumber = Maps.newHashMapWithExpectedSize(wrWrkInstToRmsGrants.size());
        wrWrkInstToRmsGrants.keySet().forEach(wrWrkInst -> {
            Optional<RmsGrant> rmsGrantOpt = findRmsGrantByPriority(wrWrkInstToRmsGrants.get(wrWrkInst));
            if (rmsGrantOpt.isPresent()) {
                RmsGrant rmsGrant = rmsGrantOpt.get();
                long rhAccountNumber = rmsGrant.getWorkGroupOwnerOrgNumber().longValueExact();
                LOGGER.debug("Get account numbers from RMS. Found account number. WrWrkInst={}, RhAccount#={}",
                    wrWrkInst, rhAccountNumber);
                wrWrkInstToAccountNumber.put(wrWrkInst, rhAccountNumber);
            } else {
                LOGGER.debug("Get account numbers from RMS. Not found account number. WrWrkInst={}", wrWrkInst);
            }
        });
        LOGGER.info("Get account numbers from RMS. Finished. WrWrkInstsCount={}, RhAccount#Count={}",
            wrWrkInsts.size(), wrWrkInstToAccountNumber.size());
        return wrWrkInstToAccountNumber;
    }

    private Optional<RmsGrant> findRmsGrantByPriority(List<RmsGrant> rmsGrants) {
        return rmsGrants.stream()
            .map(RmsGrantAttributes::lookupByRmsGrant)
            .filter(Objects::nonNull)
            .sorted(Comparator.comparing(RmsGrantPriority::getPriority))
            .map(RmsGrantPriority::getRmsGrant)
            .findFirst();
    }

    private enum RmsGrantAttributes {

        CORPORATE_TRANSACTIONAL_PHOTOCOPY(Constants.EXTERNAL, "CORPORATE", "NGT_PHOTOCOPY"),
        CORPORATE_REPERTORY_PRINT(Constants.INTERNAL, "CORPORATE", "PRINT"),
        CORPORATE_REPERTORY_DIGITAL(Constants.INTERNAL, "CORPORATE", "DIGITAL"),
        ACADEMIC_ANNUAL_PRINT(Constants.INTERNAL, "ACADEMIC", "PRINT"),
        ACADEMIC_ANNUAL_DIGITAL(Constants.INTERNAL, "ACADEMIC", "DIGITAL");

        private final String distribution;
        private final String market;
        private final String typeOfUse;

        RmsGrantAttributes(String distribution, String market, String typeOfUse) {
            this.distribution = requireNonNull(distribution);
            this.market = requireNonNull(market);
            this.typeOfUse = requireNonNull(typeOfUse);
        }

        static RmsGrantPriority lookupByRmsGrant(RmsGrant rmsGrant) {
            for (RmsGrantAttributes value : RmsGrantAttributes.values()) {
                if (value.isSuitableRmsGrant(rmsGrant)) {
                    return new RmsGrantPriority(rmsGrant, value.ordinal());
                }
            }
            return null;
        }

        private boolean isSuitableRmsGrant(RmsGrant rmsGrant) {
            return distribution.equalsIgnoreCase(rmsGrant.getDistribution())
                && market.equalsIgnoreCase(rmsGrant.getMarket())
                && typeOfUse.equalsIgnoreCase(rmsGrant.getTypeOfUse());
        }

        private static class Constants {

            private static final String EXTERNAL = "EXTERNAL";
            private static final String INTERNAL = "INTERNAL";
        }
    }

    private static class RmsGrantPriority {

        private final RmsGrant rmsGrant;
        private final int priority;

        RmsGrantPriority(RmsGrant rmsGrant, int priority) {
            this.rmsGrant = rmsGrant;
            this.priority = priority;
        }

        RmsGrant getRmsGrant() {
            return rmsGrant;
        }

        int getPriority() {
            return priority;
        }
    }
}
