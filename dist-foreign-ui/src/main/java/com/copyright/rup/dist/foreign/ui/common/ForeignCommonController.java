package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.vaadin.widget.api.CommonController;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of {@link CommonController} that provides selected product family for subclasses.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/4/19
 *
 * @param <T> type of widget
 * @author Stanislau Rudak
 */
public abstract class ForeignCommonController<T extends IWidget> extends CommonController<T> {

    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    /**
     * @return globally selected product family.
     */
    public String getSelectedProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }
}
