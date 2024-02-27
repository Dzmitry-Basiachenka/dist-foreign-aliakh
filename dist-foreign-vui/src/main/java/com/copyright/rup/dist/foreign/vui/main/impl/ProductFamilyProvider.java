package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.scopes.VaadinUIScope;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

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
@SpringComponent
@Scope(value = VaadinUIScope.VAADIN_UI_SCOPE_NAME, proxyMode = ScopedProxyMode.INTERFACES)
public class ProductFamilyProvider implements IProductFamilyProvider, Serializable {

    private static final long serialVersionUID = 1048349420836748697L;

    //TODO {vaadin23}  should be change to ACL after migration ACL PF
    private final AtomicReference<String> selectedProductFamilyHolder =
        new AtomicReference<>(FdaConstants.FAS_PRODUCT_FAMILY);

    @Override
    public void setProductFamily(String productFamily) {
        selectedProductFamilyHolder.set(Objects.requireNonNull(productFamily));
    }

    @Override
    public String getSelectedProductFamily() {
        return selectedProductFamilyHolder.get();
    }
}
