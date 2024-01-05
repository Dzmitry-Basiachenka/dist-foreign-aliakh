package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;

import com.vaadin.spring.annotation.UIScope;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of {@link IProductFamilyProvider}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/4/19
 *
 * @author Stanislau Rudak
 */
@Component
@UIScope
public class ProductFamilyProvider implements IProductFamilyProvider, Serializable {

    private static final long serialVersionUID = 1976191657247382594L;
    private final AtomicReference<String> selectedProductFamilyHolder =
        new AtomicReference<>(FdaConstants.ACL_PRODUCT_FAMILY);

    @Override
    public void setProductFamily(String productFamily) {
        selectedProductFamilyHolder.set(Objects.requireNonNull(productFamily));
    }

    @Override
    public String getSelectedProductFamily() {
        return selectedProductFamilyHolder.get();
    }
}
