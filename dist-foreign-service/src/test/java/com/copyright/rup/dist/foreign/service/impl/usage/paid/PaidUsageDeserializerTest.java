package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    public void testDeserialize() {
        assertDeserialize("paid_usages_message.json");
    }

    @Test
    public void testDeserializeWithIsoDates() {
        assertDeserialize("paid_usages_message_iso_dates.json");
    }

    @SuppressWarnings("unchecked")
    private void assertDeserialize(String filename) {
        try (InputStream inputStream = PaidUsageDeserializerTest.class.getResourceAsStream(filename)) {
            String jsonString = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            Object paidUsageMessage =
                unmarshaller.unmarshal(null, new ByteArrayInputStream(jsonString.getBytes(Charsets.UTF_8)));
            assertTrue(paidUsageMessage instanceof List);
            List<PaidUsage> usages = (List<PaidUsage>) paidUsageMessage;
            assertTrue(CollectionUtils.isNotEmpty(usages));
            assertEquals(1, usages.size());
            PaidUsage usage = usages.get(0);
            assertEquals("6039cab2-8d7d-409f-8843-3930daf6a48d", usage.getId());
            assertEquals(1000010022L, usage.getRightsholder().getAccountNumber(), 0);
            assertEquals(1000002859L, usage.getPayee().getAccountNumber(), 0);
            assertEquals("578945", usage.getCheckNumber());
            assertEquals(OffsetDateTime.parse("2017-01-15T00:00:00-05:00").toInstant(),
                usage.getCheckDate().toInstant());
            assertEquals("53256", usage.getCccEventId());
            assertEquals("FDA March 17", usage.getDistributionName());
            assertEquals(OffsetDateTime.parse("2017-01-14T00:00:00-05:00").toInstant(),
                usage.getDistributionDate().toInstant());
            assertEquals(OffsetDateTime.parse("2017-01-16T00:00:00-05:00").toInstant(),
                usage.getPeriodEndDate().toInstant());
            assertEquals("47ab477a-7007-4d78-ba39-6137474a47e3", usage.getLmDetailId());
            assertEquals(new BigDecimal("100.0"), usage.getNetAmount());
            assertEquals(new BigDecimal("16.0"), usage.getServiceFeeAmount());
            assertEquals(new BigDecimal("116.0"), usage.getGrossAmount());
            assertTrue(usage.getSplitParentFlag());
            assertFalse(usage.getPostDistributionFlag());
        } catch (IOException e) {
            fail();
        }
    }
}
