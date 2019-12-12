package com.copyright.rup.dist.foreign.ui.main.impl;

import com.copyright.rup.dist.foreign.ui.main.api.IControllerProvider;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

import java.util.function.Consumer;

/**
 * Allows to display different widgets depending on provides {@link IController} instance.
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

    private final IControllerProvider<C> controllerProvider;
    private final Consumer<W> listenerRegisterer;

    /**
     * Constructor.
     *
     * @param controllerProvider instance of {@link IControllerProvider}
     * @param listenerRegisterer a {@link Consumer} that adds listeners to the widget
     */
    public SwitchableWidget(IControllerProvider<C> controllerProvider, Consumer<W> listenerRegisterer) {
        this.controllerProvider = controllerProvider;
        this.listenerRegisterer = listenerRegisterer;
        setStyleName("switchable-widget");
        setSizeFull();
    }

    /**
     * Updates widget content provided {@link IController} instance.
     */
    public void updateProductFamily() {
        C controller = controllerProvider.getController();
        W widget = controller.initWidget();
        listenerRegisterer.accept(widget);
        setContent(widget);
    }

    @Override
    public void refresh() {
        Component content = getContent();
        if (content instanceof IRefreshable) {
            ((IRefreshable) content).refresh();
        }
    }
}
