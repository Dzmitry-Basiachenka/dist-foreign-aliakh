package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Usage;

import com.google.common.collect.Sets;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link DetailIdValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/20/17
 *
 * @author Aliaksei Pchelnikau
 */
public class DetailIdValidatorTest {

    @Test
    public void testIsValid() {
        DetailIdValidator validator = new DetailIdValidator(Sets.newHashSet(1L));
        Usage usage = new Usage();
        usage.setDetailId(1L);
        assertFalse(validator.isValid(usage));
        usage.setDetailId(2L);
        assertTrue(validator.isValid(usage));
    }

    @Test
    public void testIsNotValid() {
        DetailIdValidator validator = new DetailIdValidator(Collections.emptySet());
        Usage usage = new Usage();
        usage.setDetailId(1L);
        assertTrue(validator.isValid(usage));
        usage.setDetailId(2L);
        assertTrue(validator.isValid(usage));
    }

    @Test
    public void getErrorMessage() {
        assertEquals("Detail ID: Detail with such ID already exists",
            new DetailIdValidator(Collections.emptySet()).getErrorMessage());
    }
}
