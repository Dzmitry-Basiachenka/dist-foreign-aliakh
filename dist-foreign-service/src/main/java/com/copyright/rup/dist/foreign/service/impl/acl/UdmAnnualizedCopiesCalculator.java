package com.copyright.rup.dist.foreign.service.impl.acl;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Calculator of annualized copies for UDM usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/19/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
public class UdmAnnualizedCopiesCalculator {

    private static final Set<String> EMAIL_TOU = ImmutableSet.of("EMAIL_COPY", "SHARE_SINGLE_ELECTRONIC_COPY",
        "SUBMIT_ELECTRONIC_COPY");
    private static final Set<String> INTRANET_TOU = ImmutableSet.of("DISPLAY_IN_POWERPOINT",
        "DISTRIBUTE_IN_POWERPOINT", "STORE_COPY", "DIGITAL_SHARING_OTHER");

    @Value("$RUP{dist.foreign.udm.capped_quantity}")
    private int cappedQuantity;
    @Value("$RUP{dist.foreign.udm.email_capped_quantity}")
    private BigDecimal emailCappedQuantity;
    @Value("$RUP{dist.foreign.udm.max_email_annualized_quantity}")
    private BigDecimal maxEmailAnnualizedQuantity;

    /**
     * Calculates annualized copies.
     *
     * @param reportedTypeOfUse     reported type of use
     * @param quantity              quantity
     * @param annualMultiplier      annual multiplier
     * @param statisticalMultiplier statistical multiplier
     * @return annualized copies
     */
    public BigDecimal calculate(String reportedTypeOfUse, Integer quantity, Integer annualMultiplier,
                                BigDecimal statisticalMultiplier) {
        if (EMAIL_TOU.contains(reportedTypeOfUse)) {
            BigDecimal statisticalQuantity = new BigDecimal(quantity).multiply(statisticalMultiplier);
            BigDecimal annualizedCopies = statisticalQuantity.multiply(new BigDecimal(annualMultiplier));
            if (statisticalQuantity.compareTo(emailCappedQuantity) > 0) {
                return annualizedCopies.setScale(5, BigDecimal.ROUND_HALF_UP);
            } else {
                return annualizedCopies.min(maxEmailAnnualizedQuantity).setScale(5, BigDecimal.ROUND_HALF_UP);
            }
        } else if (INTRANET_TOU.contains(reportedTypeOfUse)) {
            return new BigDecimal(Math.min(quantity, cappedQuantity) * annualMultiplier)
                .multiply(statisticalMultiplier).setScale(5, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(quantity * annualMultiplier)
                .multiply(statisticalMultiplier).setScale(5, BigDecimal.ROUND_HALF_UP);
        }
    }
}
