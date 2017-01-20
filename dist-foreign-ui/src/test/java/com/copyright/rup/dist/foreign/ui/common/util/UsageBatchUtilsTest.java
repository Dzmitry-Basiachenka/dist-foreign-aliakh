package com.copyright.rup.dist.foreign.ui.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

/**
 * Verifies {@link UsageBatchUtils}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/21/2017
 *
 * @author Mikita Hladkikh
 */
public class UsageBatchUtilsTest {

   private static final String FY_2017 = "FY2017";
   private static final String FY_2018 = "FY2018";

    @Test
    public void privateConstructorTest() {
        Constructor<?>[] declaredConstructors = UsageBatchUtils.class.getDeclaredConstructors();
        assertTrue(ArrayUtils.isNotEmpty(declaredConstructors));
        assertEquals(1, declaredConstructors.length);
        Constructor<?> declaredConstructor = declaredConstructors[0];
        try {
            declaredConstructor.setAccessible(true);
            declaredConstructor.newInstance();
            fail("Constructor shouldn't be called directly");
        } catch (InvocationTargetException e) {
            assertEquals("Constructor shouldn't be called directly", e.getCause().getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            fail("Constructor couldn't be invoked");
        }
    }

    @Test
    public void testGetFiscalYear() {
        assertEquals(StringUtils.EMPTY, UsageBatchUtils.getFiscalYear(null));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 1, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 2, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 3, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 4, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 5, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 6, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 6, 30)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 7, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 8, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 9, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 10, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 11, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 12, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 12, 31)));
    }
}
