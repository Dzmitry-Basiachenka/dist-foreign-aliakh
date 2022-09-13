package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Verifies {@link PaidUsageDeserializer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/21/18
 *
 * @author Darya Baraukova
 */
public class PaidUsageDeserializerTest {

    private final PaidUsageUnmarshaller unmarshaller = new PaidUsageUnmarshaller();

    @Test
    @SuppressWarnings("unchecked")
    public void testDeserialize() throws IOException {
        try (InputStream inputStream =
                 PaidUsageDeserializerTest.class.getResourceAsStream("paid_usages_message.json")) {
            String jsonString = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            Object paidUsageMessage =
                unmarshaller.unmarshal(null, new ByteArrayInputStream(jsonString.getBytes(Charsets.UTF_8)));
            assertThat(paidUsageMessage, instanceOf(List.class));
            List<PaidUsage> paidUsages = (List<PaidUsage>) paidUsageMessage;
            assertEquals(1, CollectionUtils.size(paidUsages));
            verifyPaidUsage(paidUsages.get(0));
        }
    }

    private void verifyPaidUsage(PaidUsage paidUsage) {
        assertEquals("6039cab2-8d7d-409f-8843-3930daf6a48d", paidUsage.getId());
        assertEquals("79f75491-fb0d-431e-8e4e-5b819748e198", paidUsage.getRightsholder().getId());
        assertEquals("fc8c5ed1-a08d-48d2-84a3-5abae856bf89", paidUsage.getPayee().getId());
        assertEquals("578945", paidUsage.getCheckNumber());
        assertEquals(OffsetDateTime.parse("2017-01-15T00:00:00-05:00").toInstant(),
            paidUsage.getCheckDate().toInstant());
        assertEquals("53256", paidUsage.getCccEventId());
        assertEquals("FDA March 17", paidUsage.getDistributionName());
        assertEquals(OffsetDateTime.parse("2017-01-14T00:00:00-05:00").toInstant(),
            paidUsage.getDistributionDate().toInstant());
        assertEquals(OffsetDateTime.parse("2017-01-16T00:00:00-05:00").toInstant(),
            paidUsage.getPeriodEndDate().toInstant());
        assertEquals("47ab477a-7007-4d78-ba39-6137474a47e3", paidUsage.getLmDetailId());
        assertEquals(new BigDecimal("100.0"), paidUsage.getNetAmount());
        assertEquals(new BigDecimal("16.0"), paidUsage.getServiceFeeAmount());
        assertEquals(new BigDecimal("116.0"), paidUsage.getGrossAmount());
        assertTrue(paidUsage.getSplitParentFlag());
        assertFalse(paidUsage.isPostDistributionFlag());
    }
}
