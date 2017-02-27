package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteUsageBatchWindow.PaymentDateFilter;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link DeleteUsageBatchWindow.PaymentDateFilter}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/17/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(Parameterized.class)
public class PaymentDateFilterTest {

    private String searchValue;
    private boolean expectedResult;

    /**
     * Constructor.
     *
     * @param searchValue    search value
     * @param expectedResult expected result
     */
    public PaymentDateFilterTest(String searchValue, boolean expectedResult) {
        this.searchValue = searchValue;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"0", true},
            {"02", true},
            {"02/", true},
            {"02/1", true},
            {"02/17", true},
            {"02/17/", true},
            {"02/17/2", true},
            {"02/17/20", true},
            {"02/17/201", true},
            {"02/17/2017", true},
            {"2/17/2017", true},
            {"/17/2017", true},
            {"17/2017", true},
            {"7/2017", true},
            {"/2017", true},
            {"2017", true},
            {"017", true},
            {"17", true},
            {"7", true},
            {"/", true},
            {"2017/02/17", false},
            {"2017/17/02", false},
            {"2017-02-17", false},
            {"02-17-2017", false}
        });
    }

    @Test
    public void testPaymentDateFilterPassesFilter() {
        DeleteUsageBatchWindow.PaymentDateFilter filter = new PaymentDateFilter(searchValue);
        Item itemMock = createMock(Item.class);
        Property propertyMock = createMock(Property.class);
        expect(itemMock.getItemProperty("paymentDate")).andReturn(propertyMock).once();
        expect(propertyMock.getValue()).andReturn(LocalDate.of(2017, 2, 17)).once();
        replay(itemMock, propertyMock);
        assertEquals(expectedResult, filter.passesFilter(null, itemMock));
        verify(itemMock, propertyMock);
    }
}
