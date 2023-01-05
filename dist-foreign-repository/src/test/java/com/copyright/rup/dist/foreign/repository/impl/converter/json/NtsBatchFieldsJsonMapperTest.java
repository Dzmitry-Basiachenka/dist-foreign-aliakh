package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Verifies {@link NtsBatchFieldsJsonMapper}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsBatchFieldsJsonMapperTest {

    private final NtsBatchFieldsJsonMapper jsonMapper = new NtsBatchFieldsJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "nts_batch_fields.json");
        NtsFields ntsFundPool = jsonMapper.deserialize(json);
        assertEquals(2017, ntsFundPool.getFundPoolPeriodFrom().intValue());
        assertEquals(2018, ntsFundPool.getFundPoolPeriodTo().intValue());
        assertEquals(new BigDecimal("100"), ntsFundPool.getStmAmount());
        assertEquals(new BigDecimal("200."), ntsFundPool.getNonStmAmount());
        assertEquals(new BigDecimal("300.3"), ntsFundPool.getStmMinimumAmount());
        assertEquals(new BigDecimal("400.44"), ntsFundPool.getNonStmMinimumAmount());
        assertEquals(Set.of("Edu", "Gov"), ntsFundPool.getMarkets());
        assertTrue(ntsFundPool.isExcludingStm());
    }

    @Test
    public void testSerialize() throws IOException {
        NtsFields ntsFundPool = new NtsFields();
        ntsFundPool.setFundPoolPeriodFrom(2017);
        ntsFundPool.setFundPoolPeriodTo(2018);
        ntsFundPool.setStmAmount(new BigDecimal("100"));
        ntsFundPool.setNonStmAmount(new BigDecimal("200."));
        ntsFundPool.setStmMinimumAmount(new BigDecimal("300.3"));
        ntsFundPool.setNonStmMinimumAmount(new BigDecimal("400.44"));
        ntsFundPool.setMarkets(Set.of("Edu", "Gov"));
        ntsFundPool.setExcludingStm(true);
        String actualJson = jsonMapper.serialize(ntsFundPool);
        assertEquals(ntsFundPool, jsonMapper.deserialize(actualJson));
    }

    @Test
    public void testDeserializeNull() throws IOException {
        assertNull(jsonMapper.deserialize(null));
    }

    @Test
    public void testSerializeNull() throws IOException {
        assertNull(jsonMapper.serialize(null));
    }
}
