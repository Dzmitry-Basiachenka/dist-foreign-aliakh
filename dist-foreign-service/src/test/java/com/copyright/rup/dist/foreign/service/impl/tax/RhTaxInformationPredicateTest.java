package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.RhTaxInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link RhTaxInformationPredicate}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/22/20
 *
 * @author Stanislau Rudak
 */
@RunWith(Parameterized.class)
public class RhTaxInformationPredicateTest {

    private static final int MAX_NOTIFICATIONS_SENT = 3;

    private final boolean expectedResult;
    private final RhTaxInformation rhTaxInformation;
    private final Integer numberOfDays;

    public RhTaxInformationPredicateTest(boolean expectedResult, RhTaxInformation rhTaxInformation,
                                         Integer numberOfDays) {
        this.expectedResult = expectedResult;
        this.rhTaxInformation = rhTaxInformation;
        this.numberOfDays = numberOfDays;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        int numberOfDays = 15;
        OffsetDateTime outOfRangeDate = OffsetDateTime.now().minusDays(numberOfDays + 1);
        OffsetDateTime inRangeDate = OffsetDateTime.now().minusDays(numberOfDays - 1);
        Integer notificationsMoreThanMax = MAX_NOTIFICATIONS_SENT + 1;
        Integer notificationsLessThanMax = MAX_NOTIFICATIONS_SENT - 1;
        return List.of(new Object[][]{
            {true, buildRhTaxInformation(null, null), numberOfDays},
            {true, buildRhTaxInformation(outOfRangeDate, null), numberOfDays},
            {false, buildRhTaxInformation(inRangeDate, null), numberOfDays},
            {false, buildRhTaxInformation(outOfRangeDate, notificationsMoreThanMax), numberOfDays},
            {true, buildRhTaxInformation(outOfRangeDate, notificationsLessThanMax), numberOfDays}
        });
    }

    private static RhTaxInformation buildRhTaxInformation(OffsetDateTime dateOfLastNotification,
                                                          Integer notificationsSent) {
        RhTaxInformation rhTaxInformation = new RhTaxInformation();
        rhTaxInformation.setDateOfLastNotificationSent(dateOfLastNotification);
        rhTaxInformation.setNotificationsSent(notificationsSent);
        return rhTaxInformation;
    }

    @Test
    public void testApply() {
        assertEquals(expectedResult, new RhTaxInformationPredicate(numberOfDays).test(rhTaxInformation));
    }
}
