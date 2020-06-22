package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.dist.foreign.domain.RhTaxInformation;

import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Predicate to filter {@link RhTaxInformation} for tax notification reports.
 * {@link #test(RhTaxInformation)} returns true
 * if RhTaxInformation satisfies all conditions:
 * <ul>
 * <li>{@link RhTaxInformation#getNotificationsSent()}
 * is null or outside the
 * range [current date - number of days from UI, current date]</li>
 * <li>{@link RhTaxInformation#getNotificationsSent()}
 * is null or equal or less than
 * {@link #MAX_NOTIFICATIONS_SENT}.</li>
 * </ul>
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/19/20
 *
 * @author Stanislau Rudak
 */
class RhTaxInformationPredicate implements Predicate<RhTaxInformation> {

    private static final int MAX_NOTIFICATIONS_SENT = 3;

    private final int numberOfDays;
    private final OffsetDateTime currentDate;

    /**
     * Constructor.
     *
     * @param numberOfDays min number of days between current date and the date last notification was sent.
     */
    RhTaxInformationPredicate(int numberOfDays) {
        this.numberOfDays = numberOfDays;
        this.currentDate = OffsetDateTime.now();
    }

    @Override
    public boolean test(RhTaxInformation input) {
        return checkLastNotificationSent(input.getDateOfLastNotificationSent()) &&
            checkNumberOfLetters(input.getNotificationsSent());
    }

    private boolean checkLastNotificationSent(OffsetDateTime lastNotificationSent) {
        return Objects.isNull(lastNotificationSent)
            || lastNotificationSent.isBefore(currentDate.minus(Period.ofDays(numberOfDays)));
    }

    private boolean checkNumberOfLetters(Integer notificationsSent) {
        return Objects.isNull(notificationsSent) || notificationsSent <= MAX_NOTIFICATIONS_SENT;
    }
}
