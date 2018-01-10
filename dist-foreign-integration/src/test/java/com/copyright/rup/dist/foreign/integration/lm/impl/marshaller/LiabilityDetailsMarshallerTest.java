package com.copyright.rup.dist.foreign.integration.lm.impl.marshaller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link LiabilityDetailsMarshaller}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class LiabilityDetailsMarshallerTest {

    private LiabilityDetailsMarshaller liabilityDetailsMarshaller;

    @Before
    public void setUp() {
        liabilityDetailsMarshaller = new LiabilityDetailsMarshaller();
    }

    @Test
    public void testGetObjectMapper() {
        assertNotNull(liabilityDetailsMarshaller.getObjectMapper());
    }

    @Test
    public void testGetTypeReference() {
        assertEquals(LiabilityDetailsMarshaller.TYPE_REFERENCE, liabilityDetailsMarshaller.getTypeReference());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmarshal() throws Exception {
        liabilityDetailsMarshaller.unmarshal(null, null);
    }
}
