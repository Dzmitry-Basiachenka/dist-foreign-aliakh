package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.vaadin.ui.Panel;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Allows to display different widgets on {@link #refresh()} depending on provides {@link IController} instance.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @param <W> type of widget
 * @param <C> type of controller
 * @author Stanislau Rudak
 */
class SwitchableWidget<W extends IWidget<C>, C extends IController<W>> extends Panel implements IRefreshable {

    private final Supplier<C> controllerSupplier;
    private final Consumer<W> listenerRegisterer;

    /**
     * Constructor.
     *
     * @param controllerSupplier a {@link Supplier} to get a controller instance
     * @param listenerRegisterer a {@link Consumer} that adds listeners to the widget
     */
    public SwitchableWidget(Supplier<C> controllerSupplier, Consumer<W> listenerRegisterer) {
        this.controllerSupplier = controllerSupplier;
        this.listenerRegisterer = listenerRegisterer;
        setStyleName("switchable-widget");
        setSizeFull();
    }

    @Override
    public void refresh() {
        C controller = controllerSupplier.get();
        W widget = controller.initWidget();
        listenerRegisterer.accept(widget);
        setContent(widget);
        controller.refreshWidget();
    }
}
