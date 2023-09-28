package com.copyright.rup.dist.foreign.ui.main;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.ui.main.impl.ProductFamilyProvider;

import org.junit.Test;

/**
 * Verifies {@link ProductFamilyProvider}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Stanislau Rudak
 */
public class SelectedProductFamilyProviderTest {

    private final ProductFamilyProvider provider = new ProductFamilyProvider();

    @Test
    public void testGetAndSet() {
        provider.setProductFamily("NTS");
        assertEquals("NTS", provider.getSelectedProductFamily());
    }

    @Test
    public void testGetWithDefaultValue() {
        assertEquals("ACL", provider.getSelectedProductFamily());
    }

    @Test(expected = NullPointerException.class)
    public void testSetNullValue() {
        provider.setProductFamily(null);
    }
}
