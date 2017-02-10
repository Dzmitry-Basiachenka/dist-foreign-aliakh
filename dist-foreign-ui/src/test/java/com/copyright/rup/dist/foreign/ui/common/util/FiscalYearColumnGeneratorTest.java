package com.copyright.rup.dist.foreign.ui.common.util;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Objects;

/**
 * Verifies {@link FiscalYearColumnGenerator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/08/2017
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({VaadinUtils.class, UsageBatchUtils.class})
public class FiscalYearColumnGeneratorTest {

    private static final String ITEM_ID = "item id";
    private static final String COLUMN_ID = "column id";
    private static final Integer EXPECTED_VALUE = 2015;
    private static final String FISCAL_YEAR = "FY2015";

    @Test
    public void testGenerateCell() {
        mockStatic(VaadinUtils.class);
        mockStatic(UsageBatchUtils.class);
        FiscalYearColumnGenerator fiscalYearColumnGenerator = new FiscalYearColumnGenerator();
        expect(VaadinUtils.getContainerPropertyValue(null, ITEM_ID, COLUMN_ID, Integer.class))
            .andReturn(EXPECTED_VALUE).once();
        expect(UsageBatchUtils.getFiscalYear(EXPECTED_VALUE)).andReturn(FISCAL_YEAR).once();
        replay(VaadinUtils.class, UsageBatchUtils.class);
        Object result = fiscalYearColumnGenerator.generateCell(null, ITEM_ID, COLUMN_ID);
        assertNotNull(result);
        assertEquals(String.class, result.getClass());
        assertEquals(FISCAL_YEAR, Objects.toString(result));
        verify(VaadinUtils.class, UsageBatchUtils.class);
    }
}
