package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.api.ISettableProductFamilyProvider;

import com.vaadin.spring.annotation.UIScope;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of {@link ISettableProductFamilyProvider}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/4/19
 *
 * @author Stanislau Rudak
 */
@Component
@UIScope
public class ProductFamilyProvider implements ISettableProductFamilyProvider {

    private final AtomicReference<String> productFamilyHolder = new AtomicReference<>(FdaConstants.FAS_PRODUCT_FAMILY);

    @Override
    public void setProductFamily(String productFamily) {
        this.productFamilyHolder.set(Objects.requireNonNull(productFamily));
    }

    @Override
    public String getProductFamily() {
        return productFamilyHolder.get();
    }
}
