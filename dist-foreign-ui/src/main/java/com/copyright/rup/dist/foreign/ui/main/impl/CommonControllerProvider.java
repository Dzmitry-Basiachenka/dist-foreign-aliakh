package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Contains common logic for providing {@link IController} instance based on selected product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/6/19
 *
 * @param <T> type of controller
 * @author Stanislau Rudak
 */
public abstract class CommonControllerProvider<T extends IController> implements IControllerProvider<T> {

    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    private Map<String, T> productFamilyToControllerMap;

    @Override
    public T getController() {
        if (null == productFamilyToControllerMap) {
            productFamilyToControllerMap = getProductFamilyToControllerMap();
        }
        String productFamily = productFamilyProvider.getSelectedProductFamily();
        if (productFamilyToControllerMap.containsKey(productFamily)) {
            return productFamilyToControllerMap.get(productFamily);
        } else {
            throw new IllegalStateException(
                String.format("No controller found. SelectedProductFamily=%s, RegisteredProductFamilies=%s",
                    productFamily, productFamilyToControllerMap.keySet()));
        }
    }

    /**
     * @return map of product family to {@link IController} instance.
     */
    protected abstract Map<String, T> getProductFamilyToControllerMap();
}
