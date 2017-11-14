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

/**
 * Verifies {@link IntegerColumnGenerator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/08/2017
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VaadinUtils.class)
public class IntegerColumnGeneratorTest {

    private static final String ITEM_ID = "item id";
    private static final String COLUMN_ID = "column id";

    @Test
    public void testGenerateCellForIntegerValue() {
        Table table = new Table();
        mockStatic(VaadinUtils.class);
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(table, ITEM_ID, COLUMN_ID, Integer.class))
            .andReturn(2015).once();
        replay(VaadinUtils.class);
        Object result = integerColumnGenerator.generateCell(table, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals("2015", result);
        verify(VaadinUtils.class);
    }

    @Test
    public void testGenerateCellForNullValue() {
        Table table = new Table();
        mockStatic(VaadinUtils.class);
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(table, ITEM_ID, COLUMN_ID, Integer.class))
            .andReturn(null).once();
        replay(VaadinUtils.class);
        Object result = integerColumnGenerator.generateCell(table, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals(StringUtils.EMPTY, result);
        verify(VaadinUtils.class);
    }
}
