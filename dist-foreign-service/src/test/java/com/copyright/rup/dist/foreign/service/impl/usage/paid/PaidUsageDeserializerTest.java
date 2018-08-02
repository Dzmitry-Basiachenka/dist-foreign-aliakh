package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.PaidUsage;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.TimeZone;

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

    @Before
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
    }

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
            assertEquals(1000002859, usage.getPayee().getAccountNumber(), 0);
            assertEquals("578945", usage.getCheckNumber());
            assertEquals(OffsetDateTime.of(2017, 1, 15, 5, 0, 0, 0, ZoneOffset.ofHours(0)).toInstant(),
                usage.getCheckDate().toInstant());
            assertEquals("53256", usage.getCccEventId());
            assertEquals("FDA March 17", usage.getDistributionName());
            assertEquals(OffsetDateTime.of(2017, 1, 14, 5, 0, 0, 0, ZoneOffset.ofHours(0)).toInstant(),
                usage.getDistributionDate().toInstant());
            assertEquals(LocalDate.of(2017, 1, 16), usage.getPeriodEndDate());
        } catch (IOException e) {
            fail();
        }
    }
}
