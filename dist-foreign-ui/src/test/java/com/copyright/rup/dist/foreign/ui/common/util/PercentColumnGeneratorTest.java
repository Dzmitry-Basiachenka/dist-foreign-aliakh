package com.copyright.rup.dist.foreign.ui.common.util;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.vaadin.ui.Table;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

/**
 * Verifies {@link PercentColumnGenerator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 14/11/2017
 *
 * @author Aliaksandra Bayanouskaya
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VaadinUtils.class)
public class PercentColumnGeneratorTest {

    private static final String ITEM_ID = "item id";
    private static final String COLUMN_ID = "column id";

    @Test
    public void testGenerateCellForBigDecimalValue() {
        Table table = new Table();
        mockStatic(VaadinUtils.class);
        PercentColumnGenerator percentColumnGenerator = new PercentColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(table, ITEM_ID, COLUMN_ID, BigDecimal.class))
            .andReturn(new BigDecimal("0.32000")).once();
        replay(VaadinUtils.class);
        Object result = percentColumnGenerator.generateCell(table, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals("32.0", result);
        verify(VaadinUtils.class);
    }

    @Test
    public void testGenerateCellForNullValue() {
        Table table = new Table();
        mockStatic(VaadinUtils.class);
        PercentColumnGenerator percentColumnGenerator = new PercentColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(table, ITEM_ID, COLUMN_ID, BigDecimal.class))
            .andReturn(null).once();
        replay(VaadinUtils.class);
        Object result = percentColumnGenerator.generateCell(table, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals(StringUtils.EMPTY, result);
        verify(VaadinUtils.class);
    }
}
