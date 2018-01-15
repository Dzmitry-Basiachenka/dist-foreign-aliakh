package com.copyright.rup.dist.foreign.integration.lm.impl.marshaller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ExternalUsageMarshaller}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageMarshallerTest {

    private ExternalUsageMarshaller externalUsageMarshaller;

    @Before
    public void setUp() {
        externalUsageMarshaller = new ExternalUsageMarshaller();
    }

    @Test
    public void testGetObjectMapper() {
        assertNotNull(externalUsageMarshaller.getObjectMapper());
    }

    @Test
    public void testGetTypeReference() {
        assertEquals(ExternalUsageMarshaller.TYPE_REFERENCE, externalUsageMarshaller.getTypeReference());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmarshal() throws Exception {
        externalUsageMarshaller.unmarshal(null, null);
    }
}
