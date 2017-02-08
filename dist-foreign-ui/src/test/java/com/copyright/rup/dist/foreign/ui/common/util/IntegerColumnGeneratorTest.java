package com.copyright.rup.dist.foreign.ui.common.util;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.VaadinUtils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Objects;

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
    private static final Integer EXPECTED_VALUE = 2015;

    @Test
    public void testGenerateCellForIntegerValue() {
        mockStatic(VaadinUtils.class);
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(null, ITEM_ID, COLUMN_ID, Integer.class))
            .andReturn(EXPECTED_VALUE).once();
        replay(VaadinUtils.class);
        Object result = integerColumnGenerator.generateCell(null, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals(String.class, result.getClass());
        assertEquals("2015", Objects.toString(result));
        verify(VaadinUtils.class);
    }

    @Test
    public void testGenerateCellForNullValue() {
        mockStatic(VaadinUtils.class);
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(null, ITEM_ID, COLUMN_ID, Integer.class))
            .andReturn(null).once();
        replay(VaadinUtils.class);
        Object result = integerColumnGenerator.generateCell(null, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals(String.class, result.getClass());
        assertEquals(StringUtils.EMPTY, Objects.toString(result));
        verify(VaadinUtils.class);
    }
}
