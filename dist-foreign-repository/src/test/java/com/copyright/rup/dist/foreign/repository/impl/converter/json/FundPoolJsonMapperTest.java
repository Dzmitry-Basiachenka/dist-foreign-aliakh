package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;

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
public class FundPoolJsonMapperTest {

    private final FundPoolJsonMapper jsonMapper = new FundPoolJsonMapper();

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "fund_pool.json");
        FundPool fundPool = jsonMapper.deserialize(json);
        assertEquals(2017, fundPool.getFundPoolPeriodFrom().intValue());
        assertEquals(2018, fundPool.getFundPoolPeriodTo().intValue());
        assertEquals(new BigDecimal("100"), fundPool.getStmAmount());
        assertEquals(new BigDecimal("200."), fundPool.getNonStmAmount());
        assertEquals(new BigDecimal("300.3"), fundPool.getStmMinimumAmount());
        assertEquals(new BigDecimal("400.44"), fundPool.getNonStmMinimumAmount());
        assertEquals(ImmutableSet.of("Edu", "Gov"), fundPool.getMarkets());
    }

    @Test
    public void testSerialize() throws IOException {
        FundPool fundPool = new FundPool();
        fundPool.setFundPoolPeriodFrom(2017);
        fundPool.setFundPoolPeriodTo(2018);
        fundPool.setStmAmount(new BigDecimal("100"));
        fundPool.setNonStmAmount(new BigDecimal("200."));
        fundPool.setStmMinimumAmount(new BigDecimal("300.3"));
        fundPool.setNonStmMinimumAmount(new BigDecimal("400.44"));
        fundPool.setMarkets(ImmutableSet.of("Edu", "Gov"));
        String actualJson = jsonMapper.serialize(fundPool);
        assertEquals(fundPool, jsonMapper.deserialize(actualJson));
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
