package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;

import java.util.Collection;

/**
 * Implement this interface to use {@link FilterWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/16/17
 *
 * @author Aliaksandr Radkevich
 * @param <T> bean type
 * @see FilterWindow
 */
public interface IFilterWindowController<T> extends IFilterSaveListener<T> {

    /**
     * @return a collection of beans of specified parameter type.
     */
    Collection<T> loadBeans();

    /**
     * @return bean class. Should not be {@code null}.
     */
    Class<T> getBeanClass();

    /**
     * Gets a caption for the given bean.
     *
     * @param bean a bean to get caption for.
     * @return a caption for the given bean.
     */
    String getBeanItemCaption(T bean);
}
