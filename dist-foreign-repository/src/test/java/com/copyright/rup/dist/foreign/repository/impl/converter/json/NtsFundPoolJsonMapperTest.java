package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.NtsFundPool;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.collect.ImmutableSet;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Verifies {@link FundPoolJsonMapper}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/03/2018
 *
 * @author Aliaksandr Liakh
 */
public class NtsFundPoolJsonMapperTest {

    private final FundPoolJsonMapper jsonMapper = new FundPoolJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "fund_pool.json");
        NtsFundPool ntsFundPool = jsonMapper.deserialize(json);
        assertEquals(2017, ntsFundPool.getFundPoolPeriodFrom().intValue());
        assertEquals(2018, ntsFundPool.getFundPoolPeriodTo().intValue());
        assertEquals(new BigDecimal("100"), ntsFundPool.getStmAmount());
        assertEquals(new BigDecimal("200."), ntsFundPool.getNonStmAmount());
        assertEquals(new BigDecimal("300.3"), ntsFundPool.getStmMinimumAmount());
        assertEquals(new BigDecimal("400.44"), ntsFundPool.getNonStmMinimumAmount());
        assertEquals(ImmutableSet.of("Edu", "Gov"), ntsFundPool.getMarkets());
        assertTrue(ntsFundPool.isExcludingStm());
    }

    @Test
    public void testSerialize() throws IOException {
        NtsFundPool ntsFundPool = new NtsFundPool();
        ntsFundPool.setFundPoolPeriodFrom(2017);
        ntsFundPool.setFundPoolPeriodTo(2018);
        ntsFundPool.setStmAmount(new BigDecimal("100"));
        ntsFundPool.setNonStmAmount(new BigDecimal("200."));
        ntsFundPool.setStmMinimumAmount(new BigDecimal("300.3"));
        ntsFundPool.setNonStmMinimumAmount(new BigDecimal("400.44"));
        ntsFundPool.setMarkets(ImmutableSet.of("Edu", "Gov"));
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

    @Test(expected = JsonParseException.class)
    public void testDeserializeException() throws IOException {
        jsonMapper.deserialize("{wrong JSON}");
    }
}
